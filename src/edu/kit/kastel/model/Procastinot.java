package edu.kit.kastel.model;

import java.util.ArrayList;
import java.util.List;

public class Procastinot {
    private int currentTaskId = 1;
    private List<Task> tasks = new ArrayList<>();
    public void increaseCurrentTaskId() {
        currentTaskId ++;
    }

    public int addTask(Task task) {
        int taskId = getCurrentTaskId();
        increaseCurrentTaskId();
        task.setId(taskId);
        this.tasks.add(task);
        return taskId;
    }
    public int getCurrentTaskId() {
        return currentTaskId;
    }
}
