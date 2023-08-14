package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;

/**
 * Command to show a task according to an id.
 *
 * @author ukgot
 * @version 1.0
 */
public class ShowCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "show";
    private static final int EXPECTED_ARGUMENT_LENGTH = 1;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String TASK_NOT_FOUND_MESSAGE = "Task with id %s not found.";

    public ShowCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }


    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {
        if (isValidNumber(commandArguments[0])) {
            int taskId = Integer.parseInt(commandArguments[0]);
            Task targetTask = getProcastinot().getTaskById(taskId);

            if (targetTask == null) {
                System.out.printf(TASK_NOT_FOUND_MESSAGE, taskId);

            } else {
                targetTask.show(targetTask,0);
            }
        }
    }
}
