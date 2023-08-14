package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.model.TaskList;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;


/**
 * Command to assign one task to a parent task or to a list.
 *
 * @author ukgot
 * @version 1.0
 */
public class AssignCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "assign";
    private static final int EXPECTED_ARGUMENT_LENGTH = 2;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String INPUT_ERROR_MESSAGE = createError("Number of arguments is invalid.");
    private static final String INVALID_LIST_ERROR = createError("List does not exist. Please check again.");
    private static final String INVALID_TASK_ERROR = "Task with id %s does not exist. Please check again. %n";
    private static final String EXISTED_TASK_IN_LIST_MESSAGE = " Task %s already existed in list %s";
    private static final String ASSIGN_SUCCESS_MESSAGE = "assigned %s to %s%n";

    /**
     * Initiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public AssignCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }

    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {
        if (commandArguments.length > 2) {
            System.err.println(INPUT_ERROR_MESSAGE);
            return;
        }
        int taskId = Integer.parseInt(commandArguments[0]);
        String decideAssign = commandArguments[1];

        if (isList(decideAssign)) {
            assignTaskToList(taskId, decideAssign);

        } else {
            int targetTaskId = Integer.parseInt(commandArguments[1]);
            assignTasktoTask(taskId, targetTaskId);

        }



    }

    private void assignTasktoTask(int taskId, int targetTaskId) {
        Task subTask = getProcastinot().getTaskById(taskId);
        Task parentTask = getProcastinot().getTaskById(targetTaskId);


        if (subTask == null || parentTask == null || parentTask.isDeleted()) {
            System.out.printf(INVALID_TASK_ERROR, targetTaskId);
            return;
        }

        //TaskList existingParent = subTask.getParentList();
        if (subTask.getParentTask() != null) {
            Task previousParent = subTask.getParentTask();
            previousParent.removeSubTask(subTask);
        }

        parentTask.addSubTask(subTask);
        subTask.setParentTask(parentTask);


        System.out.printf(ASSIGN_SUCCESS_MESSAGE, subTask.getName(), parentTask.getName());

    }

    private void assignTaskToList(int taskId, String decideAssign) {
        Task targetTask = getProcastinot().getTaskById(taskId);
        TaskList targetList = getProcastinot().getListByName(decideAssign);

        if (targetTask == null) {
            System.err.println(INVALID_TASK_ERROR);
            return;
        }

        if (targetList == null) {
            System.err.println(INVALID_LIST_ERROR);
            return;
        }

        if (targetList.containTask(targetTask)) {
            System.out.printf(EXISTED_TASK_IN_LIST_MESSAGE, targetTask.getName(), targetList.getName());
            return;
        }
        targetList.addTask(targetTask);
        targetList.addAllSubTasks(targetTask);
        System.out.printf(ASSIGN_SUCCESS_MESSAGE, targetTask.getName(), targetList.getName());
    }

    private boolean isList(String str) {
        return str.matches("[a-zA-Z]+");
    }
}
