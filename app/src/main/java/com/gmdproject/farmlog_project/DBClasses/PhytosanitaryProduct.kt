package com.gmdproject.farmlog_project.DBClasses

data class PhytosanitaryProduct(
    val productId: Int,
    val name: String,
    val activeComponent: String,
    val manufacturer: String,
    val healthRegistration: String
)