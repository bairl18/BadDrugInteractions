package com.example.linnea.baddruginteractions2;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Created by dwatanabe on 11/14/2017.
 */

public class DrugUnitTests {

    private Drug testDrug;

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
        testDrug = new Drug(initId, initAppl_no, initProduct_no, initForm, initStrength, initReference_drug,
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



}
