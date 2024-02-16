package com.perfect.favourites

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.perfect.prodsuit.Model.InsertFavModel


class DataBaseHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    var TAG = "DataBaseHelper"


    // below variable is for our id column.
    private val ID_LIST = "id"
    private val TITLE = "title"
    private val TABLE_NAME = "lists"
    val favtitle = "title"
    override fun onCreate(db: SQLiteDatabase) {
        // TODO Auto-generated method stub

        val q1 = "CREATE TABLE favourites" +
                "(id Integer PRIMARY KEY, " +
                "title TEXT)"
        db?.execSQL(q1)


        val query = (("CREATE TABLE " + TABLE_NAME + " ("
                + ID_LIST) + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE) + " TEXT)"


        // at last we are calling a exec sql
        // method to execute above sql query

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query)
        // at last we are calling a exec sql
        // method to execute above sql query

        // at last we are calling a exec sql
        // method to execute above sql query







   /*     val q2 = "INSERT INTO favourites(id, title) " +
                "VALUES " +
                "(NULL, 'QuickLead')," +
                "(NULL, 'Dashboard')," +
                "(NULL, 'Report')"*/

        val q2 = "INSERT INTO favourites(id, title) " +
                "VALUES " +
                "(NULL, 'QuickLead')," +
                "(NULL, 'Dashboard')," +
                "(NULL, 'Lead')," +
                "(NULL, 'Service')," +
                "(NULL, 'Project')," +
                "(NULL, 'Report')"
        db?.execSQL(q2)



        //     db.execSQL("create table travel_location " + "(id integer primary key, date text,time text,battery text, address text)")


        //   db.execSQL("create table chat_all_user " + "(id integer primary key, name text, BranchName text, user_1 text,user_2 text,chatkey text)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO Auto-generated method stub

        db!!.execSQL("DROP TABLE IF EXISTS favourites")

        onCreate(db)
    }

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "favouritelist"

        // below is the variable for database version
        private val DATABASE_VERSION = 5

        // DATABASE_VERSION = 4 , table chat_user , add new colum 'userToken'
        // DATABASE_VERSION = 5 , create table company & ResellerDetails

    }

    fun query(query: String): Boolean {
        val db = this.writableDatabase
        db.execSQL(query)
        db.close()
        return true;
    } // query

    // Count
    fun count(query: String): Int {
        val db = this.writableDatabase
        val numRows =
            DatabaseUtils.longForQuery(db, query, null).toInt()
        db.close()
        return numRows;
    }

    // Get
    fun rawQuery(query: String?): Cursor? {
        val db = this.writableDatabase
        val mCursor: Cursor = db.rawQuery(query, null)
        mCursor?.moveToFirst()
        return mCursor
    }

    fun addNewitem(
        favarea: String?,
    ) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        val db = this.writableDatabase

        // on below line we are creating a
        // variable for content values.
        val values = ContentValues()

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(TITLE, favarea)


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values)

        // at last we are closing our
        // database after adding database.
        db.close()
    }

    fun itemExists(value: String): Boolean {
        val db = this.writableDatabase
        val Query = "Select * from $TABLE_NAME where $favtitle = '$value'"
        val cursor = db.rawQuery(Query, null)
        val bank = ""
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    fun getAllLabels(): List<InsertFavModel>? {
        /*    List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;*/
        val labels: ArrayList<InsertFavModel> = ArrayList<InsertFavModel>()
        // Select All Query
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0)
                val title = cursor.getString(1)

                labels.add(
                    InsertFavModel(
                        id,
                        title

                    )
                )
            } while (cursor.moveToNext())
        }

        // closing connection
        cursor.close()
        db.close()

        // returning lables
        return labels
    }

    fun deleteitem(title: String) {

        // on below line we are creating
        // a variable to write our database.
        val db = this.writableDatabase

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "title=?", arrayOf(title))
        db.close()
    }
    fun insert(
        title: String?

    ) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE, title)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

}