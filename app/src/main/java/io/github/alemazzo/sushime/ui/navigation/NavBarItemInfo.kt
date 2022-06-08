package io.github.alemazzo.sushime.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * The information about each item of a Navbar.
 */
data class NavBarItemInfo(

    /** The title of the section */
    val title: String,

    /** The id of the resource of the icon to be displayed in the navbar */
    val imageVector: ImageVector
)
