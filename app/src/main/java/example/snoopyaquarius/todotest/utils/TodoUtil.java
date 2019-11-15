package example.snoopyaquarius.todotest.utils;

import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.snoopyaquarius.todotest.R;
import example.snoopyaquarius.todotest.model.Todo;
import example.snoopyaquarius.todotest.adapter.TodoAdapter;

/**
 * Created by snoopyaquarius on 2019/11/15.
 */

public class TodoUtil {

    private static Map<Integer, View> cache = new HashMap<>();

    private static TodoAdapter todoAdapter;

    public static void setTodoAdapter(TodoAdapter todoAdapter) {
        TodoUtil.todoAdapter = todoAdapter;
    }

    public static int getCompletedTodo(List<Todo> todoList) {
        int num = 0;
        for (Todo todo : todoList) {
            if (todo.isComplete()) {
                num++;
            }
        }
        return num;
    }

    public static View put(Integer id, View view) {
        return cache.put(id, view);
    }

    public static View get(Integer id) {
        return cache.get(id);
    }

    public static void changeCompletedText(int completed, int total) {
        TextView todoCompleted = (TextView) TodoUtil.get(R.id.todo_completed);
        todoCompleted.setText("已完成" + completed + " / 全部" + total);
    }

    public static void notifyDataSetChanged(int completed, int total) {
        todoAdapter.notifyDataSetChanged();
        changeCompletedText(completed, total);
    }
}
