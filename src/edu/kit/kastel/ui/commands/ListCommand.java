package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.model.TaskList;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Command to show all the tasks that belong to one required list.
 *
 * @author ukgot
 * @version 1.0
 */
public class ListCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "list";
    private static final int EXPECTED_ARGUMENT_LENGTH = 1;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String NO_TASK_IN_LIST_MESSAGE = "No tasks in list are found.";
    private static final String LIST_CONTAINS_NO_TASK_MESSAGE = createError("List contains no tasks.");

    /**
     * Initiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public ListCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }


    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {

        String targetList = commandArguments[0];
        TaskList taskList = getProcastinot().getListByName(targetList);
        if (taskList == null) {
            System.err.println(LIST_CONTAINS_NO_TASK_MESSAGE);
            return;
        }

        List<Task> tasksOfList = taskList.getTasks();

        if (tasksOfList.isEmpty()) {
            System.out.println(NO_TASK_IN_LIST_MESSAGE);
            return;
        }

        Set<Task> alreadyPrintedTasks = new HashSet<>();

        for (Task task : tasksOfList) {
            if (!alreadyPrintedTasks.contains(task)) {
                task.showTaskAndSubtasks(task, "");
                alreadyPrintedTasks.add(task);
            }
        }

    }
}
