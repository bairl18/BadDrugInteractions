package com.example.linnea.baddruginteractions2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Created by bairl18 on 9/29/2017.
 */

public class DBHandler extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "fdaDrugs";

    // Contacts table name
    private static final String TABLE_DRUGS = "drugs";

    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_APPL_NO = "appl_no";
    private static final String KEY_PRODUCT_NUM = "product_no";
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
        + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_APPL_NO + " TEXT, " + KEY_PRODUCT_NUM + " TEXT, " + KEY_FORM + " TEXT, "
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

}
