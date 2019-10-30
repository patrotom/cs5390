package edu.utep.cs.cs4330.todotoday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ToDoDatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "todoDB";
    private static final String TODO_TABLE = "items";

    private static final String KEY_ID = "_id";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DONE = "done";

    public ToDoDatabaseHelper(Context context){
        super (context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + TODO_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_DESCRIPTION + " TEXT, "
                + KEY_DONE + " INTEGER" + ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(database);
    }

    public void addItem(ToDoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION, item.description()); // task name
        values.put(KEY_DONE, item.isDone() ? 1 : 0);
        long id = db.insert(TODO_TABLE, null, values);
        item.setId((int) id);
        db.close();
    }

    public List<ToDoItem> allItems() {
        List<ToDoItem> todoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TODO_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String description = cursor.getString(1);
                boolean done = cursor.getInt(2) == 1;
                ToDoItem task = new ToDoItem(id, description, done);
                todoList.add(task);
            } while (cursor.moveToNext());
        }
        return todoList;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, null, new String[]{});
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TODO_TABLE, KEY_ID + " = ?", new String[] { Integer.toString(id) } );
        db.close();
    }

    public void update(ToDoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION, item.description());
        values.put(KEY_DONE, item.isDone() ? 1 : 0);
        db.update(TODO_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(item.id())});
        db.close();
    }

}
