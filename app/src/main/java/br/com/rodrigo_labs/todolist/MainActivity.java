package br.com.rodrigo_labs.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.rodrigo_labs.todolist.dao.TaskDao;
import br.com.rodrigo_labs.todolist.model.Task;

public class MainActivity extends AppCompatActivity {

    private ListView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(getCurrentDate());

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(addClick());

        taskList = (ListView) findViewById(R.id.task_list);
        taskList.setOnItemClickListener(itemClick());
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadList();
        registerForContextMenu(taskList);
    }

    private View.OnClickListener addClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FormActivity.class);
                startActivity(intent);
            }
        };
    }

    private AdapterView.OnItemClickListener itemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View item, int position, long id) {
                Task task = (Task) taskList.getItemAtPosition(position);

                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("task", task);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context_main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Task task = (Task) taskList.getItemAtPosition(info.position);

        switch (item.getItemId()) {
            case R.id.delete:
                TaskDao dao = new TaskDao(this);
                dao.delete(task);
                dao.close();

                loadList();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    private void loadList() {
        TaskDao dao = new TaskDao(this);
        List<Task> tasks = dao.getTasks();
        dao.close();

        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        taskList.setAdapter(adapter);
    }

    private String getCurrentDate() {
        //todo - i use a dict to connect a pattern to a locale
        SimpleDateFormat date = new SimpleDateFormat("EEE, MMMM d", Locale.getDefault());

        return date.format(new Date(System.currentTimeMillis()));
    }

    private Context getContext() {
        return this;
    }
}
