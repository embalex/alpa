package com.apla.Actions

import com.apla.Graph.Deserializer as GraphDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiFileFactory
import com.apla.Graph.SourceData as GraphSourceData

class VirtualStructureAction() : AnAction() {
    val gson = GsonBuilder()
        .registerTypeAdapter(GraphSourceData.NodeData::class.java, GraphDeserializer(GsonBuilder().create()))
        .create()


    override fun actionPerformed(event: AnActionEvent) {
        val selectedFile = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        if (!isApphostGraphFile(selectedFile)) {
            return;
        }

        // Read the source file
        val jsonString = VfsUtil.loadText(selectedFile);

        val data = gson.fromJson(jsonString, GraphSourceData::class.java)
        println("Parsed to $data")
    }
}