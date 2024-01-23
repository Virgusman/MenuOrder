import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class Order {


    //Хранение заказа (наименование блюда / количество)
    private final LinkedHashMap<String, Integer> orderItems = new LinkedHashMap<>();

    //Конструктор нового заказа
    public Order(String s) {
        orderItems.put(s, 1);
    }

    //добавляет блюдо в созданный заказ
    public void addItemToOrder(String s) {
        if (orderItems.containsKey(s)) {
            orderItems.put(s, orderItems.get(s) + 1);

        } else {
            orderItems.put(s, 1);
        }

    }

    //удаляет блюдо из заказа
    public void removeItemFromOrder(String s) {
        if (orderItems.containsKey(s)) {
            if (orderItems.get(s) == 1) {
                orderItems.remove(s);
            } else {
                orderItems.put(s, orderItems.get(s) - 1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Данного блюда нет в заказе!");
        }
    }

    //возвращает информацию о заказе (наименование блюда - количество - цена - Итого)
    public String printOrderDetails(Menu menu) {
        StringBuilder s = new StringBuilder();
        double summa = 0;
        for (HashMap.Entry<String, Integer> item : orderItems.entrySet()) {
            summa = summa + item.getValue() * menu.getCost(item.getKey());
            s.append(String.format("%s  : %s шт * %.2f руб. = %.2f руб.\n", item.getKey(), item.getValue(), menu.getCost(item.getKey()), item.getValue() * menu.getCost(item.getKey())));
        }
        s.append(String.format("\n Итого: %.2f руб.", summa));
        return s.toString();
    }
}
