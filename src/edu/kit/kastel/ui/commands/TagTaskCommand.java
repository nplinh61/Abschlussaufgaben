package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.CommandHandler;


/**
 * Command to mark a task with a tag.
 *
 * @author ukgot
 * @version 1.0
 */
public class TagTaskCommand extends TagCommand {

    private static final String ID_NOT_FOUND_FORMAT = createError("Task with Id %d not found %s%n");
    private static final String TAG_SUCCESS_MESSAGE = "tagged %s with %s";
    private static final String TAG_ALREADY_EXISTED_MESSAGE = "Task %s already has the tag %s";
    private static final String INVALID_ID_MESSAGE = createError("Invalid task Id. Please input again.");

    protected TagTaskCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(commandHandler, procastinot);
    }


    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {

        int taskId;
        try {
            taskId = Integer.parseInt(commandArguments[0]);
        } catch (NumberFormatException e) {
            System.err.println(INVALID_ID_MESSAGE);
            return;
        }

        String tag = commandArguments[1];
        Task taggedTask = procastinot.getTaskById(taskId);
        if (taggedTask == null) {
            System.out.printf(ID_NOT_FOUND_FORMAT, taskId);
            return;
        }

        boolean success = taggedTask.addTag(tag);
        if (success) {
            System.out.printf(TAG_SUCCESS_MESSAGE, taggedTask.getName(), tag);
        } else {
            System.out.printf(TAG_ALREADY_EXISTED_MESSAGE, taggedTask.getName(), tag);
        }
    }

}

