package Graph

import com.apla.Graph.SourceData

fun createBackendData(src: SourceData.NodeData.BackendNodeData) : BackendNodeTypeData.BackendData {
    return BackendNodeTypeData.BackendData(
        backendName = src.backendName,
        timeout = src.params.timeout,
        maxAttempts = src.params.attempts.maxAttempts,
        abcService = src.params.responsibles.abcService,
        messengerChatNames = src.params.responsibles.messengerChatNames
    )
}

fun createRRData(
    src: SourceData.NodeData.BackendNodeData,
    servantName: String,
    functionName: String
) : BackendNodeTypeData.RRData {
    return BackendNodeTypeData.RRData(
        backendName = src.backendName,
        timeout = src.params.timeout,
        maxAttempts = src.params.attempts.maxAttempts,
        abcService = src.params.responsibles.abcService,
        messengerChatNames = src.params.responsibles.messengerChatNames,
        servantName = servantName,
        functionName = functionName
    )
}

fun isRREmbeddedBackend(value: SourceData.NodeData): Boolean {
    if (value !is SourceData.NodeData.EmbeddedNodeData) {
        return false
    }

    if (value.embed.size != 2) {
        return false
    }

    value.embed.find { record ->
        record["type"] == "template_params" && record["template"] != null
    } ?: return false

    value.embed.find { record ->
        record["type"] == "rr_servant" && record["handler"] != null
    } ?: return false


    return true
}

fun findRREmbeddedBackend(inputDeps: List<String>, backends: Map<String, SourceData.NodeData>): SourceData.NodeData.EmbeddedNodeData?  {
    val rrEmbeddedBackends = backends.filterValues { value -> isRREmbeddedBackend(value) } as Map<String, SourceData.NodeData.EmbeddedNodeData>
    val includedEmbeddedNodes = rrEmbeddedBackends.filterKeys { nodeName -> inputDeps.contains(nodeName) }

    if (includedEmbeddedNodes.size != 1) {
        return null
    }

    return includedEmbeddedNodes.toList()[0].second
}
