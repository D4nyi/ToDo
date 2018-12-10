package todo.miskolc.uni.iit.hu.todo.models;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Contract;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private static List<ToDoItem> todos;

    public ToDoList() {
        todos = new ArrayList<>();
    }

    @Contract(pure = true)
    public List<ToDoItem> getTodos() {
        return todos;
    }

    public String toJSON() {
        Type listType = new TypeToken<List<ToDoItem>>() {
        }.getType();
        return new GsonBuilder().create().toJson(todos, listType);
    }

    public List<ToDoItem> fromJSON(String json) {
        Type collectionType = new TypeToken<List<ToDoItem>>() {
        }.getType();
        List<ToDoItem> temp = new GsonBuilder().create().fromJson(json, collectionType);
        if (temp != null) {
            todos = temp;
        }
        return todos;
    }
}
