package jram_mack.oneg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
 *
 * this class allows the user to change his profile information
 * @author  JRAM-MACK
 * @author  CMPS253
 * @since 2/11/2017
 *
 * @version 1.0
 *
 *
 */
public class EditProfileActivity extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState : this parameter contains a String to String key-value data. This value is passed into the onCreate method every time the user reaches this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }
}
