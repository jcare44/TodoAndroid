package com.todorfvj.model;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import org.joda.time.DateTime;
        import org.joda.time.format.DateTimeFormatter;
        import org.joda.time.format.ISODateTimeFormat;

        import java.sql.Date;
        import java.sql.Timestamp;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.logging.Logger;

public class StorageHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_INITIAL =
            "CREATE TABLE todo (id TEXT PRIMARY KEY, label TEXT, content TEXT, checked INT, deleted INT, creation TEXT, tags TEXT)";

    /*private static final String SQL_INITIAL_TO_2 =
            "ALTER TABLE todo ADD COLUMN  ; ALTER TABLE todo ADD COLUMN "; */

    /*private static final String SQL_2_TO_INITIAL =
            "ALTER TABLE todo DROP COLUMN date, ALTER TABLE todo DROP COLUMN tags";*/

    public StorageHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_INITIAL);
        //this.onUpgrade(db, 1, DATABASE_VERSION);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int currentVersion = oldVersion;
        Logger.getAnonymousLogger().warning("Upgrading from " + oldVersion + " to " + newVersion);
        if(currentVersion == 1 && newVersion > currentVersion) {
            //db.execSQL(SQL_INITIAL_TO_2); currentVersion = 2;
        }
        /*if(currentVersion == 1 && newVersion > currentVersion) {
            db.execSQL(SQL_UP_2); currentVersion = 2;
        }*/
        return ;
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int currentVersion = oldVersion;
        /*if(currentVersion == 2 && newVersion < currentVersion) {
            db.execSQL(SQL_DOWN_2); currentVersion = 1;
        }*/
        if(currentVersion == 2 && newVersion < currentVersion) {
            //db.execSQL(SQL_2_TO_INITIAL); currentVersion = 0;
        }
        return ;
    }

    public void insert(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Todo", null, this.getContentValues(todo));
        db.close();
    }

    public Todo select(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Todo",                  //table
                new String[] { "id", "label", "content", "checked", "creation", "tags", "deleted" }, // columns
                "id=?",                                         // WHERE clause
                new String[] { id },            // WHERE arguments
                null, null, null, null);

        if (cursor != null) cursor.moveToFirst();

        return(this.fromCursor(cursor)) ;

    }

    public List<Todo> selectAll(){ return(this.selectAll("")) ; }

    public List<Todo> selectAll(String filter) {
        List<Todo> todoList = new ArrayList<Todo>();
        // Select All Query
        String selectQuery = "SELECT * FROM Todo" ;
        if(filter != "") selectQuery += " WHERE label LIKE '%" + filter + "%'" ;
        selectQuery += " ORDER BY checked, creation" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do { todoList.add(this.fromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return todoList;
    }

    private ContentValues getContentValues(Todo todo){
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        ContentValues values = new ContentValues();
        values.put("id", todo.getId());
        values.put("label", todo.getLabel());
        values.put("content", todo.getContent());
        values.put("checked", todo.isChecked() ? 1 : 0);

        if(todo.getCreation() == null) values.putNull("creation");
        else values.put("creation", fmt.print(todo.getCreation()));

        if(todo.getReminder() == null) values.putNull("reminder");
        else values.put("reminder",fmt.print(todo.getReminder()));

        values.put("tags", todo.getTags());
        values.put("deleted", todo.isDeteled() ? 1 : 0);
        return(values) ;
    }

    private Todo fromCursor(Cursor cursor){

        Todo todo = new Todo(
            cursor.getString(cursor.getColumnIndex("id")),
            cursor.getString(cursor.getColumnIndex("label")),
            cursor.getString(cursor.getColumnIndex("content")),
            (cursor.getInt(cursor.getColumnIndex("checked")) == 1) ? true : false,
            cursor.getString(cursor.getColumnIndex("tags")),
            new DateTime(cursor.getString(cursor.getColumnIndex("reminder"))),
            (cursor.getInt(cursor.getColumnIndex("deleted")) == 1) ? true : false,
            new DateTime(cursor.getString(cursor.getColumnIndex("creation")))
        );

        return todo;
    }

    public int countAll() {
        String countQuery = "SELECT * FROM Todo";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int update(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.update("Todo",
                this.getContentValues(todo),
                "id" + " = ?",
                new String[] { todo.getId() });
    }

    public void delete(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Todo", "id" + " = ?",
                new String[] { todo.getId() });
        db.close();
    }
}
