package jram_mack.oneg;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static jram_mack.oneg.MyRequestsActivity.listOfMyRequests;


public class Request2Activity extends AppCompatActivity {

    private RequestFunction request;
    private NumberPicker numberOfUnits;
    private Button confirm;
    private List<String> arrayHospital;
    private Spinner hospitalSpinner;
    private ArrayAdapter<String> adapter2;
    private BufferedReader bufferHospital;
    protected DatabaseReference mDatabase;

    public String blood;
    public String cityLocation;
    public String hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        getSupportActionBar().setTitle("RequestFunction Blood");
        setContentView(R.layout.activity_request2);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(listOfMyRequests == null){
            listOfMyRequests = new ArrayList<>();
        }
        confirm = (Button) findViewById(R.id.Submit);

        numberOfUnits = (NumberPicker) findViewById(R.id.numberPicker);
        numberOfUnits.setMinValue(1);
        numberOfUnits.setMaxValue(6);
        numberOfUnits.setValue(1);

        final String city = RequestActivity.citySpinner.getSelectedItem().toString();

        arrayHospital = new ArrayList<>();
        try {
            bufferHospital = new BufferedReader(new InputStreamReader(getAssets().open(city + ".txt")));

            String line3;
            while((line3 = bufferHospital.readLine()) != null){
                StringTokenizer token = new StringTokenizer(line3, "\n");

                arrayHospital.add(token.nextToken());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferHospital.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        hospitalSpinner =(Spinner)findViewById(R.id.hospitalSpinner);
        adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayHospital);
        hospitalSpinner.setAdapter(adapter2);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hospitalSpinner.getSelectedItem().toString().equals("Select Hospital")){

                    Toast.makeText(Request2Activity.this, "FILL ALL THE REQUIRED INFORMATION", Toast.LENGTH_SHORT).show();

                } else {
                    request = new RequestFunction(RequestActivity.name.getText().toString(), RequestActivity.bloodTypeSpinner.getSelectedItem().toString(),
                            hospitalSpinner.getSelectedItem().toString(), RequestActivity.citySpinner.getSelectedItem().toString(),
                            numberOfUnits.getValue(), RequestActivity.phoneNumber.getText().toString(), mDatabase.push().getKey());

                    request.setKey(mDatabase.push().getKey());

                    ////////MAKE this better later
                    blood = "";
                    if(RequestActivity.bloodTypeSpinner.getSelectedItem().toString().contains("AB+")) {
                        blood = RequestActivity.bloodTypeSpinner.getSelectedItem().toString().substring(0, 2) + "Plus";
                    }else if(RequestActivity.bloodTypeSpinner.getSelectedItem().toString().contains("AB-")) {
                        blood = RequestActivity.bloodTypeSpinner.getSelectedItem().toString().substring(0, 2) + "Minus";
                    }else if(RequestActivity.bloodTypeSpinner.getSelectedItem().toString().contains("+")) {
                        blood = RequestActivity.bloodTypeSpinner.getSelectedItem().toString().substring(0, 1) + "Plus";
                    }else {
                        blood = RequestActivity.bloodTypeSpinner.getSelectedItem().toString().substring(0, 1) + "Minus";
                    }


                    cityLocation = RequestActivity.citySpinner.getSelectedItem().toString();
                    hospital = hospitalSpinner.getSelectedItem().toString();

                    String myTopic = cityLocation + blood;
/////


                    mDatabase.child(request.getObjectType() + "/"
                            + RequestActivity.citySpinner.getSelectedItem().toString() + "/"
                            + RequestActivity.bloodTypeSpinner.getSelectedItem().toString() + "/"
                            + request.getKey()
                    ).setValue(request);

                    listOfMyRequests.add(request);


                    //
                    //FirebaseMessaging.getInstance().unsubscribeFromTopic(myTopic);
                    sendNotification();
                   // FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                    //


                    Intent i = new Intent(Request2Activity.this, HomeActivity.class);
                    Toast.makeText(Request2Activity.this, "REQUEST SUBMITTED", Toast.LENGTH_LONG).show();
                    startActivity(i);
                }


            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }


    OkHttpClient client = new OkHttpClient();
    //we can unsbbicribe from topic first, send notification, then resuscribe to it.
    public void sendNotification(){
        // Toast.makeText(this,"TOPIC: " + city + blood, Toast.LENGTH_LONG).show();
        Thread threadN = new Thread(new Runnable() {
            @Override
            public void run() {
                //
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                //
                // Log.d("RequestActivity", "********************* TOPIC: " + city + blood + " *****************************");
                String urlString = "https://fcm.googleapis.com/fcm/send";
                JSONObject jsonObjects = new JSONObject();
                JSONObject notif = new JSONObject();
                try {
                    notif.put("title", "Donate Blood Please");
                    notif.put("body", "Blood type " + blood + " needed at " + hospital + " in city " + cityLocation);
                    notif.put("sound","default");
                    //
                    notif.put("click_action", "MainActivity");
                    //
                    jsonObjects.put("notification",notif);
                    jsonObjects.put("to","/topics/" + cityLocation + blood);

                    //JUST TO TEST
                    //jsonObjects.put("to","/topics/BeirutSMinus");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody body = RequestBody.create(JSON, jsonObjects.toString());
                com.squareup.okhttp.Request req = new com.squareup.okhttp.Request.Builder()
                        .url(urlString)
                        .post(body)
                        .addHeader("Authorization","Key=AAAA7EO3Pxw:APA91bGCg3Y36vdalSD9E98ig0NQAn2eKxxp-KLuoPdvTY76ALo6KL90aF-CJy4EODJFO_N93nujo1JEj9dAporyzfwMWCp8BOeAlSpWG2crU7hrlLcEukv4laIqfjiejbMvs4JpjrqE")
                        .build();
                try {
                    Response res = client.newCall(req).execute();
                    if (!res.isSuccessful()) {
                        throw new UnknownError("Error: " + res.code() + " " + res.body().string());
                    }

                } catch (IOException e) {
                    sendNotification();
                }
            }
        });
        threadN.start();
    }


}
