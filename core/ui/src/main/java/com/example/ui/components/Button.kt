package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    shape: Shape = Shapes().medium,
    enabled: Boolean = true,
    icon: @Composable (() -> Unit) = {}
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .height(61.dp),
        enabled = enabled
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().then(rowModifier)
        ) {
            BodyMediumText(
                text = text,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = textModifier
            )
            icon()
        }
    }
}