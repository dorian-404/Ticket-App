package com.example.ticketapp.components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

 class TicketShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(Path().apply {
            val width = size.width
            val height = size.height
            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width, height - 40f)
            cubicTo(width - 20f, height - 40f, width - 20f, height, width - 40f, height)
            cubicTo(width - 20f, height, 20f, height, 40f, height - 40f)
            lineTo(0f, height - 40f)
            close()
        })
    }
}