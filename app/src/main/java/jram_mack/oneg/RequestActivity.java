package jram_mack.oneg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class RequestActivity extends AppCompatActivity {

    protected DatabaseReference mDatabase;
    private List<String> arrayBloodtype;
    private List<String> arrayCity;
    private EditText phoneNumber;
    private Button next;
    private BufferedReader bufferCity;
    private BufferedReader bufferHospital;
    private BufferedReader bufferBloodType;
    public static Spinner bloodTypeSpinner;
    private ArrayAdapter<String> adapter;
    private Spinner hospitalSpinner;
    private ArrayAdapter<String> adapter2;
    public static Spinner citySpinner;
    private ArrayAdapter<String> adapter1;
    public static EditText name;  // Added name


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        getSupportActionBar().setTitle("Request Blood");
        setContentView(R.layout.activity_request);

        //Spinner 1
        arrayBloodtype = new ArrayList<String>();
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
        bloodTypeSpinner = (Spinner) findViewById(R.id.bloodTypeSpinner);
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

        citySpinner =(Spinner)findViewById(R.id.citySpinner);
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayCity);
        citySpinner.setAdapter(adapter1);


        //Spinner 3

        phoneNumber = (EditText) findViewById(R.id.phoneNumberRequest);


        next = (Button) findViewById(R.id.requestButton3);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        name = (EditText)findViewById(R.id.NameEditText);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(citySpinner.getSelectedItem().toString().equals("Select City")
                        || bloodTypeSpinner.getSelectedItem().toString().equals("Select Blood Type")
                        || phoneNumber.getText().toString().isEmpty()){

                    Toast.makeText(RequestActivity.this, "FILL ALL THE REQUIRED INFORMATION", Toast.LENGTH_SHORT).show();

                } else {

                    Intent i = new Intent(RequestActivity.this, Request2Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("XphoneNumber", phoneNumber.getText().toString());
                    i.putExtras(bundle);

                    startActivity(i);

                }


            }
        });

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

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

}
