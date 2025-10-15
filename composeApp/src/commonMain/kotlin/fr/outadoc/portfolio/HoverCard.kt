package fr.outadoc.portfolio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

@Composable
fun Modifier.hoverCard(factor: Float = 20f, shape: Shape): Modifier {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    var isInside by remember { mutableStateOf(false) }

    val offsetX by animateFloatAsState(offset.x)
    val offsetY by animateFloatAsState(offset.y)

    val glareColor: Color by animateColorAsState(
        animationSpec = tween(durationMillis = 100),
        targetValue = if (isInside) {
            Color.White
        } else {
            Color.Transparent
        },
    )

    return this
        .onGloballyPositioned { coordinates -> size = coordinates.size }
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event: PointerEvent = awaitPointerEvent()
                    when (event.type) {
                        PointerEventType.Move -> {
                            val off = event.changes.first().position
                            if (isInside) {
                                offset =
                                    Offset(
                                        x = ((off.x - (size.width / 2)) / size.width) * 2,
                                        y = ((off.y - (size.height / 2)) / size.height) * 2,
                                    )
                            }
                        }

                        PointerEventType.Enter -> {
                            isInside = true
                        }

                        PointerEventType.Exit,
                            -> {
                            isInside = false
                            offset = Offset.Zero
                        }
                    }
                }
            }
        }
        .graphicsLayer {
            rotationX = -offsetY * factor
            rotationY = offsetX * factor
        }
        .clip(shape)
        .drawWithCache {
            val shine = Brush.linearGradient(
                listOf(
                    Color(255, 255, 255, 38), // Simulates brightness
                    Color(0, 0, 0, 64), // Simulates contrast
                    Color(128, 128, 128, 52), // Simulates saturation
                ),
            )

            val glare = Brush.radialGradient(
                colors = listOf(
                    glareColor.copy(alpha = 0.1f),
                    glareColor.copy(alpha = 0.05f),
                    Color.Transparent
                ),
                center = Offset(
                    x = (-offsetX * factor * 10) + (size.width / 2),
                    y = (-offsetY * factor * 10) + (size.height / 2),
                )
            )

            onDrawWithContent {
                drawContent()
                drawRect(shine)
                drawRect(glare)
            }
        }
}
