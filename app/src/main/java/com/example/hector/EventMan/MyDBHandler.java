package com.example.hector.EventMan;

import android.app.usage.UsageEvents;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteStatement;

import java.sql.SQLDataException;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "events.db";
    public static final  String TABLE_EVENTS = "events";
    public static final  String COLUMN_ID = "_id";
    public static final  String COLUMN_EVENT_NAME = "eventname";
    public static final  String COLUMN_EVENT_DIR = "direction";
    public static final  String COLUMN_EVENT_TIME = "evtime";
    public static final  String COLUMN_EVENT_USETIME = "evusetime";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_EVENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_NAME + " TEXT, " +
                COLUMN_EVENT_DIR + " INTEGER, " +
                COLUMN_EVENT_TIME + " INTEGER, " +
                COLUMN_EVENT_USETIME + " INTEGER " +
                ");";
        try{
        db.execSQL(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);

    }

    //Add a new row to the database
    public void addEvent(Events event) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, event.get_eventname());
        values.put(COLUMN_EVENT_DIR, event.get_direction());
        values.put(COLUMN_EVENT_TIME, event.get_evtime());
        values.put(COLUMN_EVENT_USETIME, event.get_evusetime());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EVENTS, null, values);
        db.close();

    }

    public String getEventIDs() {

        String dbString = "";
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT _id FROM " + TABLE_EVENTS + ";";
        // System.out.println("!!- "  + query);
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            //System.out.println("!!- "  + "in while " + c.getCount());
            if (c.getString(c.getColumnIndex("_id")) != null) {
                dbString += c.getString(c.getColumnIndex("_id"));
                dbString += ":"; // Delimiter between record IDs
                c.moveToNext();
            }
        }
        //System.out.println("!!- "  + dbString);
        db.close();
        return dbString;
    }

    public String getAllEvents() {

        String dbString = "";
        SQLiteDatabase db = getReadableDatabase();
        //SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS +" WHERE 1;";
       // System.out.println("!!- "  + query);
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            //System.out.println("!!- "  + "in while " + c.getCount());
            if (c.getString(c.getColumnIndex("eventname")) != null) {
                dbString += c.getString(c.getColumnIndex("_id"));
                dbString += "::"; // Delimiter between record ID & event title
                dbString += c.getString(c.getColumnIndex("eventname"));
                dbString += "~"; // Record delimiter
                c.moveToNext();
            }

        }
        db.close();
        return dbString;
    }

    // Getting a single event
    public  Events getMyEvent(String rowid) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] { COLUMN_ID,
                        COLUMN_EVENT_NAME, COLUMN_EVENT_DIR,COLUMN_EVENT_TIME,COLUMN_EVENT_USETIME }, COLUMN_ID + "=?",
                new String[] { rowid }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        //System.out.println("!!- "  + "checkbox is " + Integer.parseInt(cursor.getString(4)));

        return new Events(cursor.getString(1),
                Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)));
    }

    // Updating a single event
    public int updateEvent(Events myEvent) {
        //System.out.println("!!- "  + myEvent.get_id() + myEvent.get_eventname() + myEvent.get_evusetime() + myEvent.get_direction() + myEvent.get_evusetime());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, myEvent.get_eventname());
        values.put(COLUMN_EVENT_DIR, myEvent.get_direction());
        values.put(COLUMN_EVENT_TIME, myEvent.get_evtime());
        values.put(COLUMN_EVENT_USETIME, myEvent.get_evusetime());

        // updating row
        return db.update(TABLE_EVENTS, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(myEvent.get_id()) });
    }

    //Delete a row from the database
    public boolean deleteEvent (String eventID) {

        boolean result = false;

        SQLiteDatabase db = getWritableDatabase();
        try {
            //db.execSQL("DELETE FROM " + TABLE_EVENTS + " WHERE " + COLUMN_EVENT_NAME + "=\"" + eventName + "\";");
            db.execSQL("DELETE FROM " + TABLE_EVENTS + " WHERE " + COLUMN_ID + "=\"" + eventID + "\";");
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Get row count
    public  long getRowCount() {

        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, TABLE_EVENTS);

    }

    public String dbtostring() {

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE 1;";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("eventname")) != null) {
                dbString += c.getString(c.getColumnIndex("eventname"));
                dbString += "\n";
            }

        }
        db.close();
        return dbString;
    }

}
