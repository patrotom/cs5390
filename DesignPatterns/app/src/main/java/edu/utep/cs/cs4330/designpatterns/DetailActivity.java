package edu.utep.cs.cs4330.designpatterns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra("pattern");
        PatternDetailFragment fragment = (PatternDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        fragment.setPattern(name);
    }
}
