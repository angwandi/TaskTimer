package app.a2ms.tasktimer

import android.content.ContentValues
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        testInsert()
//        testUpdate()
//        testUpdateTwo()
//        testDelete()
        testDeleteTwo()

        val projection = arrayOf(TasksContract.Columns.TASK_NAME, TasksContract.Columns.TASK_SORT_ORDER)
        val sortColumn = TasksContract.Columns.TASK_SORT_ORDER
        val cursor = contentResolver.query(TasksContract.CONTENT_URI,
//        val cursor = contentResolver.query(TasksContract.buildUriFromId(2),
                null,
                null,
                null,
                sortColumn)
        Log.d(TAG, "******************************************")
        cursor.use {
            while (it.moveToNext()) {
                //Cycle through all records
                with(cursor) {
                    val id = getLong(0)
                    val name = getString(1)
                    val description = getString(2)
                    val sortOrder = getString(3)
                    val result = "ID: $id. Name: $name. Description $description. Sort order: $sortOrder"
                    Log.d(TAG, "onCreate: reading data $result")
                }
            }
        }
        Log.d(TAG, "******************************************")

    }

    /**
     * Test Delete2 function [testDeleteTwo]
     * */
    private fun testDeleteTwo() {
        val selection = TasksContract.Columns.TASK_DESCRIPTION + "=?"
        val selectionArgs = arrayOf("For deletion completed")
        val rowsAffected = contentResolver.delete(TasksContract.CONTENT_URI,
                selection,
                selectionArgs)
        Log.d(TAG, "Number of rows deleted $rowsAffected")

    }

    /**
     * Test Delete function [testDelete]
     * */
    private fun testDelete() {
        val taskUri = TasksContract.buildUriFromId(3)
        val rowsAffected = contentResolver.delete(taskUri, null, null)
        Log.d(TAG, "Number of rows deleted is $rowsAffected")
    }

    /**
     * Test Update2 function [testUpdateTwo]
     * */
    private fun testUpdateTwo() {
        val values = ContentValues().apply {
            put(TasksContract.Columns.TASK_SORT_ORDER, 999)
            put(TasksContract.Columns.TASK_DESCRIPTION, "For deletion completed")
        }
        val selection = TasksContract.Columns.TASK_SORT_ORDER + "=?"
        val selectionArgs = arrayOf("99")
        val rowsAffected = contentResolver.update(TasksContract.CONTENT_URI,
                values,
                selection,
                selectionArgs)
        Log.d(TAG, "Number of rows affected $rowsAffected")

    }

    /**
     * Test Update function [testUpdate]
     * */
    private fun testUpdate() {
        val values = ContentValues().apply {
            put(TasksContract.Columns.TASK_NAME, "Content Provider")
            put(TasksContract.Columns.TASK_DESCRIPTION, "Record content provider video")
        }
        val taskUri = TasksContract.buildUriFromId(3)
        val rowsAffected = contentResolver.update(taskUri, values, null, null)
        Log.d(TAG, "Number of rows affected $rowsAffected")

    }

    /**
     * Test Insert function [testInsert]
     * */
    private fun testInsert() {
        val values = ContentValues().apply {
            put(TasksContract.Columns.TASK_NAME, "New Task 1")
            put(TasksContract.Columns.TASK_DESCRIPTION, "Description 1")
            put(TasksContract.Columns.TASK_SORT_ORDER, 2)
        }
        val uri = contentResolver.insert(TasksContract.CONTENT_URI, values)
        Log.d(TAG, "New row id(in uri) is $uri")
        Log.d(TAG, "id (in uri) is ${TasksContract.getId(uri)}")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.menumain_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
