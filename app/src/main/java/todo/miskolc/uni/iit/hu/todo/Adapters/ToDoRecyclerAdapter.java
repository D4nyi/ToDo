package todo.miskolc.uni.iit.hu.todo.Adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.val;
import todo.miskolc.uni.iit.hu.todo.Models.ToDoItem;
import todo.miskolc.uni.iit.hu.todo.R;

@EqualsAndHashCode(callSuper = true)
@ToString
public class ToDoRecyclerAdapter extends RecyclerView.Adapter<ToDoRecyclerAdapter.BindingHolder> {

    private List<ToDoItem> todos;

    public ToDoRecyclerAdapter(List<ToDoItem> todos) {
        this.todos = todos;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        val v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new BindingHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        //holder.getBinding().setVariable(BR.Book, todos.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }

    static class BindingHolder extends RecyclerView.ViewHolder {
        @Getter
        private ViewDataBinding binding;

        BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
