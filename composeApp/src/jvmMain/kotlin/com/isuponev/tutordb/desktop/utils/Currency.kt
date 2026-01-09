package com.isuponev.tutordb.desktop.utils

import com.isuponev.tutordb.core.config.AppConfig
import java.util.Locale
import javax.money.CurrencyUnit
import javax.money.Monetary

fun getSystemCurrency(): CurrencyUnit {
    return Monetary.getCurrency(AppConfig.General.locale.value.currencyCode)
}
