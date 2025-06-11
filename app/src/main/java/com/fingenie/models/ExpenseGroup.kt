package com.fingenie.models

data class ExpenseGroup(
    val id: String = "",
    val name: String = "",
    val createdBy: String = "", // User ID of creator
    val members: List<String> = listOf(), // List of user IDs
    val expenses: List<String> = listOf() // List of expense IDs
) 