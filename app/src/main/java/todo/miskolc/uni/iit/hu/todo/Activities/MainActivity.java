package todo.miskolc.uni.iit.hu.todo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import todo.miskolc.uni.iit.hu.todo.Listeners.RecyclerItemClickListener;
import todo.miskolc.uni.iit.hu.todo.R;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;


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

        recyclerView = findViewById(R.id.todos_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), (view, position) -> {
            Intent intent = new Intent(MainActivity.this, AddToDoActivity.class);
            intent.putExtra(AddToDoActivity.TODO_POSITION, position);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Meg kéne nyitni a naptárat!", Toast.LENGTH_LONG).show();
        }));
    }
}
