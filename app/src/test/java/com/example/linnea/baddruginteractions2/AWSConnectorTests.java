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
    }

    public void searchForDrugId() {
        assertEquals(new AWSConnector().getSynonymID("1365"), "d00016");
        assertEquals(new AWSConnector().getSynonymID("894"), "d00148");
    }

    public void searchForIntId() {
        assertEquals(new AWSConnector().getIntID("1365", "894"), "1000002");
    }

    public void searchForIntDescription() {
        assertEquals(new AWSConnector().getIntDescription("1000002"), "The pharmacologic effects of some benzodiazepines may be increased by some beta-blockers. Propranolol and metoprolol may inhibit the hepatic metabolism of diazepam and other mechanisms may also be involved. Most changes have been clinically insignificant; however, increased reaction times and/or decreased kinetic visual acuity have been reported with some combinations. Observation for altered benzodiazepine effects is recommended if these drugs must be used together. Patients should be warned against driving or operating hazardous machinery.");
    }

}
