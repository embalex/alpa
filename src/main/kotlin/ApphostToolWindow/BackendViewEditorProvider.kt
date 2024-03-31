package ApphostToolWindow

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.rmi.UnexpectedException

class BackendViewEditorProvider: FileEditorProvider, DumbAware {
    override fun accept(project: Project, file: VirtualFile): Boolean {
        return file is BackendViewVirtualFile
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        if (file !is BackendViewVirtualFile) {
            throw UnexpectedException("File should be instance of BackendViewVirtualFile")
        }
        return BackendViewFileEditor(project, file)
    }

    override fun getEditorTypeId(): String {
        return "backend-view-editor"
    }

    override fun getPolicy(): FileEditorPolicy {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR
    }
}