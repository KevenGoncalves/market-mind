package com.example.marketmind.Repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.marketmind.Model.DB.Database
import com.example.marketmind.Model.Tuition
import com.example.marketmind.Model.User

class TuitionRespository(context: Context) : SQLiteOpenHelper(context, "churchdb", null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        Database.sql.forEach {
            p0?.execSQL(it)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun createTuition(tuition: Tuition): Long {
        val db = this.writableDatabase
        val content = ContentValues()
        content.put("title", tuition.title)
        content.put("tprice", tuition.price)
        content.put("ttype", tuition.type)
        content.put("tstate", tuition.state)
        content.put("user_id", tuition.user.id)
        return db.insert("tuition", null, content)
    }

    fun getTuitionByUserId(uid: Int): List<Tuition> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM tuition WHERE user_id = ?", arrayOf(uid.toString()))

        cursor.moveToFirst()
        val list = ArrayList<Tuition>()

        if (cursor.count > 0) {
            do {
                val titleIndex = cursor.getColumnIndex("title")
                val tpriceIndex = cursor.getColumnIndex("tprice")
                val ttypeIndex = cursor.getColumnIndex("ttype")
                val tstateIndex = cursor.getColumnIndex("tstate")
                val tIdIndex = cursor.getColumnIndex("tid")

                val title = cursor.getString(titleIndex)
                val price = cursor.getDouble(tpriceIndex)
                val type = cursor.getString(ttypeIndex)
                val state = cursor.getInt(tstateIndex)
                val id = cursor.getInt(tIdIndex)

                val tuition = Tuition(title, price, type, state, User(id = uid), id)
                list.add(tuition)
            } while ((cursor.moveToNext()))
        }

        cursor.close()
        return list
    }


    fun getTuitionByUserIdDesc(uid: Int): List<Tuition> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM tuition WHERE user_id = ? ORDER BY tid DESC", arrayOf(uid.toString()))

        cursor.moveToFirst()
        val list = ArrayList<Tuition>()

        if (cursor.count > 0) {
            do {
                val titleIndex = cursor.getColumnIndex("title")
                val tpriceIndex = cursor.getColumnIndex("tprice")
                val ttypeIndex = cursor.getColumnIndex("ttype")
                val tstateIndex = cursor.getColumnIndex("tstate")
                val tIdIndex = cursor.getColumnIndex("tid")

                val title = cursor.getString(titleIndex)
                val price = cursor.getDouble(tpriceIndex)
                val type = cursor.getString(ttypeIndex)
                val state = cursor.getInt(tstateIndex)
                val id = cursor.getInt(tIdIndex)

                val tuition = Tuition(title, price, type, state, User(id = uid), id)
                list.add(tuition)
            } while ((cursor.moveToNext()))
        }

        cursor.close()
        return list
    }

    fun getRecentTuitionByUserId(uid: Int): List<Tuition> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM tuition WHERE user_id = ? ORDER BY tid DESC LIMIT 3", arrayOf(uid.toString()))

        cursor.moveToFirst()
        val list = ArrayList<Tuition>()

        if (cursor.count > 0) {
            do {
                val titleIndex = cursor.getColumnIndex("title")
                val tpriceIndex = cursor.getColumnIndex("tprice")
                val ttypeIndex = cursor.getColumnIndex("ttype")
                val tstateIndex = cursor.getColumnIndex("tstate")
                val tIdIndex = cursor.getColumnIndex("tid")

                val title = cursor.getString(titleIndex)
                val price = cursor.getDouble(tpriceIndex)
                val type = cursor.getString(ttypeIndex)
                val state = cursor.getInt(tstateIndex)
                val id = cursor.getInt(tIdIndex)

                val tuition = Tuition(title, price, type, state, User(id = uid), id)
                list.add(tuition)
            } while ((cursor.moveToNext()))
        }

        cursor.close()
        return list
    }


    fun deleteTuition(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("tuition", "tid = ?", arrayOf(id.toString()))
    }

    fun getFinishedTuition(uid: Int): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM tuition WHERE user_id = ? AND tstate = 1",
            arrayOf(uid.toString())
        )
        return cursor.count
    }

    fun getUnFinishedTuition(uid: Int): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM tuition WHERE user_id = ? AND tstate = 0",
            arrayOf(uid.toString())
        )
        return cursor.count
    }

    fun countTuition(uid: Int): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM tuition WHERE user_id = ?",
            arrayOf(uid.toString())
        )
        return cursor.count
    }

    fun getTuitionById(id: Int): Tuition {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM tuition WHERE tid = ?",
            arrayOf(id.toString())
        )

        var tuition = Tuition()
        cursor.moveToFirst()

        if (cursor != null) {
            val titleIndex = cursor.getColumnIndex("title")
            val tpriceIndex = cursor.getColumnIndex("tprice")
            val ttypeIndex = cursor.getColumnIndex("ttype")
            val tstateIndex = cursor.getColumnIndex("tstate")
            val tIdIndex = cursor.getColumnIndex("tid")

            val title = cursor.getString(titleIndex)
            val price = cursor.getDouble(tpriceIndex)
            val type = cursor.getString(ttypeIndex)
            val state = cursor.getInt(tstateIndex)
            val id2 = cursor.getInt(tIdIndex)

            tuition = Tuition(title, price, type, state, User(), id2)
        }
        cursor.close()
        return tuition
    }

    fun updateTuition(id: Int, tuition: Tuition): Int {

        val res = getTuitionById(id)
        if (res.id != 0) {
            val db = this.writableDatabase
            val content = ContentValues()
            if (tuition.title != "") {
                content.put("title", tuition.title)
            }
            if (tuition.price > 0) {
                content.put("tprice", tuition.price)
            }

            if (tuition.type != "") {
                content.put("ttype", tuition.type)
            }

            if (tuition.state != -1 && res.state == 0) {
                content.put("tstate", tuition.state)
            }

            return db.update("tuition", content, "tid = ?", arrayOf(id.toString()))
        }

        return 0
    }

}