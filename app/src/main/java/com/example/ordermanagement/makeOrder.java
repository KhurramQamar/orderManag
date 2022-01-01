package com.example.ordermanagement;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class makeOrder extends AppCompatActivity implements View.OnClickListener {


    private ListView listview;
    private String rest_uid;
    private ArrayList<Dish_Add> dish_menu = new ArrayList<>();
    private ArrayAdapter<Dish_Add> dish_addapter;
    private DatabaseReference menu_db = FirebaseDatabase.getInstance().getReference("Menus");
    private String user_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private OrderForm order;
    private TextView totalptv;
    private Button placeorderbtn;
    private DatabaseReference users = FirebaseDatabase.getInstance().getReference("User").child(user_uid);
    private CheckBox editbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);


        editbox=findViewById(R.id.editOrd_CB_rest);
        placeorderbtn = findViewById(R.id.placeOrd_btn_rest);
        listview = findViewById(R.id.Listview_Make_Order);
        totalptv = findViewById(R.id.totalOrd_TV_rest);
        Intent i = getIntent();
        rest_uid = (String) i.getSerializableExtra("rest_uid");
        final String ordernum = rest_uid + user_uid + (((int) ((Math.random()) * 10000)));
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    order = new OrderForm(ordernum, rest_uid, user_uid, "unhandled");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!editbox.isChecked()) {
                    order.addDish(dish_menu.get(i));
                    totalptv.setText("Total Order: " + order.getTotal_price());
                } else {
                    order.removeDish(dish_menu.get(i));
                    dish_menu.remove(i);
                    dish_addapter = new ArrayAdapter<Dish_Add>(makeOrder.this, R.layout.customefont, dish_menu);
                    listview.setAdapter(dish_addapter);
                    totalptv.setText("Total price: " + order.getTotal_price());
                }
            }
        });
        placeorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ordernum = order.getOrder_num();
                String restid = order.getRest_id();
                String clientid = order.getClient_id();
                String status = order.getStatus();

                Intent i = new Intent(makeOrder.this, Place_Order.class);
                Bundle extras = new Bundle();
                extras.putString("order_id", ordernum);
                extras.putString("rest_id", restid);
                extras.putString("client_id", clientid);
                extras.putString("status", status);
                extras.putDouble("price", order.getTotal_price());

                ArrayList<String> dishes = new ArrayList<>();

                for (int j = 0; j < order.getDishs_orderd().size(); j++) {
                    dishes.add(order.getDishs_orderd().get(j).to_string());
                }

                extras.putStringArrayList("dishes", dishes);
                i.putExtra("extras", extras);
                startActivity(i);
                finish();
            }
        });
        getdata();
    }

    private void getdata() {
        menu_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.child(rest_uid).exists()) {
                    double price;
                    String name_dish;
                    String desc_dish;

                    DataSnapshot snep = dataSnapshot.child(rest_uid);
                    if (snep.child("food_list").exists()) {
                        for (DataSnapshot db : snep.child("food_list").getChildren()) {
                            price = db.child("price").getValue(double.class);
                            name_dish = db.child("dish_name").getValue(String.class);
                            desc_dish = db.child("dish_description").getValue(String.class);
                            Dish_Add temp = new Dish_Add(price, name_dish, desc_dish);
                            dish_menu.add(temp);

                        }
                    }
                    if (snep.child("drink_list").exists()) {
                        for (DataSnapshot db : snep.child("drink_list").getChildren()) {
                            price = db.child("price").getValue(double.class);
                            name_dish = db.child("dish_name").getValue(String.class);
                            desc_dish = db.child("dish_description").getValue(String.class);
                            Dish_Add temp = new Dish_Add(price, name_dish, desc_dish);
                            dish_menu.add(temp);

                        }
                    }
                    if (snep.child("others_list").exists()) {
                        for (DataSnapshot db : snep.child("others_list").getChildren()) {
                            price = db.child("price").getValue(double.class);
                            name_dish = db.child("dish_name").getValue(String.class);
                            desc_dish = db.child("dish_description").getValue(String.class);
                            Dish_Add temp = new Dish_Add(price, name_dish, desc_dish);
                            dish_menu.add(temp);

                        }
                    }
                    dish_addapter = new ArrayAdapter<Dish_Add>(makeOrder.this, R.layout.customefont, dish_menu);

                    if (editbox.isChecked()) {
                        if (!order.getDishs_orderd().isEmpty()) {
                            dish_menu.addAll(order.getDishs_orderd());
                            dish_addapter = new ArrayAdapter<Dish_Add>(makeOrder.this, R.layout.customefont, dish_menu);
                        } else {
                            Toast.makeText(makeOrder.this, "you haven't chose dishes to order", Toast.LENGTH_SHORT).show();

                        }
                    }

                    listview.setAdapter(dish_addapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) { //make sure only one chekbox will be chek
        switch (v.getId()) {

            case R.id.editOrd_CB_rest:
                recreate();


        }

    }


}