package ApphostToolWindow

import Graph.BackendNodeTypeData
import com.apla.Graph.SourceData
import com.intellij.ide.wizard.withVisualPadding
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import java.beans.PropertyChangeListener
import java.rmi.UnexpectedException

class BackendViewFileEditor(private val project: Project, private val file: BackendViewVirtualFile) : FileEditor {
    private val formPanel: DialogPanel
    private val backendModel: BackendModel
    private val components = BackendViewFileEditorComponents()

    init {
        backendModel = when (file.backend.type) {
            is BackendNodeTypeData.BackendData -> BackendModel(
                backendName = file.backend.type.backendName,
                maxAttempts = file.backend.type.maxAttempts,
                abcService =
                    file.backend.type.abcService.map( fun (abcService): ABCService {
                        return ABCService(
                            dutySlugs = abcService.dutySlugs.toMutableList(),
                            slug = abcService.slug,
                        )
                    }).toMutableList(),
                messengerChatNames = file.backend.type.messengerChatNames.toMutableList(),
                timeout = file.backend.type.timeout,
            )
            else -> throw UnexpectedException("Backend type ${file.backend.type} is unsupported")
        }


        formPanel = when (file.backend.type) {
            is BackendNodeTypeData.BackendData -> panel {
                components.backendName(this, backendModel::backendName)
                components.maxAttempts(this, backendModel::maxAttempts)
                components.abcService(this, backendModel.abcService)
                components.messengerChatNames(this, backendModel.messengerChatNames)
                components.timeout(this, backendModel::timeout)
            }
                .withVisualPadding()

            else -> panel{}
        }
    }

    override fun <T : Any?> getUserData(key: Key<T>): T? {
        return file.getUserData(key)
    }

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) {
        file.putUserData(key, value)
    }

    override fun dispose() {}

    override fun getComponent(): DialogPanel {
        return formPanel
    }

    override fun getPreferredFocusedComponent(): DialogPanel? {
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

    data class ABCService (
        var dutySlugs: MutableList<String>,
        var slug: String
    )
    internal data class BackendModel(
        var backendName: String,
        var timeout: String,

        var maxAttempts: Int,

        var abcService: MutableList<ABCService>,
        var messengerChatNames: MutableList<String>,
    )
}