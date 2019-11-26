package This.is.TwoSeaweed.Home;

public class contents {
    String year;
    String month;
    String day;
    String curday;
    String content;
    int state;

    public contents(String year, String month, String day, String curday, String content, int state) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.curday = curday;
        this.content = content;
        this.state = state;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCurday() {
        return curday;
    }

    public void setCurday(String curday) {
        this.curday = curday;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}