package com.isuponev.tutordb.desktop.views.forms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import dev.icerock.moko.resources.StringResource
import java.math.BigDecimal
import javax.money.CurrencyUnit
import javax.money.Monetary

@Composable
fun TextEditForm(
    value: String,
    isValid: (String) -> Boolean,
    onChangeValue: (String) -> Unit,
    errorMessageOfInputValue: String?,
    labelMessage: StringResource
) {
    val locale by AppConfig.General.locale.collectAsState()
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (isValid(it)) onChangeValue(it)
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                locale.localize(labelMessage)
            )
        },
        isError = errorMessageOfInputValue != null
    )
    if (errorMessageOfInputValue != null) {
        Text(
            errorMessageOfInputValue,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun TextEditForm(
    value: String,
    onChangeValue: (String) -> Unit,
    errorMessageOfInputValue: String?,
    labelMessage: StringResource
) = TextEditForm(
    value,
    { true },
    onChangeValue,
    errorMessageOfInputValue,
    labelMessage
)

@Composable
fun NumberEditForm(
    value: String,
    onChangeValue: (String) -> Unit,
    errorMessageOfInputValue: String?,
    labelMessage: StringResource
) = TextEditForm(
    value,
    { newValue ->
        try {
            BigDecimal(newValue)
            true
        } catch (_: NumberFormatException) {
            false
        }
    },
    onChangeValue,
    errorMessageOfInputValue,
    labelMessage
)

@Composable
fun MonetaryEditForm(
    amount: String,
    onChangeAmount: (String) -> Unit,
    errorMessageOfAmount: String?,
    currency: CurrencyUnit,
    onChangeCurrency: (CurrencyUnit) -> Unit,
) = Row {
    NumberEditForm(
        amount,
        onChangeAmount,
        errorMessageOfAmount,
        SharedResourcesjvmMain.strings.lbl_monetary_amount
    )
    var expanded by remember { mutableStateOf(false) }
    ChooseBoxForm(
        expanded,
        onExpandedChange = { expanded = !expanded },
        onDismissRequest = { expanded = false },
        currentValue = currency,
        entries = Monetary.getCurrencies(),
        onChooseElement = onChangeCurrency,
        converter = { it.currencyCode },
        modifier = Modifier
            .weight(AppDefaults.Weights.ONE),
        labelMessage = SharedResourcesjvmMain.strings.lbl_monetary_currency
    )
}

