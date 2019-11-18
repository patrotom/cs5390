package edu.utep.cs.cs4330.contactfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText;
    private Button findButton;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        findButton = findViewById(R.id.findButton);
        resultView = findViewById(R.id.resultView);
    }

    public void findClicked(View view) {
        String name = nameEditText.getText().toString();


    }
}
