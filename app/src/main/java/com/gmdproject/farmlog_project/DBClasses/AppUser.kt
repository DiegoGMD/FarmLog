package com.gmdproject.farmlog_project.DBClasses

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.gmdproject.farmlog_project.FarmLogDatabase

data class AppUser(
    val id: Int? = null,
    val nif: String = "",
    val firstName: String = "",
    val surnames: String = "",
    val password: String = "",
    val address: String = "",
    val city: String = "",
    val province: String = "",
    val postalCode: String = "",
    var phone: String = "",
    val email: String = ""
) {

    fun insertNewUser(context: Context) {
        val dbHelper = FarmLogDatabase(context)
        val db = dbHelper.writableDatabase

        try {
            // Count the number of users
            val cursor = db.rawQuery("SELECT COUNT(*) as total FROM AppUser", null)
            var usersAmount = 0
            if (cursor.moveToFirst()) {
                usersAmount = cursor.getInt(cursor.getColumnIndexOrThrow("total"))
            }
            cursor.close()

            // Insert new user
            val contentValues = ContentValues().apply {
                put("appuser_id", usersAmount)
                put("nif", nif)
                put("first_name", firstName)
                put("last_name", surnames)
                put("address", address)
                put("city", city)
                put("postal_code", postalCode)
                put("phone", phone)
                put("email", email)
                put("password", password)
            }

            val newRowId = db.insert("AppUser", null, contentValues)
            if (newRowId == -1L) {
                Log.e("Database", "Failed to insert new user")
            } else {
                Log.d("Database", "User inserted successfully with ID: $newRowId")
            }
        } catch (e: Exception) {
            Log.e("Database", "Error inserting new user", e)
        } finally {
            db.close()
        }
    }

    fun getUser(context: Context, nif: String, password: String): AppUser? {
        val dbHelper = FarmLogDatabase(context)
        val db = dbHelper.readableDatabase
        var user: AppUser? = null

        val query = """
        SELECT * FROM AppUser 
        WHERE nif = ? AND password = ?
    """
        val selectionArgs = arrayOf(nif, password)

        try {
            val cursor = db.rawQuery(query, selectionArgs)
            if (cursor.moveToFirst()) {
                user = AppUser(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("appuser_id")),
                    nif = cursor.getString(cursor.getColumnIndexOrThrow("nif")) ?: "",
                    firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name")) ?: "",
                    surnames = cursor.getString(cursor.getColumnIndexOrThrow("last_name")) ?: "",
                    password = cursor.getString(cursor.getColumnIndexOrThrow("password")) ?: "",
                    address = cursor.getString(cursor.getColumnIndexOrThrow("address")) ?: "",
                    city = cursor.getString(cursor.getColumnIndexOrThrow("city")) ?: "",
                    province = cursor.getString(cursor.getColumnIndexOrThrow("province")) ?: "",
                    postalCode = cursor.getString(cursor.getColumnIndexOrThrow("postal_code")) ?: "",
                    phone = cursor.getString(cursor.getColumnIndexOrThrow("phone")) ?: "",
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email")) ?: ""
                )
            }
            cursor.close()
            Log.d("Database", "Successful Mission: Getting user info")
        } catch (e: Exception) {
            Log.e("Database", "Error getting user info", e)
        } finally {
            db.close()
        }
        return user
    }

    fun getUser(context: Context, user_id: Int): AppUser? {
        val dbHelper = FarmLogDatabase(context)
        val db = dbHelper.readableDatabase
        var user: AppUser? = null

        val query = """
        SELECT * FROM AppUser 
        WHERE appuser_id = ?
    """
        val selectionArgs = arrayOf(user_id.toString())

        try {
            val cursor = db.rawQuery(query, selectionArgs)
            if (cursor.moveToFirst()) {
                user = AppUser(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("appuser_id")),
                    nif = cursor.getString(cursor.getColumnIndexOrThrow("nif")) ?: "",
                    firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name")) ?: "",
                    surnames = cursor.getString(cursor.getColumnIndexOrThrow("last_name")) ?: "",
                    password = cursor.getString(cursor.getColumnIndexOrThrow("password")) ?: "",
                    address = cursor.getString(cursor.getColumnIndexOrThrow("address")) ?: "",
                    city = cursor.getString(cursor.getColumnIndexOrThrow("city")) ?: "",
                    province = cursor.getString(cursor.getColumnIndexOrThrow("province")) ?: "",
                    postalCode = cursor.getString(cursor.getColumnIndexOrThrow("postal_code")) ?: "",
                    phone = cursor.getString(cursor.getColumnIndexOrThrow("phone")) ?: "",
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email")) ?: ""
                )
            }
            cursor.close()
            Log.d("Database", "Successful Mission: Getting user info")
        } catch (e: Exception) {
            Log.e("Database", "Error getting user info", e)
        } finally {
            db.close()
        }
        return user
    }
}