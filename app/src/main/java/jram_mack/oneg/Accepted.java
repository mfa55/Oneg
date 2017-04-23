package jram_mack.oneg;

import android.content.Intent;
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




    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent mario = new Intent(Accepted.this, MainActivity.class);
        switch(item.getItemId()){
            case R.id.mnu_item_signout:
                Intent j = new Intent(Accepted.this,MainActivity.class);
                RegisterActivity.user = null;
                MainActivity.sharedpreferences.edit().clear().commit();

                startActivity(j);
                break;
            case R.id.mnu_item_Edit:
                String phone = RegisterActivity.user.getPhoneNumber();
                mDatabase = FirebaseDatabase.getInstance().getReference("Users" + "/"
                        + RegisterActivity.user.getCity() + "/" + RegisterActivity.user.getBloodType()

                );
                mDatabase.child(phone).removeValue();
                //RegisterActivity.user = null;
                MainActivity.sharedpreferences.edit().putString("logged", "").commit();
                mDatabase = FirebaseDatabase.getInstance().getReference("ListOfAllUsers");
                mDatabase.child(phone).removeValue();

                startActivity(mario);


                break;

        }
        return true;

    }

}
