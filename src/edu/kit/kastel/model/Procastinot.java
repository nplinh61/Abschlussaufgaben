package edu.kit.kastel.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Procastinot {
    private int currentTaskId = 1;
    private int currentTaskList = 1;
    private List<Task> tasks = new ArrayList<>();
    private List<TaskList> lists = new ArrayList<>();

    private Map<Integer, Task> parentList = new HashMap<>();

    public Map<Integer, Task> getParentList() {
        return parentList;
    }

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

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getIncompleteTasks() {
        List<Task> incompleteTasks = new ArrayList<>();
        for (Task task : getTasks()) {
            addIncompleteTasks(task, incompleteTasks);
        }
        return incompleteTasks;
    }

    private void addIncompleteTasks(Task task, List<Task> incompleteTasks) {
        if (!task.isCompleted()) {
            incompleteTasks.add(task);
        }

        for (Task subTask : task.getSubTasks()) {
            addIncompleteTasks(subTask, incompleteTasks);
        }
    }

    public List<Task> getTasksWithTag(String tag) {
        List<Task> tasksWithTag = new ArrayList<>();
        for (Task task : tasks) {
            if (task.hasTag(tag)) {
                tasksWithTag.add(task);
            }
        }
        return tasksWithTag;
    }

}
