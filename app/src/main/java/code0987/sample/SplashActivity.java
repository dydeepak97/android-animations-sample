package code0987.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set splash theme

        // This way (theme defined in styles.xml, with no layout of activity) makes loading faster as styles pre-applied
        // Then we wait while main view is created, finally exiting splash
        setTheme(R.style.AppTheme_Splash);

        super.onCreate(savedInstanceState);

        // Start main
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
