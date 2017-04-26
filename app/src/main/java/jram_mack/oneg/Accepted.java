package jram_mack.oneg;

/**
 * @author  JRAM-MACK
 * @author  CMPS253
 * @since 2/11/2017
 *
 * @version 1.0
 *
 * this class allows the user to see the the request he/she accepted with the ability of calling the recipient pof the corresponding request
 *
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Accepted extends AppCompatActivity {
    private RecyclerView recyclerView2;
    private RecyclerView.Adapter adapter2;
    public static List<RecyclerItem> acceptedRequestsListItem;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<Request> listOfAcceptedRequests;
    private Button AcceptedHome;
    private Button AcceptedRequest;
    private Button AcceptedMyRequests;
    private  DatabaseReference mDatabase;

    static SharedPreferences sharedpreferences;
    protected final String MyPREFERENCES = "MyPrefs";

    /**
     *
     * @param savedInstanceState : this parameter contains a String to String key-value data. This value is passed into the onCreate method every time the user reaches this activity.
     *
     * this onCreate method provides the user with the cards so the user can see the request he/she has accepted with ability of calling the corresponding recipient
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Accepted Requests");
        setContentView(R.layout.activity_accepted);
        listOfAcceptedRequests = new ArrayList<>();
        acceptedRequestsListItem = new ArrayList<>();

        AcceptedHome = (Button)findViewById(R.id.home3);        //Buttons Start
        AcceptedMyRequests = (Button) findViewById(R.id.myReq3);
        AcceptedRequest = (Button) findViewById(R.id.requestButton3);
        AcceptedHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Accepted.this,HomeActivity.class);
                startActivity(i);
            }
        });
        AcceptedRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Accepted.this,RequestActivity.class);
                startActivity(i);
            }
        });
        AcceptedMyRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Accepted.this, MyRequestsActivity.class);
                startActivity(i);
            }
        });     //Buttons End
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView3);
        //recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference("Requests" + "/"
                + RegisterActivity.user.getCity() + "/" + RegisterActivity.user.getBloodType()
        );


        mDatabase.addChildEventListener(new ChildEventListener() {


            String value;
            Request r;
            RecyclerItem re;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                    if(dataSnapshot.hasChild(RegisterActivity.user.getPhoneNumber())){

                        if(dataSnapshot.child(RegisterActivity.user.getPhoneNumber()).getValue().equals("Accepted")){

                            r = new Request(dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("bloodType").getValue().toString(),
                                    dataSnapshot.child("hospital").getValue().toString(),
                                    dataSnapshot.child("city").getValue().toString(),
                                    Integer.parseInt(dataSnapshot.child("units").getValue().toString()),
                                    dataSnapshot.child("phoneNumberOnListView").getValue().toString(),
                                    dataSnapshot.child("key").getValue().toString()
                            );
                            value = dataSnapshot.child("hospital").getValue().toString();
                            re = new RecyclerItem(value, r.toString()); //hone u add the values
                            acceptedRequestsListItem.add(re);
                            listOfAcceptedRequests.add(r);
                        }
                    }

                adapter2.notifyDataSetChanged();



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("phoneNumber").getValue().equals(RegisterActivity.user.getPhoneNumber())) {
                    value = dataSnapshot.getValue().toString();
                    re = new RecyclerItem(value, "");
                    r = new Request(dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("bloodType").getValue().toString(),
                            dataSnapshot.child("hospital").getValue().toString(),
                            dataSnapshot.child("city").getValue().toString(),
                            Integer.parseInt(dataSnapshot.child("units").getValue().toString()),
                            dataSnapshot.child("phoneNumberOnListView").getValue().toString(),
                            dataSnapshot.child("key").getValue().toString()
                    );


                    //listOfHospitals.remove(value);
                    //listOfHospitals.add(re);
                    //listOfRequestsHome.remove(index);
                    //listOfHospitals.add(index, re);


                    //if(listOfRequestsHome.get(index).getStatus() == false){
                    //listOfHospitals.remove(index);
                    //listOfRequestsHome.remove(index);


                    adapter2.notifyDataSetChanged();
                }



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

       /* mDatabase.addChildEventListener(new ChildEventListener() {

            String value;
            Request r;
            RecyclerItem re;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(Accepted.this, "mario acc", Toast.LENGTH_SHORT).show();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Toast.makeText(Accepted.this, "mario acc0", Toast.LENGTH_SHORT).show();

                    if(ds.hasChild(RegisterActivity.user.getPhoneNumber())){
                        Toast.makeText(Accepted.this, "mario acc1", Toast.LENGTH_SHORT).show();

                        if(ds.child(RegisterActivity.user.getPhoneNumber()).getValue().equals("Accepted")){
                            Toast.makeText(Accepted.this, "mario acc2", Toast.LENGTH_SHORT).show();

                            r = new Request(dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("bloodType").getValue().toString(),
                                    dataSnapshot.child("hospital").getValue().toString(),
                                    dataSnapshot.child("city").getValue().toString(),
                                    Integer.parseInt(dataSnapshot.child("units").getValue().toString()),
                                    dataSnapshot.child("phoneNumberOnListView").getValue().toString(),
                                    dataSnapshot.child("key").getValue().toString()
                            );
                            value = dataSnapshot.child("hospital").getValue().toString();
                            re = new RecyclerItem(value, r.toString()); //hone u add the values
                            acceptedRequestsListItem.add(re);
                            listOfAcceptedRequests.add(r);
                        }
                    }
                }
                adapter2.notifyDataSetChanged();



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


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


*/




        adapter2 = new RecyclerViewAdapter(acceptedRequestsListItem,this);
        recyclerView2.setAdapter(adapter2);



    }



    /**
     * This method defines the action of the android back button
     * When pressed, the android back button takes the user from the Request page to the Home page.
     */
    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    /**
     *
     * @param menu menu in the action bar containing the sign out and delete account buttons
     * @return true(hardcoded on purpose)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    /**
     *
     * @param item item selected in the toolbar menu
     * @return true (hardcoded on purpose)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        final Intent mario = new Intent(Accepted.this, MainActivity.class);
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
                        MainActivity.sharedpreferences.edit().clear();

                        MainActivity.sharedpreferences.edit().commit();

                        MainActivity.editor.clear();

                        MainActivity.editor.commit();
                        Intent mario = new Intent(Accepted.this, MainActivity.class);

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
