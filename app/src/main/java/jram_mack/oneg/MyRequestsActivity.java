package jram_mack.oneg;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ListView;

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;

        import java.util.ArrayList;
        import java.util.List;

        import static jram_mack.oneg.HomeActivity.mDatabase;

public class MyRequestsActivity extends AppCompatActivity {
    private ListView myList;

    private Button myHome;
    private Button myReqReq;
    // ArrayList<String> data = new ArrayList<String>();
    private RecyclerView recyclerView2;
    private RecyclerView.Adapter adapter2;
    private List<RecyclerItem> listItems2;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<RequestFunction> listOfMyRequests;
    private Button AcceptedMyRequests;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        getSupportActionBar().setTitle("My Requests");
        setContentView(R.layout.activity_my_requests);
//        myList = (ListView) findViewById(R.id.myLV);
//        myList.setClickable(true);
        if(listOfMyRequests == null){
            listOfMyRequests = new ArrayList<>();
        }
        AcceptedMyRequests = (Button) findViewById(R.id.AcceptedMyRequests);
        AcceptedMyRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyRequestsActivity.this,Accepted.class);
                startActivity(i);
            }
        });

        myHome =(Button)  findViewById(R.id.myHome);
        myReqReq =(Button) findViewById(R.id.MyrequestButton);
        myReqReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyRequestsActivity.this, RequestActivity.class);
                startActivity(i);
            }
        });
        myHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        listItems2 = new ArrayList<>();
        /*for(int i =0;i<10;i++){
            RecyclerItem r = new RecyclerItem("Item"+(i+1),"this is item #"+(i+1));
            listItems2.add(r);
        }*/
        adapter2 = new SampleAdpater(listItems2,this);
        recyclerView2.setAdapter(adapter2);

//            data.add("Black");
//            data.add("white");
//            data.add("yellow");
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,  android.R.layout.simple_list_item_1,data);
//        myList.setAdapter(arrayAdapter);
//        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder altdial = new AlertDialog.Builder(MyRequestsActivity.this);
//                altdial.setMessage("Info" /* Fill put the code to get info from database here!!*/).setCancelable(false)
//                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                data.remove(position);
//                                arrayAdapter.notifyDataSetChanged();
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        });
//
//                AlertDialog alert =altdial.create();
//                alert.setTitle("RequestFunction Information");
//                alert.show();
//            }
//        });         //Dialog Box Ends


        mDatabase.addChildEventListener(new ChildEventListener() {

            String value;
            RequestFunction r;
            RecyclerItem re;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.child("phoneNumber").getValue().toString().equals(RegisterActivity.user.getPhoneNumber())){
                    r = new RequestFunction(dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("bloodType").getValue().toString(),
                            dataSnapshot.child("hospital").getValue().toString(),
                            dataSnapshot.child("city").getValue().toString(),
                            Integer.parseInt(dataSnapshot.child("units").getValue().toString()),
                            dataSnapshot.child("phoneNumber").getValue().toString(),
                            dataSnapshot.child("key").getValue().toString()
                    );

                    if(dataSnapshot.child("status").getValue().toString().equals("true")){
                        value = dataSnapshot.child("hospital").getValue().toString();
                        re = new RecyclerItem(value,r.toString()); //hone u add the values
                        listItems2.add(re);
                        listOfMyRequests.add(r);
                    }

                }
                adapter2.notifyDataSetChanged();



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                value = dataSnapshot.getValue().toString();
                re = new RecyclerItem(value,"");
                r = new RequestFunction(dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("bloodType").getValue().toString(),
                        dataSnapshot.child("hospital").getValue().toString(),
                        dataSnapshot.child("city").getValue().toString(),
                        Integer.parseInt(dataSnapshot.child("units").getValue().toString()),
                        dataSnapshot.child("phoneNumber").getValue().toString(),
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


    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }


}