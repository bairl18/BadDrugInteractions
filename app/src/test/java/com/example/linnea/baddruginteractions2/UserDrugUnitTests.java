package com.example.linnea.baddruginteractions2;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Created by dwatanabe on 11/15/2017.
 */

public class UserDrugUnitTests {

    private UserDrug testDrug;
    private Drug testDrug2;

    private final int initId = 1;
    private final String initAppl_no = "appl_no";
    private final String initProduct_no = "product_no";
    private final String initForm = "form";
    private final String initStrength = "strength";
    private final String initReference_drug = "reference_drug";
    private final String initDrug_name = "drug_name";
    private final String initActive_ingredient = "active_ingredient";
    private final String initReference_standard = "reference_standard";

    private final int newId = 2;
    private final String newAppl_no = "new_appl_no";
    private final String newProduct_no = "new_product_no";
    private final String newForm = "new_form";
    private final String newStrength = "new_strength";
    private final String newReference_drug = "new_reference_drug";
    private final String newDrug_name = "new_drug_name";
    private final String newActive_ingredient = "new_active_ingredient";
    private final String newReference_standard = "new_reference_standard";


    @Before
    public void init() {
        testDrug = new UserDrug(initDrug_name, initForm, initActive_ingredient);
    }

    @Test
    public void testGetForm() { assertEquals(initForm, testDrug.getForm());}

    @Test
    public void testGetDrug_name() { assertEquals(initDrug_name, testDrug.getDrug_name()); }

    @Test
    public void testGetActive_ingredient() { assertEquals(initActive_ingredient, testDrug.getActive_ingredient()); }

    @Test
    public void testSetForm() {
        testDrug.setForm(newForm);
        assertEquals(newForm, testDrug.getForm());}

    @Test
    public void testSetDrug_name() {
        testDrug.setDrug_name(newDrug_name);
        assertEquals(newDrug_name, testDrug.getDrug_name()); }

    @Test
    public void testSetActive_ingredient() {
        testDrug.setActive_ingredient(newActive_ingredient);
        assertEquals(newActive_ingredient, testDrug.getActive_ingredient()); }

    @Test
    public void testAsDrug() {
        Drug drug = testDrug.asDrug();
        assertNotNull(drug.getForm());
        assertNotNull(drug.getDrug_name());
        assertNotNull(drug.getActive_ingredient());
    }

    @Test
    public void testUserDrug1() {
        testDrug2 = new Drug(initDrug_name, initForm, initActive_ingredient);

        UserDrug newUserDrug = new UserDrug(testDrug2);
        assertEquals(newUserDrug.getForm(), testDrug2.getForm());
        assertEquals(newUserDrug.getDrug_name(), testDrug2.getDrug_name());
        assertEquals(newUserDrug.getActive_ingredient(), testDrug2.getActive_ingredient());
    }

    @Test
    public void testUserDrug2()
    {
        UserDrug userDrugBlank = new UserDrug();
        UserDrug userDrugBlank2 = new UserDrug();

        assertEquals(userDrugBlank.getForm(), null );
        assertEquals(userDrugBlank.getDrug_name(), null);
        assertEquals(userDrugBlank.getActive_ingredient(), null);
    }




}
