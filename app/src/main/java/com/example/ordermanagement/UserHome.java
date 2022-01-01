package com.example.ordermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserHome extends AppCompatActivity {

    private Users_Form u;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//the current online user
    private DatabaseReference ref_users; //the reference for Users real time database
    private DatabaseReference ref_rests; //the reference for Restaurant real time database
    private ListView listView;
    private ArrayList<Restraunt_form> rest_f = new ArrayList<>();
    private ArrayList<String> rest_list = new ArrayList<>(); //will contains the data of all the restaurants
    private ArrayAdapter<String> rest_adapter; //the adapter that will get the rest_list and will be added to the list view


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);


        //INIT

        //click listener 0f personal settings
        listView = (ListView) findViewById(R.id.rest_view);
        ref_users = FirebaseDatabase.getInstance().getReference("User"); //get reference to Users
        ref_rests = FirebaseDatabase.getInstance().getReference("Restaurant"); //get reference to Restaurant


        // show the name of the user on top of the page
        ref_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                u = dataSnapshot.child(user.getUid()).getValue(Users_Form.class); // get user id of the current user
                TextView Hello_Name = findViewById(R.id.hello_name);
                Hello_Name.setText("Hello, " + u.getFirstName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i_rest_page = new Intent(UserHome.this, makeOrder.class);
                //
                i_rest_page.putExtra("rest_uid", rest_f.get(i).getUID());
                startActivity(i_rest_page);
            }
        });
        getData();
    }






    public void getData() {

        Query query = ref_rests.orderByChild("location");// order the database by location
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {//if there is a restaurants in this area
                    String rest_string;
                    for (DataSnapshot db : dataSnapshot.getChildren()) {
                        rest_string = db.child("name").getValue(String.class); //get name of the restaurants
                        Restraunt_form temp_rest = db.getValue(Restraunt_form.class);
                        rest_f.add(temp_rest);
                        rest_list.add(rest_string);

                    }
                    rest_adapter = new ArrayAdapter<String>(UserHome.this, R.layout.customefont, rest_list);
                    listView.setAdapter(rest_adapter);


                } else {
                    rest_list.add("No restaurants here");
                    rest_adapter = new ArrayAdapter<String>(UserHome.this, R.layout.customefont, rest_list);
                    listView.setAdapter(rest_adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });
    }


}