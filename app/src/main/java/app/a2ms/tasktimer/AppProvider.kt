package app.a2ms.tasktimer

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log

/**
 * Provider for the TaskTimer app. This is the only class that knows about [AppDatabase]
 * */

private const val TAG = "AppProvider"
//constants for Uri and Android Authority
const val CONTENT_AUTHORITY = "app.a2ms.tasktimer.provider"

private const val TASKS = 100
private const val TASKS_ID = 101

private const val TIMINGS = 200
private const val TIMINGS_ID = 201

private const val TASK_DURATIONS = 400
private const val TASK_DURATIONS_ID = 401

val CONTENT_AUTHORITY_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")

class AppProvider : ContentProvider() {
    //variable to hold UriMatcher
    private val uriMatcher by lazy { buildUriMatch() }

    //create a matcher function to use the UriMatcher class
    private fun buildUriMatch(): UriMatcher {
        Log.d(TAG, "buildUriMatch: starts")
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        //e.g content//app.a2ms.tasktimer.provider/Tasks
        matcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME, TASKS)
        //e.g content//app.a2ms.tasktimer.provider/Tasks/8
        matcher.addURI(CONTENT_AUTHORITY, "${TasksContract.TABLE_NAME}/#", TASKS_ID)

        matcher.addURI(CONTENT_AUTHORITY, TimingsContract.TABLE_NAME, TIMINGS)
        matcher.addURI(CONTENT_AUTHORITY, "${TimingsContract.TABLE_NAME}/#", TIMINGS_ID)

//        matcher.addURI(CONTENT_AUTHORITY, DurationsContract.TABLE_NAME, TASK_DURATIONS)
//        matcher.addURI(CONTENT_AUTHORITY, "${DurationsContract.TABLE_NAME}/#", TASK_DURATIONS_ID)


        return matcher
    }

    override fun onCreate(): Boolean {
        Log.d(TAG, "onCreate: starts")
        return true
    }

    override fun getType(uri: Uri): String {
        val match = uriMatcher.match(uri)
        return when (match) {
            TASKS -> TasksContract.CONTENT_TYPE

            TASKS_ID -> TasksContract.CONTENT_ITEM_TYPE

            TIMINGS -> TimingsContract.CONTENT_TYPE

            TIMINGS_ID -> TimingsContract.CONTENT_ITEM_TYPE
//
//            TASK_DURATIONS -> DurationsContract.CONTENT_TYPE
//
//            TASK_DURATIONS_ID -> DurationsContract.CONTENT_ITEM_TYPE

            else -> throw IllegalArgumentException("unknown Uri: $uri")
        }
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?,
                       selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        Log.d(TAG, "query: called with uri $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "query: match is  $match")

        val queryBuilder: SQLiteQueryBuilder = SQLiteQueryBuilder()
        when (match) {
            TASKS -> queryBuilder.tables = TasksContract.TABLE_NAME

            TASKS_ID -> {
                queryBuilder.tables = TasksContract.TABLE_NAME
                val taskId = TasksContract.getId(uri)
                queryBuilder.appendWhere("${TasksContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$taskId")
            }

            TIMINGS -> queryBuilder.tables = TimingsContract.TABLE_NAME
            TIMINGS_ID -> {
                queryBuilder.tables = TimingsContract.TABLE_NAME
                val timingId = TimingsContract.getId(uri)
                queryBuilder.appendWhereEscapeString("${TimingsContract.Columns.ID} = ")
                queryBuilder.appendWhere("$timingId")

            }
//            TASK_DURATIONS -> queryBuilder.tables = DurationsContract.TABLE_NAME
//
//            TASK_DURATIONS_ID -> {
//                queryBuilder.tables = DurationsContract.TABLE_NAME
//                val durationId = DurationsContract.getId(uri)
//                queryBuilder.appendWhereEscapeString("${DurationsContract.Columns.ID} = ")
//                queryBuilder.appendWhere("$durationId")

// }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val db = AppDatabase.getInstance(context).readableDatabase
        val cursor = queryBuilder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder)
        Log.d(TAG, "query: rows in returned cursor = ${cursor.count}") // TODO remove this line

        return cursor


    }

    override fun insert(uri: Uri, values: ContentValues): Uri {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(uri: Uri, values: ContentValues, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
