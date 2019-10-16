package edu.utep.cs.cs4330.tuitioncalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText creditET;
    private TextView tuitionET;
    private TextView feesET;
    private TextView totalET;
    private Integer creditHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        creditET = findViewById(R.id.creditET);
        tuitionET = findViewById(R.id.tuitionET);
        feesET = findViewById(R.id.feesET);
        totalET = findViewById(R.id.totalET);

        creditET.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    creditHours = Integer.parseInt(s.toString());
                } catch(Exception e){
                    Log.e("",e.getMessage());
                    creditHours = 0;
                }

                displayTuition(creditHours);
            }
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void displayTuition(Integer creditHours) {
        Double[] tuitionArr = {1062.25, 1923.10, 2783.95, 3644.80, 4505.65, 5366.50, 6227.35, 7088.20,
                7949.05, 8809.90, 9670.75, 10531.60, 11374.95, 12218.30, 13061.65, 13880.00,
                14698.35, 15516.70, 16335.05, 17153.40, 17971.75};

        Double t, f;

        if (creditHours < 1 || creditHours > tuitionArr.length) {
            tuitionET.setText("Tuition: -");
            t = 0.0;
        }
        else {
            t = tuitionArr[creditHours - 1];
            tuitionET.setText("Tuition: " + String.valueOf(tuitionArr[creditHours - 1]));
        }

        if (creditHours >= 15 || (150.0 + 11.5 * creditHours + 25.0 * creditHours > 375.00)) {
            feesET.setText("Fees: " + String.valueOf(375.0));
            f = 375.0;
        }
        else {
            f = 150.0 + 11.5 * creditHours + 25.0 * creditHours;
            feesET.setText("Fees: " + String.valueOf(150.0 + 11.5 * creditHours + 25.0 * creditHours));
        }

        totalET.setText("Total: " + String.valueOf(f + t));

    }
}
