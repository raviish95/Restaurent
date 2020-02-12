package com.awizom.restaurent;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.restaurent.Adapter.MenuFoodListAdapter;
import com.awizom.restaurent.Adapter.MenuListAdapter;
import com.awizom.restaurent.Adapter.TableListAdapter;
import com.awizom.restaurent.Helper.WaiterHelper;
import com.awizom.restaurent.Model.FoodCategoryModel;
import com.awizom.restaurent.Model.MenuItemModel;
import com.awizom.restaurent.Model.TableModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WaiterBookTable extends AppCompatActivity {

    android.support.v7.widget.RecyclerView recyclerView, menuList;
    String result = "";
    List<TableModel> tableModelList;
    List<FoodCategoryModel> menuModelList;
    List<MenuItemModel> menuItemModelList;
    TableListAdapter tableListAdapter;
    MenuListAdapter menuListAdapter;
    MenuFoodListAdapter menuFoodListAdapter;
    TextView total;
    String tableid = "";
    Button orderBtn;
    EditText editTextName, editTextMob;
    ArrayList<String> fidarray = new ArrayList<>();
    ArrayList<String> quantarray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_book_table);
        InitView();
    }

    private void InitView() {
        total = findViewById(R.id.total);
        menuList = findViewById(R.id.menuList);
        menuList.setHasFixedSize(true);
        menuList.setLayoutManager(new LinearLayoutManager(this));
        editTextName = findViewById(R.id.editTextName);
        editTextMob = findViewById(R.id.editTextMob);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        orderBtn = findViewById(R.id.orderBtn);
        GetTableList();
        GetMenuList();
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String mobno = editTextMob.getText().toString();
                String totval = total.getText().toString();
                Toast.makeText(getApplicationContext(),fidarray.toString()+"/"+quantarray.toString()+"/"+ tableid.toString(), Toast.LENGTH_LONG).show();
            }
        });
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

    public void GetFoodMenuList(String fid, RecyclerView recyclerViewFood) {
        recyclerViewFood.setVisibility(View.VISIBLE);
        try {
            result = new WaiterHelper.GetFoodMenuList().execute(fid).get();
            if (result.isEmpty()) {
                GetFoodMenuList(fid, recyclerViewFood);
            } else {
                final Activity activity = this;
                Type listType = new TypeToken<List<MenuItemModel>>() {
                }.getType();
                /*     Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();*/
                menuItemModelList = new Gson().fromJson(result, listType);
                menuFoodListAdapter = new MenuFoodListAdapter(this, menuItemModelList, total,fidarray,quantarray);
                recyclerViewFood.setAdapter(menuFoodListAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setValue(String vals) {

        tableid = vals;
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
                tableListAdapter = new TableListAdapter(this, tableModelList, tableid);

                recyclerView.setAdapter(tableListAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
