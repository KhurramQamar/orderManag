package com.example.ordermanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class menu_adapter extends BaseAdapter implements ListAdapter {
   
    
        private ArrayList<String> list = new ArrayList<String>(); //the valus we will display
        private Context context;
        private final DatabaseReference menus = FirebaseDatabase.getInstance().getReference("Menus");
        private String type_addapter; //the type of the menu
        private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        private Menu_Form data_menu; //the menu from the data base
        private TextView item;


        public menu_adapter(ArrayList<String> list, Context context, String type, Menu_Form data_menu) {
            this.list = list;
            this.context = context;
            this.type_addapter = type;
            this.data_menu = data_menu;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.viewadapter, null);
            }
            TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
            listItemText.setText(list.get(position));

            Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
            item = view.findViewById(R.id.list_item_string);
            deleteBtn.setOnClickListener(new View.OnClickListener() { //delete function
                @Override
                public void onClick(View view) {
                    final String name = list.get(position).split(",")[0]; //name of the chosen dish
                    list.remove(position); //remove from the list of display
                    notifyDataSetChanged();
                    menus.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && dataSnapshot.child(user.getUid()).exists()){
                                DataSnapshot user_menu = dataSnapshot.child(user.getUid()); //get to the user menu
                                data_menu.remove_dish(name,type_addapter);
                                if (type_addapter == "Food"){ //if the dish is from starters then set the updated starters
                                    menus.child(user.getUid()).child("food_list").setValue(data_menu.getfood_list());
                                }
                                else if (type_addapter == "Drink"){ //same
                                    menus.child(user.getUid()).child("drink_list").setValue(data_menu.getDrink_list());
                                }
                                else if (type_addapter == "Others"){ //same
                                    menus.child(user.getUid()).child("others_list").setValue(data_menu.getothers_list());
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });

            return view;  }
    }

