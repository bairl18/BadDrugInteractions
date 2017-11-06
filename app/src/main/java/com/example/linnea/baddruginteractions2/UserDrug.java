package com.example.linnea.baddruginteractions2;

/**
 * Created by watanabe18 on 11/1/2017.
 */

public class UserDrug extends Drug {

    private int id;
    private String appl_no;
    private String product_no;
    private String form;
    private String strength;
    private String reference_drug;
    private String drug_name;
    private String active_ingredient;
    private String reference_standard;

    public UserDrug()
    {

    }

    public UserDrug(int id, String appl_no, String product_no, String form, String strength, String reference_drug,
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

    public Drug asDrug() {

        Drug drug = new Drug();

        drug.setId(this.id);
        drug.setAppl_no(this.appl_no);
        drug.setProduct_no(this.product_no);
        drug.setForm(this.form);
        drug.setStrength(this.strength);
        drug.setReference_drug(this.reference_drug);
        drug.setDrug_name(this.drug_name);
        drug.setActive_ingredient(this.active_ingredient);
        drug.setReference_standard(this.reference_standard);

        return drug;
    }

}
