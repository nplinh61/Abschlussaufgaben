package edu.kit.kastel;

import edu.kit.kastel.model.Procastinot;
import edu.kit.kastel.ui.CommandHandler;

public class Main {
    public static void main(String[] args) {
        Procastinot carSharingPlatform = new Procastinot();
        CommandHandler commandHandler = new CommandHandler(carSharingPlatform);
        commandHandler.handleUserInput();
    }
}
