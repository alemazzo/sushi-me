package io.github.alemazzo.sushime.ui.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * The Alias of a Screen of the application.
 * Each screen must accept a navigation controller and
 * the padding values in order to not hide the bottom-bar.
 */
typealias Screen = @Composable (navController: NavHostController, padding: PaddingValues, arguments: Bundle?) -> Unit
