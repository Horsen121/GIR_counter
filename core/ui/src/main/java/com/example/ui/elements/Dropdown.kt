package com.example.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.ui.components.TextFieldButton

@Composable
fun BaseDropdownMenu(
    title: String,
    data: List<String>,
    onValueChange: (String) -> Unit,
    isDropped: Boolean,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 256.dp)
    ) {
        TextFieldButton(
            text = title,
            onClick = { onDismiss() },
            shape = if (!isDropped) Shapes().medium else RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            icon = {
                Icon(
                    imageVector = if (isDropped) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    tint = Color.Black,
                    contentDescription = null
                )
            }
        )

        if (isDropped) {
            LazyColumn {
                items(data) { el ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextFieldButton(
                            text = el,
                            onClick = {
                                onValueChange(el)
                                onDismiss()
                            },
                            shape = RectangleShape,
                            textModifier = if (title != el) Modifier else Modifier
                                .offset(x = (-8).dp),
                            rowModifier = if (title != el) Modifier else Modifier
                                .drawBehind {
                                    drawRect(
                                        color = Color.Black,
                                        topLeft = Offset(-24.dp.toPx(), -16.dp.toPx()),
                                        size = androidx.compose.ui.geometry.Size(
                                            8.dp.toPx(),
                                            61.dp.toPx()
                                        )
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}