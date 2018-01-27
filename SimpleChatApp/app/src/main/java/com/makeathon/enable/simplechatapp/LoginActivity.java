package com.makeathon.enable.simplechatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public int[] colors = {R.color.colorAccent,R.color.colorPrimary,R.color.red,R.color.purple,R.color.black,R.color.grey,R.color.colorPrimaryDark};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" SIGNUP");
        int randomNum = (int)(Math.random() * ((6) + 1));
        toolbar.setBackgroundColor(colors[randomNum]);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(pref.getString("name",null)!=null){
            Intent intent = new Intent(this, Chat_List.class);
            startActivity(intent);
        }


    }


    public void disability(View v){
        EditText name = (EditText) findViewById(R.id.name);
        EditText phone  = (EditText) findViewById(R.id.phone);
        EditText password = (EditText) findViewById(R.id.password);

        String nameV = name.getText().toString();
        String phoneV = "+91"+phone.getText().toString();
        String passV = password.getText().toString();

        if (phoneV.length() != 13){
            Toast.makeText(getApplicationContext(), "Number length should be 10", Toast.LENGTH_SHORT).show();
            return;
        }
        CheckBox blind = (CheckBox) findViewById(R.id.blind);
        CheckBox deaf = (CheckBox) findViewById(R.id.deaf);
        CheckBox dumb = (CheckBox) findViewById(R.id.dumb);
        boolean cBlind = blind.isChecked();
        boolean cDeaf = deaf.isChecked();
        boolean cDumb = dumb.isChecked();
        String bl="No",de="No",du="No";
        if (cBlind){
            bl = "Yes";
        }
        if(cDeaf){
            de="Yes";
        }
        if(cDumb){
            du = "Yes";
        }



        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("name", nameV);
        edit.putString("phone", phoneV);
        edit.putString("password",passV);
        edit.putString("bl",bl);
        edit.putString("de",de);
        edit.putString("du",du);
        edit.commit();


        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        User user = new User(nameV,phoneV,passV,bl,de,du);
        mFirebaseDatabase.child(phoneV).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null){
                    Log.e(TAG, "Failed to write message", databaseError.toException());
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), Chat_List.class);
                    startActivity(intent);
                }
            }
        });



    }


    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Log.d("pref",pref.getString("name",null));
        if(pref.getString("name",null)!=null){
            Intent intent = new Intent(this, Chat_List.class);
            startActivity(intent);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(r1.menu.menu_login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == r1.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
