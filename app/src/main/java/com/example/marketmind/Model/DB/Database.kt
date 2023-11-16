package com.example.marketmind.Model.DB

class Database {
    companion object {
        val sql = arrayOf(
            "CREATE TABLE user(" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "name VARCHAR(255)," +
                    "email VARCHAR(255)," +
                    "surname VARCHAR(255)," +
                    "password VARCHAR(100));",
            "CREATE TABLE church(" +
                    "cid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "cmoney DECIMAL);" +
                    "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE",
            "CREATE TABLE tuition(" +
                    "tid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "title VARCHAR(255)," +
                    "tprice DECIMAL," +
                    "ttype VARCHAR(255)," +
                    "tstate BOOLEAN," +
                    "user_id INT NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE);",
            "CREATE TABLE contribution(" +
                    "cid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "ctitle VARCHAR(255)," +
                    "cprice DECIMAL," +
                    "ctype VARCHAR(255)," +
                    "user_id INT NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE);"
        )
    }
}