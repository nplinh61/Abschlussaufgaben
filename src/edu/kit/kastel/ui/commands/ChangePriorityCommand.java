package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.model.Task.Priority;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;
import java.util.Optional;

/**
 * Command to change the priority of one task.
 *
 * @author ukgot
 * @version 1.0
 */
public class ChangePriorityCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "change-priority";
    private static final int EXPECTED_ARGUMENT_LENGTH = 2;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String TASK_NO_PRIORITY_MESSAGE = createError("Task has not been assigned with any priority.");
    private static final String CHANGE_PRIORITY_SUCESS_MESSAGE = "changed %s to %s%n";

    /**
     * Initiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public ChangePriorityCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }
    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {

        String changedPriority = commandArguments[1];
        if (isValidNumber(commandArguments[0])) {
            int taskId = Integer.parseInt(commandArguments[0]);
            Task targetTask = getProcastinot().getTaskById(taskId);

            if (targetTask == null) {
                //TODO: print not found task message

            }
            Optional<Priority> newPriority = tryParsingPriority(changedPriority);
            if (targetTask != null) {
                if (newPriority.isPresent() && targetTask.getPriority() != null) {
                    System.out.printf(CHANGE_PRIORITY_SUCESS_MESSAGE, targetTask.getName(), newPriority.get());
                }
            }

        }
    }



}
