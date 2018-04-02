package com.example.linnea.baddruginteractions2;

/**
 * Created by dwatanabe on 3/25/2018.
 */

import java.util.*;

public class Interaction {

    private String drug_name_1;
    private String drug_name_2;
    private String severity;
    private String interaction_description;

    Interaction() {

    }

    Interaction(String dn1, String dn2, String sev, String desc) {
        drug_name_1 = dn1;
        drug_name_2 = dn2;
        severity = sev;
        interaction_description = desc;
    }

    public void setDrugName1(String dn) { drug_name_1 = dn; }
    public void setDrugName2(String dn) { drug_name_2 = dn; }
    public void setSeverity(String sev) { severity = sev; }
    public void setDescription(String desc) { interaction_description = desc; }

    public String getDrugName1() { return drug_name_1; }
    public String getDrugName2() { return drug_name_2; }
    public String getSeverity() { return severity; }
    public String getDescription() { return interaction_description; }

    // get all attributes as in a list.
    public List<String> getInfo() {
        List<String> interaction = new ArrayList<>();
        interaction.add(drug_name_1);
        interaction.add(drug_name_2);
        interaction.add(severity);
        interaction.add(interaction_description);

        return interaction;
    }
}
