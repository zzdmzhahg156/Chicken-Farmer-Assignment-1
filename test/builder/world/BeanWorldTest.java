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

//    @Test
//    public void testTilesAtPositionMultipleTilesAtSamePosition() {
//        // Both tiles at grid position (3, 3) - tile size is 32px
//        Tile tile1 = new Dirt(96, 96);
//        Tile tile2 = new Grass(100, 100);
//        world.place(tile1);
//        world.place(tile2);
//
//        List<Tile> tiles = world.tilesAtPosition(96, 96, dimensions);
//        assertEquals("Should find two tiles at position", 2, tiles.size());
//        assertTrue("Should contain tile1", tiles.contains(tile1));
//        assertTrue("Should contain tile2", tiles.contains(tile2));
//    }
//
//    @Test
//    public void testTilesAtPositionDifferentPositions() {
//        // With 32px tiles: tile1 at grid (1,1), tile2 at grid (2,2)
//        Tile tile1 = new Dirt(32, 32);  // Grid (1, 1)
//        Tile tile2 = new Grass(64, 64); // Grid (2, 2)
//        world.place(tile1);
//        world.place(tile2);
//
//        List<Tile> tilesAt1 = world.tilesAtPosition(32, 32, dimensions);
//        List<Tile> tilesAt2 = world.tilesAtPosition(64, 64, dimensions);
//
//        assertEquals("Should find one tile at first position", 1, tilesAt1.size());
//        assertEquals("Should find one tile at second position", 1, tilesAt2.size());
//        assertTrue("First position should contain tile1", tilesAt1.contains(tile1));
//        assertTrue("Second position should contain tile2", tilesAt2.contains(tile2));
//    }
//
//    @Test
//    public void testTilesAtPositionBoundaryConditions() {
//        Tile tile = new Dirt(0, 0);
//        world.place(tile);
//
//        List<Tile> tiles = world.tilesAtPosition(0, 0, dimensions);
//        assertEquals("Should find tile at boundary", 1, tiles.size());
//        assertTrue("Should contain the tile", tiles.contains(tile));
//    }
//
//    @Test
//    public void testTilesAtPositionEdgeCases() {
//        // Test with tiles at exact grid boundaries
//        int tileSize = dimensions.tileSize(); // 32px
//
//        Tile tile1 = new Dirt(tileSize - 1, tileSize - 1); // Just before boundary
//        Tile tile2 = new Dirt(tileSize, tileSize); // At boundary
//
//        world.place(tile1);
//        world.place(tile2);
//
//        // These should be in different grid cells
//        List<Tile> tilesAt1 = world.tilesAtPosition(tileSize - 1, tileSize - 1, dimensions);
//        List<Tile> tilesAt2 = world.tilesAtPosition(tileSize, tileSize, dimensions);
//
//        assertEquals("Should find tile1 in first cell", 1, tilesAt1.size());
//        assertEquals("Should find tile2 in second cell", 1, tilesAt2.size());
//        assertTrue("First cell should contain tile1", tilesAt1.contains(tile1));
//        assertTrue("Second cell should contain tile2", tilesAt2.contains(tile2));
//    }
//
//    // tick() method tests
//    @Test
//    public void testTickEmptyWorld() {
//        // Should not throw exception
//        world.tick(engineState, gameState);
//        assertTrue("Empty world should remain empty after tick", world.allTiles().isEmpty());
//    }
//
//    @Test
//    public void testTickWithTiles() {
//        Tile tile1 = new Dirt(100, 100);
//        Tile tile2 = new Grass(200, 200);
//
//        world.place(tile1);
//        world.place(tile2);
//
//        // Should not throw exception
//        world.tick(engineState, gameState);
//
//        // Verify tiles are still there
//        assertEquals("World should still contain tiles after tick", 2, world.allTiles().size());
//    }
//
//    // render() method tests
//    @Test
//    public void testRenderEmptyWorld() {
//        List<Renderable> renderables = world.render();
//        assertNotNull("render() should not return null", renderables);
//        assertTrue("Empty world should return empty renderable list", renderables.isEmpty());
//    }
//
//    @Test
//    public void testRenderSingleTile() {
//        Tile tile = new Dirt(100, 100);
//        world.place(tile);
//
//        List<Renderable> renderables = world.render();
//
//        assertFalse("Should have renderables", renderables.isEmpty());
//        assertTrue("Should contain at least the tile", renderables.size() >= 1);
//        assertTrue("Should contain the tile itself", renderables.contains(tile));
//    }
//
//    @Test
//    public void testRenderMultipleTiles() {
//        Tile tile1 = new Dirt(100, 100);
//        Tile tile2 = new Grass(200, 200);
//
//        world.place(tile1);
//        world.place(tile2);
//
//        List<Renderable> renderables = world.render();
//
//        assertFalse("Should have renderables", renderables.isEmpty());
//        assertTrue("Should contain at least both tiles", renderables.size() >= 2);
//        assertTrue("Should contain tile1", renderables.contains(tile1));
//        assertTrue("Should contain tile2", renderables.contains(tile2));
//    }
//
//    // Integration tests
//    @Test
//    public void testWorldWorkflowIntegration() {
//        // Create a realistic scenario
//        Tile dirt = new Dirt(96, 96);
//        Tile grass = new Grass(128, 128);
//
//        // Place tiles
//        world.place(dirt);
//        world.place(grass);
//
//        // Verify placement
//        assertEquals("World should contain 2 tiles", 2, world.allTiles().size());
//
//        // Test position queries
//        List<Tile> tilesAtDirt = world.tilesAtPosition(96, 96, dimensions);
//        List<Tile> tilesAtGrass = world.tilesAtPosition(128, 128, dimensions);
//
//        assertEquals("Should find dirt tile", 1, tilesAtDirt.size());
//        assertEquals("Should find grass tile", 1, tilesAtGrass.size());
//        assertTrue("Should contain dirt", tilesAtDirt.contains(dirt));
//        assertTrue("Should contain grass", tilesAtGrass.contains(grass));
//
//        // Test rendering
//        List<Renderable> renderables = world.render();
//        assertFalse("Should have renderables", renderables.isEmpty());
//
//        // Test ticking (should not throw exceptions)
//        world.tick(engineState, gameState);
//    }
//
//    @Test
//    public void testDimensionsIntegration() {
//        // Use real TileGrid dimensions
//        Dimensions realDimensions = new TileGrid(25, 800);
//
//        Tile tile = new Dirt(400, 400); // Center of 800x800 grid
//        world.place(tile);
//
//        List<Tile> tiles = world.tilesAtPosition(400, 400, realDimensions);
//        assertEquals("Should find tile with real dimensions", 1, tiles.size());
//        assertTrue("Should contain the placed tile", tiles.contains(tile));
//    }
//
//    @Test
//    public void testPixelToTileConversion() {
//        // Test the pixel to tile conversion logic
//        int tileSize = dimensions.tileSize(); // Should be 32 for TileGrid(25, 800)
//
//        // Test various positions
//        Tile tile1 = new Dirt(0, 0);      // Grid (0,0)
//        Tile tile2 = new Dirt(31, 31);    // Grid (0,0) - same cell
//        Tile tile3 = new Dirt(32, 32);    // Grid (1,1)
//        Tile tile4 = new Dirt(63, 63);    // Grid (1,1) - same cell
//
//        world.place(tile1);
//        world.place(tile2);
//        world.place(tile3);
//        world.place(tile4);
//
//        // Query grid (0,0)
//        List<Tile> tilesAt00 = world.tilesAtPosition(15, 15, dimensions);
//        assertEquals("Grid (0,0) should contain 2 tiles", 2, tilesAt00.size());
//        assertTrue("Should contain tile1", tilesAt00.contains(tile1));
//        assertTrue("Should contain tile2", tilesAt00.contains(tile2));
//
//        // Query grid (1,1)
//        List<Tile> tilesAt11 = world.tilesAtPosition(45, 45, dimensions);
//        assertEquals("Grid (1,1) should contain 2 tiles", 2, tilesAt11.size());
//        assertTrue("Should contain tile3", tilesAt11.contains(tile3));
//        assertTrue("Should contain tile4", tilesAt11.contains(tile4));
//    }
//
//    @Test
//    public void testWorldStateConsistency() {
//        // Add some tiles
//        Tile dirt = new Dirt(100, 100);
//        Tile grass = new Grass(200, 200);
//        Tile water = new Water(300, 300);
//
//        world.place(dirt);
//        world.place(grass);
//        world.place(water);
//
//        // Verify consistency across different operations
//        List<Tile> allTiles = world.allTiles();
//        List<Renderable> renderables = world.render();
//
//        assertEquals("All tiles count should match", 3, allTiles.size());
//        assertTrue("Renderables should include all tiles", renderables.size() >= 3);
//
//        // Tick shouldn't change tile count
//        world.tick(engineState, gameState);
//        assertEquals("Tile count should remain same after tick", 3, world.allTiles().size());
//
//        // Position queries should work consistently
//        List<Tile> dirtTiles = world.tilesAtPosition(100, 100, dimensions);
//        List<Tile> grassTiles = world.tilesAtPosition(200, 200, dimensions);
//        List<Tile> waterTiles = world.tilesAtPosition(300, 300, dimensions);
//
//        assertEquals("Should find dirt tile", 1, dirtTiles.size());
//        assertEquals("Should find grass tile", 1, grassTiles.size());
//        assertEquals("Should find water tile", 1, waterTiles.size());
//    }
}