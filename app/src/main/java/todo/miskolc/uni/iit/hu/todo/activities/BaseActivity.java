package todo.miskolc.uni.iit.hu.todo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import todo.miskolc.uni.iit.hu.todo.models.ToDoList;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BasicActivity";
    protected ToDoList todos = new ToDoList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String json = readFromFile();
        todos.fromJSON(json);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveToDos();
    }

    protected String readFromFile() {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = getApplicationContext().openFileInput("config.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }

        return stringBuilder.toString();
    }

    protected void saveToDos() {
        try {
            String json = todos.toJSON();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput("config.txt", MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
