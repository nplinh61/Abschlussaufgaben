package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;

import java.util.List;

/**
 * Command to show all the tasks that have not been yet done.
 *
 * @author ukgot
 * @version 1.0
 */
public class ToDoCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "todo";

    private static final int EXPECTED_ARGUMENT_LENGTH = 0;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String TASK_NOT_FOUND_MESSAGE = "Task with id %s not found.";
    private static final String NO_INCOMPLETE_TASK_MESSAGE = "No incomplete tasks.";

    public ToDoCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }


    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {

        List<Task> incompleteTask = getProcastinot().getIncompleteTasks();
        
        if (incompleteTask.isEmpty()) {
            System.out.println(NO_INCOMPLETE_TASK_MESSAGE);
        }

        for (Task task : incompleteTask) {
            if (task.getParentTask() == null) {
                task.showTaskAndSubtasks(task, "");
            }

        }

    }
}
