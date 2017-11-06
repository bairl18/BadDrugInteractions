package com.example.linnea.baddruginteractions2;

/**
 * Created by bairl18 on 9/29/2017.
 */

public class Drug {

    private int id;
    private String appl_no;
    private String product_no;
    private String form;
    private String strength;
    private String reference_drug;
    private String drug_name;
    private String active_ingredient;
    private String reference_standard;

    public Drug()
    {

    }
    public Drug(int id, String appl_no, String product_no, String form, String strength, String reference_drug,
                String drug_name, String active_ingredient, String reference_standard)
    {
        this.id = id;
        this.appl_no = appl_no;
        this.product_no = product_no;
        this.form = form;
        this.strength = strength;
        this.reference_drug = reference_drug;
        this.drug_name = drug_name;
        this.active_ingredient = active_ingredient;
        this.reference_standard = reference_standard;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setAppl_no(String appl_no) { this.appl_no = appl_no; }
    public void setProduct_no(String product_no) { this.product_no = product_no; }
    public void setForm(String form) { this.form = form; }
    public void setStrength(String strength) { this.strength = strength; }
    public void setReferenec_drug(String reference_drug) { this.reference_drug = reference_drug; }
    public void setDrug_name(String drug_name) { this.drug_name = drug_name; }
    public void setActive_ingredient(String active_ingredient) { this.active_ingredient = active_ingredient; }
    public void setReference_standard(String reference_standard) { this.reference_standard = reference_standard; }

    public int getId() {
        return id;
    }
    public String getAppl_no() { return appl_no; }
    public String getProduct_no() { return product_no; }
    public String getForm() { return form; }
    public String getStrength() { return strength; }
    public String getReference_drug() { return reference_drug; }
    public String getDrug_name() { return drug_name; }
    public String getActive_ingredient() { return active_ingredient; }
    public String getReference_standard() { return reference_standard; }

}
