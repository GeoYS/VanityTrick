/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.sidebar;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import rtsgame.components.HoverText;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.units.Unit;
import rtsgame.resources.Images;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class UnitSidebar {
    private Minimap minimap;
    private ArrayList<Unit> units;
    private UnitSidebarView unitView;
    private UnitSidebarButtons unitButtons;
    private UnitSidebarSelected unitSelected;
    private TopBar topbar;
    public static HoverText hovertext;
    
    public UnitSidebar(ArrayList<Unit> u, Camera c, BaseGameState bgs) throws SlickException{
        minimap = new Minimap(u, c, bgs);
        units = u;
        unitView = new UnitSidebarView();
        unitButtons = new UnitSidebarButtons();
        unitSelected = new UnitSidebarSelected(u);
        topbar = new TopBar(bgs);
        hovertext = null;
    }
    
    public void update() throws SlickException{
        topbar.update();
        unitSelected.update();
    }
    
    public Minimap getMinimap(){
        return minimap;
    }
    
    public void render(Graphics g) throws SlickException{
        g.drawImage(Images.SIDEBAR, 0, 720 - Images.SIDEBAR.getHeight());
        minimap.render(g);
        Unit unit = null;
        for(int u = 0; u < units.size(); u++){
            Unit tu = units.get(u);
            
            if(tu.isSelected()){
                unit = tu;
                break;
            }
        }
        unitView.render(g, unit);
        unitButtons.render(g, unit);
        unitSelected.render(g);
        topbar.render(g);
        
        
        if(hovertext != null){
            hovertext.render(g);
            hovertext = null;
        }
    }
}
