package com.vukasinprvulovic.cryptomarket

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vukasinprvulovic.design.colors.palettes.CryptoMarketplacePalette
import com.vukasinprvulovic.design.theming.CryptoMarketTheme
import com.vukasinprvulovic.scenes.marketplace.Marketplace
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        handleStatusBarColor()

        setContent {
            CryptoMarketTheme {
                Marketplace()
            }
        }
    }

    private fun handleStatusBarColor() {
        window.statusBarColor = getColor(R.color.gray)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
