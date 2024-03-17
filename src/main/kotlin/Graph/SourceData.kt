package com.apla.Graph

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class SourceData (
    val settings: Settings,
) {
    data class BackendNodeParams(val attempts: BackendNodeAttempts, val responsibles: Responsibles, val timeout: String)
    data class BackendNodeAttempts(val maxAttempts: Int)
    data class Responsibles(val abcService: List<BackendNodeABCService>, val messengerChatNames: List<String>)
    data class BackendNodeABCService(val dutySlugs: List<String>, val slug: String)

    sealed class NodeData {
        data class BackendNodeData (val backendName: String, val params: BackendNodeParams) : NodeData()
        data class EmbeddedNodeData(val embed: List<Map<String, String>>) : NodeData()
        object TransparentData : NodeData()
    }

    data class InputDeps(val inputDeps : List<String>)

    data class Settings (
        val inputDeps : List<String>,
        val outputDeps : List<String>,
        val nodeDeps : Map<String, InputDeps>,
        val nodes : Map<String, NodeData>,
        val responsibles: Responsibles,
    )
}

class Deserializer(private val gson: Gson) : JsonDeserializer<SourceData.NodeData> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): SourceData.NodeData {
        val nodeDataSourceObject = json.asJsonObject
        return if (nodeDataSourceObject.has("nodeType")) {
            when (val nodeType = nodeDataSourceObject.get("nodeType").asString) {
                "EMBED" -> gson.fromJson(nodeDataSourceObject.toString(), SourceData.NodeData.EmbeddedNodeData::class.java)
                "TRANSPARENT" -> SourceData.NodeData.TransparentData
                else -> throw IllegalArgumentException("Unexpected nodeType $nodeType")
            }
        } else {
            gson.fromJson(nodeDataSourceObject.toString(), SourceData.NodeData.BackendNodeData::class.java)
        }
    }
}