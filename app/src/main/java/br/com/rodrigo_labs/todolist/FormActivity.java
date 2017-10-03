package br.com.rodrigo_labs.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.rodrigo_labs.todolist.dao.TaskDao;
import br.com.rodrigo_labs.todolist.model.Task;

public class FormActivity extends AppCompatActivity {

    private FormHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // start the helper on method onCreate of the activity
        helper = new FormHelper(this);

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        if (task != null) {
            toolbar.setTitle(R.string.title_activity_form_edit);

            helper.setForm(task);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                Task task = helper.getForm();
                TaskDao dao = new TaskDao(this);

                if (task.getId() != null) {
                    dao.update(task);
                } else {
                    dao.insert(task);
                }
                dao.close();

                Toast.makeText(this, R.string.form_sucess, Toast.LENGTH_LONG).show();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
