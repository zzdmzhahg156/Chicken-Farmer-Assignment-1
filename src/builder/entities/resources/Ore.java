package builder.entities.resources;

import builder.GameState;
import builder.entities.Usable;
import builder.inventory.items.Jackhammer;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.SpriteGroup;
import engine.game.Entity;

/**
 *
 An entity that is stacked on an OreVein and yields coins when mined.
 The ore initially has 10 coins and can be mined by the player using the jackhammer.
 The ore is initially rendered as 'default' within SpriteGallery.rock.
 */
public class Ore extends Entity implements Usable {
    private static final SpriteGroup ore = SpriteGallery.rock;
    private int remainingValue;

    /**
     Construct a new ore entity at the given x, y position.
     Initially the ore is rendered as 'default' within SpriteGallery.rock.

     Parameters:
     x - The x-axis (horizontal) coordinate.
     y - The y-axis (vertical) coordinate.
     Requires:
     x >= 0, x is less than the window width, y >= 0, y is less than the window height
     */
    public Ore(int x, int y) {
        super(x, y);
        setSprite(ore.getSprite("default"));
        this.remainingValue = 10;
    }

    @Override
    public void use(EngineState state, GameState game) {
        if (!(game.getInventory().getHolding() instanceof Jackhammer)) {
            return;
        }

        if (state.currentTick() % 5 != 0) {
            return;
        }

        if (remainingValue <= 0) {
            markForRemoval();
            return;
        }

        int damageDelt = game.getPlayer().getDamage();
        int coinExtract = Math.min(remainingValue, damageDelt);

        game.getInventory().addCoins(coinExtract);
        remainingValue -= coinExtract;
    }

    @Override
    public void tick(EngineState engineState) {
        if (remainingValue > 9) {
            setSprite(ore.getSprite("default"));
        }  else if (remainingValue > 1) {
            setSprite(ore.getSprite("damaged"));
        }  else {
            setSprite((ore.getSprite("depleted")));
        }
    }
}
