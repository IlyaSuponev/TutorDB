package com.isuponev.tutordb.core.config.general

import kotlinx.serialization.Serializable

@Serializable
data class GeneralConfigData(
    val locale: AppLocale = AppLocale.getSystem()
)
