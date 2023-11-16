package com.example.marketmind.Repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.marketmind.Model.Contribution
import com.example.marketmind.Model.DB.Database
import com.example.marketmind.Model.User

class ContributionRepository(context: Context) : SQLiteOpenHelper(context, "churchdb", null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        Database.sql.forEach {
            p0?.execSQL(it)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun createContribution(contribution:Contribution):Long{
        val db = this.writableDatabase
        val content = ContentValues()
        content.put("cprice",contribution.price)
        content.put("ctype",contribution.type)
        content.put("ctitle",contribution.title)
        content.put("user_id",contribution.user.id)
        val res = db.insert("contribution",null,content)
        return res
    }



    fun getContributionByUserId(uid:Int):List<Contribution>{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM contribution WHERE user_id = ?", arrayOf(uid.toString()))

        val list = ArrayList<Contribution>()
        if(cursor.count > 0){
            cursor.moveToFirst()
            do {
                val tIndex = cursor.getColumnIndex("ctitle")
                val tyIndex = cursor.getColumnIndex("ctype")
                val cpriceIndex = cursor.getColumnIndex("cprice")
                val userIdIndex = cursor.getColumnIndex("user_id")
                val idIndex = cursor.getColumnIndex("cid")

                val title = cursor.getString(tIndex)
                val price = cursor.getDouble(cpriceIndex)
                val userId = cursor.getInt(userIdIndex)
                val id = cursor.getInt(idIndex)
                val type = cursor.getString(tyIndex)

                val cont = Contribution(title,price,type,id,User(id = userId))
                list.add(cont)
            }while((cursor.moveToNext()))
        }
        cursor.close()
        return list
    }

    fun getContributionByUserIdDesc(uid:Int):List<Contribution>{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM contribution WHERE user_id = ? ORDER BY cid DESC", arrayOf(uid.toString()))

        val list = ArrayList<Contribution>()
        if(cursor.count > 0){
            cursor.moveToFirst()
            do {
                val tIndex = cursor.getColumnIndex("ctitle")
                val tyIndex = cursor.getColumnIndex("ctype")
                val cpriceIndex = cursor.getColumnIndex("cprice")
                val userIdIndex = cursor.getColumnIndex("user_id")
                val idIndex = cursor.getColumnIndex("cid")

                val title = cursor.getString(tIndex)
                val price = cursor.getDouble(cpriceIndex)
                val userId = cursor.getInt(userIdIndex)
                val id = cursor.getInt(idIndex)
                val type = cursor.getString(tyIndex)

                val cont = Contribution(title,price,type,id,User(id = userId))
                list.add(cont)
            }while((cursor.moveToNext()))
        }
        cursor.close()
        return list
    }


    fun deleteContribution(id:Int):Int {
        val db = this.writableDatabase
        return db.delete("contribution","cid = ?",arrayOf(id.toString()))
    }
}