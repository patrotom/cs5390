package edu.utep.cs.cs4330.mygrade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText nameInput;
    private EditText passwordInput;
    private CheckBox rememberCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton = findViewById(R.id.submitButton);
        nameInput = findViewById(R.id.nameInput);
        passwordInput = findViewById(R.id.passwordInput);
        rememberCheckBox = findViewById(R.id.rememberCheckBox);
    }

    public void submitClicked(View view) {
        Intent i = new Intent(this, GradeActivity.class);

        if (rememberCheckBox.isChecked()) {

        }

        i.putExtra("name", String.valueOf(nameInput.getText()));
        i.putExtra("password", String.valueOf(passwordInput.getText()));

        startActivity(i);
    }
}
