package edu.kit.kastel.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store all the task lists.
 *
 * @author ukgot
 * @version 1.0
 */
public class TaskList {
    private String name;
    private List<Task> tasks;
    private List<String> tags;

    public TaskList(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addTask(Task task) {

        tasks.add(task);
        task.setParentList(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setParentList(null);
    }

    public boolean addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            return true;
        } else {
            return false;
        }
    }

    public boolean containTask (Task task) {
        return tasks.contains(task);
    }

    public void addAllSubTasks (Task task) {
        //TODO: formulieren comments
        // recursively add all the subtasks of the given task.
        // subsubtasks will also be added.
        for (Task subTask : task.getSubTasks()) {
            addTask(subTask);
            addAllSubTasks(subTask);
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
