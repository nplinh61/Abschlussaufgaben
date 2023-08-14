package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;


/**
 * Command to restore a deleted command.
 *
 * @author ukgot
 * @version 1.0
 */
public class RestoreCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "restore";
    private static final int EXPECTED_ARGUMENT_LENGTH = 1;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String INVALID_TASK_ID_MESSAGE = "Task with id %s not found %n";
    private static final String RESTORE_SUCCESS_MESSAGE = "restored %s and %s subtasks%n";

    /**
     * Initiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public RestoreCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }


    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {
        boolean deleted = false;
        if (isValidNumber(commandArguments[0])) {
            int taskId = Integer.parseInt(commandArguments[0]);
            Task targetTask = getProcastinot().getTaskById(taskId);

            if (targetTask == null) {
                System.out.printf(INVALID_TASK_ID_MESSAGE, taskId);
                return;
            }
            int numberOfSubTasks = targetTask.deleteOrRestoreTaskAndSubTasks(targetTask, deleted) - 1;
            System.out.printf(RESTORE_SUCCESS_MESSAGE, targetTask.getName(), numberOfSubTasks);
        }

    }
}
