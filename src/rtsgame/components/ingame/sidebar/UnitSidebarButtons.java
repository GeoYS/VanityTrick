/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.sidebar;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import rtsgame.components.Button;
import rtsgame.components.ingame.units.Unit;

/**
 *
 * @author GeoYS_2
 */
public class UnitSidebarButtons {
    
    public UnitSidebarButtons(){
    }
    
    public void render(Graphics g, Unit u) throws SlickException{
        if(u != null && !u.getButtons().isEmpty()){
            for(Button b : u.getButtons()){
                b.render(g);
            }
        }
    }
}
