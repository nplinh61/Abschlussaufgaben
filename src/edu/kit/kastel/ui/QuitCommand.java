package edu.kit.kastel.ui;

import edu.kit.kastel.model.Procastinot;

/**
 * Command to quit the program.
 *
 * @author Programmieren - Team
 */
final class QuitCommand extends Command {

    private static final String COMMAND_NAME = "quit";
    private static final String QUIT_WITH_ARGUMENTS_ERROR = "ERROR: quit does not allow args.";

    /**
     * Instantiates a new quit command.
     *
     * @param commandHandler the command handler
     * @param procastinot the procastinot application
     */
    public QuitCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler);
    }

    @Override
    public void execute(String[] commandArguments) {
        if (commandArguments.length != 0) {
            System.err.println(QUIT_WITH_ARGUMENTS_ERROR);
            return;
        }
        commandHandler.quit();
    }
}
