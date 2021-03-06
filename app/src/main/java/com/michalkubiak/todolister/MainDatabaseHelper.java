package com.michalkubiak.todolister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.michalkubiak.todolister.Models.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 04.04.16.
 */
public class MainDatabaseHelper extends SQLiteOpenHelper {

    private static MainDatabaseHelper lazyInstance;

    private static final String TAG = "mainDatabase";

    // Database Info
    private static final String DATABASE_NAME = "mainDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_SHOPPING = "shopping";
    private static final String TABLE_MEETING = "meeting";
    private static final String TABLE_REMINDER = "reminder";

    // Shopping Table Columns
    private static final String KEY_SHOPPING_ID = "id";
    private static final String KEY_SHOPPING_TEXT = "text";
    private static final String KEY_SHOPPING_TIME_CREATED = "timeCreated";
    private static final String KEY_SHOPPING_TIME_COMPLETED = "timeCompleted";
    private static final String KEY_SHOPPING_IS_CHECKED = "isCheked";

    // Meeting Table Columns
    private static final String KEY_MEETING_ID = "id";
    private static final String KEY_MEETING_TEXT = "text";
    private static final String KEY_MEETING_TIME_CREATED = "timeCreated";
    private static final String KEY_MEETING_TIME_COMPLETED = "timeCompleted";
    private static final String KEY_MEETING_IS_CHECKED = "isCheked";

    // Reminder Table Columns
    private static final String KEY_REMINDER_ID = "id";
    private static final String KEY_REMINDER_TEXT = "text";
    private static final String KEY_REMINDER_TIME_CREATED = "timeCreated";
    private static final String KEY_REMINDER_TIME_COMPLETED = "timeCompleted";
    private static final String KEY_REMINDER_IS_CHECKED = "isCheked";


    private MainDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHOPPING_TABLE = "CREATE TABLE " + TABLE_SHOPPING +
                "(" +
                KEY_SHOPPING_ID + " INTEGER PRIMARY KEY, " +
                KEY_SHOPPING_TEXT + " TEXT, " +
                KEY_SHOPPING_TIME_CREATED + " DATETIME, " +
                KEY_SHOPPING_TIME_COMPLETED + " DATETIME, " +
                KEY_SHOPPING_IS_CHECKED + " BOOLEAN" +
                ")";

        String CREATE_MEETING_TABLE = "CREATE TABLE " + TABLE_MEETING +
                "(" +
                KEY_MEETING_ID + " INTEGER PRIMARY KEY, " +
                KEY_MEETING_TEXT + " TEXT, " +
                KEY_MEETING_TIME_CREATED + " DATETIME, " +
                KEY_MEETING_TIME_COMPLETED + " DATETIME, " +
                KEY_MEETING_IS_CHECKED + " BOOLEAN" +
                ")";

        String CREATE_REMINDER_TABLE = "CREATE TABLE " + TABLE_REMINDER +
                "(" +
                KEY_REMINDER_ID + " INTEGER PRIMARY KEY, " +
                KEY_REMINDER_TEXT + " TEXT, " +
                KEY_REMINDER_TIME_CREATED + " DATETIME, " +
                KEY_REMINDER_TIME_COMPLETED + " DATETIME, " +
                KEY_REMINDER_IS_CHECKED + " BOOLEAN" +
                ")";

        db.execSQL(CREATE_SHOPPING_TABLE);
        db.execSQL(CREATE_MEETING_TABLE);
        db.execSQL(CREATE_REMINDER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEETING);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
            onCreate(db);
        }
    }

    public static synchronized MainDatabaseHelper getInstance(Context context) {

        if (lazyInstance == null) {
            lazyInstance = new MainDatabaseHelper(context.getApplicationContext());
        }
        return lazyInstance;
    }

    public void addShoppingItem(ListItem listItem) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_SHOPPING_TEXT, listItem.itemText);
            values.put(KEY_SHOPPING_TIME_CREATED, listItem.timeCreated);
            values.put(KEY_SHOPPING_TIME_COMPLETED, listItem.timeCompleted);
            values.put(KEY_SHOPPING_IS_CHECKED, listItem.isCheked);

            db.insertOrThrow(TABLE_SHOPPING, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add listItem to database");
        } finally {
            db.endTransaction();
        }


    }

    public List<ListItem> getNewShoppingItems() {
        List<ListItem> listItems = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = 0",
                        TABLE_SHOPPING, KEY_SHOPPING_IS_CHECKED);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ListItem newListItem = new ListItem();
                    newListItem.itemText = cursor.getString(cursor.getColumnIndex(KEY_SHOPPING_TEXT));
                    newListItem.id = cursor.getInt(cursor.getColumnIndex(KEY_SHOPPING_ID));
                    listItems.add(newListItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return listItems;
    }

    public void updateShoppingItem(int id, boolean isCompleted) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(KEY_SHOPPING_IS_CHECKED, isCompleted ? 1 : 0);

            db.update(TABLE_SHOPPING, cv, KEY_SHOPPING_ID + " = " + id, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }

    public ListItem getShoppingItem(int itemID) {

        ListItem searchedItem = new ListItem();

        String POST_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = %s",
                        TABLE_SHOPPING, KEY_SHOPPING_ID, itemID);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POST_SELECT_QUERY, null);


        try {
            if (cursor.moveToFirst()) {
                searchedItem.itemText = cursor.getString(cursor.getColumnIndex(KEY_SHOPPING_TEXT));
                searchedItem.id = cursor.getInt(cursor.getColumnIndex(KEY_SHOPPING_ID));
            } else searchedItem = null;

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return searchedItem;
    }


    public void addReminderItem(ListItem listItem) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_REMINDER_TEXT, listItem.itemText);
            values.put(KEY_REMINDER_TIME_CREATED, listItem.timeCreated);
            values.put(KEY_REMINDER_TIME_COMPLETED, listItem.timeCompleted);
            values.put(KEY_REMINDER_IS_CHECKED, listItem.isCheked);

            db.insertOrThrow(TABLE_REMINDER, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add listItem to database");
        } finally {
            db.endTransaction();
        }


    }

    public List<ListItem> getNewReminderItems() {
        List<ListItem> listItems = new ArrayList<>();


        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = 0",
                        TABLE_REMINDER, KEY_REMINDER_IS_CHECKED);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ListItem newListItem = new ListItem();
                    newListItem.itemText = cursor.getString(cursor.getColumnIndex(KEY_REMINDER_TEXT));
                    newListItem.id = cursor.getInt(cursor.getColumnIndex(KEY_REMINDER_ID));
                    listItems.add(newListItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return listItems;
    }

    public void updateReminderItem(int id, boolean isCompleted) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(KEY_REMINDER_IS_CHECKED, isCompleted ? 1 : 0);

            db.update(TABLE_REMINDER, cv, KEY_REMINDER_ID + " = " + id, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }

    public ListItem getReminderItem(int itemID) {

        ListItem searchedItem = new ListItem();

        String POST_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = %s",
                        TABLE_REMINDER, KEY_REMINDER_ID, itemID);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POST_SELECT_QUERY, null);


        try {
            if (cursor.moveToFirst()) {
                searchedItem.itemText = cursor.getString(cursor.getColumnIndex(KEY_REMINDER_TEXT));
                searchedItem.id = cursor.getInt(cursor.getColumnIndex(KEY_REMINDER_ID));
            } else searchedItem = null;

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return searchedItem;
    }


    public void addMeetingItem(ListItem listItem) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_MEETING_TEXT, listItem.itemText);
            values.put(KEY_MEETING_TIME_CREATED, listItem.timeCreated);
            values.put(KEY_MEETING_TIME_COMPLETED, listItem.timeCompleted);
            values.put(KEY_MEETING_IS_CHECKED, listItem.isCheked);

            db.insertOrThrow(TABLE_MEETING, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add listItem to database");
        } finally {
            db.endTransaction();
        }


    }

    public List<ListItem> getNewMeetingItems() {
        List<ListItem> listItems = new ArrayList<>();


        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = 0",
                        TABLE_MEETING, KEY_MEETING_IS_CHECKED);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ListItem newListItem = new ListItem();
                    newListItem.itemText = cursor.getString(cursor.getColumnIndex(KEY_MEETING_TEXT));
                    newListItem.id = cursor.getInt(cursor.getColumnIndex(KEY_MEETING_ID));
                    listItems.add(newListItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return listItems;
    }

    public void updateMeetingItem(int id, boolean isCompleted) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(KEY_MEETING_IS_CHECKED, isCompleted ? 1 : 0);

            db.update(TABLE_MEETING, cv, KEY_MEETING_ID + " = " + id, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }

    }

    public ListItem getMeetingItem(int itemID) {

        ListItem searchedItem = new ListItem();

        String POST_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = %s",
                        TABLE_MEETING, KEY_MEETING_ID, itemID);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POST_SELECT_QUERY, null);


        try {
            if (cursor.moveToFirst()) {
                searchedItem.itemText = cursor.getString(cursor.getColumnIndex(KEY_MEETING_TEXT));
                searchedItem.id = cursor.getInt(cursor.getColumnIndex(KEY_MEETING_ID));
            } else searchedItem = null;

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return searchedItem;
    }
}
