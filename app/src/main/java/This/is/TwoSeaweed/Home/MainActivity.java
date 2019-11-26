package This.is.TwoSeaweed.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import This.is.TwoSeaweed.R;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

public class MainActivity extends AppCompatActivity {
    RecyclerView RV_date;
    ArrayList<Item> items;
    RecyclerAdapter DateAdapter;
    String curTime;
    String title_year;
    String title_month;
    String title_day;
    int LastDayofMonth;
    TextView tv_titledate;
    Calendar cal;
    int pressed_state;
    DatePickerDialog dialog;
    FloatingActionButton fab;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RV_date = findViewById(R.id.home_date_rv);
        tv_titledate = findViewById(R.id.home_date);
        fab = findViewById(R.id.home_fab);
        edt = findViewById(R.id.home_edt);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_option) {
                    //옵션 선택
                } else if (item.getItemId() == R.id.menu_edit) {
                    //편집 선택
                }
                return true;
            }
        });

        cal = Calendar.getInstance();
        getDate();
        tv_titledate.setText(title_year + "년 " + title_month + "월");
        items = new ArrayList<>();
        setData();
        DateAdapter = new RecyclerAdapter(items);
        RV_date.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (e.getAction() == ACTION_UP) {
                    if (pressed_state == 1) {
                        if (child != null) {
                            DateAdapter.setday(rv.getChildViewHolder(child).getAdapterPosition() + 1);
                            DateAdapter.notifyDataSetChanged();
                            //메모 데이터 갱신
                            return true;
                        }
                    }
                }
                if (e.getAction() == ACTION_MOVE) {
                    pressed_state = 0;
                }
                if (e.getAction() == ACTION_DOWN) {
                    pressed_state = 1;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.HORIZONTAL);
        llm.isSmoothScrollbarEnabled();
        RV_date.setLayoutManager(llm);
        RV_date.setAdapter(DateAdapter);
        if (Integer.parseInt(curTime.substring(6, 8)) < 4) {
            RV_date.scrollToPosition(Integer.parseInt(curTime.substring(6, 8)));
        } else {
            RV_date.scrollToPosition(Integer.parseInt(curTime.substring(6, 8)) - 4);
        }

        dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                title_year = String.valueOf(year);
                title_month = String.valueOf(month);
                title_day = String.valueOf(day);
                tv_titledate.setText(year + "년" + " " + (month + 1) + "월");
                DateAdapter.setday(day);
                setData();
                DateAdapter.notifyDataSetChanged();
                if (day < 4) {
                    RV_date.scrollToPosition(day - 1);
                } else if (day + 4 > 30) {
                    RV_date.scrollToPosition(day - 1);
                } else {
                    RV_date.scrollToPosition(day - 4);
                }
            }
        }, Integer.parseInt(title_year), Integer.parseInt(title_month) - 1, Integer.parseInt(curTime.substring(6, 8)));
        tv_titledate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.setVisibility(View.VISIBLE);
                edt.post(new Runnable() {
                    @Override
                    public void run() {
                        edt.setFocusableInTouchMode(true);
                        edt.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edt, 0);
                    }
                });
            }
        });
    }


    private void setData() {
        items.clear();
        int tmp;
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Integer.parseInt(title_year), Integer.parseInt(title_month), Integer.parseInt(title_day));
        LastDayofMonth = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= LastDayofMonth; i++) {//첫날부터 마지막 날까지
            cal2.set(Integer.parseInt(title_year), Integer.parseInt(title_month), i);

            tmp = cal2.get(Calendar.DAY_OF_WEEK);

//            Log.e("qwe", "" + title_month + " " + LastDayofMonth+" "+tmp);
            String tmp2 = "Error";
            switch (tmp) {
                case 1:
                    tmp2 = "일";
                    break;
                case 2:
                    tmp2 = "월";
                    break;
                case 3:
                    tmp2 = "화";
                    break;
                case 4:
                    tmp2 = "수";
                    break;
                case 5:
                    tmp2 = "목";
                    break;
                case 6:
                    tmp2 = "금";
                    break;
                case 7:
                    tmp2 = "토";
                    break;
            }
            items.add(new Item(title_year, title_month, String.valueOf(i), tmp2));
        }
    }

    private void getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddE");
        Date date = new Date();
        curTime = sdf.format(date);
        title_year = curTime.substring(0, 4);
        title_month = curTime.substring(4, 6);
        title_day = curTime.substring(6, 8);
        cal.set(Integer.parseInt(title_year), Integer.parseInt(title_month) - 1, 1);
        LastDayofMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onBackPressed() {
        if(edt.getVisibility()==View.VISIBLE){
            edt.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }

    }
}
