package com.apla.Actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DefaultActionGroup


class GraphActionGroup : DefaultActionGroup() {
    override fun update(event: AnActionEvent) {
        val selectedFile = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        event.presentation.isVisible = isApphostGraphFile(selectedFile);
    }
}