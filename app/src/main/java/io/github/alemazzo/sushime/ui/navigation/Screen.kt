package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


typealias Screen = @Composable (navController: NavHostController, padding: PaddingValues) -> Unit
