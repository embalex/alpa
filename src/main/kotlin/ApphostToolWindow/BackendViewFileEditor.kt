package ApphostToolWindow

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import java.beans.PropertyChangeListener
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class BackendViewFileEditor(private val project: Project, private val file: VirtualFile) : FileEditor {
    private val formPanel: JPanel

    init {
        formPanel = JPanel()
        formPanel.add(JTextField("Hello!"))
    }

    override fun <T : Any?> getUserData(key: Key<T>): T? {
        return file.getUserData(key)
    }

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) {
        file.putUserData(key, value)
    }

    override fun dispose() {}

    override fun getComponent(): JComponent {
        return formPanel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return formPanel
    }

    override fun getName(): String {
        return "Unknown name"
    }

    override fun setState(state: FileEditorState) {
        println("Set state")
    }

    override fun isModified(): Boolean {
        return false
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {
    }

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
    }

    override fun getCurrentLocation(): FileEditorLocation? {
        return null
    }

    override fun getFile(): VirtualFile {
        return file
    }
}