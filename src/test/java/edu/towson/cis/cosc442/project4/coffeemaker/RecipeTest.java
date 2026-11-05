package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecipeTest {

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = new Recipe();
    }

    private Recipe createBlankRecipe(String name) {
        Recipe r = new Recipe();
        r.setName(name);
        return r;
    }

    @Test
    public void testDefaultValues() {
        assertNull(recipe.getName());
        assertEquals(0, recipe.getPrice());
        assertEquals(0, recipe.getAmtCoffee());
        assertEquals(0, recipe.getAmtMilk());
        assertEquals(0, recipe.getAmtSugar());
        assertEquals(0, recipe.getAmtChocolate());
    }

    // === name methods ===

    @Test
    public void testSetAndGetName() {
        recipe.setName("Mocha");
        assertEquals("Mocha", recipe.getName());
    }

    @Test
    public void testSetNameNull() {
        recipe.setName("Mocha");
        recipe.setName(null);
        assertNull(recipe.getName());
    }

    // === price methods ===

    @Test
    public void testSetPricePositive() {
        recipe.setPrice(50);
        assertEquals(50, recipe.getPrice());
    }

    @Test
    public void testSetPriceZero() {
        recipe.setPrice(50);
        recipe.setPrice(0);
        assertEquals(0, recipe.getPrice());
    }

    @Test
    public void testSetPriceNegativeDefaultsToZero() {
        recipe.setPrice(50);
        recipe.setPrice(-1);
        assertEquals(0, recipe.getPrice());
    }

    // === coffee methods ===

    @Test
    public void testSetAmtCoffeePositive() {
        recipe.setAmtCoffee(3);
        assertEquals(3, recipe.getAmtCoffee());
    }

    @Test
    public void testSetAmtCoffeeZero() {
        recipe.setAmtCoffee(3);
        recipe.setAmtCoffee(0);
        assertEquals(0, recipe.getAmtCoffee());
    }

    @Test
    public void testSetAmtCoffeeNegativeDefaultsToZero() {
        recipe.setAmtCoffee(3);
        recipe.setAmtCoffee(-5);
        assertEquals(0, recipe.getAmtCoffee());
    }

    // === milk methods ===

    @Test
    public void testSetAmtMilkPositive() {
        recipe.setAmtMilk(2);
        assertEquals(2, recipe.getAmtMilk());
    }

    @Test
    public void testSetAmtMilkZero() {
        recipe.setAmtMilk(2);
        recipe.setAmtMilk(0);
        assertEquals(0, recipe.getAmtMilk());
    }

    @Test
    public void testSetAmtMilkNegativeDefaultsToZero() {
        recipe.setAmtMilk(2);
        recipe.setAmtMilk(-5);
        assertEquals(0, recipe.getAmtMilk());
    }

    // === sugar methods ===

    @Test
    public void testSetAmtSugarPositive() {
        recipe.setAmtSugar(4);
        assertEquals(4, recipe.getAmtSugar());
    }

    @Test
    public void testSetAmtSugarZero() {
        recipe.setAmtSugar(4);
        recipe.setAmtSugar(0);
        assertEquals(0, recipe.getAmtSugar());
    }

    @Test
    public void testSetAmtSugarNegativeDefaultsToZero() {
        recipe.setAmtSugar(4);
        recipe.setAmtSugar(-5);
        assertEquals(0, recipe.getAmtSugar());
    }

    // === chocolate methods ===

    @Test
    public void testSetAmtChocolatePositive() {
        recipe.setAmtChocolate(6);
        assertEquals(6, recipe.getAmtChocolate());
    }

    @Test
    public void testSetAmtChocolateZero() {
        recipe.setAmtChocolate(6);
        recipe.setAmtChocolate(0);
        assertEquals(0, recipe.getAmtChocolate());
    }

    @Test
    public void testSetAmtChocolateNegativeDefaultsToZero() {
        recipe.setAmtChocolate(6);
        recipe.setAmtChocolate(-5);
        assertEquals(0, recipe.getAmtChocolate());
    }

    // ------------------------------------------------------------------ fields independent

    @Test
    public void testSettersDoNotAffectOtherFields() {
        recipe.setName("Latte");
        recipe.setPrice(1);
        recipe.setAmtCoffee(2);
        recipe.setAmtMilk(3);
        recipe.setAmtSugar(4);
        recipe.setAmtChocolate(5);

        assertEquals("Latte", recipe.getName());
        assertEquals(1, recipe.getPrice());
        assertEquals(2, recipe.getAmtCoffee());
        assertEquals(3, recipe.getAmtMilk());
        assertEquals(4, recipe.getAmtSugar());
        assertEquals(5, recipe.getAmtChocolate());
    }

    // ------------------------------------------------------------------ equals

    @Test
    public void testEqualsSameName() {
        assertTrue(createBlankRecipe("Coffee").equals(createBlankRecipe("Coffee")));
    }

    @Test
    public void testEqualsSameNameDifferentOtherFields() {
        Recipe a = createBlankRecipe("Coffee");
        a.setPrice(50);
        a.setAmtCoffee(3);
        Recipe b = createBlankRecipe("Coffee");
        b.setPrice(75);
        b.setAmtMilk(2);
        assertTrue(a.equals(b));
    }

    @Test
    public void testEqualsDifferentName() {
        assertFalse(createBlankRecipe("Coffee").equals(createBlankRecipe("Mocha")));
    }

    @Test
    public void testEqualsIsCaseSensitive() {
        assertFalse(createBlankRecipe("Coffee").equals(createBlankRecipe("coffee")));
    }

    @Test
    public void testEqualsThisNameNull() {
        assertFalse(new Recipe().equals(createBlankRecipe("Coffee")));
    }

    @Test
    public void testEqualsOtherNameNull() {
        assertFalse(createBlankRecipe("Coffee").equals(new Recipe()));
    }

    @Test
    public void testEqualsBothNamesNull() {
        assertFalse(new Recipe().equals(new Recipe()));
    }

    @Test
    public void testEqualsSelf() {
        Recipe r = createBlankRecipe("Coffee");
        assertTrue(r.equals(r));
    }

    @Test
    public void testEqualsIsSymmetric() {
        Recipe a = createBlankRecipe("Coffee");
        Recipe b = createBlankRecipe("Coffee");
        assertEquals(a.equals(b), b.equals(a));
    }

    // === toString ===

    @Test
    public void testToStringReturnsName() {
        assertEquals("Espresso", createBlankRecipe("Espresso").toString());
    }

    @Test
    public void testToStringDefaultIsNull() {
        assertNull(new Recipe().toString());
    }
}