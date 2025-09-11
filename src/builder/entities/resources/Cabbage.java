package builder.entities.resources;

import builder.GameState;
import builder.entities.Interactable;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.SpriteGroup;
import engine.game.Entity;
import engine.timing.RepeatingTimer;
import engine.timing.TimerDuration;

/**
 *
 An entity planted (stacked on) Dirt that grows and can be collected by the player once grown.
 A cabbage is initially rendered as 'default' within SpriteGallery.cabbage.
 */
public class Cabbage extends Entity implements Interactable {
    private static final SpriteGroup cabbage = SpriteGallery.cabbage;
    public static final int COST = 2;
    private RepeatingTimer timer;
    private final String[] stages = {"default", "budding", "growing", "grown", "collectable"};
    private int currentStage;

    /**
     * Construct a new cabbage entity at the given x, y position.
     * Initially the cabbage is rendered as 'default' within SpriteGallery.cabbage.
     * Parameters:
     * x - The x-axis (horizontal) coordinate.
     * y - The y-axis (vertical) coordinate.
     * Requires:
     * x >= 0, x is less than the window width, y >= 0, y is less than the window height
     */
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
