package jram_mack.oneg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class Accepted extends AppCompatActivity {
    private RecyclerView recyclerView2;
    private RecyclerView.Adapter adapter2;
    public static List<RecyclerItem> acceptedRequestsListItem;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<Request> listOfRequestsMyRequests;
    private Button AcceptedHome;
    private Button AcceptedRequest;
    private Button AcceptedMyRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Accepted Requests");
        setContentView(R.layout.activity_accepted);
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

        adapter2 = new RecyclerViewAdapter(acceptedRequestsListItem,this);
        recyclerView2.setAdapter(adapter2);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

}
