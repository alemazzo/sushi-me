package io.github.alemazzo.sushime.model.orders

data class SingleOrder(val items: List<SingleOrderItem>) {

    companion object {
        fun fromString(string: String): SingleOrder {
            return SingleOrder(listOf())
        }
    }
}
