package com.apla.Actions

import com.intellij.openapi.vfs.VirtualFile

fun isApphostGraphFile(file: VirtualFile): Boolean {
    val APPHOST_GRAPH_PATH = "apphost/conf/verticals"
    val APPHOST_GRAPH_EXTENSION = "json"

    return file.path.contains(APPHOST_GRAPH_PATH) && file.extension == APPHOST_GRAPH_EXTENSION
}
