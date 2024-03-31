package ApphostToolWindow

import Graph.BackendNode
import Graph.BackendNodeTypeData
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon
import javax.swing.tree.DefaultMutableTreeNode


class BackendView(private val backend: BackendNode) : DefaultMutableTreeNode() {
    private val privateVirtualFile = BackendViewVirtualFile(backend)

    val name
        get(): String {
            return backend.name
        }

    val icon
        get(): Icon = when (backend.type) {
            is BackendNodeTypeData.SubgraphData -> Icons.Collection.Graph
            is BackendNodeTypeData.BackendData -> Icons.Collection.Worker
            is BackendNodeTypeData.RRData -> Icons.Collection.Team
            is BackendNodeTypeData.TransparentData -> Icons.Collection.Ghost
            else -> Icons.Collection.QuestionMark
        }

    val virtualFile
        get(): VirtualFile {
            return privateVirtualFile
        }
}