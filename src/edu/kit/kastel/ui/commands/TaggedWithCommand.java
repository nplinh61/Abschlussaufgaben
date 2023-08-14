package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;

import java.util.List;

/**
 * Command to show all the tasks that are tagged with a given tag.
 *
 * @author ukgot
 * @version 1.0
 */
public class TaggedWithCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "tagged-with";
    private static final int EXPECTED_ARGUMENT_LENGTH = 1;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String NO_TASKS_WITH_TAG_MESSAGE = createError("No tasks with the specified tag are found.");

    /**
     * Instantiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public TaggedWithCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }


    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {
        String targetTag = commandArguments[0];

        List<Task> tasksWithTag = getProcastinot().getTasksWithTag(targetTag);
        
        if (tasksWithTag.isEmpty()) {
            System.err.println(NO_TASKS_WITH_TAG_MESSAGE);
            return;
        }

        for (Task task : tasksWithTag) {
            if (task.getParentTask() == null) {
                task.showTaskAndSubtasks(task, "");
            }
        }
    }
}
