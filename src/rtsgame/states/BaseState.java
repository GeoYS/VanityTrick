/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.states;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import rtsgame.MyMath;
import rtsgame.components.ingame.units.Unit;
import rtsgame.resources.Images;

/**
 *
 * @author GeoYS_2
 */
public class BaseState extends BasicGameState{

    public Input input;
    public static int ID = 0;
    
    @Override
    public int getID() {
        //int NotImportantNumber = 123098512;
        return ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        input = new Input(gc.getHeight());
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        drawCursor(grphcs);
    }

    public void drawCursor(Graphics g){
        g.drawImage(Images.CURSOR, input.getMouseX() - 7, input.getMouseY() - 3);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(input.isKeyDown(Input.KEY_ESCAPE)){
            gc.exit();
        }
    }
    
}
