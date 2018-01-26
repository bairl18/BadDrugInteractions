package com.example.linnea.baddruginteractions2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.*;

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
    private static final String KEY_STRENGTH = "strength";
    private static final String KEY_REFERENCE = "reference_drug";
    private static final String KEY_NAME = "drug_name";
    private static final String KEY_INGREDIENT = "active_ingredient";
    private static final String KEY_STANDARD = "reference_standard";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // http://mobilesiri.com/android-sqlite-database-tutorial-using-android-studio/
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FDA_TABLE = "CREATE TABLE " + TABLE_DRUGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_APPL_NO + " TEXT, " + KEY_PRODUCT_NO + " TEXT, " + KEY_FORM + " TEXT, " + KEY_STRENGTH + " TEXT, "
                + KEY_REFERENCE + " TEXT, " + KEY_NAME + " TEXT, " + KEY_INGREDIENT + " TEXT, " + KEY_STANDARD + " TEXT" + ")";
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
    public void addDrug(Drug drug) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, drug.getId());
        values.put(KEY_APPL_NO, drug.getAppl_no());
        values.put(KEY_PRODUCT_NO, drug.getProduct_no());
        values.put(KEY_FORM, drug.getForm());
        values.put(KEY_STRENGTH, drug.getStrength());
        values.put(KEY_REFERENCE, drug.getReference_drug());
        values.put(KEY_NAME, drug.getDrug_name());
        values.put(KEY_INGREDIENT, drug.getActive_ingredient());
        values.put(KEY_STANDARD, drug.getReference_standard());

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
    public Drug searchDrug(int id) {

        Drug drug = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query( TABLE_DRUGS,
                new String[] {KEY_ID, KEY_APPL_NO, KEY_PRODUCT_NO, KEY_FORM, KEY_STRENGTH, KEY_REFERENCE, KEY_NAME, KEY_INGREDIENT, KEY_STANDARD},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            if (cursor.getCount() != 0) {
                drug = new Drug(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
            }

            cursor.close();

        }

        // return shop
        return drug;
    }

    public ArrayList<Drug> searchDrugByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query( TABLE_DRUGS,
                new String[] {KEY_ID, KEY_APPL_NO, KEY_PRODUCT_NO, KEY_FORM, KEY_STRENGTH, KEY_REFERENCE, KEY_NAME, KEY_INGREDIENT, KEY_STANDARD},
                KEY_NAME + "=?", new String[]{name}, null, null, null, null);

        ArrayList<Drug> drugs = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                Drug drug = new Drug( Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));

                drugs.add(drug);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());

            cursor.close();

        }

        return drugs;
    }

    public ArrayList<Drug> searchDrugByIngredient(String ingredient) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query( TABLE_DRUGS,
                new String[] {KEY_ID, KEY_APPL_NO, KEY_PRODUCT_NO, KEY_FORM, KEY_STRENGTH, KEY_REFERENCE, KEY_NAME, KEY_INGREDIENT, KEY_STANDARD},
                KEY_INGREDIENT + "=?", new String[]{ingredient}, null, null, null, null);

        ArrayList<Drug> drugs = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                Drug drug = new Drug( Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));

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

    public List<Drug> getDrugList(String searchText){
        List <Drug> drugList = new ArrayList<Drug>();
        String userQuery = "SELECT * FROM " + TABLE_DRUGS + " WHERE drug_name LIKE '%" + searchText + "%' group by drug_name";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(userQuery, null);
        if (cursor.moveToFirst()){
            do{
                Drug drug = new Drug();
                drug.setId(cursor.getInt(0));
                drug.setAppl_no(cursor.getString(1));
                drug.setProduct_no(cursor.getString(2));
                drug.setForm(cursor.getString(3));
                drug.setStrength(cursor.getString(4));
                drug.setReference_drug(cursor.getString(5));
                drug.setDrug_name(cursor.getString(6));
                drug.setActive_ingredient(cursor.getString(7));
                drug.setReference_standard(cursor.getString(8));
                drugList.add(drug);
            } while(cursor.moveToNext());
        }
        return drugList;
    }
}
