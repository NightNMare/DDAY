package This.is.TwoSeaweed.Home;

import java.util.ArrayList;

public class Item {
    private String year;
    private String month;
    private String day;
    private String curday;

    public Item(String year, String month, String day, String curday) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.curday = curday;
    }

    public String getCurday() {
        return curday;
    }

    public void setCurday(String curday) {
        this.curday = curday;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}