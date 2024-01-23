import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.HashMap;
import org.apache.log4j.Logger;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class Gui {
    // Инициализация логера
    private static final Logger log = Logger.getLogger(Order.class);
    private int costOrders = 1;  //Номера заказов
    private static Menu menu;
    private final HashMap<String, Order> orders = new HashMap<>();  //Хранение заказов

    //Загрузка меню (Наименование - цена)
    static {
        try {
            menu = new Menu();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        //Отрисовка основого окна
        JFrame window = new JFrame("TEST");
        window.setTitle("Система учета заказов");
        window.setBounds(350, 220, 860, 550);
        window.setLayout(null);
        window.setResizable(false);

        //Добавление поля с меню
        JLabel label1 = new JLabel("Меню:");
        label1.setBounds(5, -12, 250, 50);
        window.add(label1);
        JList<String> menuList = new JList<>(menu.getMenu());
        menuList.setSelectionMode(SINGLE_SELECTION);
        menuList.setBounds(5, 25, 150, 470);
        window.add(menuList);

        //Добавление поля с заказами
        JLabel label2 = new JLabel("Заказы:");
        label2.setBounds(340, -12, 250, 50);
        window.add(label2);
        var listModel = new DefaultListModel<String>();
        JList<String> orderList = new JList<>(listModel);
        orderList.setSelectionMode(SINGLE_SELECTION);
        orderList.setBounds(340, 25, 150, 470);
        window.add(orderList);

        //Добавления поля с описанием заказа
        JLabel label3 = new JLabel("О заказе:");
        label3.setBounds(510, -12, 250, 50);
        window.add(label3);
        JTextArea text = new JTextArea();
        text.setBounds(507, 25, 330, 470);
        window.add(text);

        //Добавление кнопок
        JButton button1 = new JButton(">> Добавить к заказу");
        button1.setBounds(170, 155, 150, 40);
        window.add(button1);
        JButton button2 = new JButton("<< Убрать из заказа");
        button2.setBounds(170, 205, 150, 40);
        window.add(button2);
        JButton button3 = new JButton(">> В новый заказ");
        button3.setBounds(170, 105, 150, 40);
        window.add(button3);
        JButton button4 = new JButton("X Удалить заказ");
        button4.setBounds(170, 255, 150, 40);
        window.add(button4);

        //Клик по кнопке "В новый заказ"
        button3.addActionListener(e -> {
            if (menuList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Не выбрано блюдо для заказа");
            } else {
                orders.put("Заказ " + costOrders, new Order(menuList.getSelectedValue()));
                listModel.addElement("Заказ " + costOrders++);
                orderList.setSelectedIndex(orders.size() - 1);
                text.setText(orders.get("Заказ " + (costOrders - 1)).printOrderDetails(menu));
            }

        });

        //Клик по кнопке "Добавить к заказу"
        button1.addActionListener(e -> {
            if (orderList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Нет заказов для добавления!");
            } else {
                log.info(String.format("В %s добавлено блюдо %s", orderList.getSelectedValue(), menuList.getSelectedValue()));
                orders.get(orderList.getSelectedValue()).addItemToOrder(menuList.getSelectedValue());
                text.setText(orders.get(orderList.getSelectedValue()).printOrderDetails(menu));
            }
        });

        //Клик по кнопке "Убрать из заказа"
        button2.addActionListener(e -> {
            if (orderList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Нет заказов для удаления!");
            } else {
                log.info(String.format("Из %s удалено блюдо %s", orderList.getSelectedValue(), menuList.getSelectedValue()));
                orders.get(orderList.getSelectedValue()).removeItemFromOrder(menuList.getSelectedValue());
                text.setText(orders.get(orderList.getSelectedValue()).printOrderDetails(menu));
            }
        });

        //Клик по кнопке "Удалить заказ"
        button4.addActionListener(e -> {
            if (orderList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Нет заказов для удаления!");
            } else {
                orders.remove(orderList.getSelectedValue());
                text.setText("");
                listModel.removeElement(orderList.getSelectedValue());
            }
        });

        //Выбор в поле заказов -> вывод информации о заказе
        orderList.addListSelectionListener(e -> {
            if (!orderList.isSelectionEmpty()) {
                text.setText(orders.get(orderList.getSelectedValue()).printOrderDetails(menu));
            }
        });

        window.setVisible(true);

    }
}
