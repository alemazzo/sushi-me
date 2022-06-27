package io.github.alemazzo.sushime.model.orders

data class SingleOrder(val items: List<SingleOrderItem>) {

    companion object {
        fun loadFromString(string: String): SingleOrder {
            return SingleOrder(listOf())
        }
    }

    fun exportToString(): String {
        return ""
    }
}
