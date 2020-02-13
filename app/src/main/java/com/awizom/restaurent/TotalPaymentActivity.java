package com.awizom.restaurent;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awizom.restaurent.Adapter.MenuListAdapter;
import com.awizom.restaurent.Adapter.TableListAdapter;
import com.awizom.restaurent.Adapter.TotalAmountMenuAdapter;
import com.awizom.restaurent.Helper.WaiterHelper;
import com.awizom.restaurent.Model.FoodCategoryModel;
import com.awizom.restaurent.Model.TaxModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TotalPaymentActivity extends AppCompatActivity {

    TextView subTotal, netTotal, grdTotal, cname, mobno;
    EditText editDiscName, editTextSGSt, editTextCGST;
    ImageView submit;
    String result = "";
    List<TaxModel> taxModelList;
    ArrayList<String> arrylist = new ArrayList<String>();
    Spinner spin;
    String registertype = "";
    String CName = "", MobNo = "", TotalAmt = "", TabID = "";
    ArrayList<String> fidarray = new ArrayList<>();
    ArrayList<String> quantarray = new ArrayList<>();
    ArrayList<String> fnamearray = new ArrayList<>();
    ArrayList<String> fpricearray = new ArrayList<>();
    RecyclerView restolist;
    TotalAmountMenuAdapter totalAmountMenuAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_payment);
        InitView();
    }

    private void InitView() {
        TabID = getIntent().getStringExtra("TabID");
        CName = getIntent().getStringExtra("CName");
        MobNo = getIntent().getStringExtra("MobNo");
        TotalAmt = getIntent().getStringExtra("TotalAmt");
        fidarray = getIntent().getStringArrayListExtra("FoodID");
        quantarray = getIntent().getStringArrayListExtra("QuantID");
        fnamearray = getIntent().getStringArrayListExtra("FoodName");
        fpricearray = getIntent().getStringArrayListExtra("FoodPrice");
        Toast.makeText(getApplicationContext(), fidarray.toString() + "/" + quantarray.toString() + "/" + TabID.toString() + "/" + fnamearray.toString() + "/" + fpricearray.toString(), Toast.LENGTH_LONG).show();
        cname = findViewById(R.id.cname);
        mobno = findViewById(R.id.mobno);
        try {
            cname.setText(CName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mobno.setText(MobNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        restolist = findViewById(R.id.restolist);
        editTextSGSt = findViewById(R.id.editTextSGSt);
        editTextCGST = findViewById(R.id.editTextCGST);
        restolist.setHasFixedSize(true);
        restolist.setLayoutManager(new LinearLayoutManager(this));
        subTotal = findViewById(R.id.subTotal);
        subTotal.setText("SubTotal(Rs.):" + TotalAmt.toString().split("-")[1]);
        netTotal = findViewById(R.id.netTotal);
        netTotal.setText("Net Total(Rs):" + TotalAmt.toString().split("-")[1]);
        grdTotal = findViewById(R.id.grdTotal);
        editDiscName = findViewById(R.id.editDiscName);
        editDiscName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float sbtotl = Float.parseFloat(subTotal.getText().toString().split(":")[1]);
                float discamt = 0;
                try {
                    discamt = Float.parseFloat(editDiscName.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                float resnet = sbtotl - ((discamt / 100) * sbtotl);
                netTotal.setText("Net Total(Rs):" + String.valueOf(resnet));
            }
        });
        editTextSGSt = findViewById(R.id.editTextSGSt);
        editTextCGST = findViewById(R.id.editTextCGST);
        spin = findViewById(R.id.spinner);
        submit = findViewById(R.id.submit);
        GetGST();
        SetAdapter();
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), regtype[position], Toast.LENGTH_LONG).show();
                registertype = arrylist.get(position).toString();
                float halfval = Float.parseFloat(registertype.split("\\+")[1]);
                float halfgstval = halfval / 2;
                editTextSGSt.setText(String.valueOf(halfgstval));
                editTextCGST.setText(String.valueOf(halfgstval));
                float NetTotal = Float.parseFloat(netTotal.getText().toString().split(":")[1]);
                float Trate = Float.parseFloat(registertype.split("\\+")[1]);
                float selectValue = ((NetTotal * (Trate / 100)) / 2);
                float res = NetTotal + (NetTotal * (Trate / 100));
                grdTotal.setText("Grand Total(Rs):" + String.valueOf(res));
                Toast.makeText(getApplicationContext(), halfgstval + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void SetAdapter() {
        totalAmountMenuAdapter = new TotalAmountMenuAdapter(this, fnamearray, fpricearray, quantarray);
        restolist.setAdapter(totalAmountMenuAdapter);
    }

    private void GetGST() {
        try {
            String result = new WaiterHelper.GetGSTType().execute().get();
            if (result.isEmpty()) {
                GetGST();
            } else {
                Type listType = new TypeToken<List<TaxModel>>() {
                }.getType();
                taxModelList = new Gson().fromJson(result, listType);
                for (int i = 0; i < taxModelList.size(); i++) {
                    if (taxModelList.get(i).getTName().equals("GST")) {
                        arrylist.add(taxModelList.get(i).getTName() + " + " + taxModelList.get(i).getTRate());
                    }
                }
                ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrylist);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spin.setAdapter(aa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
