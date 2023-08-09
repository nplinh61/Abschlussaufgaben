package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.model.Task.Priority;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class AddCommand extends ProcastinotCommand {

    private static final String COMMAND_NAME = "add";
    private static final int EXPECTED_ARGUMENT_LENGTH = 1;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 2;

    private static final int FIRST_OPTIONAL_ARGUMENT_INDEX = 1;
    private static final int SECOND_OPTIONAL_ARGUMENT_INDEX = 2;
    private static final int NAME_INDEX = 0;

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
            System.out.println("Invalid command format. Usage: add <name> [priority] [date]");
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
                System.err.println("Second argument is neither date nor priority");
                return;
            }
        }

        if (commandArguments.length == 3) {
            String priorityString = commandArguments[FIRST_OPTIONAL_ARGUMENT_INDEX];
            String dateString = commandArguments[SECOND_OPTIONAL_ARGUMENT_INDEX];

            Optional<Priority> parsedPriority = tryParsingPriority(priorityString);
            if (parsedPriority.isPresent()) {
                priority = parsedPriority.get();
            }
            else {
                System.err.println("Wrong Priority");
                return;
            }

            Optional<LocalDate> parsedDate = tryParsingDate(dateString);
            if (parsedDate.isPresent()) {
                date = parsedDate.get();
            }
            else {
                System.err.println(INVALID_DATE_TIME_ERROR);
                return;
            }
        }

        // Here, you can create a new task and add it to your task management system
        // Replace this with the actual logic to create and add the

        Task newTask = new Task(taskName, priority);
        if (date != null) {
            newTask.setDueDate(date);
        }

        // Assuming you have a method to add a task and get its ID from your command handler
        int taskId = procastinot.addTask(newTask);

        System.out.println("added " + taskId + ": " + taskName);
    }

    private static Optional<LocalDate> tryParsingDate(String dateString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_REGEX);
        try {
            LocalDate date;
            date = LocalDate.parse(dateString, dateFormatter);
            return Optional.of(date);
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    private static Optional<Priority> tryParsingPriority(String priorityString) {
        try {
            Priority priority;
            priority = parsePriority(priorityString);
            return Optional.of(priority);
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    private static Priority parsePriority(String input) {
            return Priority.valueOf(input.toUpperCase());
    }
}