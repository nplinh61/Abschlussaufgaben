package edu.kit.kastel;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Main class that handles the whole Procastinot Application.
 *
 * @author Programmieren-Team
 * @version 1.0
 */
public final class Main {
    private Main() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    /**
     * Main entry point of the program.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        Procastinot procastinot = new Procastinot();
        CommandHandler commandHandler = new CommandHandler(procastinot);
        commandHandler.handleUserInput();
    }
}
