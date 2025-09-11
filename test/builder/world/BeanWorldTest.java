package builder.world;

import builder.GameState;
import builder.entities.tiles.Dirt;
import builder.entities.tiles.Grass;
import builder.entities.tiles.Tile;
import builder.entities.tiles.Water;
import engine.EngineState;
import engine.renderer.Dimensions;
import engine.renderer.Renderable;
import engine.renderer.TileGrid;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;

/**
 * JUnit 4 tests for the BeanWorld class.
 */
public class BeanWorldTest {

    private BeanWorld world;
    private Dimensions mockDimensions;
    private EngineState mockEngineState;
    private GameState mockGameState;

//    @Before
//    public void setUp() {
//        world = new BeanWorld();
//        mockDimensions = mock(Dimensions.class);
//        mockEngineState = mock(EngineState.class);
//        mockGameState = mock(GameState.class);
//
//        // Configure mock dimensions for a 800x800 window with 32px tiles
//        when(mockDimensions.windowSize()).thenReturn(800);
//        when(mockDimensions.tileSize()).thenReturn(32);
//        when(mockDimensions.pixelToTile(anyInt())).thenAnswer(invocation -> {
//            int pixel = invocation.getArgument(0);
//            return pixel / 32;
//        });
//    }

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
        List<Tile> tiles = world.tilesAtPosition(100, 100, mockDimensions);
        assertNotNull("tilesAtPosition should not return null", tiles);
        assertTrue("Empty world should return empty list", tiles.isEmpty());
    }

    @Test
    public void testTilesAtPositionSingleTileAtPosition() {
        // Tile at position (96, 96) which maps to grid (3, 3)
        Tile tile = new Dirt(96, 96);
        world.place(tile);

        List<Tile> tiles = world.tilesAtPosition(100, 100, mockDimensions);
        assertEquals("Should find one tile at position", 1, tiles.size());
        assertTrue("Should contain the placed tile", tiles.contains(tile));
    }

    @Test
    public void testTilesAtPositionMultipleTilesAtSamePosition() {
        // Both tiles at grid position (3, 3)
        Tile tile1 = new Dirt(96, 96);
        Tile tile2 = new Grass(100, 100);
        world.place(tile1);
        world.place(tile2);

        List<Tile> tiles = world.tilesAtPosition(96, 96, mockDimensions);
        assertEquals("Should find two tiles at position", 2, tiles.size());
        assertTrue("Should contain tile1", tiles.contains(tile1));
        assertTrue("Should contain tile2", tiles.contains(tile2));
    }

    @Test
    public void testTilesAtPositionDifferentPositions() {
        Tile tile1 = new Dirt(32, 32);  // Grid (1, 1)
        Tile tile2 = new Grass(64, 64); // Grid (2, 2)
        world.place(tile1);
        world.place(tile2);

        List<Tile> tilesAt1 = world.tilesAtPosition(32, 32, mockDimensions);
        List<Tile> tilesAt2 = world.tilesAtPosition(64, 64, mockDimensions);

        assertEquals("Should find one tile at first position", 1, tilesAt1.size());
        assertEquals("Should find one tile at second position", 1, tilesAt2.size());
        assertTrue("First position should contain tile1", tilesAt1.contains(tile1));
        assertTrue("Second position should contain tile2", tilesAt2.contains(tile2));
    }

    @Test
    public void testTilesAtPositionBoundaryConditions() {
        Tile tile = new Dirt(0, 0);
        world.place(tile);

        List<Tile> tiles = world.tilesAtPosition(0, 0, mockDimensions);
        assertEquals("Should find tile at boundary", 1, tiles.size());
        assertTrue("Should contain the tile", tiles.contains(tile));
    }

    // tick() method tests
    @Test
    public void testTickEmptyWorld() {
        // Should not throw exception
        world.tick(mockEngineState, mockGameState);
        assertTrue("Empty world should remain empty after tick", world.allTiles().isEmpty());
    }

//    @Test
//    public void testTickCallsTileTickMethods() {
//        Tile mockTile1 = mock(Tile.class);
//        Tile mockTile2 = mock(Tile.class);
//
//        world.place(mockTile1);
//        world.place(mockTile2);
//
//        world.tick(mockEngineState, mockGameState);
//
//        verify(mockTile1, times(1)).tick(mockEngineState);
//        verify(mockTile2, times(1)).tick(mockEngineState);
//    }

//    @Test
//    public void testTickRemovesMarkedTiles() {
//        Tile mockTile = mock(Tile.class);
//        when(mockTile.isMarkedForRemoval()).thenReturn(false, true); // First false, then true
//
//        world.place(mockTile);
//        assertEquals("World should contain tile before tick", 1, world.allTiles().size());
//
//        world.tick(mockEngineState, mockGameState);
//        // Note: Actual removal logic might be handled by the Tile class itself
//        // This test assumes the world handles removal of marked tiles
//    }

    // render() method tests
    @Test
    public void testRenderEmptyWorld() {
        List<Renderable> renderables = world.render();
        assertNotNull("render() should not return null", renderables);
        assertTrue("Empty world should return empty renderable list", renderables.isEmpty());
    }

//    @Test
//    public void testRenderSingleTile() {
//        Tile mockTile = mock(Tile.class);
//        List<Renderable> tileRenderables = List.of(mockTile);
//        when(mockTile.render()).thenReturn(tileRenderables);
//
//        world.place(mockTile);
//        List<Renderable> renderables = world.render();
//
//        assertEquals("Should return renderables from tile", 1, renderables.size());
//        assertTrue("Should contain tile's renderables", renderables.containsAll(tileRenderables));
//    }

//    @Test
//    public void testRenderMultipleTiles() {
//        Tile mockTile1 = mock(Tile.class);
//        Tile mockTile2 = mock(Tile.class);
//
//        List<Renderable> tile1Renderables = List.of(mockTile1);
//        List<Renderable> tile2Renderables = List.of(mockTile2);
//
//        when(mockTile1.render()).thenReturn(tile1Renderables);
//        when(mockTile2.render()).thenReturn(tile2Renderables);
//
//        world.place(mockTile1);
//        world.place(mockTile2);
//
//        List<Renderable> renderables = world.render();
//
//        assertEquals("Should return renderables from both tiles", 2, renderables.size());
//        assertTrue("Should contain first tile's renderables",
//                renderables.containsAll(tile1Renderables));
//        assertTrue("Should contain second tile's renderables",
//                renderables.containsAll(tile2Renderables));
//    }

//    @Test
//    public void testRenderTileWithStackedEntities() {
//        Tile mockTile = mock(Tile.class);
//        Renderable mockEntity = mock(Renderable.class);
//
//        List<Renderable> tileRenderables = List.of(mockTile, mockEntity);
//        when(mockTile.render()).thenReturn(tileRenderables);
//
//        world.place(mockTile);
//        List<Renderable> renderables = world.render();
//
//        assertEquals("Should return tile and stacked entity", 2, renderables.size());
//        assertEquals("Tile should come before stacked entity", mockTile, renderables.get(0));
//        assertEquals("Stacked entity should come after tile", mockEntity, renderables.get(1));
//    }

    // Integration tests
    @Test
    public void testWorldWorkflowIntegration() {
        // Create a realistic scenario
        Tile dirt = new Dirt(96, 96);
        Tile grass = new Grass(128, 128);

        // Place tiles
        world.place(dirt);
        world.place(grass);

        // Verify placement
        assertEquals("World should contain 2 tiles", 2, world.allTiles().size());

        // Test position queries
        List<Tile> tilesAtDirt = world.tilesAtPosition(96, 96, mockDimensions);
        List<Tile> tilesAtGrass = world.tilesAtPosition(128, 128, mockDimensions);

        assertEquals("Should find dirt tile", 1, tilesAtDirt.size());
        assertEquals("Should find grass tile", 1, tilesAtGrass.size());
        assertTrue("Should contain dirt", tilesAtDirt.contains(dirt));
        assertTrue("Should contain grass", tilesAtGrass.contains(grass));

        // Test rendering
        List<Renderable> renderables = world.render();
        assertFalse("Should have renderables", renderables.isEmpty());

        // Test ticking (should not throw exceptions)
        world.tick(mockEngineState, mockGameState);
    }

    @Test
    public void testDimensionsIntegration() {
        // Use real TileGrid dimensions
        Dimensions realDimensions = new TileGrid(25, 800);

        Tile tile = new Dirt(400, 400); // Center of 800x800 grid
        world.place(tile);

        List<Tile> tiles = world.tilesAtPosition(400, 400, realDimensions);
        assertEquals("Should find tile with real dimensions", 1, tiles.size());
        assertTrue("Should contain the placed tile", tiles.contains(tile));
    }
}