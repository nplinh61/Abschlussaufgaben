/*
 * Copyright (c) 2023, KASTEL. All rights reserved.
 */

package edu.kit.kastel.ui;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.ui.commands.AddCommand;
import edu.kit.kastel.ui.commands.AddListCommand;
import edu.kit.kastel.ui.commands.AssignTaskCommand;
import edu.kit.kastel.ui.commands.AssignListCommand;
import edu.kit.kastel.ui.commands.BeforeCommand;
import edu.kit.kastel.ui.commands.BetweenCommand;
import edu.kit.kastel.ui.commands.ChangeDateCommand;
import edu.kit.kastel.ui.commands.ChangePriorityCommand;
import edu.kit.kastel.ui.commands.DuplicatesCommand;
import edu.kit.kastel.ui.commands.DeleteCommand;
import edu.kit.kastel.ui.commands.FindCommand;
import edu.kit.kastel.ui.commands.ListCommand;
import edu.kit.kastel.ui.commands.RestoreCommand;
import edu.kit.kastel.ui.commands.ShowCommand;
import edu.kit.kastel.ui.commands.TaggedWithCommand;
import edu.kit.kastel.ui.commands.ToDoCommand;
import edu.kit.kastel.ui.commands.TagCommand;
import edu.kit.kastel.ui.commands.ToggleCommand;
import edu.kit.kastel.ui.commands.UpComingCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;


/**
 * This class handles the user input and executes the commands.
 *
 * @author Programmieren-Team
 * @version 1.0
 */
public final class CommandHandler {
    private static final String COMMAND_SEPARATOR_REGEX = "\\s+";
    private static final String COMMAND_NOT_FOUND = "ERROR: Command '%s' not found%n";

    private final Procastinot procastinot;
    private final Map<String, Command> commands;
    private boolean running = false;

    /**
     * Constructs a new CommandHandler.
     *
     * @param procastinot the santorini game that this instance manages
     */
    public CommandHandler(Procastinot procastinot) {
        this.procastinot = Objects.requireNonNull(procastinot);
        this.commands = new HashMap<>();
        this.initCommands();
    }

    /**
     * Starts the interaction with the user.
     */
    public void handleUserInput() {
        this.running = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while (running && scanner.hasNextLine()) {
                executeCommand(scanner.nextLine());
            }
        }
    }

    /**
     * Quits the interaction with the user.
     */
    public void quit() {
        this.running = false;
    }

    private void executeCommand(String commandWithArguments) {
        String[] splitCommand = commandWithArguments.trim().split(COMMAND_SEPARATOR_REGEX);
        String commandName = splitCommand[0];
        String[] commandArguments = Arrays.copyOfRange(splitCommand, 1, splitCommand.length);

        if (!commands.containsKey(commandName)) {
            System.err.printf(COMMAND_NOT_FOUND, commandName);
            return;
        }

        commands.get(commandName).execute(commandArguments);
    }

    private void initCommands() {
        this.addCommand(new AddCommand(this, procastinot));
        this.addCommand(new AddListCommand(this, procastinot));
        this.addCommand(new AssignListCommand(this, procastinot));
        this.addCommand(new AssignTaskCommand(this, procastinot));
        this.addCommand(new BeforeCommand(this, procastinot));
        this.addCommand(new BetweenCommand(this, procastinot));
        this.addCommand(new ChangeDateCommand(this, procastinot));
        this.addCommand(new ChangePriorityCommand(this, procastinot));
        this.addCommand(new QuitCommand(this, procastinot));
        this.addCommand(new FindCommand(this, procastinot));
        this.addCommand(new ListCommand(this, procastinot));
        this.addCommand(new RestoreCommand(this, procastinot));
        this.addCommand(new DeleteCommand(this, procastinot));
        this.addCommand(new DuplicatesCommand(this, procastinot));
        this.addCommand(new ShowCommand(this, procastinot));
        this.addCommand(new TaggedWithCommand(this, procastinot));
        this.addCommand(new TagCommand(this, procastinot));
        this.addCommand(new ToDoCommand(this, procastinot));
        this.addCommand(new ToggleCommand(this, procastinot));
        this.addCommand(new UpComingCommand(this, procastinot));
    }

    private void addCommand(Command command) {
        this.commands.put(command.getCommandName(), command);
    }
}
