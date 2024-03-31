package ApphostToolWindow

import Graph.BackendNode
import Graph.BackendNodeTypeData
import javax.swing.Icon
import javax.swing.tree.DefaultMutableTreeNode


class BackendView(private val backend: BackendNode) : DefaultMutableTreeNode() {
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
}