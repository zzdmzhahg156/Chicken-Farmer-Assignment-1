package builder.inventory;

import builder.inventory.items.Bucket;
import builder.inventory.items.Hoe;
import builder.inventory.items.Item;
import builder.inventory.items.Jackhammer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit 4 tests for the TinyInventory class.
 */
public class TinyInventoryTest {

    private TinyInventory inventory;
    private Item hoe;
    private Item bucket;
    private Item jackhammer;

    @Before
    public void setUp() {
        inventory = new TinyInventory(4);
        hoe = new Hoe();
        bucket = new Bucket();
        jackhammer = new Jackhammer();
    }

    // Constructor Tests
    @Test
    public void testConstructorWithValidSize() {
        TinyInventory inv = new TinyInventory(5);
        assertEquals("Capacity should match constructor parameter", 5, inv.getCapacity());
        assertEquals("New inventory should have 0 coins", 0, inv.getCoins());
        assertEquals("New inventory should have 0 food", 0, inv.getFood());
        assertEquals("Active slot should start at 0", 0, inv.getActiveSlot());
    }

    @Test
    public void testConstructorWithMinSize() {
        TinyInventory inv = new TinyInventory(1);
        assertEquals("Should support minimum size of 1", 1, inv.getCapacity());
    }

    @Test
    public void testConstructorWithMaxSize() {
        TinyInventory inv = new TinyInventory(10);
        assertEquals("Should support maximum size of 10", 10, inv.getCapacity());
    }

    // getCapacity() tests
    @Test
    public void testGetCapacity() {
        assertEquals("Capacity should match constructor parameter", 4, inventory.getCapacity());
    }

    @Test
    public void testGetCapacityDifferentSizes() {
        for (int size = 1; size <= 10; size++) {
            TinyInventory inv = new TinyInventory(size);
            assertEquals("Capacity should match for size " + size, size, inv.getCapacity());
        }
    }

    // Item management tests
    @Test
    public void testSetItemAndGetItem() {
        inventory.setItem(0, hoe);
        assertEquals("Should retrieve the set item", hoe, inventory.getItem(0));
    }

    @Test
    public void testSetItemOverwrite() {
        inventory.setItem(1, hoe);
        inventory.setItem(1, bucket);
        assertEquals("Should overwrite previous item", bucket, inventory.getItem(1));
    }

    @Test
    public void testSetItemNull() {
        inventory.setItem(2, hoe);
        inventory.setItem(2, null);
        assertNull("Should be able to set item to null", inventory.getItem(2));
    }

    @Test
    public void testGetItemEmptySlot() {
        assertNull("Empty slot should return null", inventory.getItem(0));
    }

    @Test
    public void testSetItemAllSlots() {
        Item[] items = {hoe, bucket, jackhammer, new Hoe()};

        for (int i = 0; i < inventory.getCapacity(); i++) {
            inventory.setItem(i, items[i]);
        }

        for (int i = 0; i < inventory.getCapacity(); i++) {
            assertEquals("Item at slot " + i + " should match", items[i], inventory.getItem(i));
        }
    }

    // Active slot tests
    @Test
    public void testGetActiveSlotDefault() {
        assertEquals("Default active slot should be 0", 0, inventory.getActiveSlot());
    }

    @Test
    public void testSetActiveSlot() {
        inventory.setActiveSlot(2);
        assertEquals("Active slot should be updated", 2, inventory.getActiveSlot());
    }

    @Test
    public void testSetActiveSlotAllValues() {
        for (int slot = 0; slot < inventory.getCapacity(); slot++) {
            inventory.setActiveSlot(slot);
            assertEquals("Active slot should be " + slot, slot, inventory.getActiveSlot());
        }
    }

    @Test
    public void testSetActiveSlotBoundary() {
        // Test boundary values
        inventory.setActiveSlot(0);
        assertEquals("Should handle slot 0", 0, inventory.getActiveSlot());

        inventory.setActiveSlot(inventory.getCapacity() - 1);
        assertEquals("Should handle last slot", inventory.getCapacity() - 1, inventory.getActiveSlot());
    }

    // getHolding() tests
    @Test
    public void testGetHoldingEmptySlot() {
        assertNull("Should return null when active slot is empty", inventory.getHolding());
    }

    @Test
    public void testGetHoldingWithItem() {
        inventory.setItem(0, hoe);
        assertEquals("Should return item from active slot", hoe, inventory.getHolding());
    }

    @Test
    public void testGetHoldingAfterSlotChange() {
        inventory.setItem(0, hoe);
        inventory.setItem(1, bucket);

        inventory.setActiveSlot(0);
        assertEquals("Should return hoe from slot 0", hoe, inventory.getHolding());

        inventory.setActiveSlot(1);
        assertEquals("Should return bucket from slot 1", bucket, inventory.getHolding());
    }

    @Test
    public void testGetHoldingConsistency() {
        inventory.setItem(2, jackhammer);
        inventory.setActiveSlot(2);

        Item holding = inventory.getHolding();
        Item directAccess = inventory.getItem(inventory.getActiveSlot());

        assertEquals("getHolding() should match getItem(getActiveSlot())", directAccess, holding);
    }

    // Coins management tests
    @Test
    public void testGetCoinsDefault() {
        assertEquals("Default coins should be 0", 0, inventory.getCoins());
    }

    @Test
    public void testAddCoinsPositive() {
        inventory.addCoins(10);
        assertEquals("Should add coins", 10, inventory.getCoins());

        inventory.addCoins(5);
        assertEquals("Should accumulate coins", 15, inventory.getCoins());
    }

    @Test
    public void testAddCoinsZero() {
        inventory.addCoins(10);
        inventory.addCoins(0);
        assertEquals("Adding zero should not change coins", 10, inventory.getCoins());
    }

    @Test
    public void testAddCoinsNegative() {
        inventory.addCoins(20);
        inventory.addCoins(-5);
        assertEquals("Should subtract coins", 15, inventory.getCoins());
    }

    @Test
    public void testAddCoinsNegativeFloor() {
        inventory.addCoins(10);
        inventory.addCoins(-20);
        assertEquals("Coins should not go below 0", 0, inventory.getCoins());
    }

    @Test
    public void testAddCoinsLargeNumbers() {
        inventory.addCoins(1000000);
        assertEquals("Should handle large numbers", 1000000, inventory.getCoins());

        inventory.addCoins(-500000);
        assertEquals("Should handle large subtractions", 500000, inventory.getCoins());
    }

    // Food management tests
    @Test
    public void testGetFoodDefault() {
        assertEquals("Default food should be 0", 0, inventory.getFood());
    }

    @Test
    public void testAddFoodPositive() {
        inventory.addFood(5);
        assertEquals("Should add food", 5, inventory.getFood());

        inventory.addFood(3);
        assertEquals("Should accumulate food", 8, inventory.getFood());
    }

    @Test
    public void testAddFoodZero() {
        inventory.addFood(7);
        inventory.addFood(0);
        assertEquals("Adding zero should not change food", 7, inventory.getFood());
    }

    @Test
    public void testAddFoodNegative() {
        inventory.addFood(15);
        inventory.addFood(-3);
        assertEquals("Should subtract food", 12, inventory.getFood());
    }

    @Test
    public void testAddFoodNegativeFloor() {
        inventory.addFood(8);
        inventory.addFood(-15);
        assertEquals("Food should not go below 0", 0, inventory.getFood());
    }

    @Test
    public void testAddFoodLargeNumbers() {
        inventory.addFood(999999);
        assertEquals("Should handle large food amounts", 999999, inventory.getFood());

        inventory.addFood(-499999);
        assertEquals("Should handle large food subtractions", 500000, inventory.getFood());
    }

    // Invariant tests
    @Test
    public void testCoinsInvariantAfterOperations() {
        // Coins should never be negative
        inventory.addCoins(-100);
        assertTrue("Coins should never be negative", inventory.getCoins() >= 0);

        inventory.addCoins(50);
        inventory.addCoins(-200);
        assertTrue("Coins should never be negative after operations", inventory.getCoins() >= 0);
    }

    @Test
    public void testFoodInvariantAfterOperations() {
        // Food should never be negative
        inventory.addFood(-100);
        assertTrue("Food should never be negative", inventory.getFood() >= 0);

        inventory.addFood(30);
        inventory.addFood(-200);
        assertTrue("Food should never be negative after operations", inventory.getFood() >= 0);
    }

    @Test
    public void testCapacityInvariant() {
        assertTrue("Capacity should be positive", inventory.getCapacity() > 0);
        assertTrue("Capacity should be <= 10", inventory.getCapacity() <= 10);
    }

    @Test
    public void testActiveSlotInvariant() {
        // Test that active slot is always within bounds
        for (int i = 0; i < 10; i++) {
            inventory.setActiveSlot(i % inventory.getCapacity());
            assertTrue("Active slot should be >= 0", inventory.getActiveSlot() >= 0);
            assertTrue("Active slot should be < capacity",
                    inventory.getActiveSlot() < inventory.getCapacity());
        }
    }

    // Edge case tests
    @Test
    public void testItemSlotBoundaries() {
        // Test first slot
        inventory.setItem(0, hoe);
        assertEquals("First slot should work", hoe, inventory.getItem(0));

        // Test last slot
        int lastSlot = inventory.getCapacity() - 1;
        inventory.setItem(lastSlot, bucket);
        assertEquals("Last slot should work", bucket, inventory.getItem(lastSlot));
    }

    @Test
    public void testMixedOperations() {
        // Complex scenario mixing all operations
        inventory.addCoins(100);
        inventory.addFood(50);
        inventory.setItem(0, hoe);
        inventory.setItem(1, bucket);
        inventory.setActiveSlot(1);

        assertEquals("Coins should be preserved", 100, inventory.getCoins());
        assertEquals("Food should be preserved", 50, inventory.getFood());
        assertEquals("Should be holding bucket", bucket, inventory.getHolding());
        assertEquals("Active slot should be 1", 1, inventory.getActiveSlot());
        assertEquals("Slot 0 should still have hoe", hoe, inventory.getItem(0));
    }

    // Integration tests
    @Test
    public void testFullInventoryWorkflow() {
        // Simulate a typical game workflow

        // 1. Start with empty inventory
        assertEquals("Should start with 0 coins", 0, inventory.getCoins());
        assertEquals("Should start with 0 food", 0, inventory.getFood());
        assertNull("Should start with no item holding", inventory.getHolding());

        // 2. Add some tools
        inventory.setItem(0, hoe);
        inventory.setItem(1, bucket);
        inventory.setItem(2, jackhammer);

        // 3. Collect some resources
        inventory.addCoins(25);
        inventory.addFood(10);

        // 4. Switch between tools
        inventory.setActiveSlot(0);
        assertEquals("Should be holding hoe", hoe, inventory.getHolding());

        inventory.setActiveSlot(2);
        assertEquals("Should be holding jackhammer", jackhammer, inventory.getHolding());

        // 5. Spend some resources
        inventory.addCoins(-5);
        inventory.addFood(-2);

        assertEquals("Should have 20 coins", 20, inventory.getCoins());
        assertEquals("Should have 8 food", 8, inventory.getFood());

        // 6. Verify all items are still there
        assertEquals("Hoe should still be in slot 0", hoe, inventory.getItem(0));
        assertEquals("Bucket should still be in slot 1", bucket, inventory.getItem(1));
        assertEquals("Jackhammer should still be in slot 2", jackhammer, inventory.getItem(2));
    }

    @Test
    public void testInventoryStateIndependence() {
        // Verify that different inventory instances don't interfere
        TinyInventory inv1 = new TinyInventory(3);
        TinyInventory inv2 = new TinyInventory(3);

        inv1.addCoins(10);
        inv1.setItem(0, hoe);
        inv1.setActiveSlot(1);

        inv2.addCoins(20);
        inv2.setItem(1, bucket);
        inv2.setActiveSlot(2);

        // Verify independence
        assertEquals("inv1 should have 10 coins", 10, inv1.getCoins());
        assertEquals("inv2 should have 20 coins", 20, inv2.getCoins());
        assertEquals("inv1 slot 0 should have hoe", hoe, inv1.getItem(0));
        assertEquals("inv2 slot 1 should have bucket", bucket, inv2.getItem(1));
        assertEquals("inv1 active slot should be 1", 1, inv1.getActiveSlot());
        assertEquals("inv2 active slot should be 2", 2, inv2.getActiveSlot());
    }

    @Test
    public void testResourceOperationsSequence() {
        // Test various sequences of resource operations

        // Sequence 1: Build up then spend
        inventory.addCoins(50);
        inventory.addFood(30);
        inventory.addCoins(-20);
        inventory.addFood(-10);
        assertEquals("Should have 30 coins", 30, inventory.getCoins());
        assertEquals("Should have 20 food", 20, inventory.getFood());

        // Sequence 2: Overspend
        inventory.addCoins(-100);
        inventory.addFood(-50);
        assertEquals("Coins should be 0 after overspend", 0, inventory.getCoins());
        assertEquals("Food should be 0 after overspend", 0, inventory.getFood());

        // Sequence 3: Build up again
        inventory.addCoins(15);
        inventory.addFood(8);
        assertEquals("Should rebuild coins", 15, inventory.getCoins());
        assertEquals("Should rebuild food", 8, inventory.getFood());
    }

    @Test
    public void testItemManipulationPatterns() {
        // Test common item manipulation patterns

        // Pattern 1: Fill inventory
        Item[] tools = {new Hoe(), new Bucket(), new Jackhammer(), new Hoe()};
        for (int i = 0; i < inventory.getCapacity(); i++) {
            inventory.setItem(i, tools[i]);
        }

        // Pattern 2: Cycle through all items
        for (int i = 0; i < inventory.getCapacity(); i++) {
            inventory.setActiveSlot(i);
            assertEquals("Should hold correct item at slot " + i, tools[i], inventory.getHolding());
        }

        // Pattern 3: Replace items
        Item newTool = new Bucket();
        inventory.setItem(1, newTool);
        inventory.setActiveSlot(1);
        assertEquals("Should hold replaced item", newTool, inventory.getHolding());

        // Pattern 4: Clear items
        for (int i = 0; i < inventory.getCapacity(); i++) {
            inventory.setItem(i, null);
        }

        for (int i = 0; i < inventory.getCapacity(); i++) {
            assertNull("All slots should be empty", inventory.getItem(i));
        }
    }
}