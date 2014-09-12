/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.MyMath;
import rtsgame.components.ingame.units.Unit;

/**
 *
 * @author GeoYS_2
 */
public class Selector {
    private Input input;
    protected boolean mouseDown, fKeyDown;
    private int clickTimer;
    private Vector2f origin, current;
    private Rectangle r;
    private Camera cam;
    
    public Selector(Input in, Camera c){
        clickTimer = 0;
        input = in;
        mouseDown = fKeyDown = false;
        current = new Vector2f(0, 0);
        origin = new Vector2f(0, 0);
        cam = c;
    }
    
    private int getNumDown(){
        for(int i = Input.KEY_1; i <= Input.KEY_9; ++i){
            if(input.isKeyDown(i))
                return i;
        }
        return Unit.UNGROUPED;
    }
    
    
    
    public void update(ArrayList<Unit> units){
        
        if(clickTimer > 0){
            clickTimer--;
        }
        // shortcut for selecting all foot soldiers in camera
        if(input.isKeyDown(Input.KEY_F)){
            fKeyDown = true;
        }
        
        if(!input.isKeyDown(Input.KEY_F) && fKeyDown){
            fKeyDown = false;
            for(Unit u : units){
                if(u.getType() == Unit.UNIT && cam.contains(u.getLocation().x, u.getLocation().y)){
                    u.select();
                }
                else{
                    u.deselect();
                }
            }
        }
        
        // set group
        if(input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL)){
            int numDown = getNumDown();
            if(input.isKeyDown(Input.KEY_Q)){
                for(Unit u : units){
                    if(!u.isEnemy()){
                        if(u.isSelected()){
                            u.setGroup(numDown);
                        }
                    }
                }
            }
            else if(numDown != Unit.UNGROUPED){
                for(Unit u : units){
                    if(!u.isEnemy()){
                        if(u.isSelected()){
                            u.setGroup(numDown);
                        }
                        else if(u.getGroup() == numDown){
                            u.setGroup(Unit.UNGROUPED);
                        }
                    }
                }
            }
        }
        // select group
        else if(getNumDown() != Unit.UNGROUPED){
            for(Unit u : units){
                if(u.getGroup() == getNumDown()){
                    u.select();
                }
                else{
                    u.deselect();
                }
            }
        }
        // select units on map
        if(!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
            if(mouseDown){
                mouseDown = false;
                for(int u = 0; u < units.size(); u++){
                    if(r.contains(units.get(u).getLocation().x,
                            units.get(u).getLocation().y)  ||
                            MyMath.distance(units.get(u).getLocation(), current) < units.get(u).getRadius() ||
                            (units.get(u).getType() == Unit.BUILDING && 
                            MyMath.distance(units.get(u).getLocation(), current) < units.get(u).getRadius() )){
                        if(units.get(u).isAlive())
                            units.get(u).select();
                        if(input.isKeyDown(Input.KEY_U)){
                            units.get(u).deselect();
                        }
                    }
                    else if(input.getMouseY() <= 580 && !input.isKeyDown(Input.KEY_U) && !input.isKeyDown(Input.KEY_LSHIFT) && !input.isKeyDown(Input.KEY_RSHIFT)) {
                        units.get(u).deselect();
                    }
                }
                if(clickTimer > 0){
                    clickTimer = 0;
                    int NotAUnitType = 195732;
                    int unitType = NotAUnitType;
                    boolean enemy = false;
                    for(int u = 0; u < units.size(); u++){
                        if(MyMath.distance(units.get(u).getLocation(), current) < units.get(u).getRadius()){
                            if(units.get(u).isAlive()){
                                units.get(u).select();
                                unitType = units.get(u).getSubType();
                                enemy = units.get(u).isEnemy();
                                break;
                            }
                        }
                    }
                    for(int u = 0; u < units.size(); u++){
                        if(units.get(u).isAlive() &&
                                cam.contains(units.get(u).getLocation().x, units.get(u).getLocation().y) &&
                                enemy == units.get(u).isEnemy() && 
                                units.get(u).getSubType() == unitType){
                            units.get(u).select();
                        }
                        else{
                            units.get(u).deselect();
                        }
                    }
                }
                else{
                    clickTimer = 10;
                }
            }
        }
        else{
            if(!mouseDown && input.getMouseY() < 580){
                mouseDown = true;
                origin.x = current.x ;
                origin.y = current.y ;
            }
        }
        
        current.x = input.getMouseX() + cam.pos.x;
        current.y = input.getMouseY() + cam.pos.y;
        //checks if selector is in sidebar
        current.y = current.y > cam.pos.y + 580 ? cam.pos.y + 580 : current.y;
        r = newRectangle();
    }
    
    
    private Rectangle newRectangle(){
        float nx = origin.x, ny = origin.y, xdif = current.x - origin.x, ydif = current.y - origin.y;
        if(xdif < 0){
            nx = origin.x + xdif;
        }
        if(ydif < 0){
            ny = origin.y + ydif;
        }
        return new Rectangle(nx, ny, Math.abs(xdif), Math.abs(ydif));
    }
    
    public void render(Graphics g){
        
        
        if(mouseDown){
            g.setColor(Color.green);
            g.draw(new Rectangle(r.getX() - cam.pos.x, r.getY() - cam.pos.y, r.getWidth(), r.getHeight()));
        }
    }
}
