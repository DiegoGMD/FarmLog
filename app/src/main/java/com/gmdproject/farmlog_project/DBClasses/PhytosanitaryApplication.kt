package com.gmdproject.farmlog_project.DBClasses

data class PhytosanitaryApplication(
    val applicationId: Int,
    val plotId: Int,
    val productId: Int,
    val applicationDate: String,
    val dosage: Double,
    val purpose: String
)