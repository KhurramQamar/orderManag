package com.example.ordermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RestrauntSignUp extends AppCompatActivity implements View.OnClickListener {

    private String UID;
    private EditText RestrauntName;
    private EditText Phone;
    private EditText city;
    private EditText description;
    private CheckBox fast,chinese,continental,home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restraunt_sign_up);
        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        RestrauntName=findViewById(R.id.rest_name);
        Phone=findViewById(R.id.rest_phone);
        city=findViewById(R.id.rest_city);
        description=findViewById(R.id.description_text);
        fast=findViewById(R.id.FastFood);
        chinese=findViewById(R.id.Chinese);
        continental=findViewById(R.id.Continental);
        home=findViewById(R.id.HomeChef);
        findViewById(R.id.finish_btn).setOnClickListener(this);//implements

        fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chinese.isChecked()) {
                    chinese.setChecked(false);
                }
                if (continental.isChecked()) {
                    continental.setChecked(false);
                }
                if (home.isChecked()) {
                    home.setChecked(false);
                }
            }
        });
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fast.isChecked()) {
                    fast.setChecked(false);
                }
                if (continental.isChecked()) {
                    continental.setChecked(false);
                }
                if (home.isChecked()) {
                    home.setChecked(false);
                }
            }
        });
        continental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chinese.isChecked()) {
                    chinese.setChecked(false);
                }
                if (fast.isChecked()) {
                    fast.setChecked(false);
                }
                if (home.isChecked()) {
                    home.setChecked(false);
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chinese.isChecked()) {
                    chinese.setChecked(false);
                }
                if (continental.isChecked()) {
                    continental.setChecked(false);
                }
                if (fast.isChecked()) {
                    fast.setChecked(false);
                }
            }
        });

    }
    private void register_restaurant() {
        final String rest_name = RestrauntName.getText().toString().trim();
        final String location = city.getText().toString().trim();
        final String phone = Phone.getText().toString().trim();
        final String descrip = description.getText().toString().trim();
        String type;
        


        if (rest_name.isEmpty()) {
            RestrauntName.setError("Restaurant Name is required");
            RestrauntName.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            Phone.setError("Phone is empty");
            Phone.requestFocus();
            return;
        }
        if (location.isEmpty()) {
            city.setError("city is empty");
            city.requestFocus();
            return;
        }
        if (descrip.isEmpty()) {
            description.setError("description is empty");
            description.requestFocus();
            return;
        }

        if (fast.isChecked()) {
            type = "fast";
        } else if (chinese.isChecked()) {
            type = "chinese";
        } else if (continental.isChecked()){
            type = "continental";
        }
        else
        {
            type="home";
        }


       Restraunt_form rest = new Restraunt_form(rest_name, location, phone,type,descrip,UID);
        FirebaseDatabase.getInstance().getReference("Restaurant").child(UID).setValue(rest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(RestrauntSignUp.this, RestrauntHome.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(RestrauntSignUp.this, "Sign up failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_btn:
                register_restaurant();
                break;
        }
    }
}