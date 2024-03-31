package Graph

import com.apla.Graph.Deserializer
import com.apla.Graph.SourceData
import com.google.gson.GsonBuilder
import java.rmi.UnexpectedException


sealed class BackendNodeTypeData {
    data class SubgraphData(
        val timeout: String,

        // Attempts
        val maxAttempts: Int,

        // Responsibles
        val abcService: List<SourceData.BackendNodeABCService>,
        val messengerChatNames: List<String>,
    ) : BackendNodeTypeData()
    data class BackendData(
        val backendName: String,
        val timeout: String,

        // Attempts
        val maxAttempts: Int,

        // Responsibles
        val abcService: List<SourceData.BackendNodeABCService>,
        val messengerChatNames: List<String>,
    ) : BackendNodeTypeData()

    data class RRData(
        val backendName: String,
        val timeout: String,

        // Attempts
        val maxAttempts: Int,

        // Responsibles
        val abcService: List<SourceData.BackendNodeABCService>,
        val messengerChatNames: List<String>,

        // Servant
        val servantName: String,
        val functionName: String,
    ) : BackendNodeTypeData()

    object TransparentData : BackendNodeTypeData()
}


class BackendNode(sourceNodeName: String, sourceData: SourceData) {
    private val rrBackendName = "RENDERER_EDUCATION"
    val name: String
    val type: BackendNodeTypeData

    init {
        name = sourceNodeName

        val srcNode = sourceData.settings.nodeDeps[sourceNodeName] ?: throw UnexpectedException("Unknown node name $sourceNodeName")
        val srcBack = sourceData.settings.nodes.get(sourceNodeName) ?: throw UnexpectedException("Unknown node name $sourceNodeName")

        type = when (srcBack) {
            is SourceData.NodeData.TransparentData -> BackendNodeTypeData.TransparentData
            is SourceData.NodeData.BackendNodeData ->
                if (srcBack.backendName != rrBackendName) {
                    createBackendData(srcBack)
                } else {
                    val rrEmbeddedBackend = findRREmbeddedBackend(srcNode.inputDeps, sourceData.settings.nodes)
                        ?: throw UnexpectedException("Can't find embedded node for $srcNode")
                    createRRData(
                        src = srcBack,
                        servantName = rrEmbeddedBackend.embed.find { mapKeyValue -> mapKeyValue["type"] == "rr_servant" }!!["handler"]!!,
                        functionName = rrEmbeddedBackend.embed.find { mapKeyValue -> mapKeyValue["type"] == "template_params" }!!["template"]!!
                    )
                }
            is SourceData.NodeData.SubgraphNodeData -> createSubgraphData(srcBack)
            else -> throw UnexpectedException("Node $sourceNodeName has type Embedded")
        }
    }
}

class Data(jsonString: String) {
    private val nodesWithoutBackend = listOf("RESPONSE")
    val nodeList: MutableList<BackendNode> = mutableListOf()

    init {
        val gson = GsonBuilder()
            .registerTypeAdapter(SourceData.NodeData::class.java, Deserializer(GsonBuilder().create()))
            .create()

        val sourceData: SourceData = gson.fromJson(updateKeysToCamelCase(jsonString), SourceData::class.java)
        sourceData.settings.nodeDeps.forEach( fun (name, _) {
            if (nodesWithoutBackend.contains(name)) {
                return
            }
            val backend = BackendNode(name, sourceData)
            nodeList.add(backend)
        })
    }
}
