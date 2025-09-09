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

import java.util.List;

public abstract class Tile
        extends Entity
        implements Interactable, Usable, RenderableGroup, HasTick {
    private int x;
    private int y;
    private SpriteGroup art;

    public Tile(int x,
                int y,
                SpriteGroup art) {
        super(x, y);
        this.art = art;
    }

    public void setArt(SpriteGroup art) {

    }

    public void updateSprite(String artName)
            throws ArtNotFoundException {}

    @Override
    public void tick(EngineState engine){}

    public List<Entity> getStackedEntities(){
        return null;
    }

    public void placeOn(Entity tile) {}

    @Override
    public void interact(EngineState state,
                         GameState game){}

    @Override
    public void use(EngineState state,
                    GameState game){}

    public boolean canWalkThrough(){
        return true;
    }

    @Override
    public List<Renderable> render(){
        return null;
    }



}
