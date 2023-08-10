package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.ui.Command;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Command to change the date that one task has to be done.
 *
 * @author ukgot
 * @version 1.0
 */
public class ChangeDateCommand extends Command {
    private static final String COMMAND_NAME = "change-date";

    /**
     * Instantiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public ChangeDateCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler);
    }

    @Override
    public void execute(String[] commandArguments) {

    }

}
