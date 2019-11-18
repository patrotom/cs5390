package edu.utep.cs.cs4330.mymusicplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private EditText urlEdit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlEdit = findViewById(R.id.urlEdit);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    /** Called when the play button is clicked. */
    public void playClicked(View view) {
        if (player == null || !player.isPlaying()) {
            player = MediaPlayer.create(this, Uri.parse(urlEdit.getText().toString()));
            if (player != null) {
                player.start();
                toast("Playing.");
                //--
                //-- TODO: WRITE YOUR CODE HERE
                //--
                //showProgressAsyncTask(player);
                //showProgressThread(player);
                //showProgressHandler(player);
                //--

                new Thread(() -> {

                }).start();

            } else {
                toast("Failed!");
            }
        }
    }

    /** Called when the stop button is clicked. */
    public void stopClicked(View view) {
        if (player != null && player.isPlaying()) {
            player.stop();
            //player.release(); // or reset?
            toast("Stopped.");
        }
    }

    /** Shows a toast message. */
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //--
    //-- WRITE YOUR CODE HERE
    //--

}
