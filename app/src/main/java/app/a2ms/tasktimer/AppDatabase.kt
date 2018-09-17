package app.a2ms.tasktimer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Basic database class for the application
 *and the only class that should  use this is [AppProvider].
 */

private const val TAG = "AppDatabase"

private const val DATABASE_NAME = "TaskTimer.db"
private const val DATABASE_VERSION = 3

internal class AppDatabase constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    //use this only for logs: todo not to use for production code
    init {
        Log.d(TAG, "AppDatabase: initialisingit" +
                "" +
                "")
    }

    override fun onCreate(db: SQLiteDatabase) {
        //CREATE TABLE Tasks(-id INTEGER PRIMARY KEY NOT NULL, Name TEXT NOT NULL, SortOrder INTEGER);
        Log.d(TAG, "onCreate starts")
        val sSQL = """CREATE TABLE ${TasksContract.TABLE_NAME} (
            ${TasksContract.Colums.ID} INTEGER PRIMARY KEY NOT NULL,
            ${TasksContract.Colums.TASK_NAME} TEXT NOT NULL,
            ${TasksContract.Colums.TASK_DESCRIPTION} TEXT,
            ${TasksContract.Colums.TASK_SORT_ORDER} INTEGER);""".replaceIndent(" ") //to format the space at start of each line
        Log.d(TAG, sSQL)
        db.execSQL(sSQL) //remove the safe operator ? because db has benn created
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

