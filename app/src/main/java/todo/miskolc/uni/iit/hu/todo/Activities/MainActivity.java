package todo.miskolc.uni.iit.hu.todo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import todo.miskolc.uni.iit.hu.todo.Models.ToDoList;
import todo.miskolc.uni.iit.hu.todo.R;

public class MainActivity extends AppCompatActivity {

    private ToDoList todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddToDoActivity.class);
            startActivity(intent);
        });

        File file = new File(getApplicationContext().getFilesDir(), "mytodos.json");
        todos = new ToDoList();

        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            br.close();
        } catch (IOException e) {
            Log.e("File Read", e.getMessage(), e);
        }

        todos.fromJSON(sb.toString());
    }
}
