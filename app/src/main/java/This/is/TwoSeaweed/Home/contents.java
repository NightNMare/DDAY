package This.is.TwoSeaweed.Home;

public class contents {
    String year;
    String month;
    String day;
    String content;
    int state;
    int key;

    public contents(String year, String month, String day, String content, int state, int key) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.content = content;
        this.state = state;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
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