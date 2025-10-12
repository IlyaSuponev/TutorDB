package com.isuponev.tutordb.core.config.general

import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
enum class AppLocale(val locale: Locale) {
    ENGLISH(Locale.ENGLISH),
    RUSSIAN(Locale.of("ru"));

    companion object {
        fun getSystem(): AppLocale {
            var locale = ENGLISH
            val default = Locale.getDefault()
            entries.forEach { entry ->
                if (entry.locale.equals(default)) locale = entry
            }
            return locale
        }
    }
}
