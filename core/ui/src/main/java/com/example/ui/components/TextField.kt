package com.example.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TextField

@Composable
fun SimpleTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit) = {},
    isError: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.length <= maxLength)
                onValueChange(it)
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        readOnly = readOnly,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
        shape = MaterialTheme.shapes.medium,
        isError = isError,
        supportingText = {
            if (maxLength != Int.MAX_VALUE)
                "${value.length} / $maxLength"
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedBorderColor = if (value.length <= maxLength) MaterialTheme.colorScheme.primary else Color.Red,
            unfocusedBorderColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = TextField,
            unfocusedContainerColor = TextField,
        ),
        modifier = modifier.fillMaxWidth()
    )
}