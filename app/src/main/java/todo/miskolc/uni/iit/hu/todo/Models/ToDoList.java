package todo.miskolc.uni.iit.hu.todo.Models;

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
    public static List<ToDoItem> getTodos() {
        return todos;
    }

    public String toJSON() {
        return new GsonBuilder().create().toJson(this, ToDoItem.class);
    }

    public List<ToDoItem> fromJSON(String json) {
        Type collectionType = new TypeToken<List<ToDoItem>>() {
        }.getType();
        todos = new GsonBuilder().create().fromJson(json, collectionType);
        return todos;
    }
}
