package com.trodar.navigation

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.trodar.common.recources.K
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class BottomNavigation(semanticsProvides: SemanticsNodeInteractionsProvider) :
    ComposeScreen<BottomNavigation>(
        semanticsProvider = semanticsProvides,
        viewBuilderAction = { hasTestTag(K.NavigationTag.animated_navigation_bar) }
    ) {
        val bottomPreachColumn: KNode = child { hasTestTag(K.NavigationTag.navigation_bar_item_column[0]) }
        val bottomTerritoryColumn: KNode = child { hasTestTag(K.NavigationTag.navigation_bar_item_column[1]) }
        val bottomNotepadColumn: KNode = child { hasTestTag(K.NavigationTag.navigation_bar_item_column[2]) }
        val bottomReportColumn: KNode = child { hasTestTag(K.NavigationTag.navigation_bar_item_column[3]) }
}