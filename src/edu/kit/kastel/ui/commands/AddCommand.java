package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.model.Priority;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Command to ask a task.
 *
 * @author ukgot
 * @version 1.0
 */
public class AddCommand extends ProcastinotCommand {

    private static final String WRONG_PRIORITY_MESSAGE = createError("Wrong Priority. Please check again.");
    private static final String INVALID_INPUT_MESSAGE = createError("Invalid input. Please check again.");
    private static final String INVALID_OPTIONAL_INPUT = createError("Optional input is wrong. Please check again.");
    private static final String COMMAND_NAME = "add";
    private static final String ADD_TASK_SUCCESS_FORMAT = "added %d: %s%n";

    private static final int EXPECTED_ARGUMENT_LENGTH = 1;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 2;

    private static final int FIRST_OPTIONAL_ARGUMENT_INDEX = 1;
    private static final int SECOND_OPTIONAL_ARGUMENT_INDEX = 2;
    private static final int NAME_INDEX = 0;
    private static final String INVALID_DATE_TIME_ERROR = createError("Invalid date time.");

    /**
     * Instantiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public AddCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }

    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {
        if (commandArguments.length < 1) {
            System.out.println(INVALID_INPUT_MESSAGE);
            return;
        }
        String taskName = commandArguments[NAME_INDEX];
        LocalDate date = null;
        Priority priority = Priority.NONE;
        if (commandArguments.length == 2) {
            boolean parsed = false;
            String nextArgument = commandArguments[FIRST_OPTIONAL_ARGUMENT_INDEX];
            Optional<Priority> parsedPriority = tryParsingPriority(nextArgument);
            if (parsedPriority.isPresent()) {
                priority = parsedPriority.get();
                parsed = true;
            }
            if (!parsed) {
                Optional<LocalDate> parsedDate = tryParsingDate(nextArgument);
                if (parsedDate.isPresent()) {
                    date = parsedDate.get();
                    parsed = true;
                }
            }
            if (!parsed) {
                System.err.println(INVALID_OPTIONAL_INPUT);
                return;
            }
        }
        if (commandArguments.length == 3) {
            String priorityString = commandArguments[FIRST_OPTIONAL_ARGUMENT_INDEX];
            String dateString = commandArguments[SECOND_OPTIONAL_ARGUMENT_INDEX];
            Optional<Priority> parsedPriority = tryParsingPriority(priorityString);
            if (parsedPriority.isPresent()) {
                priority = parsedPriority.get();
            } else {
                System.err.println(WRONG_PRIORITY_MESSAGE);
                return;
            }
            Optional<LocalDate> parsedDate = tryParsingDate(dateString);
            if (parsedDate.isPresent()) {
                date = parsedDate.get();
            } else {
                System.err.println(INVALID_DATE_TIME_ERROR);
                return;
            }
        }
        Task newTask = new Task(taskName, priority);
        if (date != null) {
            newTask.setDueDate(date);
        }
        int taskId = getProcastinot().addTask(newTask);
        System.out.printf(ADD_TASK_SUCCESS_FORMAT, taskId, taskName);
    }


}
