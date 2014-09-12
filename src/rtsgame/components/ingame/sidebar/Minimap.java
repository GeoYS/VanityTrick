 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.sidebar;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.units.Unit;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class Minimap {
    private ArrayList<Unit> units;
    public final float xscale, yscale, drawx, drawy;
    public final int width, length;
    private Image map;
    private Camera cam;
    
    public Minimap(ArrayList<Unit> u, Camera c, BaseGameState bgs) throws SlickException{
        units = u;
        width = 100;
        length = 100;
        map = new Image (width, length);
        drawx = 1280 - 140;
        drawy = 600;
        xscale = yscale = 100 / bgs.LENGTH;
        cam = c;
    }
    
    public Vector2f getTopLeft(){
        return new Vector2f(drawx, drawy);
    }
    
    public void render(Graphics g) throws SlickException{
        map.getGraphics().clear();
        map.getGraphics().setColor(Color.darkGray);
        map.getGraphics().fillRect(0, 0, 100, 100);
        map.getGraphics().setColor(Color.yellow);
        for(Unit u : units){
            if(u.getType() != Unit.GAIA && 
                    u.getType() != Unit.WALL &&
                    (u.getType() == Unit.BUILDING || !u.isEnemy() || u.isInSight())){
                if(u.isEnemy()){
                    map.getGraphics().setColor(Color.red);
                    map.getGraphics().fillOval(u.getLocation().x * xscale, u.getLocation().y * yscale, 2, 2);
                    map.getGraphics().setColor(Color.yellow);
                }
                else{
                    map.getGraphics().fillOval(u.getLocation().x * xscale, u.getLocation().y * yscale, 2, 2);
                }
            }
        }
        map.getGraphics().draw(new Rectangle(cam.pos.x * xscale, cam.pos.y * yscale, cam.width * xscale, cam.length * yscale));
        g.drawImage(map, drawx, drawy);
    }
}
