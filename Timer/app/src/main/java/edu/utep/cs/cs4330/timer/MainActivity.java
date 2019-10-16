package edu.utep.cs.cs4330.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private TextView timeDisplay;
    private Button startButton;
    private Button stopButton;
    private TimerModel timerModel;
    private String tag = "Lifecycle Step";
    private Long restoredStartTime;
    private String restoredTimeDisplayText;
    private boolean startEnabled, stopEnabled, instanceSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(tag, "In the onCreate() event");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeDisplay = findViewById(R.id.timeDisplay);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        instanceSaved = false;
        startEnabled = true;
        stopEnabled = true;
        restoredStartTime = new Long(0);
        timerModel = new TimerModel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, "In the onStart() event");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(tag, "In the onRestart() event");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag, "In the onResume() event");

        if (instanceSaved && restoredStartTime != 0) {
            timerModel = new TimerModel(restoredStartTime);
            startTimer();
        }
        else if (instanceSaved) {
            timeDisplay.setText(restoredTimeDisplayText);
        }

        startButton.setEnabled(startEnabled);
        stopButton.setEnabled(stopEnabled);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "In the onPause() event");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "In the onStop() event");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, "In the onDestroy() event");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong("startTime", timerModel.getStartTime());
        outState.putBoolean("startEnabled", startButton.isEnabled());
        outState.putBoolean("stopEnabled", stopButton.isEnabled());
        outState.putBoolean("instanceSaved", true);
        outState.putString("timeDisplayText", String.valueOf(timeDisplay.getText()));

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        restoredStartTime = savedInstanceState.getLong("startTime");
        restoredTimeDisplayText = savedInstanceState.getString("timeDisplayText");
        startEnabled = savedInstanceState.getBoolean("startEnabled");
        stopEnabled = savedInstanceState.getBoolean("stopEnabled");
        instanceSaved = savedInstanceState.getBoolean("instanceSaved");

        super.onRestoreInstanceState(savedInstanceState);
    }

    public void startClicked(View view) {
        timerModel.start();
        startTimer();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void stopClicked(View view) {
        timerModel.stop();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private void startTimer() {
        new Thread(() -> {
            while (timerModel.isRunning()) {
                this.runOnUiThread(this::displayTime);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
            }
        }).start();
    }

    private void displayTime() {
        long sec = timerModel.elapsedTime() / 1000;
        long min = sec / 60; sec %= 60;
        long hour = min / 60; min %= 60;
        timeDisplay.setText(String.format("%d:%02d:%02d", hour, min, sec));
    }
}
