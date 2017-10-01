package br.com.rodrigo_labs.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.rodrigo_labs.todolist.dao.TaskDao;
import br.com.rodrigo_labs.todolist.model.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(getCurrentDate());

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(addClick());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
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

    private String getCurrentDate() {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("EEEE, d 'de' MMMM");

        return simpleFormat.format( new Date( System.currentTimeMillis() ) );
    }

    private void loadList() {
        TaskDao dao = new TaskDao(this);
        List<Task> tasks = dao.getTasks();
        dao.close();

        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        ListView taskList = (ListView) findViewById(R.id.task_list);
        taskList.setAdapter(adapter);
    }

    private Context getContext() {
        return this;
    }
}
