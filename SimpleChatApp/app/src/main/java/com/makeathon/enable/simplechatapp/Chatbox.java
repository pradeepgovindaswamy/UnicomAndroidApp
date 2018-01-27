package com.makeathon.enable.simplechatapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chatbox extends AppCompatActivity{
private Boolean isFabOpen = false;
boolean isValid = false;
String correctedText = null;
private FloatingActionButton fab,fab1,fab2,fab3;
private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    RecyclerView recyclerViewV;
    RecyclerView.Adapter adapterV;
    LinearLayoutManager linearLayoutManagerV;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public String number,mynumber;
    public List<String> shownumber = new ArrayList<>();
    public List<String> showtext = new ArrayList<>();
    public List<String> showdate = new ArrayList<>();
    public int[] colors = {R.color.colorAccent,R.color.colorPrimary,R.color.red,R.color.purple,R.color.black,R.color.grey,R.color.colorPrimaryDark};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent i = getIntent();
        number = "+91"+i.getStringExtra("number");
        String name = i.getStringExtra("name");
        String[] na = name.split(" ");
        toolbar.setLogo(R.drawable.maleuser);
        toolbar.setTitle(na[0].toUpperCase());
        int randomNum = (int)(Math.random() * ((6) + 1));
        toolbar.setBackgroundColor(colors[randomNum]);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //fab = (FloatingActionButton)findViewById(r1.id.fab);
        //fab1 = (FloatingActionButton)findViewById(r1.id.fab1);
        //fab2 = (FloatingActionButton)findViewById(r1.id.fab2);
        //fab3 = (FloatingActionButton)findViewById(r1.id.fab3);
       // fab_open = AnimationUtils.loadAnimation(getApplicationContext(), r1.anim.fab_open);
        //fab_close = AnimationUtils.loadAnimation(getApplicationContext(),r1.anim.fab_close);
        //rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),r1.anim.rotate_forward);
        //rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),r1.anim.rotate_backward);
        //fab.setOnClickListener(this);
       // fab1.setOnClickListener(this);
        //fab2.setOnClickListener(this);
        //fab3.setOnClickListener(this);

        Button s = (Button) findViewById(R.id.next);
        Button c = (Button) findViewById(R.id.canvas);


        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Chatbox.this,SingleHand.class);
                startActivity(myIntent);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent1 = new Intent(Chatbox.this,CanvasActivity.class);
                startActivity(myIntent1);
            }
        });

        recyclerViewV = (RecyclerView) findViewById(R.id.recycleV);



        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        mynumber = pref.getString("phone",null);


        mFirebaseDatabase.child(mynumber).child(number).addValueEventListener(new ValueEventListener() {
            int i = 0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(i==0){
                    for (DataSnapshot usersnap: dataSnapshot.getChildren()){
                        Message msg = usersnap.getValue(Message.class);
                        showdate.add(msg.getDate());
                        shownumber.add(msg.getPhone());
                        showtext.add(msg.getMsg());
                        System.out.println(msg.getPhone()+" "+msg.getMsg()+" "+msg.getDate());
                    }
                    adapterV = new CustomAdapterBox(getApplicationContext(),showtext, shownumber, showdate, mynumber);
                    linearLayoutManagerV = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManagerV.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerViewV.setLayoutManager(linearLayoutManagerV);
                    recyclerViewV.setAdapter(adapterV);
//                showdate.clear();
//                showtext.clear();
//                shownumber.clear();
                    i+=1;
                }
                else{
                    String datehaha="", namehaha="", numhaha="";
                    for (DataSnapshot usersnap: dataSnapshot.getChildren()){
                        Message msg = usersnap.getValue(Message.class);
                        datehaha = msg.getDate();
                        numhaha = msg.getPhone();
                        namehaha = msg.getMsg();
                    }
                        showtext.add(namehaha);
                        shownumber.add(numhaha);
                        showdate.add(datehaha);
                        adapterV.notifyItemInserted(showtext.size()-1);
                        recyclerViewV.smoothScrollToPosition(adapterV.getItemCount()-1);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    public void sendhaha(View v){
        EditText ed = (EditText) findViewById(R.id.ed);
        String text = ed.getText().toString();
        final String originalText = text;
        ed.setText("");

        try
        {
            StringBuilder text2 = new StringBuilder(text);
            new Connection().execute(text2).get();

            Context context = this;

            // custom dialog
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.popup_custom);
            dialog.setTitle("Title...");

            // set the custom dialog components - text, image and button
            TextView text1 = (TextView) dialog.findViewById(R.id.text1);
            text1.setText(correctedText);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonYes);

            dialog.show();

            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    changeMessage(correctedText);
                }
            });

            Button dialogButton1 = (Button) dialog.findViewById(R.id.dialogButtonNo);
            // if button is clicked, close the custom dialog
            dialogButton1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    changeMessage(originalText);
                }
            });

        }
        catch(Exception ex)
        {
            System.out.println("Exception caught in Catch block");
        }
    }

    private void changeMessage(String text) {

            if (text.trim().length() == 0) {
                Toast.makeText(getApplicationContext(), "Type a msg", Toast.LENGTH_SHORT).show();

            } else {
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                String mynumber = pref.getString("phone", null);

                mFirebaseInstance = FirebaseDatabase.getInstance();
                mFirebaseDatabase = mFirebaseInstance.getReference("users");
                Date date = new Date();

                Message msg = new Message(mynumber, text, date.toString());
                mFirebaseDatabase.child(mynumber).child(number).push().setValue(msg, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.e(TAG, "Failed to write message", databaseError.toException());
                        }
                    }
                });

                mFirebaseDatabase.child(number).child(mynumber).push().setValue(msg, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.e(TAG, "Failed to write message", databaseError.toException());
                        }
                    }
                });
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_box,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.camera){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1001);
        }
        else if(item.getItemId() == android.R.id.home)
        {
            //NavUtils.navigateUpFromSameTask(this);
            finish();
        }
        else if (item.getItemId() == R.id.logout){
            final AlertDialog.Builder builder = new AlertDialog.Builder(Chatbox.this, R.style.MyDialogTheme);
            builder.setTitle("You want to logout?")
                    .setCancelable(true)
                    .setIcon(R.drawable.logo)
                    .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            builder.setCancelable(true);
                        }
                    })
                    .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor edit = pref.edit();
                            edit.clear();
                            edit.commit();
                            Toast.makeText(getApplicationContext(), "LOGGED OUT SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(getApplicationContext(),RealLogin.class);
                            getApplicationContext().startActivity(in);
                        }
                    })
                    .show();
        }


        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001 && resultCode == Activity.RESULT_OK){
            Bitmap  bitmap=(Bitmap) data.getExtras().get("data");
            Log.d("bitmap",bitmap.toString());

        String text = "Enter API url and send as bitmap";
            EditText ed = (EditText) findViewById(R.id.ed);

            ed.setText(ed.getText().toString()+" "+text);
        }
    }


    private class Connection extends AsyncTask<StringBuilder, StringBuilder, StringBuilder> {

        @Override
        protected StringBuilder doInBackground(StringBuilder... arg0) {
            StringBuilder urlText = arg0[0];
            connect(urlText);
            return urlText;
        }
    }

    private void connect(StringBuilder str) {
        try {
            HttpClient Client = new DefaultHttpClient();
            String URL = "http://139.59.5.153/convert/";

            String encodedURL = URLEncoder.encode(str.toString(), "UTF-8");
            String temp = str.toString().replaceAll(" ", "%20");
            encodedURL = URL + temp;

            HttpGet httpget = new HttpGet(encodedURL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            correctedText = Client.execute(httpget, responseHandler);
        } catch (ClientProtocolException e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
        } catch (IOException e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
        } catch (Exception e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
        }
    }

}


