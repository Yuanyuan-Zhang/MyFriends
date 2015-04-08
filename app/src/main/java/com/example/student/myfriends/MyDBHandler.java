package com.example.student.myfriends;

/**
 * Created by student on 2/25/15.
 */
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    Context ctx;
    String dbName="";
    String sub="";

    private static final String TABLE_FRIENDS = "myFriends";
    private static final String DB_PATH_SUFFIX = "/databases/";

    public MyDBHandler(Context context,String name) {
        super(context,name, null, DATABASE_VERSION);
        ctx = context;
        this.dbName = name;
        int names = dbName.lastIndexOf('.');
        if(names>=0) {
            sub = dbName.substring(0,names);
        }
        else{
            sub="";
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
    }

    public Friends Get_ContactDetails(int n) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_FRIENDS, null);
        cursor.moveToFirst();

            if (cursor != null && cursor.move(n)) {
                Friends cont = new Friends(cursor.getInt(0),cursor.getString(1), cursor.getString(2),cursor.getString(3));
                cursor.close();
                db.close();

                return cont;
            }
            return null;
    }

    public List Get_Name() {
        List nameList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FRIENDS, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            nameList.add(cursor.getString(1));  //add list
            //Log.i("TEST",cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return nameList;
    }

    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(dbName);
        // Path to the just created empty db
        String outFileName = getDatabasePath();
        // if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    private  String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + dbName;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(dbName);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying success from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }
}