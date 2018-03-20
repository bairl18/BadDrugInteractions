package com.example.linnea.baddruginteractions2;

/**
 * Created by watanabe18 on 11/2/2017.
 */

import java.net.*;
import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AWSConnector {

    /*
     * links the queries together to get the interaction description from two drug names
     */
    public String drugnamesToDescription(String drugname1, String drugname2) {

        String synid1 = this.getSynonymID(drugname1);
        String synid2 = this.getSynonymID(drugname2);

        if (synid1 != null && synid2 != null) {

            String drugid1 = this.getDrugID(synid1);
            String drugid2 = this.getDrugID(synid2);

            if (drugid1 != null && drugid2 != null) {

                String intID = this.getIntID(drugid1, drugid2);

                if (intID != null) {

                    return this.getIntDescription(intID);
                }
            }
        }
        return null; // return null if the search finds nothing
    }


    public String getSynonymID(String drugname) {

        String urlParam = drugname.replaceAll("\\s+", "+");
        List<String> searchResult = this.search("http://ec2-13-58-76-35.us-east-2.compute.amazonaws.com/getsynid.php?dn=" + urlParam);

        if (!searchResult.isEmpty()) {
            return searchResult.get(0);
        }

        return null; // return null if search finds nothing
    }

    public String getDrugID(String synid) {

        String urlParam = synid.replaceAll("\\s+", "");
        List<String> searchResult = this.search("http://ec2-13-58-76-35.us-east-2.compute.amazonaws.com/getdrugid.php?synid=" + urlParam);

        if (!searchResult.isEmpty()) {
            return searchResult.get(0);
        }

        return null; // return null if search finds nothing
    }

    public String getIntID(String drugid1, String drugid2) {

        String urlParam1 = drugid1.replaceAll("\\s+", "");
        String urlParam2 = drugid2.replaceAll("\\s+", "");
        List<String> searchResult = this.search("http://ec2-13-58-76-35.us-east-2.compute.amazonaws.com/getintid.php?id1=" + urlParam1 + "&id2=" + urlParam2);

        if (!searchResult.isEmpty()) {
            return searchResult.get(0);
        }

        return null; // return null if search finds nothing
    }

    public String getIntDescription(String intid) {

        String urlParam = intid.replaceAll("\\s+", "");
        List<String> searchResult = this.search("http://ec2-13-58-76-35.us-east-2.compute.amazonaws.com/getdesc.php?intid=" + urlParam);

        if (!searchResult.isEmpty()) {
            return searchResult.get(0);
        }

        return null; // search finds nothing
    }

    private List<String> search(String query) {

        List<String> result = new ArrayList<String>();
        String html = "";

        try {
            URL query1 = new URL(query);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(query1.openStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {

                html += inputLine;
            }
            in.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (!html.isEmpty()) {

            Document doc = Jsoup.parse(html);
            Element table = doc.select("table").first();
            Boolean header = true;

            for (Element row : table.select("tr")) {

                if (!header) {

                    Element td = row.select("td").first();
                    String text = td.text();

                    result.add(text);
                }
                else header = false;
            }
        }
        return result;
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