package org.blbulyandavbulyan.simplestore.commands;

import org.blbulyandavbulyan.simplestore.commands.exceptions.IllegalArgumentCountException;
import org.blbulyandavbulyan.simplestore.commands.exceptions.IllegalArgumentForCommandException;
import org.blbulyandavbulyan.simplestore.commands.exceptions.IllegalCommandException;
import org.blbulyandavbulyan.simplestore.entites.Product;
import org.blbulyandavbulyan.simplestore.services.interfaces.IStore;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Данный класс предоставляет обработчик текстовых команд для IStore
 */
public class Processor {

    private final IStore iStore;
    private final PrintStream ps;

    public Processor(IStore iStore, PrintStream ps) {
        this.iStore = iStore;
        this.ps = ps;
    }

    public void processCommand(String commandForParsing) {
        String[] splitCommand = Arrays.stream(commandForParsing.split(" ")).filter(s -> !s.isBlank()).toArray(String[]::new);
        String action = splitCommand[0];
        switch (action) {
            case "/showProductsByConsumer" -> {
                throwIfInvalidLength(splitCommand, 2);
                var boughtProducts = iStore.getBoughtProductsByConsumerName(splitCommand[1]);
                ps.println(boughtProducts);
            }
            case "/showConsumersByProductTitle" -> {
                throwIfInvalidLength(splitCommand, 2);
                var consumers = iStore.getConsumersByProductTitle(splitCommand[1]);
                ps.println(consumers);
            }
            case "/deleteConsumer" -> {
                throwIfInvalidLength(splitCommand, 2);
//                ps.println(iStore.deleteConsumer(splitCommand[1]) ? "удалено" : "запись не найдена");
                // FIXME: 07.07.2023 исправить это, тут нужно прокинуть соотвествующий репозиторий
            }
            case "/deleteProduct" -> {
                throwIfInvalidLength(splitCommand, 2);
//                ps.println(iStore.deleteProduct(splitCommand[1]) ? "удалено" : "запись не найдена");
                // FIXME: 07.07.2023 исправить это, тут нужно прокинуть соотвествующий репозиторий
            }
            case "/buy" -> {
                throwIfInvalidLength(splitCommand, 3);
                Long consumerId = getLongFromStringOrThrow(splitCommand[1], () -> new IllegalArgumentForCommandException("ИД потребителя должно быть число типа long!"));
                Long productId = getLongFromStringOrThrow(splitCommand[2], () -> new IllegalArgumentForCommandException("ИД продукта должно быть число типа long!"));
                var boughtProduct = iStore.buy(consumerId, productId);
                Product product = boughtProduct.getProduct();
                ps.printf("Пользователь %s купил продукт %s по цене %d\n", boughtProduct.getConsumer().getName(), product.getTitle(), product.getPrice());
            }
            default -> throw new IllegalCommandException("неверная команда " + action);
        }
    }

    private void throwIfInvalidLength(String[] arr, int requiredLength) {
        if (arr.length != requiredLength)
            throw new IllegalArgumentCountException("Неверное количество частей команды, должно быть " + requiredLength);
    }

    private <T extends Throwable> Long getLongFromStringOrThrow(String l, Supplier<T> throwableSupplier) throws T {
        try {
            return Long.parseLong(l);
        } catch (NumberFormatException e) {
            throw throwableSupplier.get();
        }
    }
}
