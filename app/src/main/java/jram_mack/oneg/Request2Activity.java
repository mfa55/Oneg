package jram_mack.oneg;


import android.content.Intent;
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
import static jram_mack.oneg.MyRequestsActivity.listItems2;

/**
 *
 * This is the second part of the request procedure. The user is required to choose a hospital according to the city chosen in the previous activity (RequestActivity). and the number of units required for the blood donation.
 * @author  JRAM-MACK
 * @author  CMPS253
 * @since 2/11/2017
 *
 * @version 1.0
 *
 *
 *
 */
public class Request2Activity extends AppCompatActivity {

    private Request request;
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
    private String phoneOnRequest2;


    /**
     *
     * @param savedInstanceState : this parameter contains a String to String key-value data. This value is passed into the onCreate method every time the user reaches this activity.
     *
     * In this method, the user is provided with two spinners containing the list of hospitals and the number of units to be chosen and one button that redirects the user to the Home page after a successful submission of the Request.
     * When a request is submitted, the request's information are stored in the Firebase database under the corresponding city and blood type.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        getSupportActionBar().setTitle("Request Blood");
        setContentView(R.layout.activity_request2);

        Bundle bundle = getIntent().getExtras();
        phoneOnRequest2 = bundle.getString("XphoneNumber");

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
                    request = new Request(RequestActivity.name.getText().toString(), RequestActivity.bloodTypeSpinner.getSelectedItem().toString(),
                            hospitalSpinner.getSelectedItem().toString(), RequestActivity.citySpinner.getSelectedItem().toString(),
                            numberOfUnits.getValue(), phoneOnRequest2, mDatabase.push().getKey());


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

                    String myTopic = RegisterActivity.makerealCity(cityLocation) + blood;
                    String userTopic = RegisterActivity.user.getCity() + RegisterActivity.user.getBloodType();
/////


                    mDatabase.child(request.getObjectType() + "/"
                            + RequestActivity.citySpinner.getSelectedItem().toString() + "/"
                            + RequestActivity.bloodTypeSpinner.getSelectedItem().toString() + "/"
                            + request.getKey()
                    ).setValue(request);

                    mDatabase.child("ListOfAllRequests" + "/" + request.getKey()).setValue(request);

                    RecyclerItem re = new RecyclerItem(hospital,request.toString());
                    listOfMyRequests.add(request);



                    //
                    //FirebaseMessaging.getInstance().unsubscribeFromTopic(myTopic);
                    if(userTopic.equals(myTopic)){
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(myTopic);
                        sendNotification();
                        FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                    } else {
                        sendNotification();
                    }

                    // FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                    //


                    Intent i = new Intent(Request2Activity.this, HomeActivity.class);
                    Toast.makeText(Request2Activity.this, "REQUEST SUBMITTED", Toast.LENGTH_LONG).show();

                    startActivity(i);
                }


            }
        });

    }


    /**
     * This method defines the action of the android back button
     * When pressed, the android back button takes the user from the Request2 page to the Home page.
     */
    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }


    OkHttpClient client = new OkHttpClient();
    //we can unsbbicribe from topic first, send notification, then resuscribe to it.


    /**
     * This method is responsible of sending a notification to all the proper users when a request is made.
     * The users that will receive this notification are the users that live in the same city and have the same blood type as the ones mentioned in the request.
     *
     * The notifications are sent according to their topic.
     * A topic is a string containing the city where the recipient lives and the blood type of the recipient
     *
     */
    public void sendNotification(){
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
                    //notif.put("click_action", "MainActivity");
                    //
                    jsonObjects.put("notification",notif);
                    jsonObjects.put("to","/topics/" + RegisterActivity.makerealCity(cityLocation) + blood);

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
