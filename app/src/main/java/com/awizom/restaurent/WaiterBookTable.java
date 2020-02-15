package com.awizom.restaurent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    TextView total, ono;
    String tableid = "";
    Button orderBtn;
    EditText editTextName, editTextMob;
    ArrayList<String> fidarray = new ArrayList<>();
    ArrayList<String> quantarray = new ArrayList<>();
    ArrayList<String> fnamearray = new ArrayList<>();
    ArrayList<String> fpricearray = new ArrayList<>();
    android.support.v7.app.AlertDialog b;
    Animation animBounce;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_book_table);
        InitView();
    }

    private void InitView() {
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Book Table");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        total = findViewById(R.id.total);
        menuList = findViewById(R.id.menuList);
        menuList.setHasFixedSize(true);
        menuList.setLayoutManager(new LinearLayoutManager(this));
        editTextName = findViewById(R.id.editTextName);
        editTextMob = findViewById(R.id.editTextMob);
        recyclerView = findViewById(R.id.recyclerView);
        ono = findViewById(R.id.ono);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        orderBtn = findViewById(R.id.orderBtn);
        GetTableList();
        GetMenuList();
        GetOrderNo();
        animBounce = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tableid.toString().equals("")) {
                    String name = editTextName.getText().toString();
                    String mobno = editTextMob.getText().toString();
                    String totval = total.getText().toString();
                    removeZero();
                    Intent intent = new Intent(WaiterBookTable.this, TotalPaymentActivity.class);
                    intent.putExtra("CName", name);
                    intent.putExtra("TabID", tableid.toString());
                    intent.putExtra("MobNo", mobno);
                    intent.putExtra("OrderNo", ono.getText().toString());
                    intent.putExtra("TotalAmt", totval);
                    intent.putStringArrayListExtra("FoodID", (ArrayList<String>) fidarray);
                    intent.putStringArrayListExtra("QuantID", (ArrayList<String>) quantarray);
                    intent.putStringArrayListExtra("FoodName", (ArrayList<String>) fnamearray);
                    intent.putStringArrayListExtra("FoodPrice", (ArrayList<String>) fpricearray);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), tableid.toString(), Toast.LENGTH_LONG).show();
                    ShowCustomTabSelectError();
                }
            }
        });
    }

    private void ShowCustomTabSelectError() {

        final br.com.simplepass.loading_button_lib.customViews.CircularProgressButton submits;
        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(WaiterBookTable.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.tab_nott_select, null);

        TextView desc = dialogView.findViewById(R.id.textViewSub7Title);
        desc.startAnimation(animBounce);
        submits = dialogView.findViewById(R.id.cirSubmitButton);
        submits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        dialogView.setBackgroundColor(Color.parseColor("#F0F8FF"));
        b = dialogBuilder.create();
        b.show();
    }

    private void GetOrderNo() {
        try {
            String resultorder = new WaiterHelper.GetOrderNo().execute().get();
            if (resultorder.isEmpty()) {
                GetOrderNo();
            } else {

                ono.setText("Order No:" + resultorder.replace("\"", ""));
                /*     Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeZero() {
        for (int i = 0; i < quantarray.size(); i++) {
            int foodis = Integer.parseInt(quantarray.get(i).toString().split("T")[1]);
            if (foodis == 0) {
                quantarray.remove(i);
                fidarray.remove(i);
                fnamearray.remove(i);
                fpricearray.remove(i);
            }
        }
        boolean retval = quantarray.contains("0");
        if (retval == true) {
            removeZero();
        }
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
                menuFoodListAdapter = new MenuFoodListAdapter(this, menuItemModelList, total, fidarray, quantarray, fnamearray, fpricearray);
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
