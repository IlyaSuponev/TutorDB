package com.isuponev.tutordb.core.utils

import java.text.DecimalFormat
import java.util.Locale
import javax.money.MonetaryAmount
import javax.money.format.MonetaryFormats

fun MonetaryAmount.toMonetaryString(locale: Locale): String {
    if (!MonetaryFormats.isAvailable(locale)) return toString()
    val numberFormatter = DecimalFormat.getInstance(locale)
    return "${numberFormatter.format(number)} ${currency.currencyCode}"
}