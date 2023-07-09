package org.blbulyandavbulyan.simplestore;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.commands.Processor;
import org.blbulyandavbulyan.simplestore.commands.exceptions.CommandProcessorException;
import org.blbulyandavbulyan.simplestore.services.interfaces.IStore;
import org.blbulyandavbulyan.simplestore.services.impl.Store;
import org.blbulyandavbulyan.simplestore.services.exceptions.StoreException;

import java.util.Scanner;

import static org.blbulyandavbulyan.simplestore.utils.ORMUtils.createEntityManagerFactory;
import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.createTables;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        try (EntityManagerFactory entityManagerFactory = createEntityManagerFactory()) {
            createTables(entityManagerFactory);
            IStore iStore = new Store(entityManagerFactory);
            Processor processor = new Processor(iStore, System.out);
            System.out.println("Введите команду>>");
            while (!(command = scanner.nextLine()).equals("/exit")) {
                try{
                    processor.processCommand(command);
                }
                catch (CommandProcessorException | StoreException e){
                    System.out.println(e.getMessage());
                }
                System.out.println("Введите команду>>");
            }
        }
    }
}
