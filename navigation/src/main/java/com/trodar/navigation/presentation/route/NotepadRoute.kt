package com.trodar.navigation.presentation.route

sealed class NotepadRoute(val route: String) {
    data object List: NotepadRoute(LIST)
    data object Edit: NotepadRoute("$EDIT?$ID={id}") {
        fun passId(id: Int = 0): String {
            return "$EDIT?$ID=$id"
        }
    }

    companion object {
        const val LIST = "notepad_list"
        const val EDIT = "notepad_edit"
        const val ID = "id"
    }
}