package cohen.dafna.newsassignment.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

import cohen.dafna.newsassignment.MainActivity;
import cohen.dafna.newsassignment.R;
import cohen.dafna.newsassignment.ui.auth.AuthActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        TextView tv = findViewById(R.id.welcome);
        tv.setX(1000);
        tv.animate()
                .setDuration(2000)
                .x(0)
                .start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {  // async -> return to main thread
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(SplashScreen.this, AuthActivity.class);
                        startActivity(i);
                    }
                });

            }
        }, 2000);

    }
}
