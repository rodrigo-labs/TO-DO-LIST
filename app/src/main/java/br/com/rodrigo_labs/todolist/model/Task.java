package br.com.rodrigo_labs.todolist.model;

public class Task {

    private long id;
    private String task;
    private String date;
    private String time;
    private boolean done;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isDone() {
        return done;
    }

    public int getDone() {
        if (done) {
            return 1;
        }

        return 0;
    }

    public void setDone(int done) {
        this.done = done != 0;
    }

    @Override
    public String toString() {
        return getTask();
    }
}
