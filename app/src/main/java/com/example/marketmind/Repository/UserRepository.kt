package com.example.marketmind.Repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.marketmind.Model.DB.Database
import com.example.marketmind.Model.User
import java.lang.Exception

class UserRepository(context: Context) : SQLiteOpenHelper(context, "churchdb", null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        Database.sql.forEach {
            p0?.execSQL(it)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun registerUser(user: User): Long {
        try {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put("name", user.name)
            contentValues.put("email", user.email)
            contentValues.put("surname", user.surname)
            contentValues.put("password", user.password)
            val res = db.insert("user", null, contentValues)

            //get user id
            val data = getUserByEmail(user.email)
            return data.id.toLong()
        } catch (e: Exception) {
            return 0
        }
    }

    fun getUserByEmail(email: String): User {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", arrayOf(email))
        cursor.moveToFirst()
        lateinit var user: User
        if (cursor.count == 1) {
            val nameIndex = cursor.getColumnIndex("name")
            val emailIndex = cursor.getColumnIndex("email")
            val surnameIndex = cursor.getColumnIndex("surname")
            val idIndex = cursor.getColumnIndex("id")

            val name = cursor.getString(nameIndex)
            val email2 = cursor.getString(emailIndex)
            val surname = cursor.getString(surnameIndex)
            val id = cursor.getInt(idIndex)
            user = User(name, surname, email2, "", id)
        }

        cursor.close()

        return user
    }

    fun login(email: String, password: String): User {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM user WHERE email = ? AND password = ?",
            arrayOf(email, password)
        )
        var user = User()
        //cursor.moveToFirst()
        while (cursor.moveToNext()) {
            val nameIndex = cursor.getColumnIndex("name")
            val emailIndex = cursor.getColumnIndex("email")
            val surnameIndex = cursor.getColumnIndex("surname")
            val idIndex = cursor.getColumnIndex("id")

            val name = cursor.getString(nameIndex)
            val email2 = cursor.getString(emailIndex)
            val surname = cursor.getString(surnameIndex)
            val id = cursor.getInt(idIndex)
            user = User(name, surname, email2, "", id)
        }

        cursor.close()

        return user
    }


    fun getUserData(id: Int): User {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE id = ?", arrayOf(id.toString()))
        cursor.moveToFirst()
        lateinit var user: User
        if (cursor.count == 1) {
            val nameIndex = cursor.getColumnIndex("name")
            val emailIndex = cursor.getColumnIndex("email")
            val surnameIndex = cursor.getColumnIndex("surname")
            val idIndex = cursor.getColumnIndex("id")

            val name = cursor.getString(nameIndex)
            val email = cursor.getString(emailIndex)
            val surname = cursor.getString(surnameIndex)
            val id = cursor.getInt(idIndex)
            user = User(name, surname, email, "", id)
        }

        cursor.close()

        return user
    }

    fun checkUser(user: User): User {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", arrayOf(user.email))
        cursor.moveToFirst()
        var user: User = User()
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val nameIndex = cursor.getColumnIndex("name")
                val emailIndex = cursor.getColumnIndex("email")
                val surnameIndex = cursor.getColumnIndex("surname")
                val idIndex = cursor.getColumnIndex("id")

                val name = cursor.getString(nameIndex)
                val email = cursor.getString(emailIndex)
                val surname = cursor.getString(surnameIndex)
                val id = cursor.getInt(idIndex)
                user = User(name, surname, email, "", id)
            }
        }

        cursor.close()

        return user
    }

    fun updateUser(uid:Int, user: User):Int{
        val db = this.writableDatabase
        val content = ContentValues()

        if(user.password != ""){
            content.put("password",user.password)
        }
        content.put("name",user.name)
        content.put("email",user.email)
        content.put("surname",user.surname)
        val res = db.update("user",content,"id = ?", arrayOf(uid.toString()))
        return res
    }
}