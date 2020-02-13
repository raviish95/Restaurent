package com.awizom.restaurent.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


public class MenuFoodListAdapter extends RecyclerView.Adapter<MenuFoodListAdapter.MyViewHolder> {

    private List<MenuItemModel> menuItemModelList;
    private Context mCtx;
    View previousSelectedItem;
    LinearLayout lout;
    TextView total;
    ArrayList<String> fidarray;
    ArrayList<String> quantarray;
    ArrayList<String> fnamearray;
    ArrayList<String> fpricearray;

    public MenuFoodListAdapter(Context baseContext, List<MenuItemModel> menuItemModelList, TextView total, ArrayList<String> fidarray, ArrayList<String> quantarray, ArrayList<String> fnamearray, ArrayList<String> fpricearray) {
        this.menuItemModelList = menuItemModelList;
        this.mCtx = baseContext;
        this.total = total;
        this.fidarray = fidarray;
        this.quantarray = quantarray;
        this.fnamearray = fnamearray;
        this.fpricearray = fpricearray;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MenuItemModel c = menuItemModelList.get(position);
        holder.foodname.setText(c.getFoodName());
        holder.foodID.setText(String.valueOf(c.getMID()));
        holder.price.setText(String.valueOf(c.getPrice()));
        fidarray.add(holder.foodID.getText().toString());
        fnamearray.add(holder.foodname.getText().toString());
        fpricearray.add(holder.price.getText().toString());
        quantarray.add(holder.foodID.getText().toString() + "T0");
        holder.position.setText(String.valueOf(position));
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = Integer.parseInt(holder.value.getText().toString());
                int positionss = 0;
               for (String products : quantarray) {
                    if (products.equals(holder.foodID.getText().toString() + "T"+value))
                        positionss = quantarray.indexOf(products);
                }
                value = value + 1;
                quantarray.set(positionss, String.valueOf(holder.foodID.getText().toString() + "T"+value));
                holder.value.setText(String.valueOf(value));
                float finalfloat = Float.parseFloat(holder.price.getText().toString());
                float totalprice = Float.parseFloat(total.getText().toString().split("-")[1]);
                total.setText("Total-" + String.valueOf(totalprice + finalfloat));
            }
        });
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = Integer.parseInt(holder.value.getText().toString());
                if (value != 0.0) {
                    int positionss = 0;
                    for (String products : quantarray) {
                        if (products.equals(holder.foodID.getText().toString() + "T"+value))
                            positionss = quantarray.indexOf(products);
                    }
                    value = value - 1;

                    holder.value.setText(String.valueOf(value));


                    quantarray.set(positionss, String.valueOf(holder.foodID.getText().toString() + "T"+value));
                    float finalfloat = Float.parseFloat(holder.price.getText().toString());
                    float totalprice = Float.parseFloat(total.getText().toString().split("-")[1]) - finalfloat;
                    total.setText("Total-" + String.valueOf(totalprice));
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return menuItemModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_menufoodlist, parent, false);
        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView foodname, foodID, decrease, increase, value, price, position;

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

        }
    }
}