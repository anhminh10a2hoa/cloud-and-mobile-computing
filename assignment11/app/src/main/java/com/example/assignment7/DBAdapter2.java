package com.example.assignment7;

import java.io.ByteArrayOutputStream;
import java.util.Vector;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
public class DBAdapter2 {
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
    public DBAdapter2(Context context, String dbPath, String dbName, String tableName) {
        this.context = context;
        this.dbPath = dbPath;
        DBAdapter2.dbName = dbName;
        DBAdapter2.tableName = tableName;
        tableColumnNames=context.getResources().getStringArray(R.array.table_images_column_names);
        tableColumnTypes=context.getResources().getStringArray(R.array.table_images_column_types);
        TAG = context.getString(R.string.db_class_tag2);
        TABLE_CREATE_QUERY = "CREATE TABLE " + tableName + " (" + tableColumnNames[0] + "  " + tableColumnTypes[0] +  " " +
                "primary key, " + tableColumnNames[1] +"  " + tableColumnTypes[1] + " not null, " + tableColumnNames[2] + "  " + tableColumnTypes[2]  + " not null);";
        TABLE_DELETE_QUERY = "DROP TABLE IF EXISTS " + tableName;
        dbHelper = new DatabaseHelper(context);
    }
    public DBAdapter2(){}
    //Here we define the DatabaseHelper class
    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            //super(context, DATABASE_NAME, null, DATABASE_VERSION);
            super(context, dbName, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(TABLE_CREATE_QUERY);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, new DBAdapter2().context.getString(R.string.upgrading_db_version) + " " + oldVersion + " to "
                    + newVersion + ". " + new DBAdapter2().context.getString(R.string.warning_data_delete));
            //Here we remove the table
            db.execSQL(TABLE_DELETE_QUERY);
            //Here we create the table again
            onCreate(db);
        }
    }
    //Here we establish connection to the database
    public DBAdapter2 openDBConnection() {
        //sqlLiteDb=dbHelper.getWritableDatabase();
        sqlLiteDb = SQLiteDatabase.openDatabase(dbPath + dbName, null, SQLiteDatabase.OPEN_READWRITE);
        return this;
    }
    //Here we close connection to the database
    public void closeDBConnection() {
        dbHelper.close();
    }
    //Here we add a customer to the database
    public void addImage(int id, String customerName, Bitmap image) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(tableColumnNames[1], customerName);
        initialValues.put(tableColumnNames[2], id);
        //Here we create a ByteArrayOutputStream object
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //Here we Write a compressed version of the bitmap to the specified
        //output stream. We need to specify the format of the compressed image
        //The quality: hint to the compressor, 0-100. 0 meaning compress for small
        //size, 100 meaning compress for max quality. Some formats, like PNG which
        //is lossless, will ignore the quality setting
        boolean compressResult = image.compress(CompressFormat.PNG, 50, outputStream);
        if (compressResult) {
            //Here we set the image to be saved under image column
            initialValues.put(tableColumnNames[0], outputStream.toByteArray());
            openDBConnection();
            sqlLiteDb.insert(tableName, null, initialValues);
            closeDBConnection();
        }
    }
    public boolean deleteCustomer(long rowID) {
        openDBConnection();
        boolean result = (sqlLiteDb.delete(tableName, tableColumnNames[2] + "=" + rowID, null) > 0);
        closeDBConnection();
        return result;
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
                dataRow[1]= cursor.getColumnName(1) + ": "  + cursor.getString(1) + "\n";
                dataRow[0]= cursor.getBlob(0);
                imgByte = cursor.getBlob(0);
                if(imgByte != null) {
                    imageView = new ImageView(context);
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
                    dataRow[0]=imageView;
                }
                dataRows.add(dataRow);
            } while(cursor.moveToNext());
        }
        return dataRows;
    }
    //This method will retrieve all customers
    public Vector<Object[]> getImage(int meetingId, String name) {
        openDBConnection();
        Cursor cursor = sqlLiteDb.rawQuery("SELECT * FROM " + tableName  + " WHERE " + tableColumnNames[1] + " LIKE '%" +name + "%'", null);
        Vector<Object[]> dataRows= getRowData(cursor);
        closeDBConnection();
        return dataRows;
    }
    //This method will update a customer
    public boolean updateCustomer(long rowID, String customerName, Bitmap image) {
        boolean compressionResult;
        boolean updateResult;
        ContentValues values = new ContentValues();
        values.put(tableColumnNames[1], customerName);
        //Here we create a ByteArrayOutputStream object
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //Here we Write a compressed version of the bitmap to the specified
        //output stream. We need to specify the format of the compressed image
        //The quality: hint to the compressor, 0-100. 0 meaning compress for small
        //size, 100 meaning compress for max quality. Some formats, like PNG which
        //is lossless, will ignore the quality setting
        compressionResult = image.compress(CompressFormat.PNG, 0, outputStream);
        if (compressionResult) {
            //Here we add image byte array to the set of data which will
            //be written to the table
            values.put(tableColumnNames[0], outputStream.toByteArray());
            openDBConnection();
            updateResult = (sqlLiteDb.update(tableName, values, tableColumnNames[2] + "=" + rowID, null) > 0);
            closeDBConnection();
            return updateResult;
        }
        //Here we return false if image.compress() fails.
        return false;
    }
}
