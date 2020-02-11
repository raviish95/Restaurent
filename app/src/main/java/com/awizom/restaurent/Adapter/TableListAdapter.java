package com.awizom.restaurent.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.awizom.restaurent.Model.TableModel;
import com.awizom.restaurent.R;
import java.util.List;


public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.MyViewHolder> {

    private List<TableModel> tableModelList;
    private Context mCtx;
    View previousSelectedItem;
    LinearLayout lout;

    public TableListAdapter(Context baseContext, List<TableModel> tableModelList) {
        this.tableModelList = tableModelList;
        this.mCtx = baseContext;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TableModel c = tableModelList.get(position);
        holder.tableName.setText(c.getTabName());
        holder.seatNo.setText(String.valueOf(c.getSeatNum()));
        if (c.getIsBooked() == 1) {
            holder.linear.setBackgroundColor(Color.parseColor("#88FB83"));
        }
        holder.tableName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previousSelectedItem!=null) {

                    lout.setBackgroundColor(Color.WHITE);
                }
                previousSelectedItem=view;
                lout=  holder.linear;

                holder.linear.setBackgroundColor(Color.parseColor("#F7EF69"));
            }
        });
        holder.seatNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previousSelectedItem!=null) {
                    lout.setBackgroundColor(Color.WHITE);
                }
                previousSelectedItem=view;
                lout=  holder.linear;
                holder.linear.setBackgroundColor(Color.parseColor("#F7EF69"));
            }
        });

    }



    @Override
    public int getItemCount() {
        return tableModelList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_tablelist, parent, false);
        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tableName, seatNo;
        LinearLayout linear;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            tableName = view.findViewById(R.id.tableName);
            seatNo = view.findViewById(R.id.seatNo);
            linear = view.findViewById(R.id.linear);
        }
    }
}