package com.example.linnea.baddruginteractions2;

/**
 * Created by bairl18 on 9/29/2017.
 */

public class Drug {

    private String drug_name;
    private String form;
    private String active_ingredient;

    public Drug()
    {

    }

    public Drug(String drug_name, String form, String active_ingredient)
    {
        this.drug_name = drug_name;
        this.form = form;
        this.active_ingredient = active_ingredient;

    }

    public void setDrug_name(String drug_name) { this.drug_name = drug_name; }
    public void setForm(String form) { this.form = form; }
    public void setActive_ingredient(String active_ingredient) { this.active_ingredient = active_ingredient; }

    public String getDrug_name() { return drug_name; }
    public String getForm() { return form; }
    public String getActive_ingredient() { return active_ingredient; }

}
