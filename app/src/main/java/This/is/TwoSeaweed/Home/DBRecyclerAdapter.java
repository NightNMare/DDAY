package This.is.TwoSeaweed.Home;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import This.is.TwoSeaweed.R;

public class DBRecyclerAdapter extends RecyclerView.Adapter<DBRecyclerAdapter.ViewHolder> {
    ArrayList<contents> datas;
    CustomDialog cd;
    int a = 0;
    int RA_position;
    Context context;

    public DBRecyclerAdapter(ArrayList<contents> datas, Context context) {
        this.datas = datas;
        this.context = context;
        cd = new CustomDialog(context);
        DisplayMetrics dm = context.getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels; //디바이스 화면 너비
        int height = dm.heightPixels;

        WindowManager.LayoutParams wm = cd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
        wm.copyFrom(cd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
        wm.width = width/3*2;  //화면 너비의 절반
        wm.height = height/3*2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_data, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ImageView img = holder.state;
        switch(datas.get(position).getState()){
            case 0: img.setImageResource(R.drawable.ic_x); break;
            case 1: img.setImageResource(R.drawable.ic_right);break;
            case 2: img.setImageResource(R.drawable.ic_clock);break;
            case 3: img.setImageResource(R.drawable.ic_check);break;
            default: img.setImageResource(R.drawable.ic_x);
        }
        holder.tv.setText(datas.get(position).content);
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(holder.itemView.getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                contents data = datas.get(position);
                db.execSQL("delete from tb_data where year='" + data.getYear() + "' AND month='" + data.getMonth() + "' AND day='" + data.getDay() + "' AND _id=" + data.getKey() + "");
                datas.remove(position);
                holder.del.setVisibility(View.GONE);
                a=0;
                //year, month, day, content, state
                DBRecyclerAdapter.this.notifyDataSetChanged();
                db.close();
            }
        });
        holder.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cd.show();
                RA_position = position;
            }
        });
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a == 0) {
                    holder.del.setVisibility(View.VISIBLE);
                    a = 1;
                } else {
                    holder.del.setVisibility(View.GONE);
                    a = 0;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView del;
        ImageView state;
        TextView tv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            del = itemView.findViewById(R.id.data_del);
            state = itemView.findViewById(R.id.data_state);
            tv = itemView.findViewById(R.id.data_tv);
        }
    }
    class CustomDialog extends Dialog {
        LinearLayout x,later,doing,finish;
        public CustomDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
            setContentView(R.layout.customdialog);     //다이얼로그에서 사용할 레이아웃입니다.
            x = findViewById(R.id.dialog_x);
            later = findViewById(R.id.dialog_later);
            doing = findViewById(R.id.dialog_doing);
            finish = findViewById(R.id.dialog_finish);
            x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setData(0);
                }
            });
            later.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setData(1);
                }
            });
            doing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setData(2);
                }
            });
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setData(3);
                }
            });

        }
        void setData(int state){
            datas.get(RA_position).setState(state);
            DBRecyclerAdapter.this.notifyDataSetChanged();
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("update tb_data set state = "+datas.get(RA_position).getState()+" where year='" + datas.get(RA_position).getYear() + "' AND month='" + datas.get(RA_position).getMonth() + "' AND day='" + datas.get(RA_position).getDay() + "' AND _id=" + datas.get(RA_position).getKey() + "");
            db.close();
            //DB에 state 변경
            dismiss();
        }
    }

}

