package com.gmdproject.farmlog_project.DBClasses

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.gmdproject.farmlog_project.FarmLogDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class AgriculturalHolding(
    val agriculturalHoldingId: Int,
    var name: String,
    val location: String,
    val owner: String,
    val registrationNumber: String
) {
    fun insertAgriculturalHolding(
        context: Context,
        userId: Int
    ): AgriculturalHolding? {
        val dbHelper = FarmLogDatabase(context)
        val db = dbHelper.writableDatabase
        db.beginTransaction()

        try {
            val contentValues = ContentValues().apply {
                put("name", name)
                put("location", location)
                put("owner", owner)
                put("registration_number", registrationNumber)
            }

            val agriculturalHoldingId = db.insert("AgriculturalHolding", null, contentValues)

            if (agriculturalHoldingId == -1L) {
                Log.e("Database", "Error inserting agricultural holding")
                return null
            }

            val userHoldingValues = ContentValues().apply {
                put("appuser_id", userId)
                put("agricultural_holding_id", agriculturalHoldingId)
            }

            val linkResult = db.insert("AppUser_AgriculturalHolding", null, userHoldingValues)

            if (linkResult == -1L) {
                Log.e("Database", "Error linking agricultural holding to user")
                return null
            }

            db.setTransactionSuccessful()

            return AgriculturalHolding(
                agriculturalHoldingId = agriculturalHoldingId.toInt(),
                name = name,
                location = location,
                owner = owner,
                registrationNumber = registrationNumber
            )
        } catch (e: Exception) {
            Log.e("Database", "Error inserting agricultural holding", e)
            return null
        } finally {
            db.endTransaction()
            db.close()
        }
    }
}

fun getAgriculturalHoldings(context: Context, userId: Int): List<AgriculturalHolding> {
    val dbHelper = FarmLogDatabase(context)
    val db = dbHelper.readableDatabase
    val agriculturalHoldings = mutableListOf<AgriculturalHolding>()

    val query = """
        SELECT ah.* FROM AgriculturalHolding ah
        JOIN AppUser_AgriculturalHolding uah ON ah.agricultural_holding_id = uah.agricultural_holding_id
        WHERE uah.appuser_id = ?
    """
    val selectionArgs = arrayOf(userId.toString())

    try {
        val cursor = db.rawQuery(query, selectionArgs)
        while (cursor.moveToNext()) {
            val holding = AgriculturalHolding(
                agriculturalHoldingId = cursor.getInt(cursor.getColumnIndexOrThrow("agricultural_holding_id")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                location = cursor.getString(cursor.getColumnIndexOrThrow("location")),
                owner = cursor.getString(cursor.getColumnIndexOrThrow("owner")),
                registrationNumber = cursor.getString(cursor.getColumnIndexOrThrow("registration_number"))
            )
            agriculturalHoldings.add(holding)
        }
        cursor.close()
    } catch (e: Exception) {
        Log.e("Database", "Error getting agricultural holdings", e)
    } finally {
        db.close()
    }
    return agriculturalHoldings
}

fun getAgriculturalHolding(context: Context, userId: Int, agriculturalHoldingId: Int): AgriculturalHolding? {
    val dbHelper = FarmLogDatabase(context)
    val db = dbHelper.readableDatabase

    val query = """
        SELECT ah.* FROM AgriculturalHolding ah
        JOIN AppUser_AgriculturalHolding uah ON ah.agricultural_holding_id = uah.agricultural_holding_id
        WHERE uah.appuser_id = ? AND ah.agricultural_holding_id = ?
    """
    val selectionArgs = arrayOf(userId.toString(), agriculturalHoldingId.toString())

    try {
        val cursor = db.rawQuery(query, selectionArgs)
        return if (cursor.moveToFirst()) {
            val holding = AgriculturalHolding(
                agriculturalHoldingId = cursor.getInt(cursor.getColumnIndexOrThrow("agricultural_holding_id")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                location = cursor.getString(cursor.getColumnIndexOrThrow("location")),
                owner = cursor.getString(cursor.getColumnIndexOrThrow("owner")),
                registrationNumber = cursor.getString(cursor.getColumnIndexOrThrow("registration_number"))
            )
            cursor.close()
            holding
        } else {
            cursor.close()
            null
        }
    } catch (e: Exception) {
        Log.e("Database", "Error getting agricultural holding", e)
        return null
    } finally {
        db.close()
    }
}