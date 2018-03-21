package com.example.linnea.baddruginteractions2;

import android.test.mock.MockApplication;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainActivityUnitTests extends MainActivity {

    DBHandler db = new DBHandler(this);
    Drug testDrug;

    private final int initId = 1;
    private final String initAppl_no = "appl_no";
    private final String initProduct_no = "product_no";
    private final String initForm = "form";
    private final String initStrength = "strength";
    private final String initReference_drug = "reference_drug";
    private final String initDrug_name = "drug_name";
    private final String initActive_ingredient = "active_ingredient";
    private final String initReference_standard = "reference_standard";

    @Test
    public void testProgramOpens() {
        assertTrue(MainActivity.openProgramTest());
    }

    /* getting database not mocked error
    @Test
    public void testGetMedInfo()
    {
        testDrug = new Drug(initId, initAppl_no, initProduct_no, initForm, initStrength, initReference_drug,
                            initDrug_name, initActive_ingredient, initReference_standard);

        db.addDrug(testDrug);

        String info = "Drug Name: " + testDrug.getDrug_name()
                + "\nActive Ingredient: " + testDrug.getActive_ingredient()
                + "\nForm: " + testDrug.getForm()
                + "\nStrength: " + testDrug.getStrength();

        assertEquals(info, getMedInfo(testDrug.getId()));

    }
    */




}