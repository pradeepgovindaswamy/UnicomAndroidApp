package com.makeathon.enable.simplechatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RealLogin extends AppCompatActivity {

        private static final String TAG = LoginActivity.class.getSimpleName();
        private DatabaseReference mFirebaseDatabase;
        private FirebaseDatabase mFirebaseInstance;
    public int[] colors = {R.color.colorAccent,R.color.colorPrimary,R.color.red,R.color.purple,R.color.black,R.color.grey,R.color.colorPrimaryDark};
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_real_login);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(" LOGIN");
            int randomNum = (int)(Math.random() * ((6) + 1));
            toolbar.setBackgroundColor(colors[randomNum]);
            setSupportActionBar(toolbar);


            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            if(pref.getString("name",null)!=null){
                Intent intent = new Intent(this, Chat_List.class);
                startActivity(intent);
            }

        }


    public void disability(View v){
        EditText phone  = (EditText) findViewById(R.id.phone);
        EditText password = (EditText) findViewById(R.id.password);

        if (phone.length() != 10){
            Toast.makeText(getApplicationContext(), "Number length should be 10", Toast.LENGTH_SHORT).show();
            return;
        }

        String phoneV = "+91"+phone.getText().toString();
        final String passV = password.getText().toString();




        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");


        mFirebaseDatabase.child(phoneV).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserFetch user = dataSnapshot.getValue(UserFetch.class);

                if(user==null){
                    Toast.makeText(getApplicationContext(), "No mobile no found",Toast.LENGTH_SHORT).show();
                    return;
                }


                System.out.println(user.getPassword());

                if (!passV.trim().equals(user.getPassword()) ){
                    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("name", user.getName());
                edit.putString("phone", user.getPhone());
                edit.putString("password",user.getPassword());
                edit.putString("bl",user.getBlind());
                edit.putString("de",user.getDeaf());
                edit.putString("du",user.getDumb());
                edit.commit();
                Intent i= new Intent(getApplicationContext(), Chat_List.class);
                getApplicationContext().startActivity(i);
                Toast.makeText(getApplicationContext(), "Hello " +user.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.signup){
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            getApplicationContext().startActivity(i);
        }
        return true;
    }
    public void onBackPressed() {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);

    }
}
