package com.makeathon.enable.simplechatapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chat_List extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public List<String> numbs;
    public List<String> names = new ArrayList<>();
    public List<String> foundNums = new ArrayList<>();
    public int[] colors = {R.color.colorAccent,R.color.colorPrimary,R.color.red,R.color.purple,R.color.black,R.color.grey,R.color.colorPrimaryDark};
    RecyclerView recyclerViewV;
    RecyclerView.Adapter adapterV;
    LinearLayoutManager linearLayoutManagerV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("  Contacts");
        int randomNum = (int)(Math.random() * ((6) + 1));
        toolbar.setBackgroundColor(colors[2]);
        setSupportActionBar(toolbar);

        recyclerViewV = (RecyclerView) findViewById(R.id.recycleV);
        recyclerViewV.setVisibility(View.GONE);

        //ImageView im = (ImageView) findViewById(r1.id.image1);
        //Glide.with(this).load(r1.drawable.loader).into(im);
        //im.setVisibility(View.VISIBLE);


        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numbs = new ArrayList<String>();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    numbs.add(child.getKey().substring(child.getKey().length() - 10));
                }
//                for (String data: numbs){
//                    Log.d("haha",data);
//                    System.out.println(data);
//                }
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
                while (phones.moveToNext())
                {
                    String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ","").replace("-","");
                    String last3;
                    if (phoneNumber == null || phoneNumber.length() <= 10) {
                        last3 = phoneNumber;
                    } else {
                        phoneNumber = phoneNumber.substring(phoneNumber.length() - 10);
                    }

                    //Toast.makeText(getApplicationContext(), phoneNumber.toString(), Toast.LENGTH_SHORT).show();

                    if(numbs.contains(phoneNumber.trim())){
                        //Log.d("found",name+"  "+ phoneNumber);
                        if (names!=null && foundNums!=null){
                            if (!(names.contains(name) && foundNums.contains(phoneNumber))){
                                names.add(name);
                                foundNums.add(phoneNumber);
                            }
                        }
                        else{
                            names.add(name);
                            foundNums.add(phoneNumber);
                        }

                    }

                }


//                names.add("Lakshay");
//                foundNums.add("9003855431");
//                names.add("Sahaja");
//                foundNums.add("9003861097");
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                String numbersRemove = pref.getString("phone",null);
                int index= foundNums.indexOf(numbersRemove.substring(numbersRemove.length()-10));
                if (index!=-1){
                    foundNums.remove(index);
                    names.remove(index);
                }


                //ImageView im = (ImageView) findViewById(r1.id.image1);
                //im.setVisibility(View.GONE);
                recyclerViewV.setVisibility(View.VISIBLE);
                adapterV = new CustomAdapterV(getApplicationContext(),names, foundNums);
                linearLayoutManagerV = new LinearLayoutManager(getApplicationContext());
                linearLayoutManagerV.setOrientation(LinearLayoutManager.VERTICAL);

                recyclerViewV.setLayoutManager(linearLayoutManagerV);
                recyclerViewV.setAdapter(adapterV);

                phones.close();
                for (String name: names){
                    System.out.println(name);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            //NavUtils.navigateUpFromSameTask(this);
            finish();
        }
        else if (item.getItemId() == R.id.logout){
            final AlertDialog.Builder builder = new AlertDialog.Builder(Chat_List.this, R.style.MyDialogTheme);
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
    public void onBackPressed() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        if(pref.getString("name",null)==null){
            super.onBackPressed();
        }
        else{
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.chat_list, menu);
        return  true;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        int randomNum = (int)(Math.random() * ((6) + 1));
        toolbar.setBackgroundColor(colors[5]);
    }
}
