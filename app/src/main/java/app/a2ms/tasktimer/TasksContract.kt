package app.a2ms.tasktimer

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

object TasksContract {
    //use internal access modifier so the  name of our table can only
    // be visible inside our package
    //define the name of the table
    internal const val TABLE_NAME = "Tasks" // we can use const inside an object but can't in class
    /**
     * The URI to access the Tasks table
     * */
    val CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vdn.$CONTENT_AUTHORITY.$TABLE_NAME"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"

    /**
     * Tasks fields : [TasksContract] constructor(object don't have constructor): to be used to refer to our table columns name
     */
    object Columns {
        const val ID = BaseColumns._ID  //The unique ID for a row.
        const val TASK_NAME = "Name"
        const val TASK_DESCRIPTION = "Description"
        const val TASK_SORT_ORDER = "SortOrder"
    }

    fun getId(uri: Uri): Long {
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }
}
