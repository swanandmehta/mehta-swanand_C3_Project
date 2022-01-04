import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Spy
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant = new Restaurant("R1", "Location", LocalTime.NOON, LocalTime.of(23, 30));
        restaurant = Mockito.spy(restaurant);
        Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.of(14, 0));
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurant = new Restaurant("R1", "Location", LocalTime.NOON, LocalTime.of(23, 30));
        restaurant = Mockito.spy(restaurant);
        Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.of(11, 0));
        assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant = getRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }



    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant = getRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant = getRestaurant();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void providing_single_item_name_should_return_cost_of_single_item() {
        restaurant = getRestaurant();
        int cost = restaurant.getOrderCost("Sweet corn soup");
        assertEquals(cost, 119);
    }

    @Test
    public void providing_multiple_item_names_should_return_order_value() {
        restaurant = getRestaurant();
        int cost = restaurant.getOrderCost("Sweet corn soup", "Vegetable lasagne");
        assertEquals(cost, (119 + 269));
    }

    @Test
    public void providing_0_item_names_should_return_0() {
        restaurant = getRestaurant();
        int cost = restaurant.getOrderCost();
        assertEquals(cost, 0);
    }

    @Test
    public void providing_invalid_item_names_should_throw_exception() {
        restaurant = getRestaurant();
        assertThrows(itemNotFoundException.class, () -> {
            restaurant.getOrderCost("ANCD", "XYZ");
        });
    }

    private Restaurant getRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        return restaurant;
    }
}
