package edu.kit.kastel.ui;

import edu.kit.kastel.model.Procastinot;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

    /**
     * A command used for handling {@link edu.kit.kastel.model.Procastinot} regarding interactions.
     *
     * @author Tobias Thirolf
     * @author Dominik Fuchß
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
        protected static final String DATE_TIME_PATTERN_FFORMAT = "%s %s";
        /**
         * Formatter used for parsing the expected date time format
         */
        protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
                .ofPattern(DATE_TIME_PATTERN_FFORMAT.formatted(DATE_PATTERN, TIME_PATTERN));
        /**
         * Error message if the car number does not correspond to a car
         */
        protected static final String CAR_NOT_FOUND_ERROR = createError("Cannot find car with given car number.");
        /**
         * Error message if the station number does not correspond to a station
         */
        protected static final String STATION_NOT_FOUND_ERROR_FORMAT
                = createError("No station with given number '%s' found.%n");
        /**
         * Error message if the duration is invalid
         */
        protected static final String INVALID_DURATION_ERROR = createError("Duration must be at least 1 hour.");
        /**
         * Error message if the car number is invalid
         */
        protected static final String CAR_NUMBER_INVALID = createError("Car number is invalid.");
        /**
         * Error message if the date or time is invalid
         */
        protected static final String INVALID_DATE_TIME_ERROR = createError("Given date or time is invalid.");
        /**
         * Basic error message if arguments are invalid
         */
        protected static final String INVALID_ARGUMENTS_ERROR = createError("Given arguments are invalid.");
        private static final String EXPECTED_INNER_ARGUMENTS_ERROR_FORMAT
                = createError("Expected %d arguments but got %d%n");

        private static final String CAR_SHARING_PLATFORM_COMMAND_SEPARATOR = ";";
        private static final String ERROR_PREFIX = "ERROR: ";
        /**
         * The car sharing platform.
         */
        protected final Procastinot procastinot;
        private final int expectedNumberOfArguments;
        private final int numberOfOptionalArguments;

        /**
         * Creates a new car sharing platform command.
         *
         * @param commandName             The name of the command.
         * @param commandHandler          The command handler.
         * @param procastinot     The procastinot application
         * @param expectedNumberOfArguments The expected number of arguments.
         */
        protected ProcastinotCommand(String commandName, CommandHandler commandHandler
                , Procastinot procastinot, int expectedNumberOfArguments) {
            this(commandName, commandHandler, procastinot, expectedNumberOfArguments,0);
        }
        protected ProcastinotCommand(String commandName, CommandHandler commandHandler
                , Procastinot procastinot, int expectedNumberOfArguments, int numberOfOptionalArguments) {
            super(commandName, commandHandler);
            this.procastinot = Objects.requireNonNull(procastinot);
            this.expectedNumberOfArguments = expectedNumberOfArguments;
            this.numberOfOptionalArguments = numberOfOptionalArguments;
        }

        @Override
        public final void execute(String[] commandArguments) {
            List<String> argumentsForCarSharingPlatformCommand = new ArrayList<>();
            for (String commandArgument : commandArguments) {
                // Arguments of car sharing platform commands are separated by another deliminator.
                // Therefore, we have to split them again and create a new argument list.
                argumentsForCarSharingPlatformCommand
                        .addAll(Arrays.asList(commandArgument.split(CAR_SHARING_PLATFORM_COMMAND_SEPARATOR)));
            }
            int numberOfArguments = argumentsForCarSharingPlatformCommand.size();
            if (numberOfArguments < expectedNumberOfArguments || numberOfArguments > expectedNumberOfArguments + numberOfOptionalArguments) {
                System.err.printf(EXPECTED_INNER_ARGUMENTS_ERROR_FORMAT, expectedNumberOfArguments
                        , argumentsForCarSharingPlatformCommand.size());
                //TODO: print error of max and min of arguments
                return;
            }

            executeProcastinotCommand(argumentsForCarSharingPlatformCommand.toArray(new String[0]));
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
            return ERROR_PREFIX + message;
        }
}

