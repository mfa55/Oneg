package jram_mack.oneg;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register2Activity extends AppCompatActivity {
    public EditText mytoken;
    public Button verify;
    public String tokenCode;


    public String realToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Register");
        setContentView(R.layout.activity_register2);

        Bundle bundle = getIntent().getExtras();
        realToken = bundle.getString("token");
        Log.d("SecondActivity", "************THE LONG TOKEN: " + realToken);
        Log.d("SecondActivity", "************THE SHORT TOKEN: " + realToken.substring(realToken.length()-4, realToken.length()));
        mytoken = (EditText) findViewById(R.id.etToken);
        //Toast.makeText(Register2Activity.this, realToken.substring(realToken.length()-4, realToken.length()), Toast.LENGTH_SHORT).show();
        verify = (Button) findViewById(R.id.btnVerify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokenCode = mytoken.getText().toString();
                signIn(tokenCode, realToken);
                //Intent i = new Intent(Register2Activity.this, HomeActivity.class);
                //startActivity(i);

            }
        });
    }

    public void signIn(String tokenCode, String realToken) {

        if(realToken.endsWith(tokenCode)) {
            FirebaseAuth auth = FirebaseAuth.getInstance();



            auth.signInWithCustomToken(realToken).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("SecondActivity", "signInWithCustomToken:onComplete:" + task.isSuccessful());

                    if(!task.isSuccessful()) {
                        Log.w("SecondActivity", "signInWithCustomToken: ", task.getException());

                    }
                }
            });
            Intent i = new Intent(Register2Activity.this, WelcomeScreen.class);
            startActivity(i);
        }else {
            Toast.makeText(Register2Activity.this, "FAILED", Toast.LENGTH_SHORT).show();
        }

    }



}
