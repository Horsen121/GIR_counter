package com.example.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TitleText(
    @StringRes text: Int,
    modifier: Modifier = Modifier.fillMaxSize(),
    color: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.titleLarge,
        color = color,
        textAlign = textAlign,
        modifier = modifier
    )
}

@Composable
fun LabelMediumText(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.labelMedium,
        color = color,
        textAlign = textAlign,
        modifier = modifier
    )
}

@Composable
fun BodyLargeText(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Unspecified
) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        textAlign = textAlign,
        modifier = modifier
    )
}

@Composable
fun BodyMediumText(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Unspecified
) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        textAlign = textAlign,
        modifier = modifier
    )
}
@Composable
fun BodyMediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Unspecified
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        textAlign = textAlign,
        modifier = modifier
    )
}