package Models;

import JavaFood.AdminPanel;
import com.google.gson.JsonObject;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Restaurant {
    public enum RestaurantTypes {
        FASTFOOD,
        IRANI,
        VEGETARIAN,
        KEBAB,
        SALAD,
        CAFE,
        SUPERMARKET,
        COFFEE
    }

    private RestaurantTypes type;
    private Integer id;
    private String name;
    private Double score = 0.0;
    private Integer scoreCounts = 0;
    private String address;
    int openHour, closeHour;
    // Food - quantity
    final LinkedHashMap<Food, Integer> foods = new LinkedHashMap<>();
    private Boolean isOpen = false;

    public Restaurant(Integer id, String name, String address,
                      int openHour, int closeHour, RestaurantTypes type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.type = type;
    }

    public void openRestaurant() {
        isOpen = true;
    }

    public void closeRestaurant() {
        isOpen = false;
    }

    public void addFood(Food food, Integer quantity) {
        foods.put(food, quantity);
    }

    public int getTodayOrdersCount() {
        return (int) AdminPanel.orders.stream()
                .filter(order ->
                        order.getOrderDateTime().getDayOfYear() ==
                                LocalDateTime.now().getDayOfYear())
                .count();
    }

    public int getAllOrdersCount() {
        return AdminPanel.orders.size();
    }

    public Double getTodayOrdersAmount() {
        return AdminPanel.orders.stream()
                .filter(order ->
                        order.getOrderDateTime().getDayOfYear() ==
                                LocalDateTime.now().getDayOfYear())
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    public Double getAllOrdersAmount() {
        return AdminPanel.orders.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    public void updateFoodQuantity(Food food, Integer quantity) {
        foods.put(food, quantity);
    }

    public Food getMostOrderedFood() {
        HashMap<Food, Integer> orderedFoods = new HashMap<>();
        for (Order order : AdminPanel.orders) {
            for (Food food : foods.keySet()) {
                if (order.getFoods().containsKey(food)) {
                    if (orderedFoods.containsKey(food)) {
                        orderedFoods.put(food, orderedFoods.get(food)
                                + order.getFoods().get(food));
                    } else {
                        orderedFoods.put(food, order.getFoods().get(food));
                    }
                }
            }
        }
        return orderedFoods.entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
