package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.ui.Command;
import edu.kit.kastel.ui.CommandHandler;

public class BetweenCommand extends Command {
    private static final String COMMAND_NAME = "between";

    /**
     * Instantiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public BetweenCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler);
    }

    @Override
    public void execute(String[] commandArguments) {

    }

}