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

            

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });

            return view;  }
    }

