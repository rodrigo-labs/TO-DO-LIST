package br.com.rodrigo_labs.todolist;

import android.widget.EditText;

import br.com.rodrigo_labs.todolist.model.Task;

public class FormHelper {

    private EditText etTask;
    private EditText etDate;
    private EditText etTime;
    private int done;

    public FormHelper(FormActivity activity) {
        etTask = (EditText) activity.findViewById(R.id.et_task);
        etDate = (EditText) activity.findViewById(R.id.et_date);
        etTime = (EditText) activity.findViewById(R.id.et_time);
        done = 0;
    }

    public Task getTask() {
        Task task = new Task();

        task.setTask(etTask.getText().toString());
        task.setDate(etDate.getText().toString());
        task.setTime(etTime.getText().toString());
        task.setDone(done);

        return task;
    }
}
