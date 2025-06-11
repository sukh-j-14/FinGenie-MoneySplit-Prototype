package com.fingenie.models

data class Expense(
    val id: String = "",
    val groupId: String = "",
    val paidBy: String = "", // User ID who paid
    val amount: Double = 0.0,
    val description: String = "",
    val date: Long = System.currentTimeMillis(),
    val splits: List<ExpenseSplit> = listOf()
)

data class ExpenseSplit(
    val userId: String = "",
    val amount: Double = 0.0,
    val isPaid: Boolean = false
) 