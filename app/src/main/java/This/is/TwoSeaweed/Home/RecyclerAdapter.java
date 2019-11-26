package This.is.TwoSeaweed.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import This.is.TwoSeaweed.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<Item> items;
    String curTime;
    public static int selected_day;
    public RecyclerAdapter(ArrayList<Item> items) {
        this.items = items;
        getDate();
        selected_day = Integer.parseInt(curTime.substring(6,8));

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        ViewHolder viewholder=  new ViewHolder(v);
        return viewholder;
    }
    void setday(int a){
        selected_day=a;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.cur_day.setText(items.get(position).getCurday());
        holder.day.setText(items.get(position).getDay());
        if(selected_day-1==position) {
            holder.itemView.setBackgroundColor(0xffffdddd);
        }else{
            holder.itemView.setBackgroundColor(0xffffffff);
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                selected_day = Integer.parseInt(holder.day.getText().toString());
////                RecyclerAdapter.this.notifyDataSetChanged();
////                //뷰 클릭되었을 때
////            }
////        });
    }
    private void getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddE");
        Date date = new Date();
        curTime = sdf.format(date);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cur_day;
        TextView day;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cur_day= itemView.findViewById(R.id.home_item_curday);
            day = itemView.findViewById(R.id.home_item_day);
        }
    }
}
