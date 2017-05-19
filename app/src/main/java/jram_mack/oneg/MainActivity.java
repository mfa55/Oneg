package jram_mack.oneg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 *
 * this page is where the user has the ability to log in or register to the app
 * @author  JRAM-MACK
 * @author  CMPS253
 * @since 2/11/2017
 *
 * @version 1.0
 *
 *
 *
 */
public class MainActivity extends AppCompatActivity {


    // private EditText password;
    private EditText phoneNumber;
    private TextView Register;
    private Button logIn;
    protected DatabaseReference mDatabase;
    public static Intent i;
    private View Rectangle;

    static SharedPreferences sharedpreferences;
    protected final String MyPREFERENCES = "MyPrefs";
    static SharedPreferences.Editor editor;

    /**
     *
     * @param savedInstanceState : this parameter contains a String to String key-value data. This value is passed into the onCreate method every time the user reaches this activity.
     *
     * the user is provided with a text box where he can enter his phone number
     * the user is provided with a two buttons: one button that grants the user access to his account
     *                           and one button that redirect the user to the regitration activity RegisterActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ListOfAllUsers");

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        editor = sharedpreferences.edit();

        if(sharedpreferences.getString("logged","").equals("true")){

            Intent redirect = new Intent(MainActivity.this, HomeActivity.class);



            RegisterActivity.user = new User(
                    sharedpreferences.getString("name", ""),
                    sharedpreferences.getString("city", ""),
                    sharedpreferences.getString("phoneNumber", ""),
                    sharedpreferences.getString("gender", ""),
                    sharedpreferences.getString("bloodType", "")
            );

            Toast.makeText(MainActivity.this, "Welcome Back " + sharedpreferences.getString("name", "").toString(), Toast.LENGTH_LONG).show();
            startActivity(redirect);


        }


        Rectangle = (View) findViewById(R.id.RectangleView);  //white rectangle
        Drawable rect = Rectangle.getBackground();          // same
        rect.setAlpha(220);                                 //rectangle opacity

        Register =(TextView) findViewById(R.id.TextRegister);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        // goes to register page
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        logIn =(Button) findViewById(R.id.buttonLogIn);

        phoneNumber = (EditText) findViewById(R.id.phoneNumberSignIn);

        // password = (EditText) findViewById(R.id.passwordSignIn);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("phoneNumber", phoneNumber.getText().toString());
                editor.commit();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(phoneNumber.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this, "ENTER A PHONE NUMBER", Toast.LENGTH_SHORT).show();
                        }
                        else if (!dataSnapshot.hasChild(phoneNumber.getText().toString())) {
                            Toast.makeText(MainActivity.this, "ACCOUNT DOESN'T EXIST", Toast.LENGTH_SHORT).show();
                        } else {
                            RegisterActivity.user = new User(
                                    dataSnapshot.child(sharedpreferences.getString("phoneNumber", "") + "/" + "name").getValue().toString(),
                                    dataSnapshot.child(sharedpreferences.getString("phoneNumber", "") + "/" + "city").getValue().toString(),
                                    dataSnapshot.child(sharedpreferences.getString("phoneNumber", "") + "/" + "phoneNumber").getValue().toString(),
                                    dataSnapshot.child(sharedpreferences.getString("phoneNumber", "") + "/" + "gender").getValue().toString(),
                                    dataSnapshot.child(sharedpreferences.getString("phoneNumber", "") + "/" + "bloodType").getValue().toString()
                                    // dataSnapshot.child(reverseString(phoneNumber.getText().toString())+ "/" + "password").getValue().toString()
                            );

                            editor.putString("name", RegisterActivity.user.getName());
                            editor.putString("city", RegisterActivity.user.getCity());
                            editor.putString("phoneNumber", RegisterActivity.user.getPhoneNumber());
                            editor.putString("gender", RegisterActivity.user.getGender());
                            editor.putString("bloodType", RegisterActivity.user.getBloodType());



                            editor.putString("logged", "true");

                            editor.commit();

                            Toast.makeText(MainActivity.this, "Welcome " + sharedpreferences.getString("name", "").toString(), Toast.LENGTH_LONG).show();

                            MainActivity.i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(MainActivity.i);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }

    /**
     *
     * @param ev movement that the user does on the screen
     * @return calls the super class
     */
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


}
