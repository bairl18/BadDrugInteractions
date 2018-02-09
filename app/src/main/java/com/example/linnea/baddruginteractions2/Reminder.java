package com.example.linnea.baddruginteractions2;

/**
 * Created by Linnea on 2/8/2018.
 */

public class Reminder {

    // Reminder attributes
    private Drug drug;
    private int startDay, startMonth, startYear;
    private int timeHour, timeMinutes;
    private String frequency; // Once, every day, weekly


    public Reminder(Drug initDrug, int initStartDay, int initStartMonth, int initStartYear,
                    int initTimeHour, int initTimeMinutes, String initFrequency)
    {

        this.drug = initDrug;
        this.startDay = initStartDay;
        this.startMonth = initStartMonth;
        this.startYear = initStartYear;
        this.timeHour = initTimeHour;
        this.timeMinutes = initTimeMinutes;
        this.frequency = initFrequency;

    }

    public void setDrug(Drug drug) { this.drug = drug; }
    public void setStartDay(int startDay) { this.startDay = startDay; }
    public void setStartMonth(int startMonth) { this.startMonth = startMonth; }
    public void setStartYear(int startYear) { this.startYear = startYear; }
    public void setTimeHour(int timeHour) { this.timeHour = timeHour; }
    public void setTimeMinutes(int timeMinutes) {this.timeMinutes = timeMinutes;}
    public void setFrequency(String frequency) {this.frequency = frequency;}

    public Drug getDrug() { return drug; }
    public int getStartDay() {return startDay;}
    public int getStartMonth() {return startMonth;}
    public int getStartYear() {return startYear;}
    public int getTimeHour() {return timeHour;}
    public int getTimeMinutes() {return timeMinutes;}
    public String getFrequency() {return frequency;}
}
