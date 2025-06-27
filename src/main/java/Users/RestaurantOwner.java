package Users;

import Models.Restaurant;

import java.util.ArrayList;

public class RestaurantOwner extends User {
    public ArrayList<Restaurant> restaurants;

    public RestaurantOwner(int id, String name) {
        super(id, name);
        this.restaurants = new ArrayList<>();
    }
}
