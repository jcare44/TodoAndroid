package com.example.model;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import java.util.ArrayList;
        import java.util.List;

public class StorageHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_UP_0 =
            "CREATE TABLE Todo (todo_id INTEGER PRIMARY KEY, title TEXT, content TEXT, checked INTEGER)";

    private static final String SQL_DOWN_0 = "DROP TABLE IF EXISTS TODO";

    private static final String SQL_UP_1 =
            "ALTER Table Todo ADD date TEXT";

    private static final String SQL_DOWN_1 = "BEGIN TRANSACTION;" +
            "CREATE TEMPORARY TABLE todo_backup(todo_id,title,content);\n" +
            "INSERT INTO todo_backup SELECT todo_id,title,content FROM todo;\n" +
            "DROP TABLE todo;\n" +
            SQL_UP_0 +
            "INSERT INTO todo SELECT todo_id,title,content FROM todo_backup;\n" +
            "DROP TABLE todo_backup;\n" +
            "COMMIT;";

    private static final String SQL_UP_2 =
            "ALTER Table Todo ADD date_done TEXT";

    private static final String SQL_DOWN_2 = "BEGIN TRANSACTION;" +
            "CREATE TEMPORARY TABLE todo_backup(todo_id,title,content,date);\n" +
            "INSERT INTO todo_backup SELECT todo_id,title,content,date FROM todo;\n" +
            "DROP TABLE todo;\n" +
            SQL_UP_0 +
            "INSERT INTO todo SELECT todo_id,title,content,date FROM todo_backup;\n" +
            "DROP TABLE todo_backup;\n" +
            "COMMIT;";

    /*private static final String SQL_UP_3 =
            "ALTER Table Todo ADD checked INTEGER";*/

    public StorageHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_UP_0);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int currentVersion = oldVersion;
        Log.d("onUpgrade","sdf");
        if(currentVersion == 0 && newVersion > currentVersion) {
            db.execSQL(SQL_UP_1);
            currentVersion = 1;
        }
        if(currentVersion == 1 && newVersion > currentVersion) {
            db.execSQL(SQL_UP_2);
            currentVersion = 2;
        }
        /*if(currentVersion == 2 && newVersion > currentVersion) {
            Log.d("onUpgrade","SQL_UP_3");
            db.execSQL(SQL_UP_3);
            currentVersion = 3;
        }*/
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int currentVersion = oldVersion;
        if(currentVersion == 2 && newVersion < currentVersion) {
            db.execSQL(SQL_DOWN_2);
            currentVersion = 1;
        }
        if(currentVersion == 1 && newVersion < currentVersion) {
            db.execSQL(SQL_DOWN_1);
            currentVersion = 0;
        }
    }

    public void addTodo(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("checked", false);
        db.insert("Todo", null, values);
        db.close();
    }

    public Todo getTodo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Todo",                  //table
                new String[] { "todo_id", "title", "content", "checked" }, // columns
                "todo_id" + "=?",                               // WHERE clause
                new String[] { String.valueOf(id) },            // WHERE arguments
                null,                                           // GROUP BY
                null,                                           // HAVING
                null,                                           // ORDER BY
                null);                                          // LIMIT
        if (cursor != null)
            cursor.moveToFirst();

        Todo todo = new Todo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getInt(3)>0 ? true : false);
        return todo;
    }


    public List<Todo> getAll() {
        List<Todo> todoList = new ArrayList<Todo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM Todo";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getInt(3)>0 ? true : false);

                todoList.add(todo);
            } while (cursor.moveToNext());
        }

        return todoList;
    }

    public int count() {
        String countQuery = "SELECT  * FROM Todo";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", todo.getLabel());
        values.put("content", todo.getContent());
        values.put("checked", todo.isChecked());

        return db.update("Todo",
                values,
                "todo_id" + " = ?",
                new String[] { String.valueOf(todo.getId()) });
    }

    public void deleteTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Todo", "todo_id" + " = ?",
                new String[] { String.valueOf(todo.getId()) });
        db.close();
    }
}
