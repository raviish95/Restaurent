package com.awizom.restaurent.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.restaurent.Model.FoodCategoryModel;
import com.awizom.restaurent.Model.MenuItemModel;
import com.awizom.restaurent.Model.TableModel;
import com.awizom.restaurent.R;
import com.awizom.restaurent.WaiterBookTable;

import java.util.ArrayList;
import java.util.List;


public class TotalAmountMenuAdapter extends RecyclerView.Adapter<TotalAmountMenuAdapter.MyViewHolder> {


    private Context mCtx;

    ArrayList<String> fnamearray;
    ArrayList<String> fpricearray;
    ArrayList<String> quantarray;

    public TotalAmountMenuAdapter(Context baseContext, ArrayList<String> fnamearray, ArrayList<String> fpricearray, ArrayList<String> quantarray) {

        this.mCtx = baseContext;
        this.fnamearray = fnamearray;
        this.fpricearray = fpricearray;
        this.quantarray = quantarray;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.foodname.setText(fnamearray.get(position));
        holder.price.setText(fpricearray.get(position));
        holder.value.setText(quantarray.get(position).toString().split("T")[1]);
        float vales=(Float.parseFloat(holder.value.getText().toString()))*(Float.parseFloat(holder.price.getText().toString()));
        holder.Totprice.setText(String.valueOf(vales));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return fnamearray.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_totalamountmenulist, parent, false);
        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView foodname, foodID, decrease, increase, value, price, position,Totprice;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            foodname = view.findViewById(R.id.foodname);
            foodID = view.findViewById(R.id.foodID);
            decrease = view.findViewById(R.id.decrease);
            increase = view.findViewById(R.id.increase);
            value = view.findViewById(R.id.value);
            price = view.findViewById(R.id.price);
            position = view.findViewById(R.id.position);
            Totprice=view.findViewById(R.id.Totprice);
        }
    }
}