package com.techquantum.template.network.firebase.firestore

enum class OrderDirection {
    ASCENDING,
    DESCENDING,
}

sealed interface FilterCondition {
    val field: String

    data class Equals(override val field: String, val value: Any) : FilterCondition
    data class GreaterThan(override val field: String, val value: Any) : FilterCondition
    data class GreaterThanOrEqual(override val field: String, val value: Any) : FilterCondition
    data class LessThan(override val field: String, val value: Any) : FilterCondition
    data class LessThanOrEqual(override val field: String, val value: Any) : FilterCondition
    data class InSet(override val field: String, val values: List<Any>) : FilterCondition
}

data class QuerySpec(
    val collectionPath: String,
    val filters: List<FilterCondition> = emptyList(),
    val orderBy: String? = null,
    val orderDirection: OrderDirection = OrderDirection.ASCENDING,
    val limit: Long? = null,
    val cursor: Any? = null,
) {
    companion object {
        const val FIRESTORE_IN_LIMIT = 30
    }
}
