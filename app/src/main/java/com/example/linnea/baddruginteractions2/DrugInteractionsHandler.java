package com.example.linnea.baddruginteractions2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.*;

/**
 * Created by dwatanabe on 3/25/2018.
 */

public class DrugInteractionsHandler extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "drugInteractions";

    // Contacts table name
    private static final String TABLE_INTERACTIONS = "drug_interactions";

    // Drugs Table Columns names
    private static final String KEY_NAME1 = "drug_name_1";
    private static final String KEY_NAME2 = "drug_name_2";
    private static final String KEY_SEVERITY = "severity";
    private static final String KEY_DESCRIPTION = "interaction_description";

    public DrugInteractionsHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_INT_TABLE = "CREATE TABLE " + TABLE_INTERACTIONS + "("
                + KEY_NAME1 + " TEXT NOT NULL, " + KEY_NAME2 + " TEXT NOT NULL, " + KEY_SEVERITY + " TEXT, " + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_INT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERACTIONS);
        // Creating tables again
        onCreate(db);
    }


    public void reset() {

        SQLiteDatabase db = this.getWritableDatabase();

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERACTIONS);

        onCreate(db);
    }


    public void addInteraction(String drugname1, String drugname2, String severity, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME1, drugname1);
        values.put(KEY_NAME2, drugname2);
        values.put(KEY_SEVERITY, severity);
        values.put(KEY_DESCRIPTION, description);

        // Inserting Row
        db.insert(TABLE_INTERACTIONS, null, values);
        db.close(); // Closing database connection
    }


    public void addInteraction(Interaction inter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME1, inter.getDrugName1());
        values.put(KEY_NAME2, inter.getDrugName2());
        values.put(KEY_SEVERITY, inter.getSeverity());
        values.put(KEY_DESCRIPTION, inter.getDescription());

        // Inserting Row
        db.insert(TABLE_INTERACTIONS, null, values);
        db.close(); // Closing database connection
    }


    public void deleteInteractionsForDrug(Drug drug) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INTERACTIONS, KEY_NAME1 + " = ?",
                new String[]{drug.getDrug_name()});
        db.delete(TABLE_INTERACTIONS, KEY_NAME2 + " = ?",
                new String[]{drug.getDrug_name()});
        db.close();
    }


    // returns a list with the format <drug_name1, drug_name2, severity, description>
    public List<Interaction> searchInteractionsForDrug(Drug drug) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Interaction> interactions = new ArrayList<>();

        Cursor cursor1 = db.query( TABLE_INTERACTIONS,
                new String[] { KEY_NAME1, KEY_NAME2, KEY_SEVERITY, KEY_DESCRIPTION},
                KEY_NAME1 + "=?", new String[]{drug.getDrug_name()}, null, null, null, null);

        if (cursor1 != null) {

            cursor1.moveToFirst();

            if (cursor1.getCount() != 0) {
                while (!cursor1.isAfterLast()) {

                    interactions.add(new Interaction(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), cursor1.getString(3)));

                    cursor1.moveToNext();
                }
            }
            cursor1.close();
        }
        Cursor cursor2 = db.query( TABLE_INTERACTIONS,
                new String[] { KEY_NAME1, KEY_NAME2, KEY_SEVERITY, KEY_DESCRIPTION},
                KEY_NAME2 + "=?", new String[]{drug.getDrug_name()}, null, null, null, null);

        if (cursor2 != null) {

            cursor2.moveToFirst();
            if (cursor2.getCount() != 0) {
                while (!cursor2.isAfterLast()) {

                    interactions.add(new Interaction(cursor2.getString(1), cursor2.getString(0), cursor2.getString(2), cursor2.getString(3))); // NOTE: the drugname1 and drugname2 get switched

                    cursor2.moveToNext();
                }
            }
            cursor1.close();
        }
        return interactions;
    }

    // get all interactions.
    public List<Interaction> asList(){
        List <Interaction> intList = new ArrayList<>();
        String intQuery = "SELECT * FROM " + TABLE_INTERACTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(intQuery, null);
        if (cursor.moveToFirst()){
            do{
                Interaction interaction = new Interaction(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                intList.add(interaction);
            } while(cursor.moveToNext());
        }
        return intList;
    }

    // searches AWS returns an interaction if it finds it.
    public Interaction lookupInteractionForDrugs(Drug drug1, Drug drug2) {

        AWSConnector aws = new AWSConnector();

        Interaction result = null;

        List<String> info = aws.drugnamesToInteractionInfo2(drug1.getDrug_name(), drug2.getDrug_name());

        if (info.size() == 2) {
            result = new Interaction(drug1.getDrug_name(), drug2.getDrug_name(), info.get(0), info.get(1));
        }

        return result;
    }

}