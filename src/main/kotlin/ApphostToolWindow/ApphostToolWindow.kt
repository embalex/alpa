package ApphostToolWindow

import com.intellij.ide.actions.ActivateToolWindowAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.*
import com.intellij.psi.PsiFile
import java.rmi.UnexpectedException

class ApphostToolWindow(val psiFile: PsiFile) {
    private val toolWindowNamePrefix = "Apphost graph:"
    private val toolWindowName = "$toolWindowNamePrefix ${psiFile.name}"
    private val toolWindowManager = ToolWindowManager.getInstance(psiFile.project)
    public val hasToolWindow = toolWindowManager.getToolWindow(toolWindowName) != null

    private fun toolWindow(): ToolWindow {
        return toolWindowManager.getToolWindow(toolWindowName) ?: throw UnexpectedException("Not found $toolWindowName")
    }

    private fun removeToolWindows() {
        val toolWindowIds = toolWindowManager.toolWindowIds

        val apphostToolWindows = toolWindowIds.filter { name -> name.contains(toolWindowNamePrefix) }
        apphostToolWindows.forEach(fun (id) { toolWindowManager.getToolWindow(id)!!.remove() })
    }

    public fun delete() {
        toolWindow().remove()
    }

    public fun register() {
        if (hasToolWindow) {
            toolWindow().activate(null)
            return
        }

        removeToolWindows()

        val registerToolWindowTask: RegisterToolWindowTaskBuilder.() -> Unit = {
            anchor = ToolWindowAnchor.LEFT
            canCloseContent = false
        }

        val toolWindow = ToolWindowManager
            .getInstance(psiFile.project)
            .registerToolWindow(toolWindowName, registerToolWindowTask)

        toolWindow.setIcon(Icons.Collection.Watch)
        toolWindow.setType(ToolWindowType.DOCKED, null)
        val toolWindowContent = Content(toolWindow)
        val content = toolWindow.contentManager.factory.createContent(toolWindowContent.contentPanel, toolWindowName, false)
        toolWindow.contentManager.addContent(content)

        toolWindow.activate(null)
    }
}