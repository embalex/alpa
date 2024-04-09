package ApphostToolWindow

import com.intellij.ui.dsl.builder.*
import com.intellij.ui.layout.rowWithIndent
import kotlin.reflect.KMutableProperty0

class BackendViewFileEditorComponents() {
    fun backendName (panel: Panel, field: KMutableProperty0<String>) {
        panel.row() {
            label("backendName: ")
                .bold()
            textField()
                .bindText(field)
        }.topGap(TopGap.MEDIUM)
    }

    fun maxAttempts (panel: Panel, field: KMutableProperty0<Int>) {
        panel.row() {
            label("maxAttempts: ")
                .bold()
            intTextField()
                .bindIntText(field)
        }.topGap(TopGap.MEDIUM)
    }

    fun abcService(panel: Panel, field: MutableList<BackendViewFileEditor.ABCService>) {
        panel.row() {
            label("ABCServices: ")
                .bold()
        }.topGap(TopGap.MEDIUM)
        field.forEach(fun (abcService) {
            panel.indent {
                row() {
                    label("dutySlugs: ")
                    label(abcService.dutySlugs.toString())
                    label("slug: ")
                    label(abcService.slug)
                }
            }
        })
        panel.row() {}.bottomGap(BottomGap.MEDIUM)
    }


    fun messengerChatNames(panel: Panel, field: MutableList<String>) {
        panel.row() {
            label("messengerChatNames: ")
                .bold()
        }.topGap(TopGap.MEDIUM)
        field.forEach(fun (messengerChatName) {
            panel.indent{
                row() {
                    label("- $messengerChatName")
                }
            }
        })
    }

    fun timeout (panel: Panel, field: KMutableProperty0<String>) {
        panel.row() {
            label("timeout: ")
                .bold()
            textField()
                .bindText(field)
        }.topGap(TopGap.MEDIUM)
    }

}