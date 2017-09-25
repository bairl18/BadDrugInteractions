package com.example.linnea.baddruginteractions2;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {

    @Test
    public void programOpens() {
        assertTrue(MainActivity.openProgramTest());
    }

    @Test
    public void search() {
        assertEquals(MainActivity.getMedName(), "Your Medication");
    }
}