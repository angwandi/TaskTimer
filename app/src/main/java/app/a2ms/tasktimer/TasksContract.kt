package app.a2ms.tasktimer

import android.provider.BaseColumns

object TasksContract {
    //use internal access modifier so the  name of our table can only
    // be visible inside our package
    //define the name of the table
    internal const val TABLE_NAME = "Tasks" // we can use const inside an object but can't in class

    /**
     * Tasks fields : [TasksContract] constructor: to be used to refer to our table columns name
     */
    object Colums {
        const val ID = BaseColumns._ID  //The unique ID for a row.
        const val TASK_NAME = "Name"
        const val TASK_DESCRIPTION = "Description"
        const val TASK_SORT_ORDER = "SortOrder"
    }
}
