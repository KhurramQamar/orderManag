package com.example.ordermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddDish extends AppCompatActivity implements View.OnClickListener{

    private TextView name;
    private TextView price;
    private TextView description;
    private Button add;
    private CheckBox  food, drink, other;
    private DatabaseReference menus;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private boolean menu_exist;
    private boolean menu_empty;
    private Menu_Form arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        name = (TextView) findViewById(R.id.dish_name);
        price = (TextView) findViewById(R.id.dish_price);
        description = (TextView) findViewById(R.id.dish_desc);
        add = (Button) findViewById(R.id.add_button);

        food = (CheckBox) findViewById(R.id.food);
        drink = (CheckBox) findViewById(R.id.Drink);
        other = (CheckBox) findViewById(R.id.Other);
        menu_empty = false;
        menu_exist = false;
        menus = FirebaseDatabase.getInstance().getReference("Menus");
        arr = new Menu_Form();
        get_menu();

        add.setOnClickListener(new View.OnClickListener() { //add button function
            @Override
            public void onClick(View view) {
                String namestr = name.getText().toString().trim();
                String desc = description.getText().toString().trim();
                String pricestr = price.getText().toString().trim();
                double price_doub;
                if (pricestr.isEmpty()) { //chek if there is a price
                    price.setError("price filed is empty");
                    price.requestFocus();
                    return;
                } else {
                    price_doub = Double.parseDouble(pricestr);
                }
                if (namestr.isEmpty()) { //chek if there is a name to the dish
                    name.setError("dish name is empty");
                    name.requestFocus();
                    return;
                }
                Dish_Add dish = new Dish_Add(price_doub, namestr, desc);
                if (menu_exist == true) {
                    AddDish_toexist_menu(dish);

                } else if (menu_empty == true) {
                    write_menu_indata(dish);
                }
            }
        });
       




    public void get_menu() { //get menu from database
        menus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.child(user.getUid()).exists()) { //if there is a data in menu and the user have a menu
                    DataSnapshot user_menu = dataSnapshot.child(user.getUid()); //user_menu get the menu data from database
                    if (user_menu.child("food_list").exists()) { //if there is a food_list menu in database add get it to arr
                        add_foodlist_menu_toarr(user_menu);
                    }

                    if (user_menu.child("drink_list").exists()) { //if there is a frink_list menu in database then add it to arr
                        add_drinklist_menu_toarr(user_menu);
                    }

                    if (user_menu.child("others_list").exists()) { //if there is a others_list menu in database then add it to arr
                        add_otherlist_menu_toarr(user_menu);
                    }

                    menu_exist = true;

                } else { //if there is no menu then create an emty one
                    arr = new Menu_Form();
                    menu_empty = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getMessage();

            }
        });
    }

    private void add_otherlist_menu_toarr(DataSnapshot dt) { //this function add all the others in the user menu in database to arr
        DataSnapshot fooddata = dt.child("others_list"); //fooddata = others menu of the current in database
        for (DataSnapshot childdata : fooddata.getChildren()) { //loop on all the dishes in other menu of the user
            String name = childdata.child("dish_name").getValue(String.class); //name of the dish
            String desc = childdata.child("dish_discription").getValue(String.class); //discription of the dish
            double price = childdata.child("price").getValue(double.class); //price of the dish
            Dish_Add dish = new Dish_Add(price, name, desc);
            arr.add_other_dish(dish); //add dish to arr disertt menu

        }
    }

    private void add_drinklist_menu_toarr(DataSnapshot dt) { //same function as: same as add_otherlist_menu_toarr with drink dish
        DataSnapshot fooddata = dt.child("drink_list");
        for (DataSnapshot childdata : fooddata.getChildren()) {
            String name = childdata.child("dish_name").getValue(String.class);
            String desc = childdata.child("dish_discription").getValue(String.class);
            double price = childdata.child("price").getValue(double.class);
            Dish_Add dish = new Dish_Add(price, name, desc);
            arr.add_drink(dish);

        }
    }



    private void add_foodlist_menu_toarr(DataSnapshot dt) { // same function as: add_otherlist_menu_toarr with food dish
        DataSnapshot fooddata = dt.child("food_list");
        for (DataSnapshot childdata : fooddata.getChildren()) {
            String name = childdata.child("dish_name").getValue(String.class);
            String desc = childdata.child("dish_discription").getValue(String.class);
            double price = childdata.child("price").getValue(double.class);
            Dish_Add dish = new Dish_Add(price, name, desc);
            arr.add_food_dish(dish);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View v) { //make sure only one chekbox will be chek
        switch (v.getId()) {

            case R.id.Other:
                if (food.isChecked()) {
                    food.setChecked(false);
                }

                if (drink.isChecked()) {
                    drink.setChecked(false);
                }
                break;
            case R.id.Drink:
                if (food.isChecked()) {
                    food.setChecked(false);
                }
                if (other.isChecked()) {
                    other.setChecked(false);
                }

                break;
            case R.id.food:

                if (other.isChecked()) {
                    other.setChecked(false);
                }
                if (drink.isChecked()) {
                    drink.setChecked(false);
                }
                break;

        }
    }
 }
