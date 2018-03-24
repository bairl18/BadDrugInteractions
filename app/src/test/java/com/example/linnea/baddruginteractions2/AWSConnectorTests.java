package com.example.linnea.baddruginteractions2;

/**
 * Created by dwatanabe on 3/20/2018.
 */

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class AWSConnectorTests {

    @Test
    public void searchForSynId() {

        assertEquals(new AWSConnector().getSynonymID("trandate"), "1365");

        assertEquals(new AWSConnector().getSynonymID("diazepam"), "894");

        assertEquals(new AWSConnector().getSynonymID("Calcium Chloride"), "16");

        assertNull(new AWSConnector().getSynonymID("not a real drug"));

    }

    @Test
    public void searchForDrugId() {

        assertEquals(new AWSConnector().getDrugID("1365"), "d00016");

        assertEquals(new AWSConnector().getDrugID("894"), "d00148");

        assertNull(new AWSConnector().getDrugID("fake id"));
    }

    @Test
    public void searchForIntId() {

        assertEquals(new AWSConnector().getIntID("d00016", "d00148"), "1000002");

        assertNull(new AWSConnector().getIntID("dXXXX1", "dXXXX2"));
    }

    @Test
    public void searchForIntDescription() {

        assertEquals(new AWSConnector().getIntDescription("1000002"), "The pharmacologic effects of some benzodiazepines may be increased by some beta-blockers. " +
                "Propranolol and metoprolol may inhibit the hepatic metabolism of diazepam and other mechanisms may also be involved. Most changes have been clinically insignificant; " +
                "however, increased reaction times and/or decreased kinetic visual acuity have been reported with some combinations. Observation for altered benzodiazepine effects is " +
                "recommended if these drugs must be used together. Patients should be warned against driving or operating hazardous machinery.");

        assertNull(new AWSConnector().getIntDescription("bad interaction"));
    }

    @Test
    public void searchForSeverityId() {

        assertEquals(new AWSConnector().getSeverityID("d00016", "d00148"), "1");

        assertNull(new AWSConnector().getSeverityID("dXXXX1", "dXXXX2"));
    }

    @Test
    public void searchForSeverityDescription() {

        assertEquals(new AWSConnector().getSeverityDesc("1"), "Minor Drug Interaction");

        assertNull(new AWSConnector().getSeverityDesc("99"));
    }

    @Test
    public void fullQueryDrugNameToInfo() {

        assertEquals(new AWSConnector().drugnamesToInteractionInfo("trandate", "diazepam").get(0), "Minor Drug Interaction");

        assertEquals(new AWSConnector().drugnamesToInteractionInfo("trandate", "diazepam").get(1), "The pharmacologic effects of some benzodiazepines may be increased by some beta-blockers. " +
                "Propranolol and metoprolol may inhibit the hepatic metabolism of diazepam and other mechanisms may also be involved. Most changes have been clinically insignificant; " +
                "however, increased reaction times and/or decreased kinetic visual acuity have been reported with some combinations. Observation for altered benzodiazepine effects is " +
                "recommended if these drugs must be used together. Patients should be warned against driving or operating hazardous machinery.");

        assertTrue(new AWSConnector().drugnamesToInteractionInfo("dummy drug", "made up med").isEmpty());

    }

    @Test
    public void searchForSimilarNames() {

        assertTrue(new AWSConnector().findSimilarDrugNames("tylenol").contains("Tylenol Cough"));

        assertTrue(new AWSConnector().findSimilarDrugNames("blah blah blah").isEmpty());

    }

}
