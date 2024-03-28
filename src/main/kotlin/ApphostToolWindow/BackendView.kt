package ApphostToolWindow

import Graph.BackendNode


class BackendView(val backend: BackendNode) {
    override fun toString(): String {
        return backend.name
    }
}