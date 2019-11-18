package edu.utep.cs.cs4330.bitcoinprice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    TextView currentPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentPriceTextView = findViewById(R.id.currentPriceTextView);
    }

    public void refreshClicked(View view) {
        try {
            URL url = new URL("https://api.gdax.com/products/BTC-USD/ticker/");
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            Log.d("APP", "==========================================Hello");
            httpConn.connect();
            Log.d("CODE", String.valueOf(httpConn.getResponseCode()));
            if (HttpURLConnection.HTTP_OK == httpConn.getResponseCode()) {
                Log.d("APP", "++++++++++++++++++++++++++++++++++++++++++Hello");
                InputStream in = httpConn.getInputStream();
                JSONObject obj = new JSONObject(String.valueOf(in));
                String price = obj.getString("price");
                currentPriceTextView.setText(String.format("Price: $" + "%.2f", Float.valueOf(price)));
            }
        } catch (Exception e) {
            if (e.getMessage() != null)
                Log.e("ERR", e.getMessage());
        }

    }
}
