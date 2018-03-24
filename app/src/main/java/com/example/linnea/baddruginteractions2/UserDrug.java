package com.example.linnea.baddruginteractions2;

/**
 * Created by watanabe18 on 11/1/2017.
 */

public class UserDrug extends Drug {

    // UserDrug contains the same attributes as Drug right now, but it is expected to contain more in the future.

    public UserDrug() {

    }

    public UserDrug(String drug_name, String form, String active_ingredient)
    {
        this.setDrug_name(drug_name);
        this.setForm(form);
        this.setActive_ingredient(active_ingredient);
    }

    // constructor with dummy values for form and active ingredient.
    public UserDrug(String drug_name)
    {
        this.setDrug_name(drug_name);
        this.setForm("<form>");
        this.setActive_ingredient("<active_ingredient>");
    }

    public UserDrug(Drug drug) {
        this.setDrug_name(drug.getDrug_name());
        this.setForm(drug.getForm());
        this.setActive_ingredient(drug.getActive_ingredient());
    }

    public Drug asDrug() {

        Drug drug = new Drug();

        drug.setDrug_name(this.getDrug_name());
        drug.setForm(this.getForm());
        drug.setActive_ingredient(this.getActive_ingredient());

        return drug;
    }

}
