package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Command to find tasks according to a string name.
 *
 * @author ukgot
 * @version 1.0
 */
public class FindCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "find";
    private static final int EXPECTED_ARGUMENT_LENGTH = 1;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String NO_TASK_WITH_NAME_PART_MESSAGE = createError("No tasks with this name part are found");

    /**
     * Initiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public FindCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }


    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {
        String targetNamePart = commandArguments[0];
        Set<Task> visitedTasks = new HashSet<>();
        List<Task> tasksWithNamePart = new ArrayList<>();

        for (Task task : getProcastinot().getTasks()) {
            tasksWithNamePart.addAll(getProcastinot().getTasksWithName(task, targetNamePart, visitedTasks));
        }

        if (tasksWithNamePart.isEmpty()) {
            System.err.println(NO_TASK_WITH_NAME_PART_MESSAGE);
        } else {
            for (Task task : tasksWithNamePart) {
                // Show task only if it doesn't have a parent task in the result list
                if (!task.hasParentTaskInList(tasksWithNamePart)) {
                    task.showTaskAndSubtasks(task, "");
                }
            }
        }
    }
}


