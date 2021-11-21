package com.example.assignment7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import java.util.Vector;
public class DBAdapter {
    private static String[] tableColumnNames, tableColumnTypes;
    private static  String TAG;
    private static String tableName;
    private static final int DATABASE_VERSION = 1;
    private static String TABLE_CREATE_QUERY;
    private static String TABLE_DELETE_QUERY;
    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqlLiteDb;
    private String dbPath;
    private static String dbName;

    //Here we define the constructor
    public DBAdapter(Context context, String dbPath, String dbName,
                     String tableName) {
        this.context = context;
        this.dbPath = dbPath;
        DBAdapter.dbName = dbName;
        DBAdapter.tableName = tableName;
        tableColumnNames=context.getResources().getStringArray(R.array.table_customer_column_names);
        tableColumnTypes=context.getResources().getStringArray(R.array.table_customer_column_types);
        TAG = context.getString(R.string.db_class_tag);
        TABLE_CREATE_QUERY = "CREATE TABLE " + tableName + " (" + tableColumnNames[0] + "  " + tableColumnTypes[0] +  " " +
                "primary key, " + tableColumnNames[1] +"  " + tableColumnTypes[1] + " not null, " + tableColumnNames[2] + "  " + tableColumnTypes[2] + " not null, " + tableColumnNames[3] + "  " + tableColumnTypes[3] + " not null);";
        TABLE_DELETE_QUERY = "DROP TABLE IF EXISTS " + tableName;
        dbHelper = new DatabaseHelper(context);
    }
    //Here we define the DatabaseHelper class
    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            //super(context, DATABASE_NAME, null, DATABASE_VERSION);
            super(context, dbName, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
             * try { db.execSQL(TABLE_CREATE); }catch(SQLException e) {
             * e.printStackTrace(); }
             */
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, new DBAdapter().context.getString(R.string.version_upgrade) + oldVersion + " to "
                    + newVersion + ". " + new DBAdapter().context.getString(R.string.warning_data_delete));
            //Here we remove the table
            db.execSQL(TABLE_DELETE_QUERY);
            //Here we create the table again
            onCreate(db);
        }
    }
    public DBAdapter(){}
    //This method will open the database
    public DBAdapter openDBConnection() {
        sqlLiteDb = SQLiteDatabase.openDatabase(dbPath + dbName, null,
                SQLiteDatabase.OPEN_READWRITE);
        return this;
    }
    //This method will close the database
    public void closeDBConnection() {
        dbHelper.close();
    }
    //Here we add a customer to the database
    public long addCustomer(int id, String title, String participants, String startTime) {
        openDBConnection();
        ContentValues initialValues = new ContentValues();
        initialValues.put(tableColumnNames[0], id);
        initialValues.put(tableColumnNames[1], title);
        initialValues.put(tableColumnNames[2], participants);
        initialValues.put(tableColumnNames[3], startTime);
        return sqlLiteDb.insert(tableName, null, initialValues);
    }
    public boolean deleteCustomer(long rowID) {
        return (sqlLiteDb.delete(tableName, tableColumnNames[0] + "=" + rowID, null) > 0);
    }
    private Vector<Object[]> getRowData(Cursor cursor){
        Object[] dataRow;
        Vector<Object[]> dataRows = new Vector<>();
        byte[] imgByte;
        ImageView imageView;
        //Here we go through each row of the result set and copy data of each row to an Object array
        if(cursor.moveToFirst()) {
            do {
                dataRow = new Object[cursor.getColumnCount()];
                dataRow[0]=  cursor.getColumnName(0) + ": " + cursor.getInt(0) + "\n";
                dataRow[1]= cursor.getColumnName(1) + ": "  + cursor.getString(1) + "\n";
                dataRow[2]=cursor.getColumnName(2) + ": " + cursor.getString(2) + "\n";
                dataRow[3]=cursor.getColumnName(3) + ": " + cursor.getString(3) + "\n";
                dataRows.add(dataRow);
            } while(cursor.moveToNext());
        }
        return dataRows;
    }
    //This method will retrieve all customers
    public Vector<Object[]> getAllCustomers() {
        openDBConnection();
        Cursor cursor = sqlLiteDb.query(tableName, new String[]{tableColumnNames[0], tableColumnNames[1], tableColumnNames[2], tableColumnNames[3]}, null, null, null, null, null);
        Vector<Object[]> dataRows= getRowData(cursor);
        closeDBConnection();
        return dataRows;
    }
    public Vector<Object[]> getCustomer(long rowID) {
        openDBConnection();
        Cursor cursor = sqlLiteDb.query(true, tableName, new String[]{tableColumnNames[0], tableColumnNames[1], tableColumnNames[2], tableColumnNames[3]},
                tableColumnNames[0] + "=" + rowID, null, null, null, null, null);
        Vector<Object[]> dataRows= getRowData(cursor);
        closeDBConnection();
        return dataRows;
    }
    public Vector<Object[]> getCustomerByParticipants(String participant) {
        openDBConnection();
        Cursor cursor = sqlLiteDb.rawQuery("SELECT * FROM " + tableName  + " WHERE " + tableColumnNames[2] + " LIKE '%" +participant + "%'", null);
        Vector<Object[]> dataRows= getRowData(cursor);
        closeDBConnection();
        return dataRows;
    }
    public Vector<Object[]> getCustomerByTime(String time) {
        openDBConnection();
        Cursor cursor = sqlLiteDb.rawQuery("SELECT * FROM " + tableName  + " WHERE " + tableColumnNames[3] + " LIKE '%" +time + "%'", null);
        Vector<Object[]> dataRows= getRowData(cursor);
        closeDBConnection();
        return dataRows;
    }
    public Vector<Object[]> getCustomerByTimeAndParticipant(String time, String participant) {
        openDBConnection();
        Cursor cursor = sqlLiteDb.rawQuery("SELECT * FROM " + tableName  + " WHERE " + tableColumnNames[3] + " LIKE '%" +time + "%' AND " + tableColumnNames[2] + " LIKE '%" +participant + "%'", null);
        Vector<Object[]> dataRows= getRowData(cursor);
        closeDBConnection();
        return dataRows;
    }
    //This method will update a customer
    public boolean updateCustomer(long rowID, String title, String participants, String startTime) {
        openDBConnection();
        ContentValues values = new ContentValues();
        values.put(tableColumnNames[1], title);
        values.put(tableColumnNames[2], participants);
        values.put(tableColumnNames[3], startTime);
        return (sqlLiteDb.update(tableName, values, tableColumnNames[0] + "=" + rowID,
                null) > 0);
    }
}