package Users;

import Models.Restaurant;

import java.util.ArrayList;

public class RestaurantOwner extends User {
    public ArrayList<Restaurant> restaurants = new ArrayList<>();

    public RestaurantOwner(int id, String name) {
        super(id, name);
    }
}
