package edu.kit.kastel.ui;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * A command used for handling {@link Procastinot} regarding interactions.
 *
 * @author Programmieren-Team
 * @version 1.0
 */
public abstract class ProcastinotCommand extends Command {
    /**
     * Regex describing a valid name for all name regarding placeholders
     */
    protected static final String NAME_REGEX = "^(?!.*[;" + System.lineSeparator() + "]).*$";
    /**
     * Regex describing the date format yyyy-MM-dd
     */
    protected static final String DATE_REGEX = "yyyy-MM-dd";
    /**
     * Regex describing the time format HH:mm prohibiting minutes which are not 00 or 30
     */
    protected static final String TIME_REGEX = "([01][0-9]|2[0-3]):[03]0";
    protected static final String NUMBER_REGEX = "-?\\d+";
    /**
     * Pattern for the date format
     */
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * Pattern for the time format
     */
    private static final String TIME_PATTERN = "HH:mm";
    /**
     * Pattern format of the junction of date and time
     */
    private static final String DATE_TIME_PATTERN_FFORMAT = "%s %s";

    private static final String EXPECTED_INNER_ARGUMENTS_ERROR_FORMAT = createError("Expected %d arguments but got %d%n");

    private static final String PROCASTINOT_PLATFORM_COMMAND_SEPARATOR = ";";
    private static final String ERROR_PREFIX = "ERROR: ";
    /**
     * Formatter used for parsing the expected date time format
     */
    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern(DATE_TIME_PATTERN_FFORMAT.formatted(DATE_PATTERN, TIME_PATTERN));


    private final int expectedNumberOfArguments;
    private final int numberOfOptionalArguments;

    private final Procastinot procastinot;


    /**
     * Creates a new car sharing platform command.
     *
     * @param commandName             The name of the command.
     * @param commandHandler          The command handler.
     * @param procastinot     The procastinot application
     * @param expectedNumberOfArguments The expected number of arguments.
     */
    protected ProcastinotCommand(String commandName, CommandHandler commandHandler, Procastinot procastinot,
                                 int expectedNumberOfArguments) {
        this(commandName, commandHandler, procastinot, expectedNumberOfArguments, 0);
    }

    /**
     * Initiates a new command.
     * @param commandName name of the new command
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     * @param expectedNumberOfArguments number of expected arguments
     * @param numberOfOptionalArguments number of optional arguments
     */
    protected ProcastinotCommand(String commandName, CommandHandler commandHandler, Procastinot procastinot,
                                 int expectedNumberOfArguments, int numberOfOptionalArguments) {
        super(commandName, commandHandler);
        this.procastinot = Objects.requireNonNull(procastinot);
        this.expectedNumberOfArguments = expectedNumberOfArguments;
        this.numberOfOptionalArguments = numberOfOptionalArguments;
    }

    @Override
    public void execute(String[] commandArguments) {
        List<String> argumentsForProcastinotCommand = new ArrayList<>();
        for (String commandArgument : commandArguments) {
            // Arguments of car sharing platform commands are separated by another deliminator.
            // Therefore, we have to split them again and create a new argument list.
            argumentsForProcastinotCommand
                    .addAll(Arrays.asList(commandArgument.split(PROCASTINOT_PLATFORM_COMMAND_SEPARATOR)));
        }
        int numberOfArguments = argumentsForProcastinotCommand.size();
        if (numberOfArguments < expectedNumberOfArguments || numberOfArguments > expectedNumberOfArguments + numberOfOptionalArguments) {
            System.err.printf(EXPECTED_INNER_ARGUMENTS_ERROR_FORMAT, expectedNumberOfArguments, argumentsForProcastinotCommand.size());
            //TODO: print error of max and min of arguments
            return;
        }
        executeProcastinotCommand(argumentsForProcastinotCommand.toArray(new String[0]));
    }

    /**
     * Executes the command on the car sharing platform.
     *
     * @param commandArguments The command arguments.
     */
    protected abstract void executeProcastinotCommand(String[] commandArguments);

    /**
     * Creates an error message.
     *
     * @param message The message.
     * @return The message as error message.
     */
    protected static String createError(String message) {
        return ERROR_PREFIX + message; }

    /**
     * @return procastinot return the procastinot application
     * The procastinot application
     */
    public Procastinot getProcastinot() {
        return procastinot;
    }

    public boolean isValidNumber (String str) {
        return str.matches(NUMBER_REGEX);
    }

    public static Optional<LocalDate> tryParsingDate(String dateString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_REGEX);
        try {
            LocalDate date = LocalDate.parse(dateString, dateFormatter);
            return Optional.of(date);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static Optional<Task.Priority> tryParsingPriority(String priorityString) {
        try {
            Task.Priority priority = parsePriority(priorityString);
            return Optional.of(priority);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static Task.Priority parsePriority(String input) {
        return Task.Priority.valueOf(input.toUpperCase()); }


}

