package edu.utep.cs.cs4330.unitconversion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView unitFromView;
    private EditText inputView;
    private TextView unitToView;
    private TextView resultView;
    private int currentUnitOption;
    private UnitConverter unitConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        unitFromView = findViewById(R.id.unitFromView);
        inputView = findViewById(R.id.inputView);
        unitToView = findViewById(R.id.unitToView);
        resultView = findViewById(R.id.resultView);

        unitConverter = new UnitConverter();
        unitFromView.setText(unitConverter.fromUnit());
        unitToView.setText(unitConverter.toUnit());

        inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                double x = 0.0;
                try {
                    x = Double.parseDouble(s.toString());
                } catch (NumberFormatException e) {}


            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        currentUnitOption = item.getItemId();

        switch (currentUnitOption) {
            case R.id.action_ft_to_m:

                unitFromView.setText("Feet");
                unitToView.setText("Meters");
                break;
            case R.id.action_in_to_cm:
                unitFromView.setText("Inches");
                unitToView.setText("Centimeters");
                break;
            case R.id.action_lb_to_g:
                unitFromView.setText("Pounds");
                unitToView.setText("Grams");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
