package builder;

import builder.inventory.Inventory;
import builder.player.Player;

public class JavaBeanGameState implements GameState{
    public JavaBeanGameState( Player player, Inventory inventory){

    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public Player getPlayer(){

        return null;
    }

//    public World getWorld(){}
}
