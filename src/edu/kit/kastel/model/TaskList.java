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

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addTag(String tag) {
        tags.add(tag);
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
