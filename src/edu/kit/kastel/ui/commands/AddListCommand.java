package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.TaskList;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;

/**
 * Command to add a new list.
 *
 * @author ukgot
 * @version 1.0
 */
public class AddListCommand extends ProcastinotCommand {

    private static final String COMMAND_NAME = "add-list";
    private static final int EXPECTED_ARGUMENT_LENGTH = 1;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String INVALID_INPUT_MESSAGE = createError("Invalid input. Please check again");
    private static final String ADD_SUCCESS_FORMAT = "added ";


    /**
     * Instantiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public AddListCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }

    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {
        if (commandArguments.length > 1) {
            System.err.println(INVALID_INPUT_MESSAGE);
            return;
        }

        String listName = commandArguments[0];

        // Here, you can create a new list and add it to your task management system
        // Replace this with the actual logic to create and add the list
        TaskList newList = new TaskList(listName);

        // Assuming you have a method to add a list from your command handler
        getProcastinot().addList(newList);

        System.out.println(ADD_SUCCESS_FORMAT + listName);

    }

}
