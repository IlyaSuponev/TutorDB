package com.isuponev.tutordb.desktop.views.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.views.AppDefaults
import dev.icerock.moko.resources.StringResource

@Composable
fun DialogButtons(
    acceptMessage: StringResource,
    cancelMessage: StringResource,
    onClickAccept: () -> Unit,
    onClickCancel: () -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.MEDIUM),
    verticalAlignment = Alignment.CenterVertically
) {
    Spacer(Modifier.weight(AppDefaults.Weights.ONE))
    val locale by AppConfig.General.locale.collectAsState()
    Button(
        onClick = onClickCancel
    ) {
        Text(
            locale.localize(cancelMessage)
        )
    }
    Button(
        onClick = onClickAccept
    ) {
        Text(
            locale.localize(acceptMessage)
        )
    }
}