package com.millenialzdev.myloginandregistersqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DataBaseHelperLogin(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        private const val DATABASE_NAME = "DB_LOGIN"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE session (id integer PRIMARY KEY, login text)")
        db.execSQL("CREATE TABLE user (id integer PRIMARY KEY AUTOINCREMENT, username text, password text)")
        db.execSQL("INSERT INTO session(id, login) VALUES (1, 'kosong')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS session")
        db.execSQL("DROP TABLE IF EXISTS user")
        onCreate(db)
    }

    // Check session
    fun checkSession(value: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM session WHERE login = ? ", arrayOf(value))
        return cursor.count > 0
    }

    // Upgrade session
    fun upgradeSession(value: String, id: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("login", value)
        val update = db.update("session", values, "id = ?", arrayOf(id.toString()))
        return update != -1

    }

    // Input user
    fun simpanUser(username: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        val insert = db.insert("user", null, values)
        return insert != -1.toLong()
    }

    // Check login
    fun checkLogin(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?", arrayOf(username, password))
        return cursor.count > 0
    }
}
