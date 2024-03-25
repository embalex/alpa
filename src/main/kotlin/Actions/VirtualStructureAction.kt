package com.apla.Actions

import ApphostToolWindow.ApphostToolWindow
import com.apla.Graph.Deserializer as GraphDeserializer
import com.google.gson.GsonBuilder
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.apla.Graph.SourceData as GraphSourceData


class VirtualStructureAction() : AnAction() {
    val gson = GsonBuilder()
        .registerTypeAdapter(GraphSourceData.NodeData::class.java, GraphDeserializer(GsonBuilder().create()))
        .create()


    override fun actionPerformed(event: AnActionEvent) {
        val selectedPsiFile = event.getData(CommonDataKeys.PSI_FILE) ?: return
        val selectedVirtualFile = selectedPsiFile.virtualFile;

        if (!isApphostGraphFile(selectedVirtualFile)) {
            return;
        }

        ApphostToolWindow(selectedPsiFile).register()
    }
}