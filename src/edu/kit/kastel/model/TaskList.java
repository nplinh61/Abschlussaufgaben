package edu.kit.kastel.model;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("List: ").append(name);

        if (!tags.isEmpty()) {
            stringBuilder.append(" (").append(String.join(", ", tags)).append(")");
        }

        for (Task task : tasks) {
            stringBuilder.append("\n").append(task.toString().replace("\n", "\n  "));
        }

        return stringBuilder.toString();
    }

}
