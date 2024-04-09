package ApphostToolWindow

import Graph.BackendNode
import com.intellij.openapi.fileEditor.impl.FileEditorManagerImpl
import com.intellij.testFramework.LightVirtualFile

class BackendViewVirtualFile(val backend: BackendNode) : LightVirtualFile(backend.name, BackendViewFileType.INSTANCE, "") {
    init {
        isWritable = false
        putUserData(FileEditorManagerImpl.FORBID_PREVIEW_TAB, true)
    }
}