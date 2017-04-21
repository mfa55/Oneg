package jram_mack.oneg;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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


public class MainActivity extends AppCompatActivity {


   // private EditText password;
    private EditText phoneNumber;
    private TextView Register;
    private Button logIn;
    protected DatabaseReference mDatabase;
    public static Intent i;
    private View Rectangle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
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

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ListOfAllUsers");

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                                        dataSnapshot.child(phoneNumber.getText().toString()+ "/" + "name").getValue().toString(),
                                        dataSnapshot.child(phoneNumber.getText().toString()+ "/" + "city").getValue().toString(),
                                        dataSnapshot.child(phoneNumber.getText().toString()+ "/" + "phoneNumber").getValue().toString(),
                                        dataSnapshot.child(phoneNumber.getText().toString()+ "/" + "gender").getValue().toString(),
                                        dataSnapshot.child(phoneNumber.getText().toString()+ "/" + "bloodType").getValue().toString()
                                       // dataSnapshot.child(reverseString(phoneNumber.getText().toString())+ "/" + "password").getValue().toString()
                                );

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