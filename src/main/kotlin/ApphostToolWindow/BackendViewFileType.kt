package ApphostToolWindow

import com.intellij.openapi.fileTypes.ex.FakeFileType
import com.intellij.openapi.vfs.VirtualFile

class BackendViewFileType: FakeFileType() {
    companion object {
        val INSTANCE: BackendViewFileType
            get() { return BackendViewFileType() }
    }

    override fun getName(): String {
        return "Backend view file type"
    }

    override fun getDescription(): String {
        return "Backend node type"
    }

    override fun isMyFileType(file: VirtualFile): Boolean {
        return file is BackendViewVirtualFile
    }
}