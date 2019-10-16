package edu.utep.cs.cs4330.designpatterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PatternListFragment fragment = (PatternListFragment)
                getSupportFragmentManager().findFragmentById(R.id.listFragment);
        fragment.setListener(this::patternClicked);
    }

    private void patternClicked(String name) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        if (fragment != null && fragment.isAdded()) {
            ((PatternDetailFragment) fragment).setPattern(name);
        }
        else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("pattern", name);
            startActivity(intent);
        }
    }
}
