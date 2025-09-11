package builder.entities.resources;

import builder.GameState;
import builder.entities.Interactable;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.SpriteGroup;
import engine.game.Entity;
import engine.timing.RepeatingTimer;
import engine.timing.TimerDuration;

public class Cabbage extends Entity implements Interactable {
    private static final SpriteGroup cabbage = SpriteGallery.cabbage;
    public static final int COST = 2;
    private RepeatingTimer timer;
    private final String[] stages = {"default", "budding", "growing", "grown", "collectable"};
    private int currentStage;

    public Cabbage(int x, int y) {
        super(x, y);
        setSprite(cabbage.getSprite("default"));
        this.currentStage = 0;
        this.timer = new RepeatingTimer(TimerDuration.SHORT);
    }

    @Override
    public void tick(EngineState engineState) {
        timer.tick();

        if (timer.isFinished() && currentStage < stages.length - 1) {
            currentStage++;
            setSprite(cabbage.getSprite(stages[currentStage]));
        }


    }

    @Override
    public void interact(EngineState state, GameState game) {
        if (currentStage == stages.length - 1) {
            game.getInventory().addCoins(3);
            game.getInventory().addFood(COST);
            markForRemoval();
        }
    }
}
