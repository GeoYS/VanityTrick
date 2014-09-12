/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.units;

import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.MyMath;
import rtsgame.components.ingame.Camera;
import rtsgame.resources.Images;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class Gaia extends Unit{
    private boolean initiating;
    
    public Gaia(Vector2f p, BaseGameState bgs){
        super(p, bgs);
        radius = 32;
        initiating = true;
    }

    @Override
    public int getType(){
        return Unit.GAIA;
    }
    
    @Override
    protected Image getSpriteImage() {
        return Images.TREE.getScaledCopy((int)radius * 2, (int)radius * 2);
    }
    
    @Override
    public void update(ArrayList<Unit> units, Camera c) {
        if(initiating){
            initiating = false;
            for(Unit u : units){
                if(!u.getLocation().equals(location) && (                    
                MyMath.distance(location, u.getLocation()) < radius + u.radius + Unit.COLLISIONBUFFER )){
                    Vector2f diff = new Vector2f(location.x - u.location.x, location.y - u.location.y);
                    float sumR =  radius + u.radius + Unit.COLLISIONBUFFER ;
                    float pushDis = (sumR - diff.length()) / 2;
                    Vector2f push = diff.normalise().scale(pushDis);
                    location.x += push.x;
                    location.y += push.y;
                    u.location.x -= push.x;
                    u.location.y -=  push.y;
                }
            }
        }
        else{
            selected = false;
            for(Unit u : units){
                if(u.getType() == Unit.GAIA){
                    if(!u.getLocation().equals(location) && (                    
                    MyMath.distance(location, u.getLocation()) < radius + u.radius + Unit.COLLISIONBUFFER )){
                        Vector2f diff = new Vector2f(location.x - u.location.x, location.y - u.location.y);
                        float sumR =  radius + u.radius + Unit.COLLISIONBUFFER ;
                        float pushDis = (sumR - diff.length()) / 2;
                        Vector2f push = diff.normalise().scale(pushDis);
                        location.x += push.x;
                        location.y += push.y;
                        u.location.x -= push.x;
                        u.location.y -=  push.y;
                    }
                }
            }
        }
    }
    
}
