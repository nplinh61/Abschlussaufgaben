package edu.kit.kastel.model;

import java.util.ArrayList;
import java.util.List;

public class Procastinot {
    private int currentTaskId = 1;
    private int currentTaskList = 1;
    private List<Task> tasks = new ArrayList<>();
    private List<TaskList> lists = new ArrayList<>();
    public void increaseCurrentTaskId() {
        currentTaskId ++;
    }
    public void increaseCurrentTaskListId() {
        currentTaskList++;
    }

    public int addTask(Task task) {
        int taskId = getCurrentTaskId();
        increaseCurrentTaskId();
        task.setId(taskId);
        this.tasks.add(task);
        return taskId;
    }

    public Task getTaskById(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    /*public int addList(TaskList taskList) {
        int taskListId = getCurrentTaskList();
        increaseCurrentTaskListId();
        this.lists.add(taskList);
        return taskListId;
    }

     */

    public void addList (TaskList taskList) {
        lists.add(taskList);
    }
    public int getCurrentTaskId() {
        return currentTaskId;
    }

    public int getCurrentTaskList() {
        return currentTaskList;
    }
    public TaskList getListByName(String listName) {
        for (TaskList taskList : lists) {
            if (taskList.getName().equals(listName)) {
                return taskList;
            }
        }
        return null;
    }
}
