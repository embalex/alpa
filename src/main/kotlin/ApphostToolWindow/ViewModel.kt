package ApphostToolWindow

import Graph.Data as GraphData
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

class ViewModel (
    graphData: GraphData
) {
    val model: DefaultTreeModel

    init {
        val backendsNode = DefaultMutableTreeNode("Backends")
        graphData.nodeList.forEach(fun (backend) {
            backendsNode.add(BackendView(backend))
        })

        model = DefaultTreeModel(backendsNode)
    }
}