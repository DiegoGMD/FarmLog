package com.gmdproject.farmlog_project

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FarmLogDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "FarmlogDB.sqlite"
        const val DATABASE_VERSION = 2

        @Volatile
        private var INSTANCE: FarmLogDatabase? = null

        fun getInstance(context: Context): FarmLogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = FarmLogDatabase(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Table creation statements
        val createAgriculturalHoldingTable = """
            CREATE TABLE IF NOT EXISTS AgriculturalHolding (
                agricultural_holding_id INTEGER PRIMARY KEY, 
                name TEXT NOT NULL, 
                location TEXT NOT NULL, 
                owner TEXT NOT NULL, 
                registration_number TEXT
            );
        """.trimIndent()

        val createPlotTable = """
            CREATE TABLE IF NOT EXISTS Plot (
                plot_id INTEGER PRIMARY KEY, 
                agricultural_holding_id INTEGER NOT NULL,
                area REAL NOT NULL, 
                crop_type TEXT NOT NULL,
                sowing_date TEXT, 
                FOREIGN KEY (agricultural_holding_id) REFERENCES AgriculturalHolding(agricultural_holding_id)
            );
        """.trimIndent()

        val createPhytosanitaryProductTable = """
            CREATE TABLE IF NOT EXISTS PhytosanitaryProduct (
                product_id INTEGER PRIMARY KEY, 
                name TEXT NOT NULL, 
                active_component TEXT, 
                manufacturer TEXT, 
                health_registration TEXT
            );
        """.trimIndent()

        val createPhytosanitaryApplicationTable = """
            CREATE TABLE IF NOT EXISTS PhytosanitaryApplication (
                application_id INTEGER PRIMARY KEY, 
                plot_id INTEGER NOT NULL,
                product_id INTEGER NOT NULL, 
                application_date TEXT, 
                dosage REAL, 
                purpose TEXT, 
                FOREIGN KEY (plot_id) REFERENCES Plot(plot_id),
                FOREIGN KEY (product_id) REFERENCES PhytosanitaryProduct(product_id)
            );
        """.trimIndent()

        val createIrrigationTable = """
            CREATE TABLE IF NOT EXISTS Irrigation (
                irrigation_id INTEGER PRIMARY KEY, 
                plot_id INTEGER NOT NULL, 
                irrigation_date TEXT, 
                irrigation_method TEXT, 
                water_volume REAL, 
                FOREIGN KEY (plot_id) REFERENCES Plot(plot_id)
            );
        """.trimIndent()

        val createAppUserTable = """
            CREATE TABLE IF NOT EXISTS AppUser (
                appuser_id INTEGER PRIMARY KEY, 
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL, 
                nif TEXT NOT NULL, 
                address TEXT, 
                city TEXT, 
                postal_code TEXT, 
                province TEXT, 
                phone TEXT, 
                email TEXT, 
                password TEXT
            );
        """.trimIndent()

        val createAppUserAgriculturalHoldingTable = """
            CREATE TABLE IF NOT EXISTS AppUser_AgriculturalHolding (
                appuser_id INTEGER NOT NULL, 
                agricultural_holding_id INTEGER NOT NULL, 
                PRIMARY KEY (appuser_id, agricultural_holding_id),
                FOREIGN KEY (appuser_id) REFERENCES AppUser(appuser_id),
                FOREIGN KEY (agricultural_holding_id) REFERENCES AgriculturalHolding(agricultural_holding_id)
            );
        """.trimIndent()

        db?.apply {
            execSQL(createAgriculturalHoldingTable)
            execSQL(createPlotTable)
            execSQL(createPhytosanitaryProductTable)
            execSQL(createPhytosanitaryApplicationTable)
            execSQL(createIrrigationTable)
            execSQL(createAppUserTable)
            execSQL(createAppUserAgriculturalHoldingTable)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.apply {
            execSQL("DROP TABLE IF EXISTS AppUser_AgriculturalHolding")
            execSQL("DROP TABLE IF EXISTS AppUser")
            execSQL("DROP TABLE IF EXISTS Irrigation")
            execSQL("DROP TABLE IF EXISTS PhytosanitaryApplication")
            execSQL("DROP TABLE IF EXISTS PhytosanitaryProduct")
            execSQL("DROP TABLE IF EXISTS Plot")
            execSQL("DROP TABLE IF EXISTS AgriculturalHolding")
            onCreate(this)
        }
    }
}