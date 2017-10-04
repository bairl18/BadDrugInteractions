package com.example.linnea.baddruginteractions2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Created by bairl18 on 9/29/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "fdaDrugs";

    // Contacts table name
    private static final String TABLE_DRUGS = "drugs";

    // Drugs Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_APPL_NO = "appl_no";
    private static final String KEY_PRODUCT_NO = "product_no";
    private static final String KEY_FORM = "form";
    private static final String KEY_REFERENCE = "reference_drug";
    private static final String KEY_NAME = "drug_name";
    private static final String KEY_INGREDIENT = "active_ingredient";
    private static final String KEY_STANDARD = "reference_standard";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Edit here- add column names    http://mobilesiri.com/android-sqlite-database-tutorial-using-android-studio/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FDA_TABLE = "CREATE TABLE " + TABLE_DRUGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_APPL_NO + " TEXT, " + KEY_PRODUCT_NO + " TEXT, " + KEY_FORM + " TEXT, "
                + KEY_REFERENCE + " TEXT, " + KEY_NAME + " TEXT, " + KEY_INGREDIENT + " TEXT, " + KEY_STANDARD + " TEXT" + ")";
        db.execSQL(CREATE_FDA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_DRUGS);
        // Creating tables again
        onCreate(db);
    }

    // Add a drug
    public void addDrug(Drug drug) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, drug.getDrug_name()); // Drug Name
        values.put(KEY_APPL_NO, drug.getAppl_no()); // Drug Appl No

        // Inserting Row
        db.insert(TABLE_DRUGS, null, values);
        db.close(); // Closing database connection
    }


    // Delete a drug
    public void deleteDrug(Drug drug) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DRUGS, KEY_ID + " = ?",
                new String[]{String.valueOf(drug.getId())});
        db.close();
    }

    // Get one drug
    public Drug getDrug(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DRUGS, new String[]{KEY_ID, KEY_NAME, KEY_APPL_NO}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) cursor.moveToFirst();

        Drug contact = new Drug();

        // return shop
        return contact;
    }
}
