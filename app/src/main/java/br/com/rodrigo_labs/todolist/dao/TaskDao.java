package br.com.rodrigo_labs.todolist.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo_labs.todolist.model.Task;

public class TaskDao extends SQLiteOpenHelper {

    public TaskDao(Context context) {
        super(context, "todolist", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE task (id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT NOT NULL, " +
                "date TEXT, time TEXT, done INTEGER NOT NULL);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS task;";

        db.execSQL(sql);
        onCreate(db);
    }

    public void insertTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = new ContentValues();

        data.put("task", task.getTask());
        data.put("date", task.getDate());
        data.put("time", task.getTime());
        data.put("done", task.isDone());

        db.insert("task", null, data);
    }

    public List<Task> getTasks() {
        SQLiteDatabase db =getReadableDatabase();
        String sql = "SELECT * FROM task;";
        Cursor cursor = db.rawQuery(sql, null);

        List<Task> tasks = new ArrayList<>();
        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getLong(cursor.getColumnIndex("id")));
            task.setTask(cursor.getString(cursor.getColumnIndex("task")));
            task.setDate(cursor.getString(cursor.getColumnIndex("date")));
            task.setTime(cursor.getString(cursor.getColumnIndex("time")));
            task.setDone(cursor.getInt(cursor.getColumnIndex("done")));

            tasks.add(task);
        }

        cursor.close();
        return tasks;
    }
}
