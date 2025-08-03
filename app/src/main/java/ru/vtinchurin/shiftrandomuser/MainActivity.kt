package ru.vtinchurin.shiftrandomuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.vtinchurin.shiftrandomuser.core.navigation.AppNavHost
import ru.vtinchurin.shiftrandomuser.ui.theme.ShiftRandomUserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShiftRandomUserTheme {
                AppNavHost()
            }
        }
    }
}