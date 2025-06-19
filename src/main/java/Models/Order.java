package Models;

import Exceptions.*;
import JavaFood.AdminPanel;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        Optional<Map.Entry<Food, Integer>> first = restaurant.foods.entrySet().stream()
                .filter(entry -> entry.getKey().getId().equals(foodId))
                .findFirst();
        if (first.isEmpty()) {
            throw new FoodNotFound();
        }
        if (first.get().getValue() < orderQuantity) {
            throw new FoodOutOfStock();
        }
        if (!restaurant.getIsOpen()) {
            throw new RestaurantIsClose();
        }
        foods.put(first.get().getKey(), orderQuantity);
        totalPrice += orderQuantity * foods.get(first.get().getKey());
    }

    public void addDiscount(String code) {
        Optional<Discount> first = AdminPanel.discounts.stream()
                .filter(discount -> discount.getCode().equals(code)).findFirst();
        if (first.isEmpty()) {
            throw new InvalidDiscountCode("Discount code not found");
        }
        if (first.get().getIsUsed()) {
            throw new InvalidDiscountCode("Discount code already");
        }
        if (first.get().getExpireDate().getDayOfYear() > LocalDate.now().getDayOfYear()) {
            throw new InvalidDiscountCode("Discount is over");
        }
        if (!first.get().getUserId().equals(userId)) {
            throw new InvalidDiscountCode("This discount is not for you");
        }
    }

    public void pay(Double amount) {
        /*
            Year/Month/Day Hour:Minute
        */
        orderDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        if (!amount.equals(totalPrice)) {
            throw new PayException();
        }
        if (isPaid) {
            orderDateTime = AdminPanel.todayDate.atStartOfDay();
        }
    }

    public void scoreOrder(Integer score) {
        if (isScored) {
            throw new InvalidScore("Already scored");
        }
        if (!isPaid) {
            throw new InvalidScore("Not paid yet");
        }
        if (score > 5 || score < 1) {
            throw new InvalidScore("Invalid score");
        }
        this.score = score;
    }
}
