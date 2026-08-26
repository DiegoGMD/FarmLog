package com.gmdproject.farmlog_project.DBClasses

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.gmdproject.farmlog_project.FarmLogDatabase

data class Plot(
    val plotId: Int?,
    val agriculturalHoldingId: Int?,
    val area: Double,
    val cropType: String,
    val sowingDate: String
) {
    fun insertNewPlot(context: Context) {
        val dbHelper = FarmLogDatabase(context)
        val db = dbHelper.writableDatabase
        var newRowId: Long = -1

        try {
            val cursor = db.rawQuery("SELECT COUNT(*) as total FROM Plot", null)
            var plotsAmount = 0
            if (cursor.moveToFirst()) {
                plotsAmount = cursor.getInt(cursor.getColumnIndexOrThrow("total"))
            }
            cursor.close()

            val contentValues = ContentValues().apply {
                put("plot_id", plotsAmount)
                put("agricultural_holding_id", agriculturalHoldingId)
                put("area", area)
                put("crop_type", cropType)
                put("sowing_date", sowingDate)
            }

            newRowId = db.insert("Plot", null, contentValues)
            if (newRowId == -1L) {
                Log.e("Database", "Failed to insert new plot")
            } else {
                Log.d("Database", "Plot inserted successfully with ID: $newRowId")
            }
        } catch (e: Exception) {
            Log.e("Database", "Error inserting new plot", e)
        } finally {
            db.close()
        }
    }

    fun updatePlot(context: Context): Int {
        val dbHelper = FarmLogDatabase(context)
        val db = dbHelper.writableDatabase
        var rowsAffected = 0

        try {
            val contentValues = ContentValues().apply {
                put("agricultural_holding_id", agriculturalHoldingId)
                put("area", area)
                put("crop_type", cropType)
                put("sowing_date", sowingDate)
            }

            val whereClause = "plot_id = ?"
            val whereArgs = arrayOf(plotId.toString())

            rowsAffected = db.update("Plot", contentValues, whereClause, whereArgs)

            if (rowsAffected > 0) {
                Log.d("Database", "Plot updated successfully. Rows affected: $rowsAffected")
            } else {
                Log.e("Database", "Failed to update plot")
            }
        } catch (e: Exception) {
            Log.e("Database", "Error updating plot", e)
        } finally {
            db.close()
        }
        return rowsAffected
    }

    fun erasePlot(context: Context): Int {
        val dbHelper = FarmLogDatabase(context)
        val db = dbHelper.writableDatabase
        var rowsAffected = 0

        try {
            plotId?.let { id ->
                val whereClause = "plot_id = ?"
                val whereArgs = arrayOf(id.toString())

                rowsAffected = db.delete("Plot", whereClause, whereArgs)

                if (rowsAffected > 0) {
                    Log.d("Database", "Plot deleted successfully. Rows affected: $rowsAffected")
                } else {
                    Log.e("Database", "Failed to delete plot with ID: $id")
                }
            } ?: run {
                Log.e("Database", "Cannot delete plot: plotId is null")
            }
        } catch (e: Exception) {
            Log.e("Database", "Error deleting plot", e)
        } finally {
            db.close()
        }
        return rowsAffected
    }
}

fun getPlotsByAgriculturalHolding(context: Context, agriculturalHoldingId: Int): List<Plot> {
    val dbHelper = FarmLogDatabase(context)
    val db = dbHelper.readableDatabase
    val plots = mutableListOf<Plot>()

    val query = """SELECT * FROM Plot WHERE agricultural_holding_id = ?"""
    val selectionArgs = arrayOf(agriculturalHoldingId.toString())

    try {
        val cursor = db.rawQuery(query, selectionArgs)
        while (cursor.moveToNext()) {
            val plot = Plot(
                plotId = cursor.getInt(cursor.getColumnIndexOrThrow("plot_id")),
                agriculturalHoldingId = cursor.getInt(cursor.getColumnIndexOrThrow("agricultural_holding_id")),
                area = cursor.getDouble(cursor.getColumnIndexOrThrow("area")),
                cropType = cursor.getString(cursor.getColumnIndexOrThrow("crop_type")) ?: "",
                sowingDate = cursor.getString(cursor.getColumnIndexOrThrow("sowing_date")) ?: ""
            )
            plots.add(plot)
        }
        cursor.close()
        Log.d("Database", "Successful Mission: Getting plot info")
    } catch (e: Exception) {
        Log.e("Database", "Error getting plot info", e)
    } finally {
        db.close()
    }
    return plots
}