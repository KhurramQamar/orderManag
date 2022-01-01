package com.example.ordermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Menu extends AppCompatActivity implements View.OnClickListener {



        private CheckBox food;
        private CheckBox drink;
        private CheckBox other;
        private DatabaseReference ref_menus;
        private TextView menu_name;
        private Menu_Form data_menu;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<String> arr;
       menu_adapter addapter;
        ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



            data_menu = new Menu_Form();
            ref_menus = FirebaseDatabase.getInstance().getReference("Menus");
            list = findViewById(R.id.Menu_List);
            arr = new ArrayList<String>();
            findViewById(R.id.Add_Dish).setOnClickListener(this);
            get_menu("Food");
            get_menu("Drink");
            get_menu("Other");



        }
        private void get_menu(String str){ //get the chosen menu and set menu_name
            //show the menu name
            final String type_dish = str;
            ref_menus.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.child(user.getUid()).exists()) { //if the user has menu
                        DataSnapshot user_menu = dataSnapshot.child(user.getUid());
                        if (type_dish == "Food") { //if we chose starters then add it to menu and add it to the list we display (arr)
                            add_starterslist_menu_todata_menu(user_menu);
                            get_arrtype(arr,data_menu.getfood_list() );


                        } else if (type_dish == "Drink") { //same
                            add_desertlist_menu_todata_menu(user_menu);
                            get_arrtype(arr,data_menu.getDrink_list() );


                        } else if (type_dish == "Other") { //same
                            add_drinklist_menu_todata_menu(user_menu);
                            get_arrtype(arr,data_menu.getothers_list() );


                        } 
                        addapter = new menu_adapter(arr,Menu.this,type_dish,data_menu); // intilize addapter
                        list.setAdapter(addapter); //show the addapter
                        data_menu.clea_others();
                        data_menu.clear_foods();
                        data_menu.clear_drink();
                        
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        private void add_starterslist_menu_todata_menu(DataSnapshot dt) { //this function add all the deserts in the user menu in database to arr
            DataSnapshot maindata = dt.child("food_list"); //maindata = deserts menu of the current in database
            for (DataSnapshot childdata : maindata.getChildren()) { //loop on all the dishes in desert menu of the user
                String name = childdata.child("dish_name").getValue(String.class); //name of the dish
                String desc = childdata.child("dish_description").getValue(String.class); //discription of the dish
                double price = childdata.child("price").getValue(double.class); //price of the dish
                Dish_Add dish = new Dish_Add(price, name, desc);
                data_menu.add_food_dish(dish); //add dish to arr disertt menu
            }
        }
        private void add_desertlist_menu_todata_menu(DataSnapshot dt) { //this function add all the deserts in the user menu in database to arr
            DataSnapshot maindata = dt.child("drink_list"); //maindata = deserts menu of the current in database
            for (DataSnapshot childdata : maindata.getChildren()) { //loop on all the dishes in desert menu of the user
                String name = childdata.child("dish_name").getValue(String.class); //name of the dish
                String desc = childdata.child("dish_description").getValue(String.class); //discription of the dish
                double price = childdata.child("price").getValue(double.class); //price of the dish
                Dish_Add dish = new Dish_Add(price, name, desc);
                data_menu.add_drink(dish); //add dish to arr disertt menu
            }
        }
        private void add_drinklist_menu_todata_menu(DataSnapshot dt) { //same function as: same as add_desertlist_menu_toarr with drink dish
            DataSnapshot maindata = dt.child("others_list");
            for (DataSnapshot childdata : maindata.getChildren()) {
                String name = childdata.child("dish_name").getValue(String.class);
                String desc = childdata.child("dish_description").getValue(String.class);
                double price = childdata.child("price").getValue(double.class);
                Dish_Add dish = new Dish_Add(price, name, desc);
                data_menu.add_other_dish(dish);
            }
        }



        @Override
        public void onClick(View v) { //make sure only one chekbox will be chek
            switch (v.getId()) {

                case R.id.Add_Dish:
                    Intent i = new Intent(this, AddDish.class);
                    startActivity(i);

            }
        }
        public void get_arrtype(ArrayList<String> arr_list, ArrayList<Dish_Add> dishes){ //add the dish info to arr, if arr is not empty clear it first

            for (int i = 0; i < dishes.size(); i++){
                arr_list.add(dishes.get(i).to_string());
            }
        }
    }
