package todo.miskolc.uni.iit.hu.todo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import todo.miskolc.uni.iit.hu.todo.R;

public class MainActivity extends BaseActivity {

    public ListView listView;
    public ArrayAdapter<String> adapter;
    String[] titles;

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

        listView = findViewById(R.id.list_view);

        generateListItems();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, AddToDoActivity.class);
            intent.putExtra(AddToDoActivity.ITEM, position);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        generateListItems();
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        Log.e("RESUME", "RESUMED");
    }

    private void generateListItems() {
        titles = new String[todos.getTodos().size()];
        for (int i = 0; i < todos.getTodos().size(); i++) {
            titles[i] = todos.getTodos().get(i).getTitle();
        }


        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_listview_layout, titles);
        listView.setAdapter(adapter);
    }
}
