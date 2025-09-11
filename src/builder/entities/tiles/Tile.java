package builder.entities.tiles;

import builder.GameState;
import builder.entities.Interactable;
import builder.entities.Usable;
import builder.ui.RenderableGroup;
import engine.EngineState;
import engine.art.ArtNotFoundException;
import engine.art.sprites.SpriteGroup;
import engine.game.Entity;
import engine.game.HasTick;
import engine.renderer.Renderable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a tile on the 'ground' of our world. Each tile is responsible for managing:
 * what entities are stacked upon it,
 * gathering the Renderables for itself and its stacked entities, and
 * (in stage 3) interactions with itself and entities stacked upon it (related: Interactable and Usable).
 * Invariant:
 * getX() >= 0, getX() is less than the window height, getY() >= 0, getY() is less than the window width
 */
public abstract class Tile
        extends Entity
        implements Interactable, Usable, RenderableGroup, HasTick {
    private SpriteGroup art;
    private List<Entity> stackedEntitites;

    /**
     *
     Constructs an instance of Tile.
     Parameters:
     x - The x-axis (horizontal) coordinate.
     y - The y-axis (vertical) coordinate.
     art - The sprite group art to use for this tile,
     the tile will initially render as the 'default' sprite for this group.
     Requires:
     x >= 0, x is less than the window width, y >= 0, y is less than the window height,
     The given sprite group must contain a 'default' sprite.
     */
    public Tile(int x,
                int y,
                SpriteGroup art) {
        super(x, y);
        this.art = art;
        this.stackedEntitites = new ArrayList<>();
        updateSprite("default");
    }

    /**
     * Set the sprite group for this tile and updates the current sprite (see updateSprite(String)) to the 'default' sprite of the given group.
     * Parameters:
     * art - A sprite group to use for this tile's sprites.
     * Requires:
     * The given sprite group must contain a 'default' sprite.
     */
    public void setArt(SpriteGroup art) {
        this.art = art;
        updateSprite("default");
    }

    /**
     * Parameters:
     * artName - The name of the art within the sprite group.
     * Throws:
     * ArtNotFoundException - If the given name doesn't exist within the sprite group.
     * Hint:
     * You don't need to do anything special to throw ArtNotFoundException, SpriteGroup.getSprite(String) will do it for you.
     */
    public void updateSprite(String artName)
            throws ArtNotFoundException {
        setSprite(art.getSprite(artName));
    }

    @Override
    public void tick(EngineState engine) {
        Iterator<Entity> it = stackedEntitites.iterator();

        while (it.hasNext()) {
            Entity entity = it.next();
            if (entity.isMarkedForRemoval()) {
                it.remove();
            }
        }
        for (Entity entity : stackedEntitites) {
            if (entity instanceof HasTick tickable) {
                tickable.tick(engine);
            }
        }

    }

    /**
     *
     Return the list of entities stacked upon this tile.
     Modifying the returned list must not modify the tile's state
     (although modifying the entities within will).

     Returns:
     Any entities stacked onto this tile, e.g. Cabbage.
     */
    public List<Entity> getStackedEntities() {
        return new ArrayList<>(stackedEntitites);
    }

    /**
     *Place the given tile on top of this tile.
     * Parameters:
     * tile - The tile instance to place.
     * Ensures:
     * The tile is contained within getStackedEntities()
     */
    public void placeOn(Entity tile) {
        if (tile != null && !stackedEntitites.contains(tile)) {
            stackedEntitites.add(tile);
        }
    }

    @Override
    public void interact(EngineState state,
                         GameState game) {
        for (Entity entity : stackedEntitites) {
            if (entity instanceof Interactable) {
                ((Interactable) entity).interact(state, game);
            }
        }
    }

    @Override
    public void use(EngineState state,
                    GameState game) {
        for (Entity entity : stackedEntitites) {
            if (entity instanceof Usable) {
                ((Usable) entity).use(state, game);
            }
        }
    }

    /**
     *
     Whether this tile can be walked through by other entities. True by default.
     Returns:
     true if this tile can be walked through, false otherwise.
     */
    public boolean canWalkThrough() {
        return true;
    }

    @Override
    public List<Renderable> render() {
        List<Renderable> renderables = new ArrayList<>();
        renderables.add(this);
        renderables.addAll(stackedEntitites);
        return renderables;
    }



}
