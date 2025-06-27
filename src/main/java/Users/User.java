package Users;

import JavaFood.AdminPanel;
import Models.Order;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

@Data
public class User {
    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ArrayList<Order> getOrders() {
        return AdminPanel.orders
                .stream()
                .filter(order -> order.getUserId() == id)
                .collect(toCollection(ArrayList::new));
    }
}
