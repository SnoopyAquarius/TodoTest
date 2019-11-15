package example.snoopyaquarius.todotest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.snoopyaquarius.todotest.R;
import example.snoopyaquarius.todotest.adapter.TodoAdapter;
import example.snoopyaquarius.todotest.model.Todo;
import example.snoopyaquarius.todotest.utils.TodoUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Todo> todoList = initTodoList();

        final RecyclerView todoView = (RecyclerView) findViewById(R.id.todo_view);
        final Button todoAdd = (Button) findViewById(R.id.todo_add);
        final EditText todoInput = (EditText) findViewById(R.id.todo_input);
        final TextView todoCompleted = (TextView) findViewById(R.id.todo_completed);
        final Button todoClear = (Button) findViewById(R.id.todo_clear);
        final CheckBox todoAllComplete = (CheckBox) findViewById(R.id.todo_all_complete);

        TodoUtil.put(R.id.todo_view, todoView);
        TodoUtil.put(R.id.todo_add, todoAdd);
        TodoUtil.put(R.id.todo_input, todoInput);
        TodoUtil.put(R.id.todo_completed, todoCompleted);
        TodoUtil.put(R.id.todo_clear, todoClear);
        TodoUtil.put(R.id.todo_all_complete, todoAllComplete);

        final TodoAdapter todoAdapter = new TodoAdapter(todoList);
        TodoUtil.setTodoAdapter(todoAdapter);

        int num = TodoUtil.getCompletedTodo(todoAdapter.getTodoList());
        TodoUtil.changeCompletedText(num, todoAdapter.getTodoList().size());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this); // 线性布局
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); // 让布局竖向排列
        todoView.setLayoutManager(layoutManager); // 指定RecyclerView的布局方式

        todoView.setAdapter(todoAdapter);

        todoInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                todoAdd.setEnabled(!s.toString().equals(""));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        todoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    1. 获取文本
                    2. 创建Todo对象
                    3. 将新的todo加入到todoAdapter.todoList中
                    4. 通知RecyclerView更新
                    5. 更新todoCompleted的文本
                    6. 更新头部组件
                      1) 输入框内容恢复为空
                      2) 添加按钮设置为不可点击
                    7. 全选框设置为false
                    8. 设置允许勾选全选框
                 */
                String newTodoContent = todoInput.getText().toString();
                Todo newTodo = new Todo(newTodoContent, false);
                todoAdapter.getTodoList().add(newTodo);

                int num = TodoUtil.getCompletedTodo(todoAdapter.getTodoList());

                TodoUtil.notifyDataSetChanged(num, todoAdapter.getTodoList().size()); // 2

                // other thing
                todoInput.setText("");
                todoAdd.setEnabled(false);

                todoAllComplete.setChecked(false);
                todoAllComplete.setEnabled(true);
            }
        });

        todoClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    1. 找出没有完成的todo
                    2. 更新todoAdapter.todoList的内容
                    3. 通知RecyclerView更新
                    4. 更新todoCompleted的文本
                    5. 全选框设置为false
                    6. 如果没有todo则不允许勾选全选框
                 */
                List<Todo> todos = new ArrayList<>();
                for (Todo todo : todoAdapter.getTodoList()) {
                    if (!todo.isComplete()) {
                        todos.add(todo);
                    }
                }

                todoAdapter.setTodoList(todos);

                TodoUtil.notifyDataSetChanged(0, todos.size());

                // other thing
                todoAllComplete.setChecked(false);
                todoAllComplete.setEnabled(todos.size() > 0);
            }
        });

        todoAllComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    1. 将todoAdapter.todoList中的todo设置为全选框的值
                    2. 通知RecyclerView更新
                    3. 更新todoCompleted的文本
                 */
                boolean isChecked = todoAllComplete.isChecked();
                List<Todo> todos = todoAdapter.getTodoList();
                for (Todo todo : todos) {
                    todo.setComplete(isChecked);
                }

                int num = TodoUtil.getCompletedTodo(todos);

                TodoUtil.notifyDataSetChanged(num, todos.size());
            }
        });

        todoAllComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*
                    如果checked则显示全不选，否则显示全选
                 */
                todoAllComplete.setText(isChecked ? "全不选" : "全选");
            }
        });
    }

    public List<Todo> initTodoList() {
        List<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo("吃饭", false));
        todoList.add(new Todo("睡觉", false));
        todoList.add(new Todo("打豆豆", false));
        return todoList;
    }
}
