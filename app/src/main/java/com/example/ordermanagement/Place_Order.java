package com.example.ordermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Place_Order extends AppCompatActivity {

    
    private TextView ordernum_view;
    private TextView totslprice_view;
    private Button orderbtn;
    private ListView listview;
    private ArrayList<String> dishes_str = new ArrayList<>();
    private ArrayList<Dish_Add> dishes = new ArrayList<>();
    private ArrayAdapter<Dish_Add> addapter;
    private double total_price;
    private OrderForm order;
    private final DatabaseReference ordersdata = FirebaseDatabase.getInstance().getReference("Orders");
    private String City;
    private String Street;
    private String House_num;
    private String Phone_num;
    private boolean bool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
    

       


        ordernum_view = findViewById(R.id.orderNum_TV_rest);
        totslprice_view = findViewById(R.id.ordPrice_TV_rest);
        orderbtn = findViewById(R.id.Ord_btn_rest);
        listview = findViewById(R.id.ListView_place_order);
        Intent i = getIntent();
        Bundle extras = i.getBundleExtra("extras");
        final String order_num = extras.getString("order_id");
        String rest_id = extras.getString("rest_id");
        String client_id = extras.getString("client_id");
        String status = extras.getString("status");
        total_price = extras.getDouble("price");
        City = extras.getString("City");
        Street = extras.getString("Street");
        House_num = extras.getString("House_num");
        Phone_num = extras.getString("Phone_num");
       
        dishes_str.addAll(extras.getStringArrayList("dishes"));
        order = new OrderForm(order_num, rest_id, client_id, status);
        string_to_dishes_array();
        addapter = new ArrayAdapter<>(Place_Order.this, R.layout.customefont, dishes);
        totslprice_view.setText("price: " + Double.toString(total_price));
        ordernum_view.setText("order number" + order_num.replaceAll("[^0-9]", ""));
        listview.setAdapter(addapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                order.removeDish(dishes.get(i));
                dishes.remove(i);
                addapter = new ArrayAdapter<>(Place_Order.this, R.layout.customefont, dishes);
                listview.setAdapter(addapter);
                total_price = order.getTotal_price();
                totslprice_view.setText("price: " + total_price);
            }
        });

        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ordersdata.child(order_num).setValue(order);

                    Intent i = new Intent(Place_Order.this, PageOrder.class);
                    startActivity(i);


            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    private void string_to_dishes_array() {
        for (int j = 0; j < dishes_str.size(); j++) {
            String[] split = dishes_str.get(j).split(" ");
            String name = split[0];
            double price = Double.parseDouble(split[2].split(",")[0]);
            String desc = "";
            if (split.length >= 5) {
                desc = split[4];
            }
            Dish_Add dish = new Dish_Add(price, name, desc);
            dishes.add(dish);
        }
        for (int i = 0; i < dishes.size(); i++) {
            order.addDish(dishes.get(i));
        }
    }
   
}