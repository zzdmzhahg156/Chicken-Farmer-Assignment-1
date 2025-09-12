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

}