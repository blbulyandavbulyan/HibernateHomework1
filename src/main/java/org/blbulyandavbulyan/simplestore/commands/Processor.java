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
    /**
     * Магазин, с которым будет взаимодействовать данный обработчик команд
     */
    private final IStore iStore;

    /**
     * Поток, куда данный обработчик команд будет печатать результат работы команд
     */
    private final PrintStream ps;

    /**
     * Создаёт экземпляр обработчика команд
     *
     * @param iStore магазин, с которым данный обработчик команд будет взаимодействовать
     * @param ps     поток, куда данный обработчик команд будет печатать результаты
     */
    public Processor(IStore iStore, PrintStream ps) {
        this.iStore = iStore;
        this.ps = ps;
    }

    /**
     * Метод обрабатывает переданную обработчику команду<br>
     * Доступные команды:<br>
     * <ul>
     *     <li>/showProductsByConsumer имя_покупателя - печатает в консоль товары, которые приобрел покупатель, по имени покупателя</li>
     *     <li>/showConsumersByProductTitle название_товара - печатает имена покупателей, купивших указанный товар, по названию товара</li>
     *     <li>/deleteConsumer имя_покупателя - удаляет из базы покупателей по имени</li>
     *     <li>/deleteProduct название_продукта - удаляет из базы товар по названию</li>
     *     <li>/buy id_покупателя id_товара - предоставляет возможность “покупки товара” по id покупателя и товара.</li>
     * </ul>
     *
     * @param commandForParsing команда, которую нужно обработать
     */
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
                ps.println(iStore.getConsumersRepository().deleteConsumer(splitCommand[1]) ? "удалено" : "запись не найдена");
            }
            case "/deleteProduct" -> {
                throwIfInvalidLength(splitCommand, 2);
                ps.println(iStore.getProductsRepository().deleteProduct(splitCommand[1]) ? "удалено" : "запись не найдена");
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

    /**
     * Данный метод проверяет длину массива на корректность
     * @param arr массив, длину которого нужно проверить
     * @param requiredLength необходимая длина
     * @throws IllegalArgumentCountException если длинна массива неверная
     */
    private void throwIfInvalidLength(String[] arr, int requiredLength) {
        if (arr.length != requiredLength)
            throw new IllegalArgumentCountException("Неверное количество частей команды, должно быть " + requiredLength);
    }

    /**
     * Данный метод пытается превратить строку в число Long
     * @param l строка, которую нужно преобразовать
     * @param throwableSupplier поставщик исключения, которое будет выброшено в случае если не удалось превратить строку в число
     * @return число типа Long, полученное из переданной строки
     * @param <T> тип исключения, которое будет выброшено из данного метода, полученное из throwableSupplier
     * @throws T в случае если не удалось преобразовать строку в число
     */
    private <T extends Throwable> Long getLongFromStringOrThrow(String l, Supplier<T> throwableSupplier) throws T {
        try {
            return Long.parseLong(l);
        } catch (NumberFormatException e) {
            throw throwableSupplier.get();
        }
    }
}
