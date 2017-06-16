package pl.rudnicki.dailynews;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rudnicki.dailynews.ui.articles.ArticlesFragment;
import pl.rudnicki.dailynews.ui.sources.SourcesFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeListener shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        setSupportActionBar(toolbar);
        initShakeDetection();
        loadFragment(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    public void showInfoDialog(Context context, String name, String category, String description) {
        new AlertDialog.Builder(context)
                .setTitle(name)
                .setMessage("Category: " + category + "\n\nDescription:\n" + description)
                .show();
    }

    private void initShakeDetection() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeListener();
        shakeDetector.setOnShakeListener(this::handleShakeEvent);
    }

    private void handleShakeEvent(int count) {
        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(1000);
        Toast.makeText(this, "Stop shaking me, I am not responsible for these news!", Toast.LENGTH_SHORT).show();
    }

    public void loadFragment(Bundle savedInstanceState) {
        if(null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.framelayout_main_container, new SourcesFragment())
                    .commit();
        }
    }

    public void loadArticlesFragment(ArticlesFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.framelayout_main_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
