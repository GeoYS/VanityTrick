/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.MyMath;
import rtsgame.components.ingame.sidebar.Minimap;
import rtsgame.components.ingame.units.Building;
import rtsgame.components.ingame.units.FootSoldier;
import rtsgame.components.ingame.units.Gaia;
import rtsgame.components.ingame.units.Grenadier;
import rtsgame.components.ingame.units.Nest;
import rtsgame.components.ingame.units.Scout;
import rtsgame.components.ingame.units.Sniper;
import rtsgame.components.ingame.units.Tank;
import rtsgame.components.ingame.units.Unit;
import rtsgame.components.ingame.units.Wall;

/**
 *
 * @author GeoYS_2
 */
public class Controller {
    private Input input;
    private Selector selector;
    private boolean rightMouseDown , leftMouseDown;
    private Line wall;
    private ArrayList<Unit> unitsToPlace;
    private float flagx, flagy, flagcounter;
    private Camera cam;
    private Minimap minimap;
    private boolean edit;
    
    public Controller(Input in, Camera c, Minimap map, boolean editor){
        input = in;
        selector = new Selector(in, c);
        rightMouseDown = leftMouseDown = false;
        wall = null;
        flagx = 0;
        flagy = 0;
        flagcounter = 0;
        cam = c;
        minimap = map;
        unitsToPlace = new ArrayList();
        edit = editor;
    }
    
    public boolean isPlacing(){
        return !unitsToPlace.isEmpty();
    }
    
    public void update(ArrayList<Unit> units, int delta){
        if(edit){
            if(input.isKeyDown(Input.KEY_F1)){
                unitsToPlace.clear();
                wall = null;
                unitsToPlace.add(new FootSoldier(new Vector2f(input.getMouseX(), input.getMouseY()), cam.getGame()));
            }

            if(input.isKeyDown(Input.KEY_F2)){
                unitsToPlace.clear();
                wall = null;
                unitsToPlace.add(new Tank(new Vector2f(input.getMouseX(), input.getMouseY()), cam.getGame()));
            }

            if(wall == null && input.isKeyDown(Input.KEY_F3) && (unitsToPlace.isEmpty() || unitsToPlace.get(0).getType() != Unit.WALL)){
                unitsToPlace.clear();
                wall = null;
                unitsToPlace.add(new Wall(new Vector2f(input.getMouseX(), input.getMouseY()), 0, Wall.START, cam.getGame()));
            }
            if(input.isKeyDown(Input.KEY_F4)){
                unitsToPlace.clear();
                wall = null;
                unitsToPlace.add(new Nest(new Vector2f(input.getMouseX(), input.getMouseY()), cam.getGame()));
            }
            if(input.isKeyDown(Input.KEY_F5)){
                unitsToPlace.clear();
                wall = null;
                unitsToPlace.add(new Sniper(new Vector2f(input.getMouseX(), input.getMouseY()), cam.getGame()));
            }

            if(input.isKeyDown(Input.KEY_F6)){
                unitsToPlace.clear();
                wall = null;
                unitsToPlace.add(new Grenadier(new Vector2f(input.getMouseX(), input.getMouseY()), cam.getGame()));
            }

            if(input.isKeyDown(Input.KEY_F7)){
                unitsToPlace.clear();
                wall = null;
                unitsToPlace.add(new Scout(new Vector2f(input.getMouseX(), input.getMouseY()), cam.getGame()));
            }
            
            if(input.isKeyDown(Input.KEY_F8)){
                unitsToPlace.clear();
                wall = null;
                unitsToPlace.add(new Building(new Vector2f(input.getMouseX(), input.getMouseY()), cam.getGame()));
            }
            
            if(input.isKeyDown(Input.KEY_F9)){
                unitsToPlace.clear();
                wall = null;
                unitsToPlace.add(new Gaia(new Vector2f(input.getMouseX(), input.getMouseY()), cam.getGame()));
            }
        }
        
        // clear all placing units values
        if(input.isKeyDown(Input.KEY_Q)){
            unitsToPlace.clear();
            wall = null;
        }
        
        // update line on which wall will be placed
        if(wall != null){
            wall = new Line(wall.getX1(), wall.getY1(), input.getMouseX(), input.getMouseY());
            unitsToPlace.clear();
            Vector2f dist = new Vector2f(wall.getEnd().sub(wall.getStart()));
            for(float d = 0; d <= wall.length() / 32 /*wall circumference*/; ++d){
                int position;
                if(d == 0){
                    position = Wall.START;
                }
                else if(d + 1 > wall.length() / 32){
                    position = Wall.END;
                }
                else{
                    position = Wall.MIDDLE;
                }
                Vector2f indivDist = new Vector2f(dist).normalise().scale(d * 32);
                unitsToPlace.add(new Wall(new Vector2f(wall.getX1() + indivDist.x + cam.pos.x, wall.getY1() + indivDist.y + cam.pos.y), (float)Math.toDegrees(Math.atan2(dist.y, dist.x)), position, cam.getGame()));
            }
        }
        
        // update unit-To-Place position
        if(!unitsToPlace.isEmpty()){
            if(unitsToPlace.size() == 1 && wall == null){
                unitsToPlace.get(0).getLocation().x = input.getMouseX() + cam.pos.x;
                unitsToPlace.get(0).getLocation().y = input.getMouseY() + cam.pos.y;
            }
        }
        
        if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
            rightMouseDown = true;
        }
        
        if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !selector.mouseDown){
            leftMouseDown = true;
            // minimap unit control
            if(new Rectangle(minimap.getTopLeft().x, minimap.getTopLeft().y, minimap.width, 100).contains(input.getMouseX(), input.getMouseY())){

                cam.pos.x = (input.getMouseX() - minimap.getTopLeft().x) / minimap.xscale - cam.width/2;
                cam.pos.y = (input.getMouseY() - minimap.getTopLeft().y) / minimap.yscale - cam.length/2;
            }
        }
        
        if(!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !selector.mouseDown){
            if(leftMouseDown){
                leftMouseDown = false;
                                
                // placing unit
                if(!unitsToPlace.isEmpty()){
                    if(input.getMouseY() > 580){
                        unitsToPlace.clear();
                        wall = null;
                    }
                    else{ 
                        // placing wall(barbed wire)
                        if(!unitsToPlace.isEmpty() && unitsToPlace.get(0).getType() == Unit.WALL){
                            if(wall == null){
                                wall = new Line(input.getMouseX(), input.getMouseY(), input.getMouseX(), input.getMouseY());
                            }
                            else{
                                wall = null;
                                // removes overlapping barbed wire
                                for(int u = unitsToPlace.size() - 1; u >= 0; --u){
                                    for(Unit unit : units){
                                        if(MyMath.distance(unitsToPlace.get(u).getLocation(), unit.getLocation()) < unit.getRadius() + unitsToPlace.get(u).getRadius()){
                                            unitsToPlace.remove(u);
                                            break;
                                        }
                                    }
                                }
                                units.addAll(unitsToPlace);
                                unitsToPlace.clear();
                            }
                        }
                        // placing all other units
                        else{
                            units.addAll(unitsToPlace);
                            unitsToPlace.clear();
                        }
                        unitsToPlace.clear();
                    }
                }
            }
        }
        
        // move unit to spot on minimap
        if(!input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
            if(rightMouseDown){
                if(new Rectangle(minimap.drawx, minimap.drawy, 100, 100).contains(input.getMouseX(), input.getMouseY())){
                    
                    rightMouseDown = false;
                    flagcounter = 0;
                    flagx = (input.getMouseX() - minimap.drawx) / minimap.xscale;
                    flagy = (input.getMouseY() - minimap.drawy) / minimap.yscale;
                    for(int u = 0; u < units.size(); ++u){
                        if(units.get(u).isSelected()){
                            if(!units.get(u).isEnemy()){
                                if(!input.isKeyDown(Input.KEY_LSHIFT) && !input.isKeyDown(Input.KEY_RSHIFT)){
                                    units.get(u).setTarget(new Vector2f(flagx, flagy));
                                }
                                else{
                                    units.get(u).addTarget(new Vector2f(flagx, flagy));
                                }
                            }
                        }
                    }
                }
            }
        }
        
        
        if(flagx != 0 && flagy != 0){
            flagcounter += delta;
        }
        if(flagcounter > 1000){
            flagcounter = 0;
            flagx = 0;
            flagy = 0;
        }
        
        if(!isPlacing()){
            selector.update(units);
        }
        if(input.getMouseY() > 580){
            return;
        }
        
        // attack enemy
        if(!input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && rightMouseDown){
            rightMouseDown = false;
            boolean attacking = false;
            Unit enemy = null;
            for(int u = 0; u < units.size(); ++u){
                if(((units.get(u).isEnemy() && 
                        units.get(u).isInSight() && 
                        units.get(u).getType() != Unit.BUILDING) ||
                        (units.get(u).getType() == Unit.WALL))&&
                        MyMath.distance(units.get(u).getLocation(), new Vector2f(input.getMouseX() + cam.pos.x, input.getMouseY() + cam.pos.y)) < units.get(u).getRadius()){
                    attacking = true;
                    enemy = units.get(u);
                    break;
                }
            }
            if(attacking == true){
                for(int u = 0; u < units.size(); ++u){
                    if(!units.get(u).isEnemy() &&
                        units.get(u).isSelected()){
                        units.get(u).setIsChasing(true);
                        units.get(u).setEnemy(enemy);
                    }
                }
            }
            // move units
            else{
                flagx = input.getMouseX() + cam.pos.x;
                flagy = input.getMouseY() + cam.pos.y;
                flagcounter = 0;
                for(int u = 0; u < units.size(); ++u){
                    if(units.get(u).isSelected()){
                        if(!units.get(u).isEnemy()){
                            units.get(u).setIsChasing(false);
                            if(units.get(u).getEnemy() != null && units.get(u).getEnemy().getType() == Unit.WALL){
                                units.get(u).setEnemy(null);
                            }
                            if(!input.isKeyDown(Input.KEY_LSHIFT) && !input.isKeyDown(Input.KEY_RSHIFT)){
                                units.get(u).setTarget(new Vector2f(flagx, flagy));
                            }
                            else{
                                units.get(u).addTarget(new Vector2f(flagx, flagy));
                            }
                        }
                    }
                }
            }
        }
        if(input.isKeyDown(Input.KEY_Q)){
            for(Unit u : units){
                if(u.isSelected()){
                    u.setTarget(u.getLocation());
                }
            }
        }
        if(input.isKeyDown(Input.KEY_DELETE)){
            for(Unit u : units){
                if(u.isSelected() &&
                        u.getType() != Unit.BUILDING && 
                        !u.isEnemy()){
                    u.setHp(0);
                }
            }
        }
        
    }
    
    public void render(Graphics g){
        
        if(!isPlacing()){
            selector.render(g);
        }
        if(flagx != 0 && flagy != 0){
            g.setColor(Color.red);
            g.draw(new Circle(flagx - cam.pos.x, flagy - cam.pos.y, 3));
        }
        // draw unit to place
        if(!unitsToPlace.isEmpty() && input.getMouseY() < 580 - unitsToPlace.get(0).getRadius()){
            for(Unit u : unitsToPlace){
                u.render(g, cam);
            }
        }
    }
}
