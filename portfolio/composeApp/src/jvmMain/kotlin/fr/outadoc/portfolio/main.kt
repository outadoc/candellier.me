package fr.outadoc.portfolio

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication

fun main() {
    singleWindowApplication(
        title = "Portfolio",
        state = WindowState(size = DpSize(1500.dp, 1800.dp))
    ) {
        App()
    }
}
