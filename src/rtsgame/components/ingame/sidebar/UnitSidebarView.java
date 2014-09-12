/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.sidebar;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import rtsgame.components.ingame.units.Unit;

/**
 *
 * @author GeoYS_2
 */
public class UnitSidebarView {
    private Image view;
    
    public UnitSidebarView() throws SlickException{
        view = new Image(100, 100);
    }
    
    public void render(Graphics g, Unit u) throws SlickException{
        view.getGraphics().clear();
        view.getGraphics().setColor(Color.black);
        view.getGraphics().fillRect(0, 0, 100, 100);
        if(u != null){
            u.renderSidebar(view.getGraphics());
            view.getGraphics().setColor(Color.green);
            view.getGraphics().fillRect(0, 90, 100 * u.getHp() / u.getTotalHp(), 10);
        }
        g.drawImage(view, 20, 600);
    }
}
