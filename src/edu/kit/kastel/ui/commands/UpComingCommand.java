package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcastinotCommand;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Command to show all the tasks that due on the next seven days,
 * starting from a certain date.
 *
 * @author ukgot
 * @version 1.0
 */
public class UpComingCommand extends ProcastinotCommand {
    private static final String COMMAND_NAME = "upcoming";
    private static final int EXPECTED_ARGUMENT_LENGTH = 1;
    private static final int NUMBER_OPTIONAL_ARGUMENTS = 0;
    private static final String NO_UPCOMING_TASK_MESSAGE = createError("No upcoming tasks are found.");

    /**
     * Initiates a new command.
     *
     * @param commandHandler the command handler for this command
     * @param procastinot the procastinot application
     */
    public UpComingCommand(CommandHandler commandHandler, Procastinot procastinot) {
        super(COMMAND_NAME, commandHandler, procastinot, EXPECTED_ARGUMENT_LENGTH, NUMBER_OPTIONAL_ARGUMENTS);
    }


    @Override
    protected void executeProcastinotCommand(String[] commandArguments) {
        Optional<LocalDate> targetDate = tryParsingDate(commandArguments[0]);
        if (targetDate.isPresent()) {
            LocalDate startDate = targetDate.get();
            LocalDate endDate = startDate.plusDays(6);

            List<Task> upcomingTasks = new ArrayList<>();

            for (Task task : getProcastinot().getTasks()) {
                task.getUpcomingTasks(task, upcomingTasks, startDate, endDate);
            }

            if (upcomingTasks.isEmpty()) {
                System.err.println(NO_UPCOMING_TASK_MESSAGE);
            } else {
                for (Task task : upcomingTasks) {

                    if (!task.hasParentTaskInList(upcomingTasks)) {
                        task.showTaskAndSubtasks(task, "");
                    }
                }
            }

        }




    }
}
