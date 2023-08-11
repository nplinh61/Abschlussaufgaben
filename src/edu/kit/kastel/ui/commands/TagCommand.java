package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.model.TaskList;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;

/**
 * Command to add tag to whether a task or a list.
 *
 * @author ukgot
 * @version 1.0
 */
public class TagCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "tag";
    private static final String INPUT_ERROR_MESSAGE = createError("Number of arguments is invalid. Please check again.");
    private static final String LIST_NOT_FOUND_MESSAGE = createError("List with name %s not found.");
    private static final String TAG_SUCCESS_MESSAGE = "tagged %s with %s%n";
    private static final String TASK_NOT_FOUND_MESSAGE = createError("Task with id %s not found.");
    private static final String TAG_ALREADY_EXISTED_MESSAGE = createError("Tag %s already existed.");
    private static final int EXPECTED_ARGUMENT_LENGTH = 2;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;

    /**
     * Initiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public TagCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }

    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {
        if (commandArguments.length > 2) {
            System.err.println(INPUT_ERROR_MESSAGE);
            return;
        }

        String decideTag = commandArguments[0];
        String tag = commandArguments[1];
        if (isList(decideTag)) {
            tagList(decideTag, tag);
        } else {
            int taskId = Integer.parseInt(decideTag);
            tagTask(taskId, tag);
        }

    }
    private boolean isList(String str) {
        return str.matches("[a-zA-Z]+");
    }

    private void tagTask(int taskId, String tag) {
        Task taggedTask = getProcastinot().getTaskById(taskId);
        if (taggedTask == null) {
            System.out.printf(TASK_NOT_FOUND_MESSAGE, taskId);
            return;
        }

        boolean canAddTagToTask = taggedTask.addTag(tag);
        if (canAddTagToTask) {
            System.out.printf(TAG_SUCCESS_MESSAGE, taggedTask.getName(), tag);
        } else {
            System.out.printf(TAG_ALREADY_EXISTED_MESSAGE, tag);

        }


    }

    private void tagList(String listName, String tag) {
        TaskList taggedList = getProcastinot().getListByName(listName);
        if (taggedList == null) {
            System.out.printf(LIST_NOT_FOUND_MESSAGE, listName);
            return;
        }
        boolean canAddTagToList = taggedList.addTag(tag);
        if (canAddTagToList) {
            System.out.printf(TAG_SUCCESS_MESSAGE, taggedList.getName(), tag);
        } else {
            System.out.printf(TAG_ALREADY_EXISTED_MESSAGE, tag);
        }

    }

}
