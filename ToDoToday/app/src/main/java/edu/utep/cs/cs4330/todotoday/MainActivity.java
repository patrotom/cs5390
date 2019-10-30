package edu.utep.cs.cs4330.todotoday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ToDoDatabaseHelper dbHelper;
    private List<ToDoItem> todoItems;
    private ToDoAdapter todoAdapter;
    private EditText todoEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new ToDoDatabaseHelper(this);
        todoItems = dbHelper.allItems();
        todoAdapter = new ToDoAdapter(this, R.layout.todo_item, todoItems);
        todoAdapter.setItemClikListener(item -> dbHelper.update(item));
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(todoAdapter);

        todoEdit = findViewById(R.id.todoEdit);
    }

    public void addClicked(View view) {
        String description = todoEdit.getText().toString();
        ToDoItem item = new ToDoItem(description);
        todoAdapter.add(item);
        todoAdapter.notifyDataSetChanged();
    }

    public void removeClicked(View view) {

    }
}
