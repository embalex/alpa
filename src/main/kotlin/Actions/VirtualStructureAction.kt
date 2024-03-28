package com.apla.Actions

import ApphostToolWindow.ApphostToolWindow
import Graph.Data
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.vfs.VfsUtil


class VirtualStructureAction() : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val selectedPsiFile = event.getData(CommonDataKeys.PSI_FILE) ?: return
        val selectedVirtualFile = selectedPsiFile.virtualFile;

        if (!isApphostGraphFile(selectedVirtualFile)) {
            return;
        }

        val jsonString = VfsUtil.loadText(selectedVirtualFile);
        val graphData = Data(jsonString)

        ApphostToolWindow(selectedPsiFile.name, selectedPsiFile.project, graphData).register()
    }
}