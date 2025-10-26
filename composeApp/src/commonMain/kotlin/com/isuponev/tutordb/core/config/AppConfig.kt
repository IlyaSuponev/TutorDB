package com.isuponev.tutordb.core.config

import ca.gosyer.appdirs.AppDirs
import co.touchlab.kermit.Logger
import com.isuponev.tutordb.core.config.AppConfig.General.setLocale
import com.isuponev.tutordb.core.config.AppConfig.state
import com.isuponev.tutordb.core.config.general.AppLocale
import com.isuponev.tutordb.core.config.general.GeneralConfigData
import com.isuponev.tutordb.core.config.ui.ThemeMode
import com.isuponev.tutordb.core.config.ui.UIConfigData
import com.isuponev.tutordb.core.interfaces.Applicable
import com.isuponev.tutordb.core.interfaces.ConvertableTo
import com.isuponev.tutordb.core.logging.appLoggerClose
import com.isuponev.tutordb.core.logging.appLoggerConfig
import com.isuponev.tutordb.core.resources.SharedResources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.Closeable
import java.io.File
import java.io.IOException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Central configuration management object for the TutorDB application.
 *
 * AppConfig serves as the single source of truth for all application settings,
 * providing a unified interface for managing, persisting, and observing configuration
 * changes across the entire application.
 *
 * Key features:
 * - **Unified Configuration**: Combines UI, general, and future configuration domains
 * - **Reactive State Management**: Uses StateFlow for real-time configuration updates
 * - **Automatic Persistence**: Automatically saves changes to disk with debouncing
 * - **Thread-Safe Operations**: Protects file operations with mutex and coroutines
 * - **Lifecycle Management**: Proper resource cleanup on application shutdown
 * - **Error Handling**: Comprehensive error states for robust configuration management
 *
 * The configuration is organized into domain-specific objects:
 * - [UI] - User interface settings (themes, appearance)
 * - [General] - General application settings (locale, behavior)
 *
 * @see UIConfigData
 * @see GeneralConfigData
 * @see AppConfigState
 * @see Closeable
 */
object AppConfig : Closeable {
    val appDirs = AppDirs {
        appName = SharedResources.strings.appName.localized()
        appAuthor = SharedResources.strings.appAuthor.localized()
    }
    private val _state = MutableStateFlow<AppConfigState>(AppConfigState.Updated)

    /**
     * Public state flow representing the current configuration operation state.
     *
     * Use this to observe loading, saving, and error states throughout the application.
     * The state updates automatically during all configuration operations.
     *
     * @see AppConfigState
     */
    val state: StateFlow<AppConfigState>
        get() = _state

    /**
     * Application logger instance configured with platform-specific settings.
     *
     * This logger is pre-configured with the appropriate log writers for the current
     * platform and uses the application name as the default tag.
     */
    val logger = Logger(
        appLoggerConfig(appDirs),
        tag = SharedResources.strings.appName.localized()
    )

    private val manager = ConfigManager(appDirs, logger = logger)

    /**
     * User Interface configuration domain object.
     *
     * Provides reactive access to UI-related settings and implements both
     * [Applicable] and [ConvertableTo] interfaces for seamless integration
     * with the configuration management system.
     *
     * @see ThemeMode
     * @see UIConfigData
     * @see Applicable
     * @see ConvertableTo
     */
    object UI : Applicable<UIConfigData>, ConvertableTo<UIConfigData> {
        private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)

        /**
         * Public state flow for observing theme mode changes.
         *
         * Emits the current theme mode and updates automatically when the theme changes.
         * Changes to this value are automatically persisted to disk.
         */
        val themeMode: StateFlow<ThemeMode>
            get() = _themeMode

        /**
         * Changes the application theme mode and triggers automatic persistence.
         *
         * @param mode The new theme mode to apply.
         */
        fun setThemeMode(mode: ThemeMode) {
            logger.d { "Switch theme mode from ${themeMode.value} to $mode" }
            _themeMode.value = mode
            save()
        }

        override fun apply(value: UIConfigData) {
            _themeMode.value = value.themeMode
        }

        override fun convert(): UIConfigData = UIConfigData(themeMode.value)
    }

    /**
     * General application configuration domain object.
     *
     * Provides access to general application settings and implements both
     * [Applicable] and [ConvertableTo] interfaces for configuration management.
     *
     * @see AppLocale
     * @see GeneralConfigData
     * @see Applicable
     * @see ConvertableTo
     */
    object General : Applicable<GeneralConfigData>, ConvertableTo<GeneralConfigData> {
        private val _locale = MutableStateFlow(AppLocale.getSystem())
        /**
         * Public state flow for observing application locale.
         *
         * Changes to this property are automatically persisted to disk through
         * the observable delegate. The default value is the system-detected locale.
         *
         * To change this property use [setLocale].
         *
         * @see AppLocale.getSystem
         */
        val locale: StateFlow<AppLocale>
            get() = _locale

        /**
         * Changes the application locale and triggers automatic persistence.
         *
         * @param locale The new locale to apply.
         */
        fun setLocale(locale: AppLocale) {
            logger.d { "Switch app locale from ${_locale.value} to $locale" }
            _locale.value = locale
            save()
        }

        override fun apply(value: GeneralConfigData) {
            _locale.value = value.locale
        }

        override fun convert(): GeneralConfigData = GeneralConfigData(locale.value)
    }

    object Platform

    init {
        load()
    }

    /**
     * Loads configuration from persistent storage.
     *
     * This method attempts to read the configuration file and apply the settings
     * to the respective domain objects. If the file doesn't exist or contains
     * invalid data, default values are used.
     *
     * The state flow is updated to reflect loading progress and any errors.
     *
     * @see AppConfigState.Loading
     * @see AppConfigState.Error
     * @see AppConfigState.Updated
     */
    fun load() {
        if (_state.value == AppConfigState.Saving) return
        _state.value = AppConfigState.Loading
        manager.run {
            load(
                onSuccess = { data ->
                    General.apply(data.general)
                    UI.apply(data.ui)
                    _state.value = AppConfigState.Updated
                },
                onFailure = { error ->
                    _state.value = AppConfigState.Error(error)
                }
            )
        }
    }

    /**
     * Saves the current configuration to persistent storage.
     *
     * This method collects the current settings from all domain objects and
     * writes them to the configuration file. The operation is debounced and
     * thread-safe.
     *
     * The state flow is updated to reflect saving progress and any errors.
     *
     * @see AppConfigState.Saving
     * @see AppConfigState.Error
     * @see AppConfigState.Updated
     */
    fun save() {
        if (_state.value == AppConfigState.Loading) return
        _state.value = AppConfigState.Saving
        manager.save(
            data = ConfigData(
                General.convert(),
                UI.convert()
            ),
            onSuccess = {
                _state.value = AppConfigState.Updated
            },
            onFailure = { error ->
                _state.value = AppConfigState.Error(error)
            }
        )
    }

    override fun close() {
        manager.dispose()
        appLoggerClose()
    }

    /**
     * Internal data class representing the complete configuration state for serialization.
     *
     * @property general The general application configuration.
     * @property ui The user interface configuration.
     */
    @Serializable
    private data class ConfigData(
        val general: GeneralConfigData = GeneralConfigData(),
        val ui: UIConfigData = UIConfigData(),
    )

    /**
     * Sealed class representing possible states of configuration operations.
     *
     * Used by [state] flow to notify observers about configuration lifecycle events.
     */
    sealed class AppConfigState {
        /**
         * Configuration is currently being loaded from persistent storage.
         */
        object Loading : AppConfigState()

        /**
         * Configuration is currently being saved to persistent storage.
         */
        object Saving : AppConfigState()

        /**
         * Configuration is up-to-date and no operations are in progress.
         */
        object Updated : AppConfigState()

        /**
         * An error occurred during configuration operations.
         *
         * @param T type of error cause
         * @property cause The exception that caused the error.
         */
        data class Error<T: Throwable>(val cause: T) : AppConfigState()
    }

    /**
     * Internal configuration manager handling file operations with proper synchronization.
     *
     * This class manages all file I/O operations for configuration persistence,
     * providing thread-safe loading and saving with debouncing and error handling.
     *
     * @param appDirs Application directories provider for file path resolution.
     * @property logger Optional logger for operation tracing.
     */
    private class ConfigManager(
        appDirs: AppDirs,
        val logger: Logger? = null
    ) {
        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        private val configFile = File(appDirs.getUserConfigDir(), "config.json")
        private val fileMutex = Mutex()
        private var currentJob: Job? = null
        private val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        private val delay: Duration = 400.milliseconds
        private val delayOnClose: Duration = 200.milliseconds

        init {
            configFile.parentFile?.mkdirs()
        }

        /**
         * Loads configuration from file asynchronously.
         *
         * @param onSuccess Callback invoked with loaded configuration data.
         * @param onFailure Callback invoked when loading fails.
         */
        fun load(
            onSuccess: (ConfigData) -> Unit,
            onFailure: (Throwable) -> Unit
        ) {
            currentJob?.cancel()
            currentJob = scope.launch {
                fileMutex.withLock {
                    logger?.i { "Start loading config" }
                    delay(delay)
                    try {
                        if (configFile.exists()) {
                            val content = configFile.readText()
                            val configData = json.decodeFromString<ConfigData>(content)
                            onSuccess(configData)
                        } else {
                            onSuccess(ConfigData())
                        }
                    } catch (e: SerializationException) {
                        onFailure(e)
                    } catch (e: IllegalArgumentException) {
                        onFailure(e)
                    } catch (e: IOException) {
                        onFailure(e)
                    }
                    logger?.i { "Finish loading config" }
                }
            }
        }

        /**
         * Saves configuration to file asynchronously.
         *
         * @param data Configuration data to save.
         * @param onSuccess Callback invoked when saving completes successfully.
         * @param onFailure Callback invoked when saving fails.
         */
        fun save(
            data: ConfigData,
            onSuccess: () -> Unit,
            onFailure: (Throwable) -> Unit
        ) {
            currentJob?.cancel()
            currentJob = scope.launch {
                fileMutex.withLock {
                    logger?.i { "Start saving config" }
                    delay(delay)
                    try {
                        val data = json.encodeToString(data)
                        configFile.writeText(data)
                        onSuccess()
                    } catch (e: SerializationException) {
                        onFailure(e)
                    } catch (e: IOException) {
                        onFailure(e)
                    }
                    logger?.i { "Finish saving config" }
                }
            }
        }

        /**
         * Disposes the configuration manager and releases all resources.
         *
         * Cancels pending operations and waits for completion with timeout.
         */
        fun dispose() {
            runBlocking {
                val job = currentJob
                job?.cancel()
                withTimeoutOrNull(delayOnClose) {
                    job?.join()
                }
                scope.cancel()
            }
        }
    }
}
