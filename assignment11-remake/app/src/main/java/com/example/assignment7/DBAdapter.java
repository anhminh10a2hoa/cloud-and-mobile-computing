package com.example.assignment7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Vector;

public class DBAdapter {

    // define a tag for the class
    private static final String TAG = "assignment7.DBAdapter";

    // define database name
    private static final String MEETINGS_TABLE_NAME = "customer";
    private static final String PARTICIPANTS_TABLE_NAME = "participants";
    private static final int DATABASE_VERSION = 1;
    // define database column names
    private static final String MEETINGS_KEY_ROW_ID = "id";
    private static final String MEETINGS_KEY_TITLE = "title";
    private static final String MEETINGS_KEY_START_TIME = "starttime";

    private static final String PARTICIPANTS_KEY_MEETING_ID = "meeting_id";
    private static final String PARTICIPANTS_KEY_NAME = "name";
    private static final String PARTICIPANTS_KEY_IMAGE = "image";

    // SQL command for creating table if not exist
    private static final String MEETINGS_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + MEETINGS_TABLE_NAME
            + " (" + MEETINGS_KEY_ROW_ID + " INTEGER PRIMARY KEY, "
            + MEETINGS_KEY_TITLE + " TEXT, "
            + MEETINGS_KEY_START_TIME + " TEXT);";
    private static final String PARTICIPANTS_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + PARTICIPANTS_TABLE_NAME
            + " (" + PARTICIPANTS_KEY_MEETING_ID + " INTEGER, "
            + PARTICIPANTS_KEY_NAME + " TEXT, "
            + PARTICIPANTS_KEY_IMAGE + " BLOB);";
    // SQL command for deleting table if exist
    private static final String MEETINGS_TABLE_DELETE = "DROP TABLE IF EXISTS " + MEETINGS_TABLE_NAME;
    private static final String PARTICIPANTS_TABLE_DELETE = "DROP TABLE IF EXISTS " + PARTICIPANTS_TABLE_NAME;
    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqLiteDb;
    private String dbPath;
    private static String dbName;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, dbName, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(MEETINGS_TABLE_CREATE);
                db.execSQL(PARTICIPANTS_TABLE_CREATE);
                Log.w(TAG, "TABLE CREATED!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ". All old data will be deleted!");
            // Here we remove the table
            db.execSQL(MEETINGS_TABLE_DELETE);
            db.execSQL(PARTICIPANTS_TABLE_DELETE);
            // Here we create the table again
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Downgrading database from version " + oldVersion + " to "
                    + newVersion + ". All old data will be deleted!");
            // Here we remove the table
            db.execSQL(MEETINGS_TABLE_DELETE);
            db.execSQL(PARTICIPANTS_TABLE_DELETE);
            // Here we create the table again
            onCreate(db);
        }
    }

    public DBAdapter(Context context, String dbPath, String dbName) {
        this.context = context;
        this.dbPath = dbPath;
        DBAdapter.dbName = dbName;
        dbHelper = new DatabaseHelper(context);
    }

    public DBAdapter openDbConnection() {
        sqLiteDb = SQLiteDatabase.openDatabase(dbPath + dbName, null,
                SQLiteDatabase.OPEN_READWRITE);
        return this;
    }

    public void closeDbConnection() {
        dbHelper.close();
    }

    public long addCustomer(int rowId, String title, String startTime, ArrayList<String> participants, ArrayList<Bitmap> images) {
        long updatedRows = 0;
        openDbConnection();
        // set values
        ContentValues values = new ContentValues();
        values.put(MEETINGS_KEY_ROW_ID, rowId);
        values.put(MEETINGS_KEY_TITLE, title);
        values.put(MEETINGS_KEY_START_TIME, startTime);

        updatedRows = sqLiteDb.insert(MEETINGS_TABLE_NAME, null, values);
        closeDbConnection();
        addImages(rowId, participants, images);
        return updatedRows;
    }

    public long addImages(int meetingId, ArrayList<String> participants, ArrayList<Bitmap> images) {
        long result = 0;
        openDbConnection();
        for (int i = 0; i < participants.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(PARTICIPANTS_KEY_MEETING_ID, meetingId);
            values.put(PARTICIPANTS_KEY_NAME, participants.get(i));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Bitmap image = images.get(i);
            boolean compressResult = image.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
            if (compressResult) {
                values.put(PARTICIPANTS_KEY_IMAGE, outputStream.toByteArray());
                result += sqLiteDb.insert(PARTICIPANTS_TABLE_NAME, null, values);
            } else {
                closeDbConnection();
                return -1;
            }
        }
        closeDbConnection();
        return result;
    }


    public int deleteMeeting(long rowId) {
        openDbConnection();
        int updatedRows = sqLiteDb.delete(MEETINGS_TABLE_NAME, MEETINGS_KEY_ROW_ID + "=" + rowId, null);
        updatedRows += sqLiteDb.delete(PARTICIPANTS_TABLE_NAME, PARTICIPANTS_KEY_MEETING_ID + "=" + rowId, null);
        closeDbConnection();
        return updatedRows;
    }

    private Vector<Object[]> getMeetingRowData(Cursor cursor) {
        Object[] dataRow;
        Vector<Object[]> dataRows = new Vector<>();

        if (cursor.moveToFirst()) {
            do {
                dataRow = new Object[cursor.getColumnCount() + 1];  // +1 for participants table
                dataRow[0] = cursor.getInt(0);      // id
                dataRow[1] = cursor.getString(1);   // title
                dataRow[2] = cursor.getString(2);   // date_time
                dataRow[3] = getRowParticipants((int) dataRow[0]);    // participants and images from participants table

                dataRows.add(dataRow);
            } while (cursor.moveToNext());
        }
        return dataRows;
    }

    private Vector<Object[]> getRowParticipants(int meeting_id) {
        Object[] dataRow;
        Vector<Object[]> dataRows = new Vector<>();
        byte[] imageByte;
        ImageView imageView;

        String[] selectedColumns = new String[]{PARTICIPANTS_KEY_NAME, PARTICIPANTS_KEY_IMAGE};
        String whereClause = PARTICIPANTS_KEY_MEETING_ID + "=" + meeting_id;

        Cursor cursor = sqLiteDb.query(PARTICIPANTS_TABLE_NAME, selectedColumns, whereClause,
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                dataRow = new Object[cursor.getColumnCount()];
                dataRow[0] = cursor.getString(0);
                imageByte = cursor.getBlob(1);
                if (imageByte != null) {
                    imageView = new ImageView(context);
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
                    dataRow[1] = imageView;
                }
                dataRows.add(dataRow);
            } while (cursor.moveToNext());
        }
        return dataRows;
    }

    public Vector<Object[]> getAllMeetings() {
        String[] selectedColumns = new String[]{
                MEETINGS_KEY_ROW_ID,
                MEETINGS_KEY_TITLE,
                MEETINGS_KEY_START_TIME};
        openDbConnection();
        // query
        Cursor cursor = sqLiteDb.query(MEETINGS_TABLE_NAME, selectedColumns,
                null, null, null, null, null);
        Vector<Object[]> result = getMeetingRowData(cursor);


        closeDbConnection();
        return result;
    }

    public Vector<Object[]> getMeetingsById(long rowId) {
        String[] selectedColumns = new String[]{
                MEETINGS_KEY_ROW_ID,
                MEETINGS_KEY_TITLE,
                MEETINGS_KEY_START_TIME};
        String whereClause = MEETINGS_KEY_ROW_ID + "=" + rowId;

        openDbConnection();
        // query
        Cursor cursor = sqLiteDb.query(true, MEETINGS_TABLE_NAME, selectedColumns, whereClause,
                null, null, null, null, null);
        closeDbConnection();
        Vector<Object[]> result = getMeetingRowData(cursor);
        return result;
    }

    public Vector<Object[]> searchMeetings(String participants, String startTime) {
        String tables = MEETINGS_TABLE_NAME + ", " + PARTICIPANTS_TABLE_NAME;

        String[] selectedColumns = new String[]{
                MEETINGS_TABLE_NAME + "." + MEETINGS_KEY_ROW_ID,
                MEETINGS_TABLE_NAME + "." + MEETINGS_KEY_TITLE,
                MEETINGS_TABLE_NAME + "." + MEETINGS_KEY_START_TIME};

        String whereClause = MEETINGS_TABLE_NAME + "." + MEETINGS_KEY_ROW_ID + "=" + PARTICIPANTS_TABLE_NAME + "." + PARTICIPANTS_KEY_MEETING_ID
                + " AND " + PARTICIPANTS_TABLE_NAME + "." + PARTICIPANTS_KEY_NAME + " LIKE '%" + participants + "%'"
                + " AND " + MEETINGS_TABLE_NAME + "." + MEETINGS_KEY_START_TIME + " LIKE '%" + startTime + "%'";

        openDbConnection();
        Cursor cursor = sqLiteDb.query(true, tables, selectedColumns, whereClause,
                null, null, null, null, null);
        Vector<Object[]> result = getMeetingRowData(cursor);
        return result;
    }

    public long updateCustomer(int rowId, String title, String startTime, ArrayList<String> participants, ArrayList<Bitmap> images) {
        openDbConnection();
        // update
        deleteMeeting(rowId);
        long updatedRows = addCustomer(rowId, title, startTime, participants, images);
        closeDbConnection();
        return updatedRows;
    }
}
