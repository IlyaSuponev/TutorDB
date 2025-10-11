package com.isuponev.tutordb.core.config

import ca.gosyer.appdirs.AppDirs
import com.isuponev.tutordb.core.config.general.AppLocale
import com.isuponev.tutordb.core.config.general.GeneralConfigData
import com.isuponev.tutordb.core.config.ui.ThemeMode
import com.isuponev.tutordb.core.config.ui.UIConfigData
import com.isuponev.tutordb.core.resources.SharedResources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
    private val manager = ConfigManager(appDirs)
    private val _state = MutableStateFlow<AppConfigState>(AppConfigState.Updated)
    val state: StateFlow<AppConfigState>
        get() = _state

    object UI : Applicable<UIConfigData>, Convertable<UIConfigData> {
        var themeMode: ThemeMode by Delegates.observable(ThemeMode.SYSTEM) { _, _, _ ->
            save()
        }

        override fun apply(value: UIConfigData) {
            themeMode = value.themeMode
        }

        override fun convert(): UIConfigData = UIConfigData(themeMode)
    }

    object General : Applicable<GeneralConfigData>, Convertable<GeneralConfigData> {
        var locale: AppLocale by Delegates.observable(AppLocale.getSystem()) { _, _, _ ->
            save()
        }

        override fun apply(value: GeneralConfigData) {
            locale = value.locale
        }

        override fun convert(): GeneralConfigData = GeneralConfigData(locale)
    }

    fun load() {
        if (_state.value == AppConfigState.Saving) return
        _state.value = AppConfigState.Loading
        manager.load(
            onSuccess = { data ->
                General.apply(data.general)
                UI.apply(data.ui)
                _state.value = AppConfigState.Updated
                print(_state.value)
            },
            onFailure = { error ->
                _state.value = AppConfigState.Error(error)
                print(_state.value)
            }
        )
    }

    fun save() {
        if (_state.value == AppConfigState.Loading) return
        _state.value = AppConfigState.Saving
        manager.save(
            data = ConfigData(
                GeneralConfigData(General.locale),
                UIConfigData(UI.themeMode)
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
    }

    init {
        load()
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

    private class ConfigManager(appDirs: AppDirs, private val delay: Duration = 1500.milliseconds) {
        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        private val configFile = File(appDirs.getUserConfigDir(), "config.json")
        private val fileMutex = Mutex()
        private var currentJob: Job? = null
        private val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }

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
                    println("Loading start")
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
                    println("Loading end")
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
                    println("Saving start")
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
                    println("Saving end")
                }
            }
        }

        fun dispose() = scope.cancel()
    }
}