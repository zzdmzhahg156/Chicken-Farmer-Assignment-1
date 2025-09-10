package builder.entities.resources;

import builder.GameState;
import builder.entities.Usable;
import builder.inventory.items.Jackhammer;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.SpriteGroup;
import engine.game.Entity;

public class Ore extends Entity implements Usable {
    private static final SpriteGroup ore = SpriteGallery.rock;
    private int remainingValue;

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
        }
        else if (remainingValue > 1) {
            setSprite(ore.getSprite("damaged"));
        }
        else {
            setSprite((ore.getSprite("depleted")));
        }
    }
}
