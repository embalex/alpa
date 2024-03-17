package com.apla.Actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.pom.Navigatable

class GraphViewAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val currentProject: Project? = event.project
        val message: StringBuilder = StringBuilder(event.presentation.text + " Selected!")
        // If an element is selected in the editor, add info about it.
        val selectedElement: Navigatable? = event.getData(CommonDataKeys.NAVIGATABLE)
        if (selectedElement != null) {
            message.append("\nSelected Element: ").append(selectedElement)
        }
        val title: String = event.presentation.description
        Messages.showMessageDialog(
                currentProject,
                message.toString(),
                title,
                Messages.getInformationIcon())
    }
}