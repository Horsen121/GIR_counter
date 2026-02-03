package com.example.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TextField

@Composable
fun CoordinateTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit) = {},
    isError: Boolean = false,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = {newValue ->
            val hasOnlyValidChars = newValue.replace(Regex("[0-9.]"), "").isEmpty()
            val hasOneDot = newValue.count { it == '.' } <= 1
            if (hasOnlyValidChars && hasOneDot) {
                onValueChange(newValue)
            }
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        readOnly = readOnly,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.medium,
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            unfocusedBorderColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = TextField,
            unfocusedContainerColor = TextField,
        ),
        modifier = modifier
    )
}