package com.mirea.city

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mirea.city.presentation.theme.CityAppTheme
import com.mirea.city.presentation.viewmodel.CityAppViewModel

class MainActivity : ComponentActivity() {
    private val favoritesViewModel: CityAppViewModel by viewModels()

    companion object {
        const val TAG = "MainActivity" // единый тег
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            CityAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CityApp(
                        windowSize = windowSizeClass.widthSizeClass,
                        cityAppViewModel = favoritesViewModel
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Compact")
@Composable
fun CityAppCompactPreview() {
    CityAppTheme {
        CityApp(
            windowSize = WindowWidthSizeClass.Compact,
            cityAppViewModel = CityAppViewModel.preview()
        )
    }
}

@Preview(showBackground = true, widthDp = 700, name = "Medium")
@Composable
fun CityAppMediumPreview() {
    CityAppTheme {
        CityApp(
            windowSize = WindowWidthSizeClass.Medium,
            cityAppViewModel = CityAppViewModel.preview()
        )
    }
}

@Preview(showBackground = true, widthDp = 1000, name = "Expanded")
@Composable
fun CityAppExpandedPreview() {
    CityAppTheme {
        CityApp(
            windowSize = WindowWidthSizeClass.Expanded,
            cityAppViewModel = CityAppViewModel.preview()
        )
    }
}