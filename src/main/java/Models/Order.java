package Models;

import Exceptions.*;
import JavaFood.AdminPanel;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Data
public class Order {
    private Integer id;
    // Food - quantity
    private final HashMap<Food, Integer> foods = new HashMap<>();
    private Double discountPrice = 0.0;
    private Integer userId;
    private Restaurant restaurant;
    private LocalDateTime orderDateTime;
    private Double totalPrice = 0.0;
    private ReceivingType receivingType;
    private Boolean isPaid = false, isScored = false;
    private Integer score;

    public Order(Integer id, Integer userId, Restaurant restaurant, ReceivingType receivingType) {
        this.id = id;
        this.userId = userId;
        this.restaurant = restaurant;
        this.receivingType = receivingType;
    }

    public enum ReceivingType {
        IN_PERSON,
        COURIER
    }

    public void addFoodToOrder(Integer foodId, int orderQuantity) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void addDiscount(String code) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void pay(Double amount) {
        /*
            Year/Month/Day Hour:Minute
        */
        orderDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void scoreOrder(Integer score) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
