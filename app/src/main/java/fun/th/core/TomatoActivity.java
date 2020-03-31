package fun.th.core;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.th.core.R;
import java.util.Timer;
import java.util.TimerTask;
import timber.log.Timber;
import org.matomo.sdk.Matomo;
import org.matomo.sdk.Tracker;
import org.matomo.sdk.TrackerBuilder;
import org.matomo.sdk.extra.TrackHelper;

public class TomatoActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private Timer regularTimer;
    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());
        Timber.i("Entering into onCreate...");
        tracker = TrackerBuilder.createDefault("https://th.matomo.cloud/matomo.php", 1).build((Matomo.getInstance(this)));
        TrackHelper.track().event("intro", "start").name("thstart").value(1000f).with(tracker);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        LinearLayout mainLayout = findViewById (R.id.mainLayout);
        final TextView mTextField = findViewById(R.id.headview);
        mTextField.setText("seconds remaining: ");
        Bundle params = new Bundle();
        params.putString("image_name", "matomo");
        params.putString("full_text", "creating");
        mFirebaseAnalytics.logEvent("start", params);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterDown(mTextField);
                Timber.i("Tomato click");
            }
        });

        regularTimer = new Timer();
        regularTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 5000);

    }


    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        Timber.i("Running internal timer");
        this.runOnUiThread(Timer_Tick);

    }


    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.
            //Do something to the UI thread here
            Timber.i("Running internal timer UI");
            Toast.makeText(getApplicationContext(), "timer", Toast.LENGTH_SHORT).show();

        }
    };

    private void counterDown(final TextView mTextField) {
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTextField.setText("WAIT UNTIL ... " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                /*ImageView imageView = findViewById(R.id.imageView1);
                Glide.with(MainActivity.this).load(R.drawable.thhappy).into(imageView);*/
                mTextField.setText("done!");
                mTextField.setText("seconds remaining: ");
                Bundle params = new Bundle();
                params.putString("image_name", "tomato");
                params.putString("full_text", "finish");
                mFirebaseAnalytics.logEvent("finish", params);
                TrackHelper.track().event("last", "finish").name("thfinish").value(1000f).with(tracker);
                Timber.i("Entering into onFinish...");
            }
        }.start();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Timber.i("Entering onPostCreate >>>");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.i("Entering onStart >>>");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.i("Entering onStop >>>");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Timber.i("Entering onRestart >>>");

    }
}
