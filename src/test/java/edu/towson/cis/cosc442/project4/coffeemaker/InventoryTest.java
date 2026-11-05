package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InventoryTest {
    private Inventory inventory;

    @BeforeEach 
    public void setUp() {
        inventory = new Inventory();
    }

    private Recipe recipe(int coffee, int milk, int sugar, int chocolate) {
        Recipe r = new Recipe();
        r.setName("Test");
        r.setAmtCoffee(coffee);
        r.setAmtMilk(milk);
        r.setAmtSugar(sugar);
        r.setAmtChocolate(chocolate);
        return r;
    }

    @Test
    public void testDefaultAmountsAreFifteen() {
        assertEquals(15, inventory.getCoffee());
        assertEquals(15, inventory.getMilk());
        assertEquals(15, inventory.getSugar());
        assertEquals(15, inventory.getChocolate());
    }

    // === coffee methods ===

    @Test
    public void testSetCoffeePositive() {
        inventory.setCoffee(30);
        assertEquals(30, inventory.getCoffee());
    }

    @Test
    public void testSetCoffeeZero() {
        inventory.setCoffee(0);
        assertEquals(0, inventory.getCoffee());
    }

    @Test
    public void testSetCoffeeNegativeClampsToZero() {
        inventory.setCoffee(-1);
        assertEquals(0, inventory.getCoffee());
    }

    // === milk methods ===

    @Test
    public void testSetMilkPositive() {
        inventory.setMilk(30);
        assertEquals(30, inventory.getMilk());
    }

    @Test
    public void testSetMilkZero() {
        inventory.setMilk(0);
        assertEquals(0, inventory.getMilk());
    }

    @Test
    public void testSetMilkNegativeClampsToZero() {
        inventory.setMilk(-1);
        assertEquals(0, inventory.getMilk());
    }

    // === sugar methods ===

    @Test
    public void testSetSugarPositive() {
        inventory.setSugar(30);
        assertEquals(30, inventory.getSugar());
    }

    @Test
    public void testSetSugarZero() {
        inventory.setSugar(0);
        assertEquals(0, inventory.getSugar());
    }

    @Test
    public void testSetSugarNegativeClampsToZero() {
        inventory.setSugar(-1);
        assertEquals(0, inventory.getSugar());
    }

    // === chocolate methods ===

    @Test
    public void testSetChocolatePositive() {
        inventory.setChocolate(30);
        assertEquals(30, inventory.getChocolate());
    }

    @Test
    public void testSetChocolateZero() {
        inventory.setChocolate(0);
        assertEquals(0, inventory.getChocolate());
    }

    @Test
    public void testSetChocolateNegativeClampsToZero() {
        inventory.setChocolate(-1);
        assertEquals(0, inventory.getChocolate());
    }

    @Test
    public void testSettersAreIndependent() {
        inventory.setCoffee(1);
        inventory.setMilk(2);
        inventory.setSugar(3);
        inventory.setChocolate(4);
        assertEquals(1, inventory.getCoffee());
        assertEquals(2, inventory.getMilk());
        assertEquals(3, inventory.getSugar());
        assertEquals(4, inventory.getChocolate());
    }

    // /**
    //  * Inventory stores its quantities in static fields, so every instance
    //  * shares the same state. Each Inventory should hold its own quantities.
    //  */
    // @Test
    // public void testInstancesAreIndependent() {
    //     Inventory other = new Inventory();
    //     inventory.setCoffee(5);
    //     assertEquals(15, other.getCoffee());
    // }

    // /** Constructing a new Inventory should not disturb an existing one. */
    // @Test
    // public void testNewInstanceDoesNotResetExistingInstance() {
    //     inventory.setMilk(3);
    //     new Inventory();
    //     assertEquals(3, inventory.getMilk());
    // }

    // === enoughIngredients tests === 

    @Test
    public void testEnoughIngredientsWellWithinStock() {
        assertTrue(inventory.enoughIngredients(recipe(1, 1, 1, 1)));
    }

    @Test
    public void testEnoughIngredientsExactlyEqualStock() {
        assertTrue(inventory.enoughIngredients(recipe(15, 15, 15, 15)));
    }

    @Test
    public void testEnoughIngredientsEmptyRecipe() {
        assertTrue(inventory.enoughIngredients(new Recipe()));
    }

    @Test
    public void testEnoughIngredientsEmptyInventoryEmptyRecipe() {
        inventory.setCoffee(0);
        inventory.setMilk(0);
        inventory.setSugar(0);
        inventory.setChocolate(0);
        assertTrue(inventory.enoughIngredients(new Recipe()));
    }

    @Test
    public void testNotEnoughCoffee() {
        assertFalse(inventory.enoughIngredients(recipe(16, 0, 0, 0)));
    }

    @Test
    public void testNotEnoughMilk() {
        assertFalse(inventory.enoughIngredients(recipe(0, 16, 0, 0)));
    }

    @Test
    public void testNotEnoughSugar() {
        assertFalse(inventory.enoughIngredients(recipe(0, 0, 16, 0)));
    }

    @Test
    public void testNotEnoughChocolate() {
        assertFalse(inventory.enoughIngredients(recipe(0, 0, 0, 16)));
    }

    @Test
    public void testNotEnoughWhenAllShort() {
        assertFalse(inventory.enoughIngredients(recipe(16, 16, 16, 16)));
    }

    @Test
    public void testEnoughIngredientsAfterDepletingStock() {
        inventory.setCoffee(2);
        assertTrue(inventory.enoughIngredients(recipe(2, 0, 0, 0)));
        assertFalse(inventory.enoughIngredients(recipe(3, 0, 0, 0)));
    }

    @Test
    public void testEnoughIngredientsDoesNotModifyInventory() {
        inventory.enoughIngredients(recipe(5, 5, 5, 5));
        assertEquals(15, inventory.getCoffee());
        assertEquals(15, inventory.getMilk());
        assertEquals(15, inventory.getSugar());
        assertEquals(15, inventory.getChocolate());
    }

    // === toString tests ===

    @Test
    public void testToStringDefault() {
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", inventory.toString());
    }

    @Test
    public void testToStringReflectsChanges() {
        inventory.setCoffee(1);
        inventory.setMilk(2);
        inventory.setSugar(3);
        inventory.setChocolate(4);
        assertEquals("Coffee: 1\nMilk: 2\nSugar: 3\nChocolate: 4\n", inventory.toString());
    }
}