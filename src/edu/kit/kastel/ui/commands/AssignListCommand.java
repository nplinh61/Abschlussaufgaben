package edu.kit.kastel.ui.commands;


import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.ui.Command;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Command to assign a list
 *
 * @author ukgot
 * @version 1.0
 */
public class AssignListCommand extends Command {
    private static final String LIST_COMMAND_NAME = "assign";

    /**
     * Instantiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public AssignListCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(LIST_COMMAND_NAME, commandHandler);
    }

    @Override
    public void execute(String[] commandArguments) {

    }
}