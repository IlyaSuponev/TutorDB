package com.isuponev.tutordb.desktop.utils

import java.util.Locale
import javax.money.CurrencyUnit
import javax.money.Monetary

fun getSystemCurrency(): CurrencyUnit {
    return Monetary.getCurrency(Locale.getDefault())
}