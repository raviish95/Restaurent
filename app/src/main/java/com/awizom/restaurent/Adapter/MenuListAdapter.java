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

import com.awizom.restaurent.Model.FoodCategoryModel;
import com.awizom.restaurent.Model.TableModel;
import com.awizom.restaurent.R;

import java.util.List;


public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private List<FoodCategoryModel> menumodelist;
    private Context mCtx;
    View previousSelectedItem;
    LinearLayout lout;

    public MenuListAdapter(Context baseContext, List<FoodCategoryModel> menumodelist) {
        this.menumodelist = menumodelist;
        this.mCtx = baseContext;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        FoodCategoryModel c = menumodelist.get(position);
        holder.dishname.setText(c.getFCategory());
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
        TextView dishname;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public MyViewHolder(View view) {
            super(view);
            dishname = view.findViewById(R.id.dishname);

        }
    }
}