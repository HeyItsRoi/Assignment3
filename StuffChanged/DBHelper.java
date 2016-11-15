package com.example.heyitsroi.databaseactiviry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.sql.SQLException;

/**
 * Created by Roijan, Melina on 11/3/2016.
 */

public class DBHelper {
    public static final String KEY_CEGEPID = "cegep_id";
    public static final String KEY_CEGEPNAME = "cegep_name";
    public static final String KEY_CITY = "city";
    private static final String DATABASE_NAME = "CegepFriends";
    private static final String CEGEP_TABLE = "Cegep";


    private static final String DATABASE_CEGEPCREATE =
    "create table cegep (cegep_id integer primary key autoincrement, "
            + "cegep_name text not null, city text not null);";


    public static final String KEY_FRIENDID = "friend_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_AGE = "age";
    public static final String KEY_CID = "c_id";
    private static final String FRIEND_TABLE = "Friend";

    private static final String DATABASE_FRIENDCREATE =
            "create table friend (friend_id integer primary key autoincrement, "
                    + "name text not null, age integer not null, c_id integer not null, " +
                    "foreign key(c_id) references cegep(cegep_id));";

    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBAdapter";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBHelper(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CEGEPCREATE);
            db.execSQL(DATABASE_FRIENDCREATE);

        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }
    //---opens the database---
    public DBHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //CEGEP

    //---insert a contact into the database---
    public long insertCegepContact(String cegep_name, String city)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CEGEPNAME, cegep_name);
        initialValues.put(KEY_CITY, city);
        return db.insert("cegep", null, initialValues);
    }
    //---deletes a particular contact---
    public boolean deleteCegepContact(String name)
    {
        return db.delete(CEGEP_TABLE, KEY_CEGEPNAME + "=\'" + name + "\'", null) > 0;
    }
    //---retrieves all the contacts---
    public Cursor getAllCegepContacts()
    {
        return db.query("cegep", new String[] {KEY_CEGEPID, KEY_CEGEPNAME,
                KEY_CITY}, null, null, null, null, null);
    }
    //---retrieves a particular contact---
    public Cursor getCegepContact(String name) throws SQLException
    {
        Cursor mCursor =
                db.query(true, CEGEP_TABLE, new String[] {KEY_CEGEPID,
                        KEY_CEGEPNAME, KEY_CITY}, KEY_CEGEPNAME + "=\'" + name + "\'", null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //---updates a contact---
    public boolean updateCegepContact(long rowId, String name, String city)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_CEGEPNAME, name);
        args.put(KEY_CITY, city);
        return db.update(CEGEP_TABLE, args, KEY_CEGEPID + "=" + rowId, null) > 0;
    }


    //FRIEND

    //---insert a contact into the database---
    public long insertFriendContact(String name, int age, int c_id)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_AGE, age);
        initialValues.put(KEY_CID, c_id);
        return db.insert(FRIEND_TABLE, null, initialValues);
    }
    //---deletes a particular contact---
    public boolean deleteFriendContact(String name)
    {
        return db.delete(FRIEND_TABLE, KEY_NAME + "=\'" + name +"\'", null) > 0;
    }
    //---retrieves all the contacts---
    public Cursor getAllFriendContacts()
    {
        return db.query(FRIEND_TABLE, new String[] {KEY_FRIENDID, KEY_NAME,
                KEY_AGE, KEY_CID}, null, null, null, null, null);
    }
    //---retrieves a particular contact---
    public Cursor getFriendContact(String name) throws SQLException
    {
        Cursor mCursor =
                db.query(true, FRIEND_TABLE, new String[] {KEY_FRIENDID,
                                KEY_NAME, KEY_AGE, KEY_CID}, KEY_NAME + "=\'" + name + "\'", null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //---updates a contact---
    public boolean updateFriendContact(long rowId, String name, int age, int c_id)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_AGE, age);
        args.put(KEY_CID, c_id);
        return db.update(FRIEND_TABLE, args, KEY_FRIENDID + "=" + rowId, null) > 0;
    }
}
