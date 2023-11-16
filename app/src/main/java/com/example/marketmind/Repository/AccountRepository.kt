package com.example.marketmind.Repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.marketmind.Model.Account
import com.example.marketmind.Model.DB.Database
import com.example.marketmind.Model.User
import java.lang.Exception

class AccountRepository(context:Context):SQLiteOpenHelper(context,"churchdb",null,1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        Database.sql.forEach {
            p0?.execSQL(it)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun registerChurch(account: Account):Long{
        try {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put("cmoney",account.money)
            contentValues.put("user_id",account.user.id)
            val res = db.insert("church",null,contentValues)
            return res
        }catch (e:Exception){
            return 0
        }
    }

    fun getChurchData(id:Int):Account{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM church WHERE cid = ?", arrayOf(id.toString()))
        cursor.moveToFirst()
        var account = Account()
        if(cursor.count == 1) {
            val moneyIndex = cursor.getColumnIndex("cmoney")
            val idInd = cursor.getColumnIndex("cid")

            val money = cursor.getDouble(moneyIndex)
            val churchId = cursor.getInt(idInd)
            val i = cursor.getColumnIndex("user_id")
            val uid = cursor.getInt(i)

            account = Account(money,User(id = uid),churchId)
        }

        return account
    }

    fun getChurchByUserId(userId:Int):Account{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT user.*, church.* FROM user, church WHERE" +
                " user.id = church.user_id AND user.id = ?", arrayOf(userId.toString())
        )
        var account = Account()
        var user = User()
        cursor.moveToFirst()
        if(cursor.count == 1) {
            //user data
            val nameIndex = cursor.getColumnIndex("name")
            val emailIndex = cursor.getColumnIndex("email")
            val surnameIndex = cursor.getColumnIndex("surname")
            val idIndex = cursor.getColumnIndex("id")
            //church data
            val moneyIndex = cursor.getColumnIndex("cmoney")
            val idInd = cursor.getColumnIndex("cid")

            //user
            val name = cursor.getString(nameIndex)
            val email = cursor.getString(emailIndex)
            val surname = cursor.getString(surnameIndex)
            val id = cursor.getInt(idIndex)

            //church
            val money = cursor.getDouble(moneyIndex)
            val churchId = cursor.getInt(idInd)

            user = User(name,surname,email,"",id)
            account = Account(money,user,churchId)

        }

        return account
    }

    fun updateChurch(id:Int, account: Account, sign:Char):Int{

        val res = getChurchData(id)

        if(res.id > 0) {
            val db = this.writableDatabase
            val content = ContentValues()
            if(account.money > 0){
                if(sign == '-'){
                    if((res.money - account.money) >= 0) {
                        content.put("cmoney", (res.money - account.money))
                    }else{
                        content.put("cmoney", res.money)
                    }
                }else if(sign == '+'){
                    content.put("cmoney", res.money + account.money)
                }
            }
            return db.update("church",content,"cid = ?",arrayOf(id.toString()))
        }

        return 0
    }
}