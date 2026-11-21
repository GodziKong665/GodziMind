package com.godzistudio.godzimind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.godzistudio.godzimind.ui.GodziMindApp
import com.godzistudio.godzimind.ui.theme.GodziMindTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GodziMindTheme {
                GodziMindApp()
            }
        }
    }
}
