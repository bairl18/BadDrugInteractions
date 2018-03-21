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
        testDrug = new UserDrug(initId, initAppl_no, initProduct_no, initForm, initStrength, initReference_drug,
                initDrug_name, initActive_ingredient, initReference_standard);
    }

    @Test
    public void testGetId() { assertEquals(initId, testDrug.getId()); }

    @Test
    public void testGetAppl_no() { assertEquals(initAppl_no, testDrug.getAppl_no()); }

    @Test
    public void testGetProduct_no() { assertEquals(initProduct_no, testDrug.getProduct_no()); }

    @Test
    public void testGetForm() { assertEquals(initForm, testDrug.getForm());}

    @Test
    public void testGetStrength() { assertEquals(initStrength, testDrug.getStrength()); }

    @Test
    public void testGetReference_drug() { assertEquals(initReference_drug, testDrug.getReference_drug()); }

    @Test
    public void testGetDrug_name() { assertEquals(initDrug_name, testDrug.getDrug_name()); }

    @Test
    public void testGetActive_ingredient() { assertEquals(initActive_ingredient, testDrug.getActive_ingredient()); }

    @Test
    public void testGetReference_standard() { assertEquals(initReference_standard, testDrug.getReference_standard());}

    @Test
    public void testSetId() {
        testDrug.setId(newId);
        assertEquals(newId, testDrug.getId()); }

    @Test
    public void testSetAppl_no() {
        testDrug.setAppl_no(newAppl_no);
        assertEquals(newAppl_no, testDrug.getAppl_no()); }

    @Test
    public void testSetProduct_no() {
        testDrug.setProduct_no(newProduct_no);
        assertEquals(newProduct_no, testDrug.getProduct_no()); }

    @Test
    public void testSetForm() {
        testDrug.setForm(newForm);
        assertEquals(newForm, testDrug.getForm());}

    @Test
    public void testSetStrength() {
        testDrug.setStrength(newStrength);
        assertEquals(newStrength, testDrug.getStrength()); }

    @Test
    public void testSetReference_drug() {
        testDrug.setReference_drug(newReference_drug);
        assertEquals(newReference_drug, testDrug.getReference_drug()); }

    @Test
    public void testSetDrug_name() {
        testDrug.setDrug_name(newDrug_name);
        assertEquals(newDrug_name, testDrug.getDrug_name()); }

    @Test
    public void testSetActive_ingredient() {
        testDrug.setActive_ingredient(newActive_ingredient);
        assertEquals(newActive_ingredient, testDrug.getActive_ingredient()); }

    @Test
    public void testSetReference_standard() {
        testDrug.setReference_standard(newReference_standard);
        assertEquals(newReference_standard, testDrug.getReference_standard());}

    @Test
    public void testAsDrug() {
        Drug drug = testDrug.asDrug();
        assertEquals(drug.getId(), testDrug.getId());
        assertNotNull(drug.getForm());
        assertNotNull(drug.getDrug_name());
        assertNotNull(drug.getActive_ingredient());
    }

    @Test
    public void testUserDrug1() {
        testDrug2 = new UserDrug(initId, initAppl_no, initProduct_no, initForm, initStrength, initReference_drug,
                initDrug_name, initActive_ingredient, initReference_standard);

        UserDrug newUserDrug = new UserDrug(testDrug2);
        assertEquals(newUserDrug.getId(), testDrug2.getId());
        assertEquals(newUserDrug.getAppl_no(), testDrug2.getAppl_no());
        assertEquals(newUserDrug.getProduct_no(), testDrug2.getProduct_no());
        assertEquals(newUserDrug.getForm(), testDrug2.getForm());
        assertEquals(newUserDrug.getStrength(), testDrug2.getStrength());
        assertEquals(newUserDrug.getReference_drug(), testDrug2.getReference_drug());
        assertEquals(newUserDrug.getDrug_name(), testDrug2.getDrug_name());
        assertEquals(newUserDrug.getActive_ingredient(), testDrug2.getActive_ingredient());
        assertEquals(newUserDrug.getReference_standard(), testDrug2.getReference_standard());
    }

    @Test
    public void testUserDrug2()
    {
        UserDrug userDrugBlank = new UserDrug();
        UserDrug userDrugBlank2 = new UserDrug();

        assertEquals(userDrugBlank.getId(), 0);
        assertEquals(userDrugBlank.getAppl_no(), null);
        assertEquals(userDrugBlank.getProduct_no(), null);
        assertEquals(userDrugBlank.getForm(), null );
        assertEquals(userDrugBlank.getStrength(), null);
        assertEquals(userDrugBlank.getReference_drug(), null);
        assertEquals(userDrugBlank.getDrug_name(), null);
        assertEquals(userDrugBlank.getActive_ingredient(), null);
        assertEquals(userDrugBlank.getReference_standard(), null);
    }




}
