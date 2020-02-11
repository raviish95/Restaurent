package com.awizom.restaurent;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.awizom.restaurent.Adapter.MenuListAdapter;
import com.awizom.restaurent.Adapter.TableListAdapter;
import com.awizom.restaurent.Helper.WaiterHelper;
import com.awizom.restaurent.Model.FoodCategoryModel;
import com.awizom.restaurent.Model.TableModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class WaiterBookTable extends AppCompatActivity {

    android.support.v7.widget.RecyclerView recyclerView,menuList;
    String result="";
    List<TableModel> tableModelList;
    List<FoodCategoryModel> menuModelList;
    TableListAdapter tableListAdapter;
    MenuListAdapter menuListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_book_table);
        InitView();
    }

    private void InitView() {
        menuList=findViewById(R.id.menuList);
        menuList.setHasFixedSize(true);
        menuList.setLayoutManager(new LinearLayoutManager(this));
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        GetTableList();
        GetMenuList();
    }

    private void GetMenuList() {

        try {
            result = new WaiterHelper.GetMenuList().execute().get();
            if (result.isEmpty()) {
                GetMenuList();
            } else {
                final Activity activity = this;
                Type listType = new TypeToken<List<FoodCategoryModel>>() {
                }.getType();
                /*     Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();*/
                menuModelList = new Gson().fromJson(result, listType);
                menuListAdapter = new MenuListAdapter(this, menuModelList);
                menuList.setAdapter(menuListAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void GetTableList() {

        try {
            result = new WaiterHelper.GetTableList().execute().get();
            if (result.isEmpty()) {
                GetTableList();
            } else {
                final Activity activity = this;
                Type listType = new TypeToken<List<TableModel>>() {
                }.getType();
                /*     Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();*/
                tableModelList = new Gson().fromJson(result, listType);
                tableListAdapter = new TableListAdapter(this, tableModelList);

                recyclerView.setAdapter(tableListAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
