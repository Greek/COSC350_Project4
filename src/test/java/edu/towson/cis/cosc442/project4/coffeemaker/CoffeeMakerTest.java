package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoffeeMakerTest {
    private CoffeeMaker cm;
    private Recipe coffee;
    private Recipe mocha;
    private Recipe latte;
    private Recipe cappuccino;
    private Recipe extra;

    private static Recipe recipe(String name, int price, int coffee, int milk, int sugar, int chocolate) {
        Recipe r = new Recipe();
        r.setName(name);
        r.setPrice(price);
        r.setAmtCoffee(coffee);
        r.setAmtMilk(milk);
        r.setAmtSugar(sugar);
        r.setAmtChocolate(chocolate);
        return r;
    }

    // === Arrange by setting up recipes before each test ===

    @BeforeEach
    public void setUp() {
        cm = new CoffeeMaker();
        coffee = recipe("Coffee", 50, 3, 1, 1, 0);
        mocha = recipe("Mocha", 75, 2, 1, 1, 2);
        latte = recipe("Latte", 100, 2, 3, 1, 0);
        cappuccino = recipe("Cappuccino", 80, 2, 2, 0, 0);
        extra = recipe("Extra", 10, 1, 1, 1, 1);
    }

    private int countNamedRecipes() {
        int n = 0;
        for (Recipe r : cm.getRecipes()) {
            if (r.getName() != null) {
                n++;
            }
        }
        return n;
    }

    // === Constructor ===

    @Test
    public void testConstructorCreatesFourEmptyRecipes() {
        Recipe[] recipes = cm.getRecipes();
        assertNotNull(recipes);
        assertEquals(4, recipes.length);
        for (Recipe r : recipes) {
            assertNotNull(r);
            assertNull(r.getName());
        }
    }

    @Test
    public void testConstructorCreatesDefaultInventory() {
        Inventory inv = cm.checkInventory();
        assertEquals(15, inv.getCoffee());
        assertEquals(15, inv.getMilk());
        assertEquals(15, inv.getSugar());
        assertEquals(15, inv.getChocolate());
    }

    // === addRecipe ===

    @Test
    public void testAddRecipeSuccess() {
        assertTrue(cm.addRecipe(coffee));
        assertEquals(1, countNamedRecipes());
    }

    @Test
    public void testAddRecipeStoresSameInstance() {
        cm.addRecipe(coffee);
        assertSame(coffee, cm.getRecipeForName("Coffee"));
    }

    // Javadoc: "add recipe to first empty spot".
    @Test
    public void testAddRecipeUsesFirstEmptySlot() {
        cm.addRecipe(coffee);
        assertSame(coffee, cm.getRecipes()[0]);
    }

    @Test
    public void testAddRecipesFillSlotsInOrder() {
        cm.addRecipe(coffee);
        cm.addRecipe(mocha);
        cm.addRecipe(latte);
        cm.addRecipe(cappuccino);
        Recipe[] r = cm.getRecipes();
        assertSame(coffee, r[0]);
        assertSame(mocha, r[1]);
        assertSame(latte, r[2]);
        assertSame(cappuccino, r[3]);
    }

    @Test
    public void testAddFourRecipes() {
        assertTrue(cm.addRecipe(coffee));
        assertTrue(cm.addRecipe(mocha));
        assertTrue(cm.addRecipe(latte));
        assertTrue(cm.addRecipe(cappuccino));
        assertEquals(4, countNamedRecipes());
    }

    @Test
    public void testAddRecipeFifthFails() {
        cm.addRecipe(coffee);
        cm.addRecipe(mocha);
        cm.addRecipe(latte);
        cm.addRecipe(cappuccino);
        assertFalse(cm.addRecipe(extra));
        assertEquals(4, countNamedRecipes());
        assertEquals(null, cm.getRecipeForName("Extra").getName());
    }

    @Test
    public void testAddRecipeDuplicateFails() {
        assertTrue(cm.addRecipe(coffee));
        assertFalse(cm.addRecipe(coffee));
        assertEquals(1, countNamedRecipes());
    }

    @Test
    public void testAddRecipeDuplicateNameDifferentInstanceFails() {
        assertTrue(cm.addRecipe(coffee));
        assertFalse(cm.addRecipe(recipe("Coffee", 99, 9, 9, 9, 9)));
        assertEquals(1, countNamedRecipes());
        assertEquals(50, cm.getRecipeForName("Coffee").getPrice());
    }

    // === deleteRecipe ===

    @Test
    public void testDeleteRecipeExisting() {
        cm.addRecipe(coffee);
        assertTrue(cm.deleteRecipe(coffee));
    }

    @Test
    public void testDeleteRecipeRemovesFromMachine() {
        cm.addRecipe(coffee);
        cm.deleteRecipe(coffee);
        assertNull(cm.getRecipeForName("Coffee").getName());
        assertEquals(0, countNamedRecipes());
    }

    @Test
    public void testDeleteRecipeFreesSlotForReuse() {
        cm.addRecipe(coffee);
        cm.addRecipe(mocha);
        cm.addRecipe(latte);
        cm.addRecipe(cappuccino);
        assertTrue(cm.deleteRecipe(mocha));
        assertTrue(cm.addRecipe(extra));
        assertEquals(4, countNamedRecipes());
    }

    @Test
    public void testDeleteThenReAddSameRecipe() {
        cm.addRecipe(coffee);
        cm.deleteRecipe(coffee);
        assertTrue(cm.addRecipe(coffee));
    }

    @Test
    public void testDeleteRecipeNotPresent() {
        cm.addRecipe(coffee);
        assertFalse(cm.deleteRecipe(mocha));
        assertEquals(1, countNamedRecipes());
    }

    @Test
    public void testDeleteRecipeNull() {
        assertFalse(cm.deleteRecipe(null));
    }

    @Test
    public void testDeleteRecipeTwiceSecondFails() {
        cm.addRecipe(coffee);
        assertTrue(cm.deleteRecipe(coffee));
        assertFalse(cm.deleteRecipe(coffee));
    }

    @Test
    public void testDeleteRecipeLeavesOthersIntact() {
        cm.addRecipe(coffee);
        cm.addRecipe(mocha);
        cm.deleteRecipe(coffee);
        assertSame(mocha, cm.getRecipeForName("Mocha"));
    }

    // === editRecipe ===

    @Test
    public void testEditRecipeSuccess() {
        cm.addRecipe(coffee);
        assertTrue(cm.editRecipe(coffee, mocha));
    }

    @Test
    public void testEditRecipeReplacesOldWithNew() {
        cm.addRecipe(coffee);
        cm.editRecipe(coffee, mocha);
        assertSame(mocha, cm.getRecipeForName("Mocha"));
        assertNull(cm.getRecipeForName("Coffee").getName());
        assertEquals(1, countNamedRecipes());
    }

    @Test
    public void testEditRecipeSameNameUpdatesValues() {
        cm.addRecipe(coffee);
        Recipe updated = recipe("Coffee", 60, 4, 2, 2, 1);
        assertTrue(cm.editRecipe(coffee, updated));
        assertEquals(60, cm.getRecipeForName("Coffee").getPrice());
        assertEquals(1, countNamedRecipes());
    }

    @Test
    public void testEditRecipeOldNotPresentFails() {
        cm.addRecipe(coffee);
        assertFalse(cm.editRecipe(latte, mocha));
        assertNull(cm.getRecipeForName("Mocha").getName());
        assertSame(coffee, cm.getRecipeForName("Coffee"));
    }

    @Test
    public void testEditRecipeOnEmptyMachineFails() {
        assertFalse(cm.editRecipe(coffee, mocha));
        assertEquals(0, countNamedRecipes());
    }

    @Test
    public void testEditRecipeLeavesOthersIntact() {
        cm.addRecipe(coffee);
        cm.addRecipe(latte);
        cm.editRecipe(coffee, mocha);
        assertSame(latte, cm.getRecipeForName("Latte"));
        assertEquals(2, countNamedRecipes());
    }

    // === addInventory ===

    @Test
    public void testAddInventorySuccess() {
        assertTrue(cm.addInventory(1, 2, 3, 4));
        Inventory inv = cm.checkInventory();
        assertEquals(16, inv.getCoffee());
        assertEquals(17, inv.getMilk());
        assertEquals(18, inv.getSugar());
        assertEquals(19, inv.getChocolate());
    }

    @Test
    public void testAddInventoryAllZeros() {
        assertTrue(cm.addInventory(0, 0, 0, 0));
        Inventory inv = cm.checkInventory();
        assertEquals(15, inv.getCoffee());
        assertEquals(15, inv.getMilk());
        assertEquals(15, inv.getSugar());
        assertEquals(15, inv.getChocolate());
    }

    @Test
    public void testAddInventorySugarOnly() {
        assertTrue(cm.addInventory(0, 0, 5, 0));
        assertEquals(20, cm.checkInventory().getSugar());
    }

    @Test
    public void testAddInventoryNegativeCoffeeFails() {
        assertFalse(cm.addInventory(-1, 0, 0, 0));
        assertInventoryUnchanged();
    }

    @Test
    public void testAddInventoryNegativeMilkFails() {
        assertFalse(cm.addInventory(0, -1, 0, 0));
        assertInventoryUnchanged();
    }

    @Test
    public void testAddInventoryNegativeSugarFails() {
        assertFalse(cm.addInventory(0, 0, -1, 0));
        assertInventoryUnchanged();
    }

    @Test
    public void testAddInventoryNegativeChocolateFails() {
        assertFalse(cm.addInventory(0, 0, 0, -1));
        assertInventoryUnchanged();
    }

    @Test
    public void testAddInventoryAccumulates() {
        cm.addInventory(1, 1, 1, 1);
        cm.addInventory(2, 2, 2, 2);
        Inventory inv = cm.checkInventory();
        assertEquals(18, inv.getCoffee());
        assertEquals(18, inv.getMilk());
        assertEquals(18, inv.getSugar());
        assertEquals(18, inv.getChocolate());
    }

    private void assertInventoryUnchanged() {
        Inventory inv = cm.checkInventory();
        assertEquals(15, inv.getCoffee());
        assertEquals(15, inv.getMilk());
        assertEquals(15, inv.getSugar());
        assertEquals(15, inv.getChocolate());
    }

    // === checkInventory ===

    @Test
    public void testCheckInventoryReturnsNonNull() {
        assertNotNull(cm.checkInventory());
    }

    @Test
    public void testCheckInventoryReturnsSameObject() {
        assertSame(cm.checkInventory(), cm.checkInventory());
    }

    // === makeCoffee ===

    @Test
    public void testMakeCoffeeReturnsChange() {
        assertEquals(50, cm.makeCoffee(coffee, 100));
    }

    @Test
    public void testMakeCoffeeExactPaymentReturnsZero() {
        assertEquals(0, cm.makeCoffee(coffee, 50));
    }

    @Test
    public void testMakeCoffeeDeductsAllIngredients() {
        cm.makeCoffee(mocha, 100); // 2 coffee, 1 milk, 1 sugar, 2 chocolate
        Inventory inv = cm.checkInventory();
        assertEquals(13, inv.getCoffee());
        assertEquals(14, inv.getMilk());
        assertEquals(14, inv.getSugar());
        assertEquals(13, inv.getChocolate());
    }

    @Test
    public void testMakeCoffeeInsufficientPaymentReturnsAmountPaid() {
        assertEquals(49, cm.makeCoffee(coffee, 49));
    }

    @Test
    public void testMakeCoffeeInsufficientPaymentLeavesInventory() {
        cm.makeCoffee(coffee, 10);
        assertInventoryUnchanged();
    }

    @Test
    public void testMakeCoffeeZeroPaymentForPaidRecipe() {
        assertEquals(0, cm.makeCoffee(coffee, 0));
        assertInventoryUnchanged();
    }

    @Test
    public void testMakeCoffeeNotEnoughCoffeeReturnsAmountPaid() {
        Recipe big = recipe("Big", 10, 16, 0, 0, 0);
        assertEquals(100, cm.makeCoffee(big, 100));
        assertInventoryUnchanged();
    }

    @Test
    public void testMakeCoffeeNotEnoughMilk() {
        Recipe big = recipe("Big", 10, 0, 16, 0, 0);
        assertEquals(100, cm.makeCoffee(big, 100));
        assertInventoryUnchanged();
    }

    @Test
    public void testMakeCoffeeNotEnoughSugar() {
        Recipe big = recipe("Big", 10, 0, 0, 16, 0);
        assertEquals(100, cm.makeCoffee(big, 100));
        assertInventoryUnchanged();
    }

    @Test
    public void testMakeCoffeeNotEnoughChocolate() {
        Recipe big = recipe("Big", 10, 0, 0, 0, 16);
        assertEquals(100, cm.makeCoffee(big, 100));
        assertInventoryUnchanged();
    }

    @Test
    public void testMakeCoffeeUsesExactRemainingStock() {
        Recipe all = recipe("All", 10, 15, 15, 15, 15);
        assertEquals(0, cm.makeCoffee(all, 10));
        Inventory inv = cm.checkInventory();
        assertEquals(0, inv.getCoffee());
        assertEquals(0, inv.getMilk());
        assertEquals(0, inv.getSugar());
        assertEquals(0, inv.getChocolate());
    }

    @Test
    public void testMakeCoffeeRepeatedUntilOutOfStock() {
        Recipe r = recipe("R", 10, 5, 0, 0, 0);
        assertEquals(0, cm.makeCoffee(r, 10));
        assertEquals(0, cm.makeCoffee(r, 10));
        assertEquals(0, cm.makeCoffee(r, 10));
        // Stock exhausted: money returned in full
        assertEquals(10, cm.makeCoffee(r, 10));
        assertEquals(0, cm.checkInventory().getCoffee());
    }

    @Test
    public void testMakeCoffeeEmptyRecipeFreeDrink() {
        assertEquals(25, cm.makeCoffee(new Recipe(), 25));
        assertInventoryUnchanged();
    }

    @Test
    public void testAddInventoryThenMakeCoffee() {
        Recipe big = recipe("Big", 10, 20, 0, 0, 0);
        assertEquals(10, cm.makeCoffee(big, 10)); // not enough coffee yet
        cm.addInventory(5, 0, 0, 0);
        assertEquals(0, cm.makeCoffee(big, 10));
        assertEquals(0, cm.checkInventory().getCoffee());
    }

    // === getRecipes ===

    @Test
    public void testGetRecipesLengthIsFour() {
        assertEquals(4, cm.getRecipes().length);
    }

    @Test
    public void testGetRecipesContainsAddedRecipes() {
        cm.addRecipe(coffee);
        cm.addRecipe(mocha);
        boolean foundCoffee = false;
        boolean foundMocha = false;
        for (Recipe r : cm.getRecipes()) {
            if (r == coffee) {
                foundCoffee = true;
            }
            if (r == mocha) {
                foundMocha = true;
            }
        }
        assertTrue(foundCoffee);
        assertTrue(foundMocha);
    }

    // === getRecipeForName ===

    @Test
    public void testGetRecipeForNameFound() {
        cm.addRecipe(coffee);
        cm.addRecipe(mocha);
        assertSame(mocha, cm.getRecipeForName("Mocha"));
        assertSame(coffee, cm.getRecipeForName("Coffee"));
    }

    @Test
    public void testGetRecipeForNameNotFoundReturnsEmptyRecipe() {
        cm.addRecipe(coffee);
        Recipe r = cm.getRecipeForName("Unknown");
        assertNotNull(r);
        assertNull(r.getName());
    }

    @Test
    public void testGetRecipeForNameIsCaseSensitive() {
        cm.addRecipe(coffee);
        assertNull(cm.getRecipeForName("coffee").getName());
    }

    @Test
    public void testGetRecipeForNameNullName() {
        cm.addRecipe(coffee);
        Recipe r = cm.getRecipeForName(null);
        assertNotNull(r);
        assertNull(r.getName());
    }

    @Test
    public void testGetRecipeForNameOnEmptyMachine() {
        Recipe r = cm.getRecipeForName("Coffee");
        assertNotNull(r);
        assertNull(r.getName());
    }
}