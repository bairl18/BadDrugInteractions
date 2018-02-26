package com.example.linnea.baddruginteractions2;

import java.text.DecimalFormat;

/**
 * Created by Linnea on 2/8/2018.
 */

public class Reminder {

    // Reminder attributes
    private String drugName;
    private int startDay, startMonth, startYear;
    private int timeHour, timeMinutes, id;
    private String amPm;
    private String frequency; // Once, every day, weekly


    public Reminder(int initId, String initDrugName, int initStartDay, int initStartMonth, int initStartYear,
                    int initTimeHour, int initTimeMinutes, String initAmPm, String initFrequency)
    {

        this.id = initId;
        this.drugName = initDrugName;
        this.startDay = initStartDay;
        this.startMonth = initStartMonth;
        this.startYear = initStartYear;
        this.timeHour = initTimeHour;
        this.timeMinutes = initTimeMinutes;
        this.amPm = initAmPm;
        this.frequency = initFrequency;
    }

    public void setDrugName(String drugName) { this.drugName = drugName; }
    public void setStartDay(int startDay) { this.startDay = startDay; }
    public void setStartMonth(int startMonth) { this.startMonth = startMonth; }
    public void setStartYear(int startYear) { this.startYear = startYear; }
    public void setTimeHour(int timeHour) { this.timeHour = timeHour; }
    public void setTimeMinutes(int timeMinutes) {this.timeMinutes = timeMinutes;}
    public void setAmPm(String amPm){this.amPm = amPm;}
    public void setFrequency(String frequency) {this.frequency = frequency;}

    public String getDrugName() { return drugName; }
    public int getStartDay() {return startDay;}
    public int getStartMonth() {return startMonth;}
    public int getStartYear() {return startYear;}
    public int getTimeHour() {return timeHour;}
    public String getTimeMinutes()
    {
        DecimalFormat df = new DecimalFormat("00");
        String minuteStr = df.format(timeMinutes);
        return minuteStr;
    }
    public String getAmPm() {return amPm;}
    public String getFrequency() {return frequency;}
    public int getId() {return id;}
}
