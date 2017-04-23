package jram_mack.oneg;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static jram_mack.oneg.Accepted.acceptedRequestsListItem;
import static jram_mack.oneg.Accepted.listOfAcceptedRequests;



public class HomeActivity extends AppCompatActivity {

    public static DatabaseReference mDatabase;
    public static RecyclerView recyclerView;
    public static ArrayList<RecyclerItem> listOfHospitals;
    public static ArrayList<Request> listOfRequestsHome;
    private Button request;
    private Button myReq;
    public static RecyclerView.Adapter adapter;
    public static List<RecyclerItem> listItems;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayAdapter<String> arrayAdapter;
    private Button AcceptedHome;
    static SharedPreferences sharedpreferences;
    protected final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();





        String title = "Requests "+ RegisterActivity.user.getBloodType();
        getSupportActionBar().setTitle(title);
        setContentView(R.layout.activity_home);
        AcceptedHome = (Button) findViewById(R.id.AcceptedHome);
        if(listOfAcceptedRequests == null){
            listOfAcceptedRequests = new ArrayList<>();
            if(acceptedRequestsListItem == null){
                acceptedRequestsListItem = new ArrayList<>();
            }
        }

        AcceptedHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,Accepted.class);
                startActivity(i);
            }
        });



        mDatabase = FirebaseDatabase.getInstance().getReference("Requests" + "/"
                + RegisterActivity.user.getCity() + "/" + RegisterActivity.user.getBloodType()
        );

        listOfHospitals = new ArrayList<>();
        listOfRequestsHome = new ArrayList<>();
        //starts
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();

        RecyclerItem r = new RecyclerItem("Item",listOfHospitals.toString());
        listItems.add(r);

        adapter = new MyAdapter(listOfHospitals,this);
        recyclerView.setAdapter(adapter);
        //ends




        myReq = (Button) findViewById(R.id.myReq3);
        myReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, MyRequestsActivity.class);
                startActivity(i);
            }
        });




        request = (Button) findViewById(R.id.requestButton3);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, RequestActivity.class);
                startActivity(i);
            }
        });


        mDatabase.addChildEventListener(new ChildEventListener() {

            String value;
            Request r;
            RecyclerItem re;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.hasChild("status")) {
                    if (dataSnapshot.child("status").getValue().toString().equals("true")) {

                        if (!dataSnapshot.child("phoneNumber").getValue().toString().equals(RegisterActivity.user.getPhoneNumber())) {
                            r = new Request(dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("bloodType").getValue().toString(),
                                    dataSnapshot.child("hospital").getValue().toString(),
                                    dataSnapshot.child("city").getValue().toString(),
                                    Integer.parseInt(dataSnapshot.child("units").getValue().toString()),
                                    dataSnapshot.child("phoneNumberOnListView").getValue().toString(),
                                    dataSnapshot.child("key").getValue().toString()
                            );

                            if (dataSnapshot.child("status").getValue().toString().equals("true")) {
                                if (dataSnapshot.hasChild(RegisterActivity.user.getPhoneNumber())) {
                                    if (!dataSnapshot.child(RegisterActivity.user.getPhoneNumber()).getValue().equals("Accepted")) {
                                        value = dataSnapshot.child("hospital").getValue().toString();
                                        re = new RecyclerItem(value, r.toString()); //hone u add the values
                                        listOfHospitals.add(re);
                                        listOfRequestsHome.add(r);
                                    } else {
                                        listOfAcceptedRequests.add(r);

                                    }
                                } else {
                                    value = dataSnapshot.child("hospital").getValue().toString();
                                    re = new RecyclerItem(value, r.toString()); //hone u add the values
                                    listOfHospitals.add(re);
                                    listOfRequestsHome.add(r);
                                }


                            }
                            adapter.notifyDataSetChanged();

                        }
                    }

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    value = dataSnapshot.getValue().toString();
                    re = new RecyclerItem(value,"");
                    r = new Request(dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("bloodType").getValue().toString(),
                            dataSnapshot.child("hospital").getValue().toString(),
                            dataSnapshot.child("city").getValue().toString(),
                            Integer.parseInt(dataSnapshot.child("units").getValue().toString()),
                            dataSnapshot.child("phoneNumberOnListView").getValue().toString(),
                            dataSnapshot.child("key").getValue().toString()
                    );




                    adapter.notifyDataSetChanged();



                // }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }


    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    @Override
    public void onBackPressed(){
        Intent mainActivity = new Intent(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        final Intent mario = new Intent(HomeActivity.this, MainActivity.class);
        switch(item.getItemId()){
            case R.id.mnu_item_signout:

                //RegisterActivity.user = null;
                editor.putString("logged", "");
                editor.commit();

                startActivity(mario);

                break;
            case R.id.mnu_item_Edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete Account");
                builder.setMessage("Are you sure? This action cannot be reverted");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String phone = RegisterActivity.user.getPhoneNumber();
                        mDatabase = FirebaseDatabase.getInstance().getReference("Users" + "/"
                                + RegisterActivity.user.getCity() + "/" + RegisterActivity.user.getBloodType()

                        );
                        mDatabase.child(phone).removeValue();
                        RegisterActivity.user = null;
                        mDatabase = FirebaseDatabase.getInstance().getReference("ListOfAllUsers");
                        mDatabase.child(phone).removeValue();

                        startActivity(mario);
                        dialogInterface.cancel();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert =builder.create();
                alert.show();


                break;


        }
        return true;

    }


}