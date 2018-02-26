package com.example.linnea.baddruginteractions2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.*;

import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Created by bairl18 on 9/29/2017.
 */

public class UserRemindersHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "userReminders";

    // Contacts table name
    private static final String TABLE_REMINDERS = "reminders";

    // Reminders Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DRUG_NAME = "drugName";
    private static final String KEY_START_DAY = "startDay";
    private static final String KEY_START_MONTH = "startMonth";
    private static final String KEY_START_YEAR = "startYear";
    private static final String KEY_TIME_HOUR = "timeHour";
    private static final String KEY_TIME_MINUTES = "timeMinutes";
    private static final String KEY_AM_PM = "amPm";
    private static final String KEY_FREQUENCY = "frequency";


    public UserRemindersHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_REMS_TABLE = "CREATE TABLE " + TABLE_REMINDERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_DRUG_NAME + " TEXT, " + KEY_START_DAY + " TEXT, " + KEY_START_MONTH
                + " TEXT, " + KEY_START_YEAR + " TEXT, " + KEY_TIME_HOUR + " TEXT, " + KEY_TIME_MINUTES + " TEXT, " + KEY_AM_PM + " TEXT, "
                + KEY_FREQUENCY + ")";
        db.execSQL(CREATE_REMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        // Creating tables again
        onCreate(db);
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);

        onCreate(db);
    }

    // Add a reminder
    public void addRem(Reminder rem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, rem.getId());
        values.put(KEY_DRUG_NAME, rem.getDrugName());
        values.put(KEY_START_DAY, rem.getStartDay());
        values.put(KEY_START_MONTH, rem.getStartMonth());
        values.put(KEY_START_YEAR, rem.getStartYear());
        values.put(KEY_TIME_HOUR, rem.getTimeHour());
        values.put(KEY_TIME_MINUTES, rem.getTimeMinutes());
        values.put(KEY_AM_PM, rem.getAmPm());
        values.put(KEY_FREQUENCY, rem.getFrequency());

        // Inserting Row
        db.insert(TABLE_REMINDERS, null, values);
        db.close(); // Closing database connection
    }


    // Delete a reminder
    public void deleteRem(Reminder rem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + " = ?",
                new String[]{String.valueOf(rem.getId())});
        db.close();
    }

    // Get one reminder
    public Reminder searchReminder(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Reminder rem = null;

        Cursor cursor = db.query( TABLE_REMINDERS,
                new String[] {KEY_ID, KEY_DRUG_NAME, KEY_START_DAY, KEY_START_MONTH, KEY_START_YEAR, KEY_TIME_HOUR, KEY_TIME_MINUTES, KEY_AM_PM, KEY_FREQUENCY},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            if (cursor.getCount() != 0) {
                rem = new Reminder(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)),
                        Integer.parseInt(cursor.getString(6)), cursor.getString(7), cursor.getString(8));
            }

            cursor.close();
        }

        return rem;
    }

    public int countRows() {
        String query = "SELECT count(*) FROM " + TABLE_REMINDERS;
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    public List<Reminder> asList(){
        List <Reminder> remList = new ArrayList<Reminder>();
        String userQuery = "SELECT * FROM " + TABLE_REMINDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(userQuery, null);
        if (cursor.moveToFirst()){
            do{
                Reminder rem = new Reminder(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                        cursor.getInt(5),cursor.getInt(6), cursor.getString(7), cursor.getString(8));
                remList.add(rem);
            } while(cursor.moveToNext());
        }
        return remList;
    }

    // Find the reminder with the highest id #
    public int getMaxId()
    {
        int max = 0;

        List<Reminder> remList = asList();

        for(int i = 0; i < remList.size(); i++)
        {

            Reminder current = remList.get(i);

            if(current.getId() > max)
            {
                max = current.getId();
            }

        }
        return max;
    }
}
