package builder.world;

import builder.GameState;
import builder.JavaBeanGameState;
import builder.entities.tiles.Dirt;
import builder.entities.tiles.Grass;
import builder.entities.tiles.Tile;
import builder.entities.tiles.Water;
import builder.inventory.Inventory;
import builder.inventory.TinyInventory;
import builder.player.ChickenFarmer;
import builder.player.Player;
import engine.EngineState;
import engine.input.KeyState;
import engine.input.MouseState;
import engine.renderer.Dimensions;
import engine.renderer.Renderable;
import engine.renderer.TileGrid;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * JUnit 4 tests for the BeanWorld class.
 */
public class BeanWorldTest {

    private BeanWorld world;
    private Dimensions dimensions;
    private EngineState engineState;
    private GameState gameState;
    private Player player;
    private Inventory inventory;

    // Simple test implementation of KeyState
    private static class TestKeyState implements KeyState {
        @Override
        public List<Character> getDown() {
            return List.of();
        }

        @Override
        public boolean isDown(char key) {
            return false;
        }
    }

    // Simple test implementation of MouseState
    private static class TestMouseState implements MouseState {
        @Override
        public int getMouseX() {
            return 0;
        }

        @Override
        public int getMouseY() {
            return 0;
        }

        @Override
        public boolean isLeftPressed() {
            return false;
        }

        @Override
        public boolean isRightPressed() {
            return false;
        }

        @Override
        public boolean isMiddlePressed() {
            return false;
        }

    }

    // Simple test implementation of EngineState
    private static class TestEngineState implements EngineState {
        private final Dimensions dimensions;
        private final KeyState keyState;
        private final MouseState mouseState;

        public TestEngineState(Dimensions dimensions) {
            this.dimensions = dimensions;
            this.keyState = new TestKeyState();
            this.mouseState = new TestMouseState();
        }

        @Override
        public Dimensions getDimensions() {
            return dimensions;
        }

        @Override
        public KeyState getKeys() {
            return keyState;
        }

        @Override
        public MouseState getMouse() {
            return mouseState;
        }

        @Override
        public int currentTick() {
            return 0;
        }
    }

    @Before
    public void setUp() {
        world = new BeanWorld();
        dimensions = new TileGrid(25, 800); // 25 tiles per row, 800px window
        engineState = new TestEngineState(dimensions);

        // Create real player and inventory
        player = new ChickenFarmer(400, 400);
        inventory = new TinyInventory(4);

        // Create game state with real objects
        gameState = new JavaBeanGameState(world, player, inventory);
    }

    // Constructor Tests
    @Test
    public void testConstructorCreatesEmptyWorld() {
        BeanWorld newWorld = new BeanWorld();
        assertTrue("New world should be empty", newWorld.allTiles().isEmpty());
    }

    // place() method tests
    @Test
    public void testPlaceSingleTile() {
        Tile tile = new Dirt(100, 100);
        world.place(tile);

        List<Tile> allTiles = world.allTiles();
        assertEquals("World should contain one tile", 1, allTiles.size());
        assertTrue("World should contain the placed tile", allTiles.contains(tile));
    }

    @Test
    public void testPlaceMultipleTiles() {
        Tile tile1 = new Dirt(100, 100);
        Tile tile2 = new Grass(200, 200);
        Tile tile3 = new Water(300, 300);

        world.place(tile1);
        world.place(tile2);
        world.place(tile3);

        List<Tile> allTiles = world.allTiles();
        assertEquals("World should contain three tiles", 3, allTiles.size());
        assertTrue("World should contain tile1", allTiles.contains(tile1));
        assertTrue("World should contain tile2", allTiles.contains(tile2));
        assertTrue("World should contain tile3", allTiles.contains(tile3));
    }

    @Test
    public void testPlaceNullTile() {
        world.place(null);
        assertTrue("World should remain empty after placing null", world.allTiles().isEmpty());
    }

    @Test
    public void testPlaceDuplicateTile() {
        Tile tile = new Dirt(100, 100);
        world.place(tile);
        world.place(tile);

        List<Tile> allTiles = world.allTiles();
        assertEquals("World should contain two instances of the same tile", 2, allTiles.size());
    }

    // allTiles() method tests
    @Test
    public void testAllTilesEmptyWorld() {
        List<Tile> tiles = world.allTiles();
        assertNotNull("allTiles() should not return null", tiles);
        assertTrue("Empty world should return empty list", tiles.isEmpty());
    }

    @Test
    public void testAllTilesDoesNotReturnOriginalList() {
        Tile tile = new Dirt(100, 100);
        world.place(tile);

        List<Tile> tiles1 = world.allTiles();
        List<Tile> tiles2 = world.allTiles();

        assertNotSame("allTiles() should return different list instances", tiles1, tiles2);
        assertEquals("Both lists should contain the same elements", tiles1, tiles2);
    }

    @Test
    public void testAllTilesModificationDoesNotAffectWorld() {
        Tile tile = new Dirt(100, 100);
        world.place(tile);

        List<Tile> tiles = world.allTiles();
        tiles.clear();

        assertEquals("Modifying returned list should not affect world",
                1, world.allTiles().size());
    }

    // tilesAtPosition() method tests
    @Test
    public void testTilesAtPositionEmptyWorld() {
        List<Tile> tiles = world.tilesAtPosition(100, 100, dimensions);
        assertNotNull("tilesAtPosition should not return null", tiles);
        assertTrue("Empty world should return empty list", tiles.isEmpty());
    }

    @Test
    public void testTilesAtPositionSingleTileAtPosition() {
        // With TileGrid(25, 800), each tile is 32px (800/25)
        // Tile at position (96, 96) which maps to grid (3, 3)
        Tile tile = new Dirt(96, 96);
        world.place(tile);

        // Query position (100, 100) which should be in the same grid cell
        List<Tile> tiles = world.tilesAtPosition(100, 100, dimensions);
        assertEquals("Should find one tile at position", 1, tiles.size());
        assertTrue("Should contain the placed tile", tiles.contains(tile));
    }

}