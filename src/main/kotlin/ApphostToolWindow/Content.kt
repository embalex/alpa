package ApphostToolWindow

import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.treeStructure.Tree
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.*

class Content(private val toolWindow: ToolWindow, private val treeModel: ViewModel) {
    val contentPanel = JPanel()

    init {
        contentPanel.layout = BorderLayout(0, 20)
        contentPanel.border = BorderFactory.createEmptyBorder(40, 0, 20, 0)
        contentPanel.border = BorderFactory.createEmptyBorder(0, 0, 0, 0)
        contentPanel.add(createTree(), BorderLayout.LINE_START)
        contentPanel.add(createControlsPanel(), BorderLayout.PAGE_END)
    }

    private fun createTree() : JPanel {
        val panel = JPanel()
        val tree = Tree()

        tree.model = treeModel.model
        tree.cellRenderer = TreeCellRender()
        panel.add(tree)

        return panel
    }

    private fun createControlsPanel(): JPanel {
        val controlsPanel = JPanel()
        val closeToolWindowButton = JButton("Close")
        closeToolWindowButton.addActionListener(fun (e: ActionEvent?) {
            toolWindow.remove()

            val projectToolWindow = ToolWindowManager.getInstance(toolWindow.project).getToolWindow("Project") ?: return
            projectToolWindow.activate(null)
        })
        controlsPanel.add(closeToolWindowButton)

        return controlsPanel
    }
}