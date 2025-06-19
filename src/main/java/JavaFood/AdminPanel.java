package JavaFood;

import Models.Discount;
import Models.Food;
import Models.Order;
import Models.Restaurant;
import Users.RestaurantOwner;
import Users.User;
import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class AdminPanel {
    public static LocalDate todayDate;
    public static ArrayList<Restaurant> restaurants = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Discount> discounts = new ArrayList<>();
    public static ArrayList<Order> orders = new ArrayList<>();

    public void loadFromJSONFile(String fileAddress) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void addDiscount(Discount discount) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void addUser(User user) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void addRestaurant(Restaurant restaurant) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Order createOrder(Integer id, Integer userId, Integer restaurant_id, Order.ReceivingType type) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void setDate(LocalDate todayDate) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Restaurant getBestRestaurant() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Restaurant getMostOrderedRestaurant() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
