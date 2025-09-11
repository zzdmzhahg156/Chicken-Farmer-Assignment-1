package builder.player;

import builder.GameState;
import builder.Tickable;
import builder.entities.tiles.Tile;
import builder.ui.RenderableGroup;
import builder.world.World;
import engine.EngineState;
import engine.game.Direction;
import engine.input.KeyState;
import engine.input.MouseState;
import engine.renderer.Dimensions;
import engine.renderer.Renderable;

import java.util.List;

/**
 *
 Manages the users interaction with the player through keyboard/mouse interactions.
 Stores and provides access to the player instance and renders the player via the render method.
 */
public class PlayerManager implements Tickable, RenderableGroup {
    private final ChickenFarmer chickenFarmer;

    /**
     * Construct a new player manager and a new player instance at the given x, y position.
     * Parameters:
     * x - The x-axis (horizontal) coordinate to spawn the player.
     * y - The y-axis (vertical) coordinate to spawn the player.
     * Requires:
     * x, y is a valid position within the game, i.e. positive and less than the window size.
     */
    public PlayerManager(int x, int y) {
        this.chickenFarmer = new ChickenFarmer(x, y);
    }

    private boolean canMove(EngineState state, GameState game) {
        if (game == null) {
            return true;
        }

        World world = game.getWorld();
        if (world == null) {
            return true;
        }

        KeyState keys = state.getKeys();
        int targetX = chickenFarmer.getX();
        int targetY = chickenFarmer.getY();
        int moveAmount = state.getDimensions().tileSize() / 2;

        if (keys.isDown('w')) {
            targetY -= moveAmount;
        } else if (keys.isDown('s')) {
            targetY += moveAmount;
        } else if (keys.isDown('a')) {
            targetX -= moveAmount;
        } else if (keys.isDown('d')) {
            targetX += moveAmount;
        }

        Dimensions dimensions = state.getDimensions();
        List<Tile> tilesAtTarget = world.tilesAtPosition(targetX, targetY, dimensions);

        for (Tile tile : tilesAtTarget) {
            if (!tile.canWalkThrough()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void tick(EngineState state, GameState game) {
        KeyState keys = state.getKeys();
        this.chickenFarmer.tick(state);

        if (keys.isDown('w') && canMove(state, game)) {
            chickenFarmer.move(Direction.NORTH, 1);
        } else if (keys.isDown('s') && canMove(state, game)) {
            chickenFarmer.move(Direction.SOUTH, 1);
        } else if (keys.isDown('a') && canMove(state, game)) {
            chickenFarmer.move(Direction.WEST, 1);
        } else if (keys.isDown('d') && canMove(state, game)) {
            chickenFarmer.move(Direction.EAST, 1);
        }

        World world = game.getWorld();
        Dimensions dimension = state.getDimensions();

        List<Tile> tiles = world.tilesAtPosition(
                chickenFarmer.getX(), chickenFarmer.getY(), dimension);
        for (Tile tile : tiles) {
            tile.interact(state, game);
        }

        MouseState mouse = state.getMouse();
        if (mouse.isLeftPressed()) {
            chickenFarmer.use(game.getInventory().getHolding());

            for (Tile tile : tiles) {
                tile.use(state, game);
            }

        }
    }

    /**
     * Returns the player instance managed by this manager.
     * Returns:
     * The player instance.
     */
    public Player getPlayer() {
        return chickenFarmer;
    }


    @Override
    public List<Renderable> render() {
        return List.of(chickenFarmer);
    }
}
