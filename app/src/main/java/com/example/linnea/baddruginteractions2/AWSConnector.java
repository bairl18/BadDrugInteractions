package com.example.linnea.baddruginteractions2;

/**
 * Created by watanabe18 on 11/2/2017.
 */

public class AWSConnector {

    // this class will be used to deal with connecting to and querying AWS

    public AWSConnector(){

    }

    // in the final implementation, a UserDrug will be created from the data queried from AWS
    // right now, this method creates a UserDrug with predefined test values.
    public UserDrug createUserDrug(int id) {

        String appl_no = "TestAppl";
        String product_no = "TestProduct";
        String form = "TestForm";
        String strength = "TestStrength";
        String reference_drug = "TestReference";
        String drug_name = "TestName";
        String active_ingredient = "TestIngredient";
        String reference_standard = "TestStandard";

        UserDrug ud = new UserDrug(id, appl_no, product_no, form, strength, reference_drug, drug_name, active_ingredient, reference_standard);

        return ud;

    }

}
