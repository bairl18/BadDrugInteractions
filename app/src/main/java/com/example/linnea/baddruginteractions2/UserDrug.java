package com.example.linnea.baddruginteractions2;

/**
 * Created by watanabe18 on 11/1/2017.
 */

public class UserDrug extends Drug {

    // UserDrug contains the same attributes as Drug right now, but it is expected to contain more in the future.

    public UserDrug()
    {

    }

    public UserDrug(int id, String appl_no, String product_no, String form, String strength, String reference_drug,
                String drug_name, String active_ingredient, String reference_standard)
    {
        this.setId(id);
        this.setAppl_no(appl_no);
        this.setProduct_no(product_no);
        this.setForm(form);
        this.setStrength(strength);
        this.setReference_drug(reference_drug);
        this.setDrug_name(drug_name);
        this.setActive_ingredient(active_ingredient);
        this.setReference_standard(reference_standard);
    }

    public UserDrug(Drug drug) {

        this.setId(drug.getId());
        this.setAppl_no(drug.getAppl_no());
        this.setProduct_no(drug.getProduct_no());
        this.setForm(drug.getForm());
        this.setStrength(drug.getStrength());
        this.setReference_drug(drug.getReference_drug());
        this.setDrug_name(drug.getDrug_name());
        this.setActive_ingredient(drug.getActive_ingredient());
        this.setReference_standard(drug.getReference_standard());

    }

    public Drug asDrug() {

        Drug drug = new Drug();

        drug.setId(this.getId());
        drug.setAppl_no(this.getAppl_no());
        drug.setProduct_no(this.getProduct_no());
        drug.setForm(this.getForm());
        drug.setStrength(this.getStrength());
        drug.setReference_drug(this.getReference_drug());
        drug.setDrug_name(this.getDrug_name());
        drug.setActive_ingredient(this.getActive_ingredient());
        drug.setReference_standard(this.getReference_standard());

        return drug;
    }

}
