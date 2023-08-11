package edu.kit.kastel.ui.commands;


import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;

import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;


/**
 * Command to mark a task with x when the task has already been done.
 *
 * @author ukgot
 * @version 1.0
 */
public class ToggleCommand extends ProcastinotCommand {


    private static final String COMMAND_NAME = "toggle";
    private static final int EXPECTED_ARGUMENT_LENGTH = 1;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String INVALID_TASK_ID = createError("Invalid task ID");
    private static final String TASK_NOT_EXIST_MESSAGE = createError("Task does not exist.");
    private static final String TOGGLE_SUCCESS_MESSAGE = "toggled %s and %s subtasks %n";

    /**
     * Instantiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public ToggleCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }

    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {

        if (isValidNumber(commandArguments[0])) {
            int taskId = Integer.parseInt(commandArguments[0]);
            toggleTaskAndSubTasks(taskId);
        } else {
            System.err.println(INVALID_TASK_ID);
        }
    }

    private void toggleTaskAndSubTasks(int taskId) {
        Task targetTask = getProcastinot().getTaskById(taskId);
        if (targetTask == null) {
            System.err.println(TASK_NOT_EXIST_MESSAGE);
            return;
        }
        boolean state = !targetTask.isCompleted();
        int numberOfSubTasks = toggleSubTasks(targetTask, state) - 1;
        targetTask.setCompleted(state);
        System.out.printf(TOGGLE_SUCCESS_MESSAGE, targetTask.getName(), numberOfSubTasks);


    }

    private int toggleSubTasks(Task parentTask, boolean state) {
        int numberOfSubTasks = 0;
        for (Task subTask : parentTask.getSubTasks()) {
            numberOfSubTasks += toggleSubTasks(subTask, state);
        }
        parentTask.setCompleted(state);
        numberOfSubTasks++;

        return numberOfSubTasks;
    }


}
