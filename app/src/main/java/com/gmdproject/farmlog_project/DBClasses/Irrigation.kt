package com.gmdproject.farmlog_project.DBClasses

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.gmdproject.farmlog_project.FarmLogDatabase

data class Irrigation(
    val irrigationId: Int = 0,
    val plotId: Int,
    val irrigationDate: String,
    val irrigationMethod: String,
    val waterVolume: Double
) {
    fun insertNewIrrigation(context: Context) {
        val dbHelper = FarmLogDatabase(context)
        val db = dbHelper.writableDatabase

        try {
            val cursor = db.rawQuery("SELECT COUNT(*) as total FROM Irrigation", null)
            var irrigationsAmount = 0
            if (cursor.moveToFirst()) {
                irrigationsAmount = cursor.getInt(cursor.getColumnIndexOrThrow("total"))
            }
            cursor.close()

            val contentValues = ContentValues().apply {
                put("irrigation_id", irrigationsAmount)
                put("plot_id", plotId)
                put("irrigation_date", irrigationDate)
                put("irrigation_method", irrigationMethod)
                put("water_volume", waterVolume)
            }

            val newRowId = db.insert("Irrigation", null, contentValues)
            if (newRowId == -1L) {
                Log.e("Database", "Failed to insert new irrigation")
            } else {
                Log.d("Database", "Irrigation inserted successfully with ID: $newRowId")
            }
        } catch (e: Exception) {
            Log.e("Database", "Error inserting new irrigation", e)
        } finally {
            db.close()
        }
    }

    fun updateIrrigation(context: Context): Int {
        val dbHelper = FarmLogDatabase(context)
        val db = dbHelper.writableDatabase
        var rowsAffected = 0

        try {
            val contentValues = ContentValues().apply {
                put("irrigation_date", irrigationDate)
                put("irrigation_method", irrigationMethod)
                put("water_volume", waterVolume)
            }

            val whereClause = "irrigation_id = ?"
            val whereArgs = arrayOf(irrigationId.toString())

            rowsAffected = db.update("Irrigation", contentValues, whereClause, whereArgs)

            if (rowsAffected > 0) {
                Log.d("Database", "Irrigation updated successfully. Rows affected: $rowsAffected")
            } else {
                Log.e("Database", "Failed to update irrigation")
            }
        } catch (e: Exception) {
            Log.e("Database", "Error updating irrigation", e)
        } finally {
            db.close()
        }
        return rowsAffected
    }

    fun eraseIrrigation(context: Context): Int {
        val dbHelper = FarmLogDatabase(context)
        val db = dbHelper.writableDatabase
        var rowsAffected = 0

        try {
            val whereClause = "irrigation_id = ?"
            val whereArgs = arrayOf(irrigationId.toString())

            rowsAffected = db.delete("Irrigation", whereClause, whereArgs)

            if (rowsAffected > 0) {
                Log.d("Database", "Irrigation deleted successfully. Rows affected: $rowsAffected")
            } else {
                Log.e("Database", "Failed to delete irrigation with ID: $irrigationId")
            }
        } catch (e: Exception) {
            Log.e("Database", "Error deleting irrigation", e)
        } finally {
            db.close()
        }
        return rowsAffected
    }
}

fun getIrrigationsByAgriculturalHolding(
    context: Context,
    agriculturalHoldingId: Int
): List<Irrigation> {
    val dbHelper = FarmLogDatabase(context)
    val db = dbHelper.readableDatabase
    val irrigations = mutableListOf<Irrigation>()

    val query = """
        SELECT i.* FROM Irrigation i
        JOIN Plot p ON i.plot_id = p.plot_id
        WHERE p.agricultural_holding_id = ?
    """
    val selectionArgs = arrayOf(agriculturalHoldingId.toString())

    try {
        val cursor = db.rawQuery(query, selectionArgs)
        while (cursor.moveToNext()) {
            val irrigation = Irrigation(
                irrigationId = cursor.getInt(cursor.getColumnIndexOrThrow("irrigation_id")),
                plotId = cursor.getInt(cursor.getColumnIndexOrThrow("plot_id")),
                irrigationDate = cursor.getString(cursor.getColumnIndexOrThrow("irrigation_date")) ?: "",
                irrigationMethod = cursor.getString(cursor.getColumnIndexOrThrow("irrigation_method")) ?: "",
                waterVolume = cursor.getDouble(cursor.getColumnIndexOrThrow("water_volume"))
            )
            irrigations.add(irrigation)
        }
        cursor.close()
        Log.d("Database", "Successful Mission: Getting irrigation info")
    } catch (e: Exception) {
        Log.e("Database", "Error getting irrigation info", e)
    } finally {
        db.close()
    }
    return irrigations
}