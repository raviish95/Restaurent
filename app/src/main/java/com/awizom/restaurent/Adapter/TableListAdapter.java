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
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.restaurent.Model.TableModel;
import com.awizom.restaurent.R;
import com.awizom.restaurent.WaiterBookTable;

import java.util.List;


public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.MyViewHolder> {

    private List<TableModel> tableModelList;
    private Context mCtx;
    View previousSelectedItem;
    LinearLayout lout;
    String tableid;

    public TableListAdapter(Context baseContext, List<TableModel> tableModelList, String tableid) {
        this.tableModelList = tableModelList;
        this.mCtx = baseContext;
        this.tableid=tableid;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TableModel c = tableModelList.get(position);
        holder.tableName.setText(c.getTabName());
        holder.seatNo.setText(String.valueOf(c.getSeatNum()));
        if (c.getIsBooked() == 1) {
            holder.booked.setText("Book");
            holder.linear.setBackgroundColor(Color.parseColor("#88FB83"));
        }
        holder.tableid.setText(String.valueOf(c.getTabID()));
        holder.tableName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.booked.getText().toString().equals("Book")) {
                    if (previousSelectedItem != null) {

                        lout.setBackgroundColor(Color.WHITE);
                    }
                    ((WaiterBookTable)mCtx).setValue(holder.tableid.getText().toString());

                    previousSelectedItem = view;
                    lout = holder.linear;
                    holder.linear.setBackgroundColor(Color.parseColor("#F7EF69"));

                } else {
                    Toast.makeText(mCtx, "Table is already booked", Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.seatNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.booked.getText().toString().equals("Book")) {
                    if (previousSelectedItem != null) {

                        lout.setBackgroundColor(Color.WHITE);
                    }

                    tableid=String.valueOf(holder.tableid.getText().toString());
                    previousSelectedItem = view;
                    lout = holder.linear;
                    holder.linear.setBackgroundColor(Color.parseColor("#F7EF69"));

                } else {
                    Toast.makeText(mCtx, "Table is already booked", Toast.LENGTH_LONG).show();
                }
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
        TextView tableName, seatNo, tableid, booked;
        LinearLayout linear;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            tableName = view.findViewById(R.id.tableName);
            seatNo = view.findViewById(R.id.seatNo);
            linear = view.findViewById(R.id.linear);
            tableid = view.findViewById(R.id.tableid);
            booked = view.findViewById(R.id.booked);
        }
    }
}