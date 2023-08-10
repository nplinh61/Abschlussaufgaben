package edu.kit.kastel.ui.commands;


import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.ui.Command;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Command to mark a task with x when the task has already been done.
 *
 * @author ukgot
 * @version 1.0
 */
public class ToggleCommand extends Command {
    private static final String COMMAND_NAME = "todo";

    /**
     * Instantiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public ToggleCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler);
    }

    @Override
    public void execute(String[] commandArguments) {

    }

}
