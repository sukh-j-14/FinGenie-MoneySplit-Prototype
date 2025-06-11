package com.fingenie.models

data class Group(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val createdBy: String = "",
    val members: List<String> = emptyList(),
    val expenses: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
) 