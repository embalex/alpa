package ApphostToolWindow

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project
import com.intellij.ui.treeStructure.Tree
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent


class MouseListener(private val tree: Tree, private val project: Project) : MouseAdapter() {
    override fun mousePressed(event: MouseEvent) {
        if (event.clickCount == 2) {
            val node = tree.lastSelectedPathComponent;
            if (node !is BackendView) {
                println("Node is not BackendView")
                return
            }
            FileEditorManagerEx.getInstanceEx(project).openFile(node.virtualFile, true, false)
        }
    }
}