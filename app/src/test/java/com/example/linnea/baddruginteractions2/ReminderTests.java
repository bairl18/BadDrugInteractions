package com.example.linnea.baddruginteractions2;

import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * Created by Linnea on 3/4/2018.
 */
public class ReminderTests {

    Reminder testReminder;

    int initId = 1;
    String initDrugName = "drugName";
    int initStartDay = 1;
    int initStartMonth = 1;
    int initStartYear = 2000;
    int initTimeHour = 1;
    int initTimeMinutes = 1;
    String initAmPm = "amPm";
    String initFrequency = "frequency";

    int newId = 2;
    String newDrugName = "drugName 2";
    int newStartDay = 2;
    int newStartMonth = 2;
    int newStartYear = 2001;
    int newTimeHour = 2;
    int newTimeMinutes = 2;
    String newAmPm = "amPm 2";
    String newFrequency = "frequency 2";

    @Before
    public void testReminder() {
        testReminder = new Reminder(initId, initDrugName, initStartDay, initStartMonth, initStartYear,
                                                initTimeHour, initTimeMinutes, initAmPm, initFrequency);
    }

    @Test
    public void testSetDrugName() {
        testReminder.setDrugName(newDrugName);
        assertEquals(testReminder.getDrugName(), newDrugName);
    }

    @Test
    public void testSetStartDay() {
        testReminder.setStartDay(newStartDay);
        assertEquals(testReminder.getStartDay(), newStartDay);
    }

    @Test
    public void testSetStartMonth() {
        testReminder.setStartMonth(newStartMonth);
        assertEquals(testReminder.getStartMonth(), newStartMonth);
    }

    @Test
    public void testSetStartYear() {
        testReminder.setStartYear(newStartYear);
        assertEquals(testReminder.getStartYear(), newStartYear);
    }

    @Test
    public void testSetTimeHour() {
        testReminder.setTimeHour(newTimeHour);
        assertEquals(testReminder.getTimeHour(), newTimeHour);
    }

    @Test
    public void testSetTimeMinutes() {
        DecimalFormat df = new DecimalFormat("00");
        String newTimeMinutesStr = df.format(newTimeMinutes);
        testReminder.setTimeMinutes(newTimeMinutes);
        assertEquals(testReminder.getTimeMinutes(), newTimeMinutesStr);
    }

    @Test
    public void testSetAmPm() {
        testReminder.setAmPm(newAmPm);
        assertEquals(testReminder.getAmPm(), newAmPm);
    }

    @Test
    public void testSetFrequency() {
        testReminder.setFrequency(newFrequency);
        assertEquals(testReminder.getFrequency(), newFrequency);
    }

    @Test
    public void testGetDrugName() {
        assertEquals(testReminder.getDrugName(), initDrugName);
    }

    @Test
    public void testGetStartDay() {
        assertEquals(testReminder.getStartDay(), initStartDay);
    }

    @Test
    public void testGetStartMonth() {
        assertEquals(testReminder.getStartMonth(), initStartMonth);
    }

    @Test
    public void testGetStartYear() {
        assertEquals(testReminder.getStartYear(), initStartYear);
    }

    @Test
    public void testGetTimeHour() {
        assertEquals(testReminder.getTimeHour(), initTimeHour);
    }

    @Test
    public void testGetTimeMinutes() {
        DecimalFormat df = new DecimalFormat("00");
        String minuteStr = df.format(initTimeMinutes);
        assertEquals(testReminder.getTimeMinutes(), minuteStr);
    }

    @Test
    public void testGetAmPm() {
        assertEquals(testReminder.getAmPm(), initAmPm);
    }

    @Test
    public void testGetFrequency() {
        assertEquals(testReminder.getFrequency(), initFrequency);
    }

    @Test
    public void testGetId() {
        assertEquals(testReminder.getId(), initId);
    }

}