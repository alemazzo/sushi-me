package io.github.alemazzo.sushime.model.orders

data class SingleOrder(val items: List<SingleOrderItem>) {

    companion object {
        fun loadFromString(string: String): SingleOrder {
            val splittedItems = string.split(",")
            val items = splittedItems.map { item ->
                SingleOrderItem(
                    dishId = item.split(":")[0].toInt(),
                    quantity = item.split(":")[1].toInt()
                )
            }
            return SingleOrder(items)
        }
    }

    fun exportToString(): String =
        items.joinToString(separator = ",") { "${it.dishId}:${it.quantity}" }

}
