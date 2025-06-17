package Models;

import JavaFood.AdminPanel;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void openRestaurant() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void closeRestaurant() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void addFood(Food food, Integer quantity) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public int getTodayOrdersCount() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public int getAllOrdersCount() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Double getTodayOrdersAmount() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Double getAllOrdersAmount() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void updateFoodQuantity(Food food, Integer quantity) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Food getMostOrderedFood() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
