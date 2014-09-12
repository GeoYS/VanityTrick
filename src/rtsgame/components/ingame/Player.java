/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import rtsgame.components.ingame.sidebar.Minimap;
import rtsgame.components.ingame.units.Unit;

/**
 *
 * @author GeoYS_2
 */
public class Player {
    public static Input input;
    public static int kills, losses, morale;
    private Controller controller;
    
    public Player(GameContainer gc, Camera c, Minimap map, boolean editor){
        input = new Input(gc.getHeight());
        controller = new Controller(input, c, map, editor);
        kills = losses = morale = 0;
    }
    
    public void update(ArrayList<Unit> units, int delta){
        controller.update(units, delta);
        if(input.isKeyDown(Input.KEY_ESCAPE)){
            System.exit(0);
        }
    }
    
    public void render(Graphics g){
        controller.render(g);
    }
}
