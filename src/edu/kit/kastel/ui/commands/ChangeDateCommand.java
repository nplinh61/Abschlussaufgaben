package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Command to change the date that one task has to be done.
 *
 * @author ukgot
 * @version 1.0
 */
public class ChangeDateCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "change-date";
    private static final int EXPECTED_ARGUMENT_LENGTH = 2;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String CHANGE_DATE_SUCCESS_MASSAGE = "changed %s to %s%n";
    private static final String CHANGE_DATE_FAILED_MESSAGE = createError("Cannot change the date.");


    /**
     * Initiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public ChangeDateCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }

    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {

        String changedDate = commandArguments[1];

        if (isValidNumber(commandArguments[0])) {
            int taskId = Integer.parseInt(commandArguments[0]);
            Task targetTask = getProcastinot().getTaskById(taskId);



            Optional<LocalDate> newDeadlines = tryParsingDate(changedDate);
            if (newDeadlines.isPresent()) {
                targetTask.setDueDate(newDeadlines.get());
                System.out.printf(CHANGE_DATE_SUCCESS_MASSAGE, targetTask.getName(), newDeadlines.get());
            } else {
                System.err.print(CHANGE_DATE_FAILED_MESSAGE);
            }

        }

    }
}
