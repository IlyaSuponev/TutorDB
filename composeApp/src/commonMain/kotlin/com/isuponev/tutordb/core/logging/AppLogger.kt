package com.isuponev.tutordb.core.logging

import co.touchlab.kermit.Logger
import com.isuponev.tutordb.core.config.AppConfig

val appLogger: Logger
    get() = AppConfig.logger
