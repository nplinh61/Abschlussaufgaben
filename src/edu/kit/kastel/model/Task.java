package edu.kit.kastel.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
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
    private Procastinot procastinot;
    /**
     * Pattern for the date format
     */
    private static final String DATE_PATTERN = "yyyy-MM-dd";

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

    public List<String> getTags() {
        return tags;
    }

    public boolean addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            return true;
        }
        return false;
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
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


    /*
    public void show(Task task, int level) {
        String indentation = "  ".repeat(level);
        String priorityPresentation = getPriority() != Priority.NONE ? "[" + getPriority() + "]" : "";
        String tagPresentation = getTags().isEmpty() ? "" : ": " + String.join(", ", getTags());
        String statePresentation = isCompleted() ? "x" : " ";
        String datePresentation = task.getDueDate() != null ? " --> " + task.getFormattedDueDate() : "";

        if (task.isCompleted()) {
            return;
        }

        System.out.println(indentation + "- [" + statePresentation + "] " + task.getName() + " "
                + priorityPresentation + tagPresentation + datePresentation);

        List<Task>  sortedSubTasks = new ArrayList<>(task.getSubTasks());
        sortedSubTasks.sort(Comparator.comparing(Task::getPriority, Comparator.nullsLast(Priority::compareTo))
                .thenComparing(Task::getId));

        for (Task subTask : sortedSubTasks) {
            show(subTask, level+1);
        }
    }

     */


    public String formattedTaskPresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append("- [").append(isCompleted() ? "x" : " ").append("] ").append(getName());

        if (getPriority() != Priority.NONE) {
            builder.append(" [").append(getPriority()).append("]");
        }

        if (!getTags().isEmpty() || dueDate != null) {
            builder.append(":");
        }

        if (!getTags().isEmpty()) {
            builder.append(" (").append(String.join(", ", getTags())).append(")");
        }

        if (dueDate != null) {
            builder.append(" --> ").append(getFormattedDueDate());
        }

        return builder.toString();
    }



    public List<Task> getSubTasksWithTag(String tag) {
        List<Task> taggedSubTasks = new ArrayList<>();

        for (Task subTask : subTasks) {
            if (subTask.hasTag(tag)) {
                taggedSubTasks.add(subTask);
                taggedSubTasks.addAll(subTask.getSubTasksWithTag(tag));
            }
        }

        return taggedSubTasks;
    }



    public void showTaskAndSubtasks(Task task, String indent) {
        System.out.println(indent + task.formattedTaskPresentation());
        List<Task> sortedSubTasks = new ArrayList<>(task.getSubTasks());
        sortedSubTasks.sort(Comparator.comparing(Task::getPriority, Comparator.nullsLast(Priority::compareTo))
                .thenComparing(Task::getId));

        for (Task subTask : sortedSubTasks) {
            showTaskAndSubtasks(subTask, indent + "  ");
        }
    }


    public boolean hasParentTaskInList(List<Task> taskList) {
        Task other = this;
        while (true) {
            Task parent = other.getParentTask();
            if (parent == null) return false;
            if (taskList.contains(parent)) return true;
            other = parent;
        }
    }




    private String getFormattedDueDate() {
        if (dueDate == null) {
            return "";

        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return dueDate.format(formatter);
    }


}
