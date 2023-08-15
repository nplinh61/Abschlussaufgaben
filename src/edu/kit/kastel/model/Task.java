package edu.kit.kastel.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a task of Procastinot Application.
 *
 * @author ukgot
 * @version 1.0
 */
public class Task {
    private int id;
    private final String name;
    private boolean completed;
    private boolean deleted;
    private boolean restored;
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

    /**
     * Instanciate a new task.
     * @param name name of the task.
     */
    public Task(String name) {
        this(name, Priority.NONE);
    }

    /**
     * Instanciate a new task with name and priority.
     * @param name name of the task.
     * @param priority priority that was assigned to the task.
     */
    public Task(String name, Priority priority) {
        this.name = name;
        this.completed = false;
        this.dueDate = null;
        this.priority = priority;
        this.tags = new ArrayList<>();
        this.subTasks = new ArrayList<>();
        this.parentTask = null;
    }

    /**
     * Instanciate a new task with ID, name and priority.
     * @param id id of the task.
     * @param name name of the task.
     * @param priority priority that was assigned to the task.
     */
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

    /**
     * Get the id of the task.
     * @return the id of the task.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the task.
     * @return the name of the task.
     */
    public String getName() {
        return name;
    }

    public void markAsCompleted() {
        this.completed = true;
    }

    /**
     * Set the id of the task.
     * @param id id of the task
     */
    public void setId(int id) {
        this.id = id;
    }


    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
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


    public void restore() {
        this.restored = true;
    }

    public void unrestore() {
        this.restored = false;
    }

    public boolean isRestored() {
        return restored;
    }

    public void setRestored(boolean restored) {
        this.restored = restored;
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
        for (Task subtask : subtasksModified) {
            numberOfSubTasks++;
            subtask.setDeleted(delete);
        }
        if (task.getParentTask() != null) {
            if (delete) {

                task.getParentTask().removeSubTask(task);
            } else {
                task.setRestored(true);
                task.getParentTask().addSubTask(task);
            }
        }
        if (task.getParentList() != null) {
            if (delete) {
                task.getParentList().removeTask(task);
            } else {
                task.setRestored(true);
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
        if (!task.isDeleted() || task.isRestored()) {
            System.out.println(indent + task.formattedTaskPresentation());
            List<Task> sortedSubTasks = new ArrayList<>(task.getSubTasks());
            sortedSubTasks.sort(Comparator.comparing(Task::getPriority, Comparator.nullsLast(Priority::compareTo))
                    .thenComparing(Task::getId));

            for (Task subTask : sortedSubTasks) {
                showTaskAndSubtasks(subTask, indent + "  ");
            }
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

    /**
     * Collects all the upcoming tasks that have to be done
     * within the next 7 days starting from start date.
     * @param task task that needs to be checked.
     * @param upcomingTasks list of the upcoming tasks.
     * @param startDate start date of the range that needs to be checked.
     * @param endDate end date of the range that needs to be checked.
     */
    public void getUpcomingTasks(Task task, List<Task> upcomingTasks, LocalDate startDate, LocalDate endDate) {
        if (isWithinDateRange(task.getDueDate(), startDate, endDate )) {
            upcomingTasks.add(task);
        }

        for (Task subTask : task.getSubTasks()) {
            getUpcomingTasks(subTask, upcomingTasks, startDate, endDate);
        }

    }

    /**
     * Check if one task is within the required range.
     * @param taskDueDate the duedate of one task.
     * @param startDate start date of the range.
     * @param endDate end date of the range.
     * @return return true if the task is within the range.
     */
    public boolean isWithinDateRange(LocalDate taskDueDate, LocalDate startDate, LocalDate endDate) {
        return taskDueDate != null && !taskDueDate.isBefore(startDate) && !taskDueDate.isAfter(endDate);
    }


    public void upcomingTasksPresentation(List<Task> upcomingTasks) {
       for (Task task : upcomingTasks) {
            if (!hasParentTaskInList(upcomingTasks)) {
                task.showTaskAndSubtasks(task, "");
            }
        }

    }



}
