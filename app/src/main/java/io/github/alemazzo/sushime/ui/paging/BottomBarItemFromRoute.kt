package io.github.alemazzo.sushime.ui.paging

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.github.alemazzo.sushime.ui.navigation.navbar.NavBarItemInfo

@Composable
fun RowScope.BottomBarItemFromRoute(
    element: NavBarItemInfo,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        label = { Text(element.title) },
        icon = {
            Icon(
                imageVector = element.imageVector,
                contentDescription = element.title
            )
        }
    )
}
