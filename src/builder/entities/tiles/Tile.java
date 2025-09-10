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

public abstract class Tile
        extends Entity
        implements Interactable, Usable, RenderableGroup, HasTick {
    private SpriteGroup art;
    private List<Entity> stackedEntitites;

    public Tile(int x,
                int y,
                SpriteGroup art) {
        super(x, y);
        this.art = art;
        this.stackedEntitites = new ArrayList<>();
        updateSprite("default");
    }

    public void setArt(SpriteGroup art) {
        this.art = art;
        updateSprite("default");
    }

    public void updateSprite(String artName)
            throws ArtNotFoundException {
        setSprite(art.getSprite(artName));
    }

    @Override
    public void tick(EngineState engine){
        Iterator<Entity> it = stackedEntitites.iterator();
        while (it.hasNext()) {
            Entity entity = it.next();
            if (entity.isMarkedForRemoval()) {
                it.remove();
            }
        }
        for (Entity entity: stackedEntitites) {
            if (entity instanceof HasTick tickable) {
                tickable.tick(engine);
            }
        }

    }
    public List<Entity> getStackedEntities(){
        return new ArrayList<>(stackedEntitites);
    }

    public void placeOn(Entity tile) {
        if (tile != null && !stackedEntitites.contains(tile)) {
            stackedEntitites.add(tile);
        }
    }

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
        List<Renderable> renderables= new ArrayList<>();
        renderables.add(this);
        renderables.addAll(stackedEntitites);
        return renderables;
    }



}
