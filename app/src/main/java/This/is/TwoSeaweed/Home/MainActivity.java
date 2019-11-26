package This.is.TwoSeaweed.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import This.is.TwoSeaweed.R;

public class MainActivity extends AppCompatActivity {
    RecyclerView RV_date;
    ArrayList<Item> items;
    RecyclerAdapter DateAdapter;
    String curTime;
    String title_year;
    String title_month;
    int LastDayofMonth;
    TextView tv_curdate;
    Calendar cal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RV_date = findViewById(R.id.home_date_rv);
        tv_curdate = findViewById(R.id.home_date);
        cal = Calendar.getInstance();

        getDate();
        tv_curdate.setText(title_year + "년 " + title_month + "월");
        getLastDayofMonth();
        items = new ArrayList<>();

        setData();

        DateAdapter = new RecyclerAdapter(items);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.HORIZONTAL);
        llm.isSmoothScrollbarEnabled();
        RV_date.setLayoutManager(llm);
        RV_date.setAdapter(DateAdapter);


    }

    private void setData() {
        ArrayList<contents> datas = new ArrayList<>();
        int tmp;
        Calendar cal2 = Calendar.getInstance();
        for (int i = 1; i <= LastDayofMonth; i++) {//첫날부터 마지막 날까지
            cal2.set(Integer.parseInt(title_year),Integer.parseInt(title_month)-1,i);

            tmp = cal2.get(Calendar.DAY_OF_WEEK);

            Log.e("qwe",""+tmp+" "+LastDayofMonth);
            String tmp2 = "Error";
            switch(tmp){
                case 1: tmp2 = "일";break;
                case 2: tmp2 = "월";break;
                case 3: tmp2 = "화";break;
                case 4: tmp2 = "수";break;
                case 5: tmp2 = "목";break;
                case 6: tmp2 = "금";break;
                case 7: tmp2 = "토";break;
            }
            items.add(new Item(datas, title_year, title_month, String.valueOf(i), tmp2));
        }
    }

    private void getLastDayofMonth() {
        cal.set(Integer.parseInt(title_year),Integer.parseInt(title_month)-1,1);
        LastDayofMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private void getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddE");
        Date date = new Date();
        curTime = sdf.format(date);
        title_year = curTime.substring(0, 4);
        title_month = curTime.substring(4, 6);
    }
}
