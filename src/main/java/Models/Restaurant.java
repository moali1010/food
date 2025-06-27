package Models;

import JavaFood.AdminPanel;
import com.google.gson.JsonObject;
import lombok.Data;

import java.time.LocalDate;
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

    public Restaurant(Integer id, String name, String address, int openHour, int closeHour, RestaurantTypes type) {
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
                .filter(order -> order.getRestaurant().id.equals(id)
                        && order.getOrderDateTime().toLocalDate().equals(AdminPanel.todayDate))
                .count();
    }

    public int getAllOrdersCount() {
        return (int) AdminPanel.orders.stream()
                .filter(order -> order.getRestaurant().id.equals(id))
                .count();
    }

    public Double getTodayOrdersAmount() {
        return AdminPanel.orders.stream()
                .filter(order -> order.getRestaurant().id.equals(id)
                        && order.getOrderDateTime().toLocalDate().equals(AdminPanel.todayDate))
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    public Double getAllOrdersAmount() {
        return AdminPanel.orders.stream()
                .filter(order -> order.getRestaurant().id.equals(id))
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    public void updateFoodQuantity(Food food, Integer quantity) {
        foods.put(food, quantity);
    }

    public Food getMostOrderedFood() {
        HashMap<Food, Integer> orderedFoods = new HashMap<>();
        for (Order order : AdminPanel.orders) {
            for (Food food : order.getFoods().keySet()) {
                if (order.getRestaurant().id.equals(this.id)) {
                    if (orderedFoods.containsKey(food)) {
                        orderedFoods.put(food, orderedFoods.get(food)
                                + order.getFoods().get(food));
                    } else {
                        orderedFoods.put(food, order.getFoods().get(food));
                    }
                }
            }
        }
        Food mostOrdered = null;
        int maxCount = 0;
        for (Map.Entry<Food, Integer> entry : orderedFoods.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostOrdered = entry.getKey();
            }
        }
        return mostOrdered;
    }

}
