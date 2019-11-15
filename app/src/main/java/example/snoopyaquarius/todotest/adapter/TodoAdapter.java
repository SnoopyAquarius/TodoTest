package example.snoopyaquarius.todotest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import example.snoopyaquarius.todotest.R;
import example.snoopyaquarius.todotest.model.Todo;
import example.snoopyaquarius.todotest.utils.TodoUtil;

/**
 * Created by snoopyaquarius on 2019/11/15.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private static final String TAG = "TodoAdapter";

    private List<Todo> todoList;

    public TodoAdapter(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Todo todo = todoList.get(position);
        holder.todoContent.setText(todo.getContent());
        holder.todoComplete.setChecked(todo.isComplete());

        holder.todoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 后

                CheckBox todoAllComplete = (CheckBox) TodoUtil.get(R.id.todo_all_complete);

                int num = TodoUtil.getCompletedTodo(todoList);

                todoAllComplete.setChecked(num == todoList.size());

                TodoUtil.changeCompletedText(num, todoList.size());
            }
        });

        holder.todoComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // 先
                todo.setComplete(holder.todoComplete.isChecked());
                int num = TodoUtil.getCompletedTodo(todoList);
                checkedChanged(num);
            }
        });
    }

    public void checkedChanged(int num) {
        Button todoCLear = ((Button) TodoUtil.get(R.id.todo_clear));
        todoCLear.setEnabled(num > 0);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View todoView;
        TextView todoContent;
        CheckBox todoComplete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.todoView = itemView;
            this.todoComplete = (CheckBox) itemView.findViewById(R.id.todo_complete);
            this.todoContent = (TextView) itemView.findViewById(R.id.todo_content);
        }
    }


}
