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

public class UserProfileHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "userDrugs";

    // Contacts table name
    private static final String TABLE_DRUGS = "user_drugs";

    // Drugs Table Columns names
    private static final String KEY_NAME = "drug_name";
    private static final String KEY_FORM = "form";
    private static final String KEY_INGREDIENT = "active_ingredient";

    public UserProfileHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Edit here- add column names    http://mobilesiri.com/android-sqlite-database-tutorial-using-android-studio/
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FDA_TABLE = "CREATE TABLE " + TABLE_DRUGS + "("
                + KEY_NAME + " TEXT NOT NULL, " + KEY_FORM + " TEXT, " + KEY_INGREDIENT + " TEXT" + ")";
        db.execSQL(CREATE_FDA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRUGS);
        // Creating tables again
        onCreate(db);
    }

    public void reset() {

        SQLiteDatabase db = this.getWritableDatabase();

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRUGS);

        onCreate(db);
    }

    // Add a drug
    public void addDrug(UserDrug drug) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.d("UserDrug", "name: " + drug.getDrug_name());

        values.put(KEY_NAME, drug.getDrug_name());
        values.put(KEY_FORM, drug.getForm());
        values.put(KEY_INGREDIENT, drug.getActive_ingredient());

        // Inserting Row
        db.insert(TABLE_DRUGS, null, values);
        db.close(); // Closing database connection
    }


    // Delete a drug
    public void deleteDrug(UserDrug drug) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DRUGS, KEY_NAME + " = ?",
                new String[]{drug.getDrug_name()});
        db.close();
    }

    // Get one drug
    public UserDrug searchDrug(String drugname) {
        SQLiteDatabase db = this.getReadableDatabase();

        UserDrug drug = null;

        Cursor cursor = db.query( TABLE_DRUGS,
                new String[] { KEY_NAME, KEY_FORM, KEY_INGREDIENT},
                KEY_NAME + "=?", new String[]{drugname}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            if (cursor.getCount() != 0) {
                drug = new UserDrug(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            }

            cursor.close();

        }

        return drug;
    }


    public List<UserDrug> searchDrugByForm(String form) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query( TABLE_DRUGS,
                new String[] {KEY_NAME, KEY_FORM, KEY_INGREDIENT},
                KEY_FORM + "=?", new String[]{form}, null, null, null, null);

        List<UserDrug> drugs = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                UserDrug drug = new UserDrug( cursor.getString(0), cursor.getString(1), cursor.getString(2));

                drugs.add(drug);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());

            cursor.close();

        }

        return drugs;
    }

    public List<UserDrug> searchDrugByIngredient(String ingredient) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query( TABLE_DRUGS,
                new String[] {KEY_NAME, KEY_FORM, KEY_INGREDIENT},
                KEY_INGREDIENT + "=?", new String[]{ingredient}, null, null, null, null);

        List<UserDrug> drugs = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                UserDrug drug = new UserDrug( cursor.getString(0), cursor.getString(1), cursor.getString(2));

                drugs.add(drug);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());

            cursor.close();

        }

        return drugs;
    }

    public int countRows() {
        String query = "SELECT count(*) FROM " + TABLE_DRUGS;
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    public List<UserDrug> asList(){
        List <UserDrug> drugList = new ArrayList<>();
        String userQuery = "SELECT * FROM " + TABLE_DRUGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(userQuery, null);
        if (cursor.moveToFirst()){
            do{
                UserDrug drug = new UserDrug(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2));
                drugList.add(drug);
            } while(cursor.moveToNext());
        }
        return drugList;
    }
}