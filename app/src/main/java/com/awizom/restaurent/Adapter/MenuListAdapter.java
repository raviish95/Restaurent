package com.awizom.restaurent.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.awizom.restaurent.Model.TableModel;
import com.awizom.restaurent.R;
import com.awizom.restaurent.WaiterBookTable;
import com.razorpay.Card;

import java.util.ArrayList;
import java.util.List;

import static com.awizom.restaurent.R.drawable.arrow_up;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private List<FoodCategoryModel> menumodelist;
    private Context mCtx;
    View previousSelectedItem;
    ArrayList<RecyclerView> prerecyclerView;
    ArrayList<CardView> preCardView;

    public MenuListAdapter(Context baseContext, List<FoodCategoryModel> menumodelist) {
        this.menumodelist = menumodelist;
        this.mCtx = baseContext;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        FoodCategoryModel c = menumodelist.get(position);
        holder.dishname.setText(c.getFCategory());
        holder.menuId.setText(String.valueOf(c.getFoodID()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if (preCardView != null) {
                    if (preCardView.contains(holder.cardView)) {
                        int posi = preCardView.indexOf(holder.cardView);
                        prerecyclerView.get(posi).setVisibility(View.VISIBLE);
                    } else {

                        preCardView.add(holder.cardView);
                        prerecyclerView.add(holder.recyclerViewFood);
                        ((WaiterBookTable) mCtx).GetFoodMenuList(holder.menuId.getText().toString(), holder.recyclerViewFood);
                    }
                }*/

                if (holder.test.getText().toString().equals("1")) {
                    Drawable img = mCtx.getResources().getDrawable(R.drawable.arrow_up);
                    holder.dishname.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    ((WaiterBookTable) mCtx).GetFoodMenuList(holder.menuId.getText().toString(), holder.recyclerViewFood);
                    holder.test.setText("2");
                } else if (holder.test.getText().toString().equals("2")) {
                    holder.recyclerViewFood.setVisibility(View.GONE);
                    Drawable img = mCtx.getResources().getDrawable(R.drawable.arrow_down);
                    holder.dishname.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    holder.test.setText("3");
                } else if (holder.test.getText().toString().equals("3")) {
                    holder.recyclerViewFood.setVisibility(View.VISIBLE);
                    Drawable img = mCtx.getResources().getDrawable(R.drawable.arrow_up);
                    holder.dishname.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    holder.test.setText("2");
                }
             /*   preCardView =new ArrayList<CardView>();
                prerecyclerView=new ArrayList<RecyclerView>();
                preCardView.add(holder.cardView);
                prerecyclerView.add(holder.recyclerViewFood);
                ((WaiterBookTable) mCtx).GetFoodMenuList(holder.menuId.getText().toString(), holder.recyclerViewFood);*/

           /*     if(previousSelectedItem!=null)
                {
                    prerecyclerView.setVisibility(View.GONE);
                }
                previousSelectedItem=view;
                prerecyclerView=holder.recyclerViewFood;*/

            }
        });
    }


    @Override
    public int getItemCount() {
        return menumodelist.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_menulist, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dishname, menuId, test;
        android.support.v7.widget.CardView cardView;
        android.support.v7.widget.RecyclerView recyclerViewFood;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            dishname = view.findViewById(R.id.dishname);
            menuId = view.findViewById(R.id.menuId);
            test = view.findViewById(R.id.test);
            cardView = view.findViewById(R.id.cardView);
            recyclerViewFood = view.findViewById(R.id.recyclerViewFood);
            recyclerViewFood.setHasFixedSize(true);
            recyclerViewFood.setLayoutManager(new LinearLayoutManager(mCtx));
        }
    }
}