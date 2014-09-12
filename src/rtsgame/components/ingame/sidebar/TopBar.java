/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.sidebar;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import rtsgame.Game;
import rtsgame.components.Button;
import rtsgame.components.HoverText;
import rtsgame.components.ingame.Player;
import rtsgame.resources.Images;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class TopBar {
    private Button menu;
    
    public TopBar(final BaseGameState bgs){
        menu = new Button(Images.MENU, 750, 0, new Input((int)Game.GCHEIGHT)){

            @Override
            protected void onClick() {
                bgs.toMenu();
            }
        };
    }
    
    public void update(){
        menu.update();
    }
    
    public void render(Graphics g) throws SlickException{
        g.setColor(Color.black);
        new HoverText("Morale: " + Player.morale, 600, 32).render(g);
        menu.render(g);
    }
}
