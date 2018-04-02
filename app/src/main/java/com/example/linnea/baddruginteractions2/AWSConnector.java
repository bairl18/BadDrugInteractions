package com.example.linnea.baddruginteractions2;

/**
 * Created by watanabe18 on 11/2/2017.
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.AsyncTask;
import android.util.Log;

public class AWSConnector {

        private String awsURL = "http://ec2-13-58-76-35.us-east-2.compute.amazonaws.com/";

        /*
         * get the severity and interaction description.
         *
         * the expected output for an existing interaction is a 2-element list.
         * index0 = severity
         * index1 = description
         *
         * it will return an empty list if no interaction is found.
         */
        public List<String> drugnamesToInteractionInfo(String drugname1, String drugname2) {

            List<String> info = new ArrayList<>();

            String synid1 = this.getSynonymID(drugname1);
            String synid2 = this.getSynonymID(drugname2);

            if (synid1 != null && synid2 != null) {

                String drugid1 = this.getDrugID(synid1);
                String drugid2 = this.getDrugID(synid2);

                if (drugid1 != null && drugid2 != null) {

                    String sevid = this.getSeverityID(drugid1, drugid2);
                    String intid = this.getIntID(drugid1, drugid2);

                    if (sevid != null && intid != null) {

                        String sevdesc = this.getSeverityDesc(sevid);
                        if (sevdesc !=null) {
                            info.add(sevdesc);
                        }
                        else {
                            info.add("Severity Not Found");
                        }

                        String intdesc = this.getIntDescription(intid);
                        if (intdesc !=null) {
                            info.add(intdesc);
                        }
                        else {
                            info.add("Interaction Description Not Found");
                        }

                        return info;
                    }
                }
            }
            return new ArrayList<String>(); // return empty list if the search finds nothing
        }

        /*
         * Does the same thing as drugnamesToInteractionInfo
         * except it checks again with the inputs reversed
         */
        public List<String> drugnamesToInteractionInfo2(String drugname1, String drugname2) {

            List<String> info = drugnamesToInteractionInfo(drugname1, drugname2);

            if (info.isEmpty()) {
                info = drugnamesToInteractionInfo(drugname2, drugname1);
            }
            return info;
        }

        /*
         * get a list of drug names that starts with the input string.
         *
         * i.e. input "tylenol" will return a list of drug names with entries like "Tylenol Cold Relief Nighttime" and "Tylenol Sinus Gelcap"
         *
         */
        public List<String> findSimilarDrugNames(String drugname) {

            if(!drugname.isEmpty()) {
                String urlParam = drugname.replaceAll("\\s+", "+"); // replace white spaces with '+'. URL friendly.
                List<String> searchResult = this.search(awsURL + "nameSearch.php?dn=" + urlParam);

                return searchResult;
            }
            return new ArrayList<String>();
        }

        // drug_name --> drug_synonym_id
        public String getSynonymID(String drugname) {

            String urlParam = drugname.replaceAll("\\s+", "+");
            List<String> searchResult = this.search(awsURL + "getsynid.php?dn=" + urlParam);

            if (!searchResult.isEmpty()) {
                return searchResult.get(0);
            }

            return null; // return null if search finds nothing
        }

        // drug_synonym_id --> drug_id
        public String getDrugID(String synid) {

            String urlParam = synid.replaceAll("\\s+", "");
            List<String> searchResult = this.search(awsURL + "getdrugid.php?synid=" + urlParam);

            if (!searchResult.isEmpty()) {
                return searchResult.get(0);
            }

            return null; // return null if search finds nothing
        }

        // drug_id + drug_id --> int_id
        public String getIntID(String drugid1, String drugid2) {

            String urlParam1 = drugid1.replaceAll("\\s+", "");
            String urlParam2 = drugid2.replaceAll("\\s+", "");
            List<String> searchResult = this.search(awsURL + "getintid.php?id1=" + urlParam1 + "&id2=" + urlParam2);

            if (!searchResult.isEmpty()) {
                return searchResult.get(0);
            }

            return null; // return null if search finds nothing
        }

        // int_id --> int_description
        public String getIntDescription(String intid) {

            String urlParam = intid.replaceAll("\\s+", "");
            List<String> searchResult = this.search(awsURL + "getdesc.php?intid=" + urlParam);

            if (!searchResult.isEmpty()) {
                return searchResult.get(0);
            }

            return null; // return null if search finds nothing
        }

        // drug_id + drug_id --> severity_id
        public String getSeverityID(String drugid1, String drugid2) {

            String urlParam1 = drugid1.replaceAll("\\s+", "");
            String urlParam2 = drugid2.replaceAll("\\s+", "");
            List<String> searchResult = this.search(awsURL + "getsevid.php?id1=" + urlParam1 + "&id2=" + urlParam2);

            if (!searchResult.isEmpty()) {
                return searchResult.get(0);
            }

            return null; // return null if search finds nothing
        }

        // severity_id --> severity_desc
        public String getSeverityDesc(String sevid) {

            String urlParam = sevid.replaceAll("\\s+", "");
            List<String> searchResult = this.search(awsURL + "getsevdesc.php?sevid=" + urlParam);

            if (!searchResult.isEmpty()) {
                return searchResult.get(0);
            }

            return null; // return null if search finds nothing
        }

        // html parser
        private List<String> search(String phpurl) {

            List<String> result = new ArrayList<>();
            String html = "";

            try {
                html = new Connection().execute(phpurl).get();
            }
            catch(InterruptedException e) {
                Log.d("AsyncTask: ", "InterruptedException");
            }
            catch(ExecutionException e) {
                Log.d("AsyncTask: ", "ExecutionException");
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

        private class Connection extends AsyncTask<String, Integer, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... url) {
                String myURL = url[0];
                String html = "";

                try {
                    URL query1 = new URL(myURL);
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
                return html;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }

}