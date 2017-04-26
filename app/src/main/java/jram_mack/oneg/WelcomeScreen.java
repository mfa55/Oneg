package jram_mack.oneg;
/**
 * @author  JRAM-MACK
 * @author  CMPS253
 * @since 2/11/2017
 *
 * @version 1.0
 *
 * This is a welcome screen that appears to the user when the registration procedure is done
 * This page guides the user with the instructions on how to use the application
 * Only one button exists, a continue button that redirect the user to the home page.
 *
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class WelcomeScreen extends AppCompatActivity {
    private Button button2;

    /**
     *
     * @param savedInstanceState : this parameter contains a String to String key-value data. This value is passed into the onCreate method every time the user reaches this activity.
     *
     * only one button is defined which will redirect the user to the home page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome_screen);
        button2 = (Button) findViewById(R.id.button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomeScreen.this,HomeActivity.class);
                startActivity(i);
            }
        });
    }
}
