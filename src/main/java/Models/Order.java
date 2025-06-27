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
    public void addFoodToOrder(Integer foodId, int orderQuantity) {
        int num = -1;
        Food foundFood = null;
        for (Map.Entry<Food, Integer> entry : restaurant.foods.entrySet()) {
            if (entry.getKey().getId().equals(foodId)) {
                foundFood = entry.getKey();
                num = entry.getValue();
                break;
            }
        }
        if (foundFood == null) {
            throw new FoodNotFound();
        }
        if (orderQuantity > num) {
            throw new FoodOutOfStock();
        }
        if (restaurant.getIsOpen() == false) {
            throw new RestaurantIsClose();
        }
        foods.put(foundFood, orderQuantity);
        restaurant.foods.put(foundFood, num - orderQuantity);
        totalPrice += foundFood.getPrice() * orderQuantity;
    }

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

    public void addDiscount(String code) {
        Discount foundDiscount = null;
        for (Discount discount : AdminPanel.discounts) {
            if (discount.getCode().equals(code)) {
                foundDiscount = discount;
                break;
            }
        }
        if (foundDiscount == null) {
            throw new InvalidDiscountCode("Discount code not found");
        }
        if (foundDiscount.getIsUsed()) {
            throw new InvalidDiscountCode("Discount code already used");
        }
        if (foundDiscount.getExpireDate().isBefore(LocalDate.now())) {
            throw new InvalidDiscountCode("Discount is over");
        }
        if (!foundDiscount.getUserId().equals(userId)) {
            throw new InvalidDiscountCode("This discount is not for you");
        }
        if (foundDiscount.getDiscountType() == Discount.DiscountType.AMOUNT) {
            discountPrice += foundDiscount.getAmount();
            totalPrice = totalPrice - discountPrice;
        } else {
            discountPrice += totalPrice * foundDiscount.getPercentage() / 100;
            totalPrice = totalPrice - discountPrice;
        }
        foundDiscount.setIsUsed(true);
    }

    public void pay(Double amount) {
        /*
            Year/Month/Day Hour:Minute
        */
        // orderDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        if (!totalPrice.equals(amount)) {
            throw new PayException();
        }
        isPaid = true;
        orderDateTime = LocalDateTime.of(AdminPanel.todayDate, LocalTime.now());
        orderDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        AdminPanel.orders.add(this);
    }

    public void scoreOrder(Integer score) {
        if (isScored) {
            throw new InvalidScore("Already scored");
        }
        if (!isPaid) {
            throw new InvalidScore("Not paid yet");
        }
        if (score < 1 || score > 5) {
            throw new InvalidScore("Invalid score");
        }
        this.score = score;
        isScored = true;

        double totalScore = restaurant.getScore() * restaurant.getScoreCounts() + score;
        restaurant.setScoreCounts(restaurant.getScoreCounts() + 1);
        restaurant.setScore(totalScore / restaurant.getScoreCounts());
    }

    public enum ReceivingType {
        IN_PERSON,
        COURIER
    }
}
