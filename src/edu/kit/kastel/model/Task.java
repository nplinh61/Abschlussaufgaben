package edu.kit.kastel.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Task {
    public enum Priority {
        HI, MD, LO, NONE
    }
    private int id;
    private final String name;
    private boolean completed;
    private boolean deleted;
    private LocalDate dueDate;
    private Priority priority;
    private final List<String> tags;
    private final List<Task> subTasks;
    private Task parentTask;
    private TaskList parentList;

    public Task(String name) {
        this(name, Priority.NONE);
    }
    public Task(String name, Priority priority) {
        this.name = name;
        this.completed = false;
        this.dueDate = null;
        this.priority = priority;
        this.tags = new ArrayList<>();
        this.subTasks = new ArrayList<>();
        this.parentTask = null;
    }
    public Task(int id, String name, Priority priority) {
        this.id = id;
        this.name = name;
        this.completed = false;
        this.dueDate = null;
        this.priority = priority;
        this.tags = new ArrayList<>();
        this.subTasks = new ArrayList<>();
        this.parentTask = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void markAsCompleted() {
        this.completed = true;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void markAsIncomplete() {
        this.completed = false;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            return true;
        }
        return false;
    }

    public void addSubTask(Task subTask) {

        subTask.setParentTask(this);
        this.subTasks.add(subTask);
    }

    public void removeSubTask(Task subTask) {
        this.subTasks.remove(subTask);
        subTask.setParentTask(null);
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public List<Task> getSubTasks() {
        return Collections.unmodifiableList(this.subTasks);
    }

    public TaskList getParentList() {
        return parentList;
    }

    public void setParentList(TaskList parentList) {
        this.parentList = parentList;
    }

    public int deleteOrRestoreTaskAndSubTasks(Task task, boolean delete) {
        List<Task> subtasksModified = new ArrayList<>();
        collectSubTasks(task, subtasksModified);
        int numberOfSubTasks = 0;
        //numberOfSubTasks += task.getSubTasks().size();

        for (Task subtask : subtasksModified) {
            numberOfSubTasks++;
            subtask.setDeleted(delete);
        }


        if (task.getParentTask() != null) {
            if(delete) {
                task.getParentTask().removeSubTask(task);
            } else {
                task.getParentTask().addSubTask(task);
            }

        }
        if (task.getParentList() != null) {
            if (delete) {
                task.getParentList().removeTask(task);
            } else {
                task.getParentList().addTask(task);
            }
        }
        task.setDeleted(delete);
        return numberOfSubTasks;

    }

    private void collectSubTasks (Task task, List<Task> subtasks) {
        subtasks.add(task);
        for (Task subtask : task.getSubTasks()) {
            collectSubTasks(subtask, subtasks);
        }
    }

    @Override
    public String toString() {
        String completionStatus = completed ? "[ x ]" : "[ ]";
        String tagsString = tags.isEmpty() ? "" : " (" + String.join(", ", tags) + ")";
        String dueDateArrow = dueDate != null ? " -> " + dueDate.toString() : "";
        String priorityString = priority == Priority.NONE ? "" : priority.toString();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(completionStatus).append(" ").append(name).append(tagsString).append(dueDateArrow)
                .append(" ").append(priorityString);

        for (Task subTask : subTasks) {
            stringBuilder.append("\n").append(subTask.toString().replace("\n", "\n  "));
        }

        return stringBuilder.toString();
    }
}
