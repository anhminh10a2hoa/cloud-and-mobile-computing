package com.example.assignment7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
public class DBAdapter {
    //Here we define a tag foe the class
    private static final String TAG = "assignment10.DBAdapter";
    //Here we define database name
    private static final String DATABASE_NAME = "MyDB";
    private static final String TABLE_NAME = "user";
    //These are table column names
    private static final String KEY_ROW_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PARTICIPANTS = "participants";
    private static final String KEY_STARTTIME = "startTime";
    private static final int DATABASE_VERSION = 1;
    //Here we define SQL command to create the database table if it does not exist
    private static final String TABLE_CREATE ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + KEY_ROW_ID + " INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT NOT NULL, " + KEY_PARTICIPANTS + " TEXT NOT NULL, " + KEY_STARTTIME + " TEXT NOT NULL);";
    //Here we define SQL command for releting the table if it exists
    private static final String TABLE_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqlLiteDb;
    //Here we define the class constructor
    public DBAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }
    // Here we define the static class DatabaseHelper
    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(TABLE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ". All old data will be deleted! ");
// Here we remove the table
            db.execSQL(TABLE_DELETE);
// Here we create the table again
            onCreate(db);
        }
    }
    // This method will open the database
    public DBAdapter openDBConnection() {
        sqlLiteDb = dbHelper.getWritableDatabase();
        return this;
    }
    // This method will close the database
    public void closeDBConnection() {
        dbHelper.close();
    }
    // Here we add a customer to the database
    public long addCustomer(int id, String title, String participants, String startTime) {
        //Here we open database connection
        openDBConnection();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROW_ID, id);
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_PARTICIPANTS, participants);
        initialValues.put(KEY_STARTTIME, startTime);
        long updatedRows= sqlLiteDb.insert(TABLE_NAME, null, initialValues);
//Here we close database connection
        closeDBConnection();
        return updatedRows;
    }
    //This method will delete the customer with given ID
    public int deleteCustomer(long rowID) {
//Here we open database connection
        openDBConnection();
        int updatedRows=sqlLiteDb.delete(TABLE_NAME, KEY_ROW_ID + "=" + rowID, null);
//Here we close database connection
        closeDBConnection();
        return updatedRows;
    }
    //This method will retrieve all customers
    public String getAllCustomers() {
        StringBuilder queryResult = new StringBuilder();
        //Here we open database connection
        openDBConnection();
        Cursor cursor= sqlLiteDb.query(TABLE_NAME, new String[] { KEY_ROW_ID, KEY_TITLE, KEY_PARTICIPANTS,
                KEY_STARTTIME }, null, null, null, null, null);
        if(cursor !=null && cursor.moveToFirst()) {
            do {
                queryResult.append(KEY_ROW_ID + "=" + cursor.getString(0) + ", " + KEY_TITLE + "=" +  cursor.getString(1) + ", "  + KEY_PARTICIPANTS + "=" + cursor.getString(2) + ", "  + KEY_STARTTIME + "=" + cursor.getString(3) + "\n\n");
            } while(cursor.moveToNext());
        }
        //Here we close database connection
        closeDBConnection();
        return  queryResult.toString();
    }
    // This method will retrieve a particular customer.
//The Cursor exposes results from a query on a SQLiteDatabase.
    public String getCustomer(long rowID) {
        StringBuilder queryResult = new StringBuilder();
        //Here we open database connection
        openDBConnection();
//        Cursor cursor = sqlLiteDb.query(true, TABLE_NAME, new String[] {
//                        KEY_ROW_ID, KEY_TITLE, KEY_PARTICIPANTS, KEY_STARTTIME }, KEY_ROW_ID + "=" + rowID,
//                null, null, null, null, null);
        Cursor cursor = sqlLiteDb.rawQuery("SELECT * FROM " + TABLE_NAME  + " WHERE " + KEY_ROW_ID + " = "+rowID, null);
        if(cursor != null && cursor.moveToFirst()) {
            queryResult.append(KEY_ROW_ID + "=" + cursor.getString(0) + ", " + KEY_TITLE + "=" +  cursor.getString(1) + ", " + KEY_PARTICIPANTS + "=" + cursor.getString(2) + ", " + KEY_STARTTIME + "=" + cursor.getString(3));
        }
        //Here we close database connection
        closeDBConnection();
        return queryResult.toString();
    }

    public String getCustomerByParticipants(String participant) {
        StringBuilder queryResult = new StringBuilder();
        //Here we open database connection
        openDBConnection();
        Cursor cursor = sqlLiteDb.rawQuery("SELECT * FROM " + TABLE_NAME  + " WHERE " + KEY_PARTICIPANTS + " LIKE '%" +participant + "%'", null);
        if(cursor != null && cursor.moveToFirst()) {
            queryResult.append(KEY_ROW_ID + "=" + cursor.getString(0) + ", " + KEY_TITLE + "=" +  cursor.getString(1) + ", " + KEY_PARTICIPANTS + "=" + cursor.getString(2) + ", " + KEY_STARTTIME + "=" + cursor.getString(3));
        }
        //Here we close database connection
        closeDBConnection();
        return queryResult.toString();
    }
    public String getCustomerByTime(String time) {
        StringBuilder queryResult = new StringBuilder();
        //Here we open database connection
        openDBConnection();
        Cursor cursor = sqlLiteDb.rawQuery("SELECT * FROM " + TABLE_NAME  + " WHERE " + KEY_STARTTIME + " LIKE '%" +time + "%'", null);
        if(cursor != null && cursor.moveToFirst()) {
            queryResult.append(KEY_ROW_ID + "=" + cursor.getString(0) + ", " + KEY_TITLE + "=" +  cursor.getString(1) + ", " + KEY_PARTICIPANTS + "=" + cursor.getString(2) + ", " + KEY_STARTTIME + "=" + cursor.getString(3));
        }
        //Here we close database connection
        closeDBConnection();
        return queryResult.toString();
    }
    public String getCustomerByTimeAndParticipant(String time, String participant) {
        StringBuilder queryResult = new StringBuilder();
        //Here we open database connection
        openDBConnection();
        Cursor cursor = sqlLiteDb.rawQuery("SELECT * FROM " + TABLE_NAME  + " WHERE " + KEY_STARTTIME + " LIKE '%" +time + "%' AND " + KEY_PARTICIPANTS + " LIKE '%" +participant + "%'", null);

        if(cursor != null && cursor.moveToFirst()) {
            queryResult.append(KEY_ROW_ID + "=" + cursor.getString(0) + ", " + KEY_TITLE + "=" +  cursor.getString(1) + ", " + KEY_PARTICIPANTS + "=" + cursor.getString(2) + ", " + KEY_STARTTIME + "=" + cursor.getString(3));
        }
        //Here we close database connection
        closeDBConnection();
        return queryResult.toString();
    }
    //This method will update the customer data with given ID
    public int updateCustomer(long rowID, String title, String participants, String startTime) {
        //Here we open database connection
        openDBConnection();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_PARTICIPANTS, participants);
        values.put(KEY_STARTTIME, startTime);
        int updatedRows=sqlLiteDb.update(TABLE_NAME, values, KEY_ROW_ID + "=" + rowID,
                null);
        //Here we close database connection
        closeDBConnection();
        return updatedRows;
    }
}
