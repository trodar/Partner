package com.trodar.test.features.preach.items

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.trodar.common.recources.K
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class PreachTimeItem(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<PreachTimeItem>(
        semanticsProvider,
        viewBuilderAction = { hasTestTag(K.PreachTag.timeRow) }) {

    val buttonMinus: KNode = child { hasTestTag(K.PreachTag.timeButtonMinus) }
    val textTime: KNode = child { hasTestTag(K.PreachTag.timeTextTime) }
    val buttonPlus: KNode = child { hasTestTag(K.PreachTag.timeButtonPlus) }

}