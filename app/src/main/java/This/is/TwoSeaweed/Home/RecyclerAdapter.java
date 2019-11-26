package This.is.TwoSeaweed.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import This.is.TwoSeaweed.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<Item> items;

    public RecyclerAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        ViewHolder viewholder=  new ViewHolder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cur_day.setText(items.get(position).getCurday());
        holder.day.setText(items.get(position).getDay());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //뷰 클릭되었을 때
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView cur_day;
        TextView day;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cur_day= itemView.findViewById(R.id.home_item_curday);
            day = itemView.findViewById(R.id.home_item_day);
        }
    }
}
