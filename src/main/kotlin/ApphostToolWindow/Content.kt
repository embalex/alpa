package ApphostToolWindow

import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.util.*
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class Content(private val toolWindow: ToolWindow) {
    val contentPanel = JPanel()

    init {
        contentPanel.layout = BorderLayout(0, 20)
        contentPanel.border = BorderFactory.createEmptyBorder(40, 0, 20, 0)
        contentPanel.add(createControlsPanel(), BorderLayout.PAGE_END)
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