package edu.utep.cs.cs4330.mygrade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class GradeActivity extends AppCompatActivity {
    private TextView dateView;
    private TextView gradeView;
    private TextView totalView;
    private ListView listScoresView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        dateView = findViewById(R.id.dateView);
        gradeView = findViewById(R.id.gradeView);
        totalView = findViewById(R.id.totalView);
        listScoresView = findViewById(R.id.listScoresView);

        WebClient web = new WebClient(new WebClient.GradeListener() {

            /** Called when a requested grade is received. */
            public void onGrade(String date, Grade grade) {
                runOnUiThread(() -> {
                    dateView.setText(date);
                    gradeView.setText("Grade: " + grade.grade);
                    totalView.setText("W-Total: " + grade.total);

                    List<Grade.Score> scores = grade.scores();
                    listScoresView.setAdapter(new ScoreListAdapter(GradeActivity.this, scores));
                });
            }

            /** Called when an error is encountered. */
            public void onError(String msg) {
                runOnUiThread(() -> {
//                    gradeView.setText(Html.fromHtml("<b>An unexpected error occurred!</b>"));
                });
            }
        });

        Intent i = getIntent();
        new Thread(() -> web.query(i.getStringExtra("name"), i.getStringExtra("password"))).start();
    }

    private static class ScoreListAdapter extends ArrayAdapter<Grade.Score> {
        private final List<Grade.Score> scores;

        public ScoreListAdapter(Context ctx, List<Grade.Score> scores) {
            super(ctx, -1, scores);
            this.scores = scores;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView != null ? convertView
                    : LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grade_detail_row, parent, false);
            Grade.Score score = scores.get(position);
            TextView view = row.findViewById(R.id.nameView);
            view.setText(score.name);
            view = row.findViewById(R.id.maxView);
            view.setText(Integer.toString(score.max));
            view = row.findViewById(R.id.earnedView);
            view.setText(Integer.toString(score.earned));
            return row;
        }
    }
}
