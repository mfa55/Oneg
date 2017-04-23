package jram_mack.oneg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class RegisterActivity extends AppCompatActivity {

    protected Button register;
    protected DatabaseReference mDatabase;
    private EditText EditTextName;
    private List<String> arrayBloodtype;
    private List<String> arrayCity;
    private List<String> arrayGender;
    private EditText phoneNumber;
    //private EditText ePass;
    public static User user;
    private static Spinner CitySpinner;
    private ArrayAdapter<String> adapter3;
    private BufferedReader bufferCity;
    private BufferedReader bufferBloodType;
    private BufferedReader bufferGender;
    private Spinner GenderSpinner;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter;
    private Spinner bloodTypeSpinner;

    public String customToken;
    public String number;

    public String blood;
    public String myTopic;

    static SharedPreferences sharedpreferences;
    protected final String MyPREFERENCES = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        getSupportActionBar().setTitle("Register");
        setContentView(R.layout.activity_register);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();

        EditTextName = (EditText) findViewById(R.id.TextName);

        phoneNumber = (EditText) findViewById(R.id.phoneNumberSignUp);

        //Spinner 1
        arrayBloodtype = new ArrayList<>();
        try {
            bufferBloodType = new BufferedReader(new InputStreamReader(getAssets().open("BloodTypes.txt")));

            String line1;
            while((line1 = bufferBloodType.readLine()) != null){
                StringTokenizer token = new StringTokenizer(line1, "\n");
                arrayBloodtype.add(token.nextToken());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferBloodType.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        bloodTypeSpinner = (Spinner) findViewById(R.id.bloodtype);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayBloodtype);
        bloodTypeSpinner.setAdapter(adapter);

        //Spinner 2
        arrayCity = new ArrayList<>();
        try {
            bufferCity = new BufferedReader(new InputStreamReader(getAssets().open("Cities.txt")));

            String line1;
            while((line1 = bufferCity.readLine()) != null){
                StringTokenizer token = new StringTokenizer(line1, "\n");
                arrayCity.add(token.nextToken());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferCity.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        CitySpinner =(Spinner)findViewById(R.id.spinnerCity);
        adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCity);
        CitySpinner.setAdapter(adapter3);




        //Spinner 3
        arrayGender = new ArrayList<String>();
        try {
            bufferGender = new BufferedReader(new InputStreamReader(getAssets().open("Genders.txt")));

            String line1;
            while((line1 = bufferGender.readLine()) != null){
                StringTokenizer token = new StringTokenizer(line1, "\n");
                arrayGender.add(token.nextToken());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferGender.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        GenderSpinner = (Spinner) findViewById(R.id.spinnerGender);
        adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayGender);
        GenderSpinner.setAdapter(adapter2);


        //end of spinners
        mDatabase = FirebaseDatabase.getInstance().getReference();

        register =(Button) findViewById(R.id.Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //NEW
                blood = "";
                if(bloodTypeSpinner.getSelectedItem().toString().contains("AB+")) {
                    blood = bloodTypeSpinner.getSelectedItem().toString().substring(0, 2) + "Plus";
                }else if(bloodTypeSpinner.getSelectedItem().toString().contains("AB-")) {
                    blood = bloodTypeSpinner.getSelectedItem().toString().substring(0, 2) + "Minus";
                }else if(bloodTypeSpinner.getSelectedItem().toString().contains("+")) {
                    blood = bloodTypeSpinner.getSelectedItem().toString().substring(0, 1) + "Plus";
                }else {
                    blood = bloodTypeSpinner.getSelectedItem().toString().substring(0, 1) + "Minus";
                }


                myTopic = CitySpinner.getSelectedItem().toString() + blood;
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                /*FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                FirebaseMessaging.getInstance().subscribeToTopic(myTopic);
                */
                ////


                number = phoneNumber.getText().toString();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(CitySpinner.getSelectedItem().toString() + "/"
                                + bloodTypeSpinner.getSelectedItem().toString() + "/"
                                + phoneNumber.getText().toString())) {
                            Toast.makeText(RegisterActivity.this, "ACCOUNT ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                        } else {
                            if(dataSnapshot.hasChild("ListOfAllUsers" + "/" + phoneNumber.getText().toString())){
                                Toast.makeText(RegisterActivity.this, "ACCOUNT ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                            } else {
                                RegisterActivity.user = new User(EditTextName.getText().toString(),
                                        CitySpinner.getSelectedItem().toString(), phoneNumber.getText().toString(),
                                        GenderSpinner.getSelectedItem().toString(), bloodTypeSpinner.getSelectedItem().toString()/*, ePass.getText().toString()*/);


                                mDatabase.child(user.getObjectType() + "/" + user.getCity() +
                                        "/" + user.getBloodType() +
                                        "/" + user.getKey()
                                ).setValue(user);

                                mDatabase.child("ListOfAllUsers" + "/" + user.getKey()).setValue(user);
                                editor.putString("name", RegisterActivity.user.getName());
                                editor.putString("city", RegisterActivity.user.getCity());
                                editor.putString("phoneNumber", RegisterActivity.user.getPhoneNumber());
                                editor.putString("gender",RegisterActivity.user.getGender());
                                editor.putString("bloodType", RegisterActivity.user.getBloodType());
                                editor.putString("logged", "true");
                                editor.commit();
                                signUP();


                                // Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                                // startActivity(i);
                            }


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


            }
        });

    }
    public static String getCitySpinner(){
        return CitySpinner.getSelectedItem().toString();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void signUP() {
        //Log.d("RegisterActivity", "****CALLED SIGNUP******");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://onegapp.herokuapp.com/token/" + number;
                Log.d("RegisterActivity", "******** URL : " + url + " *******");
                try {
                    customToken = callOnNetwork(url);
                    Log.d("RegisterActivity", "******** FULL TOKEN: " + customToken + " *******");//the Full token we get from heroku
                    String realPhoneNumber = makeReal(phoneNumber.getText().toString());
                    String twilioURL = "https://onegapp.herokuapp.com/twiliosms/" + realPhoneNumber + "/" + "Code:" + customToken.substring(customToken.length()-4, customToken.length());
                    //String twilioURL = "https://onegapp.herokuapp.com/twiliosms/+9613504030/" + "Code:" + customToken.substring(customToken.length() - 4, customToken.length());
                    callOnNetwork(twilioURL);

                    Intent i = new Intent(RegisterActivity.this, Register2Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("token", customToken);
                    i.putExtras(bundle);
                    startActivity(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    OkHttpClient client = new OkHttpClient();



    String callOnNetwork(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String makeReal(String n) {
        if(n.startsWith("0")) {
            return "+961" + n.substring(1);
        }else {
            return "+961" + n;
        }
    }

}
