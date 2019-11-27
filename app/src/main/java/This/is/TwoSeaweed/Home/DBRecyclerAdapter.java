package This.is.TwoSeaweed.Home;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import This.is.TwoSeaweed.R;

public class DBRecyclerAdapter extends RecyclerView.Adapter<DBRecyclerAdapter.ViewHolder> {
    ArrayList<contents> datas;
    int a=0;

    public DBRecyclerAdapter(ArrayList<contents> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_data, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tv.setText(datas.get(position).content);
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(holder.itemView.getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                contents data = datas.get(position);
                Log.e("asd", "delete from tb_data where year='" + data.getYear() + "' AND month='" + data.getMonth() + "' AND day='" + data.getDay() + "' AND _id='" + data.getKey() + "'");
                db.execSQL("delete from tb_data where year='" + data.getYear() + "' AND month='" + data.getMonth() + "' AND day='" + data.getDay() + "' AND _id=" + data.getKey() + "");
                datas.remove(position);
                //year, month, day, content, state
                DBRecyclerAdapter.this.notifyDataSetChanged();
                db.close();
            }
        });
        holder.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "state" + position, Toast.LENGTH_SHORT).show();//////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        });
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(a==0) {
                    holder.del.setVisibility(View.VISIBLE);
                    a=1;
                }else{
                    holder.del.setVisibility(View.GONE);
                    a=0;
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
}
