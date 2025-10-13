package com.isuponev.tutordb.core.config

import ca.gosyer.appdirs.AppDirs
import co.touchlab.kermit.Logger
import com.isuponev.tutordb.core.config.general.AppLocale
import com.isuponev.tutordb.core.config.general.GeneralConfigData
import com.isuponev.tutordb.core.config.ui.ThemeMode
import com.isuponev.tutordb.core.config.ui.UIConfigData
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
import kotlin.properties.Delegates
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object AppConfig : Closeable {
    private val appDirs = AppDirs {
        appName = SharedResources.strings.appName.localized()
        appAuthor = SharedResources.strings.appAuthor.localized()
    }
    private val _state = MutableStateFlow<AppConfigState>(AppConfigState.Updated)
    val state: StateFlow<AppConfigState>
        get() = _state

    val logger = Logger(
        appLoggerConfig(appDirs),
        tag = SharedResources.strings.appName.localized()
    )

    private val manager = ConfigManager(appDirs, logger = logger)

    object UI : Applicable<UIConfigData>, ConvertableTo<UIConfigData> {
        private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
        val themeMode: StateFlow<ThemeMode> = _themeMode

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

    object General : Applicable<GeneralConfigData>, ConvertableTo<GeneralConfigData> {
        var locale: AppLocale by Delegates.observable(AppLocale.getSystem()) { _, _, _ ->
            save()
        }

        override fun apply(value: GeneralConfigData) {
            locale = value.locale
        }

        override fun convert(): GeneralConfigData = GeneralConfigData(locale)
    }

    init {
        load()
    }

    fun load() {
        if (_state.value == AppConfigState.Saving) return
        _state.value = AppConfigState.Loading
        manager.load(
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

    @Serializable
    private data class ConfigData(
        val general: GeneralConfigData = GeneralConfigData(),
        val ui: UIConfigData = UIConfigData(),
    )

    sealed class AppConfigState {
        object Loading : AppConfigState()
        object Saving : AppConfigState()
        object Updated : AppConfigState()
        data class Error<T: Throwable>(val cause: T) : AppConfigState()
    }

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
        val delay: Duration = 400.milliseconds
        val delayOnClose: Duration = 200.milliseconds

        init {
            configFile.parentFile?.mkdirs()
        }

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
