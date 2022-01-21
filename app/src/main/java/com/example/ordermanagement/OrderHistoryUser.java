package com.example.ordermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryUser extends AppCompatActivity {
    private ListView listview;
    private ArrayList<OrderForm> orders_list;
    private ArrayAdapter<OrderForm> adapter;
    private final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private final DatabaseReference ref_orders = FirebaseDatabase.getInstance().getReference("Orders");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_user);


        orders_list = new ArrayList<>();
        listview = findViewById(R.id.listView_order_history);
        ref_orders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!orders_list.isEmpty()) {
                    orders_list.clear();
                    adapter.clear();
                    listview.clearAnimation();
                }
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snep : dataSnapshot.getChildren()) {
                        if (snep.child("client_id").getValue().equals(uid) && snep.child("status").getValue().equals("done")) {
                            String order_num = snep.child("order_num").getValue(String.class);
                            String rest_id = snep.child("rest_id").getValue(String.class);
                            String client_id = snep.child("client_id").getValue(String.class);
                            String status = snep.child("status").getValue(String.class);
                            OrderForm order = new OrderForm(order_num, rest_id, client_id, status);

                            DataSnapshot dishes_ordered = snep.child("dishs_orderd");
                            
                        }
                    }
                }
                adapter = new ArrayAdapter<OrderForm>(OrderHistoryUser.this, android.R.layout.simple_list_item_1, orders_list);
                listview.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OrderHistoryUser.this, Place_Order.class);
                OrderForm temp_order = orders_list.get(i);
                Bundle extras = new Bundle();
                put_to_extras(temp_order, extras);
                intent.putExtra("extras", extras);
                startActivity(intent);
                finish();
            }
        });
    }

    private void put_to_extras(OrderForm temp_order, Bundle extras) {
        final String ordernum = temp_order.getRest_id() + temp_order.getClient_id()+ (((int) ((Math.random()) * 10000)));
        extras.putString("order_id", ordernum);
        extras.putString("rest_id", temp_order.getRest_id());
        extras.putString("client_id", temp_order.getClient_id());
        extras.putString("status", temp_order.getStatus());
        extras.putDouble("price", temp_order.getTotal_price());

        ArrayList<String> dishes = new ArrayList<>();
        for (int j = 0; j < temp_order.getDishs_orderd().size(); j++) {
            dishes.add(temp_order.getDishs_orderd().get(j).to_string());
        }
        extras.putStringArrayList("dishes", dishes);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

