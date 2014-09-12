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
import rtsgame.components.ingame.vfx.SplashVFX;
import rtsgame.components.ingame.vfx.VFX;
import rtsgame.resources.Images;
import rtsgame.resources.Sounds;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class Grenade extends Unit implements VFX{
    private Vector2f target;
    private boolean exploded;
    private float splashRange;
    
    public Grenade(Vector2f l, Vector2f t, BaseGameState bgs){
        super(l, bgs);
        super.radius = 8;
        super.speed = 4;
        splashRange = 100;
        exploded = false;
        target = t;
        enemy = this;
    }
    
    public int getType(){
        return Unit.GAIA;
    }
    
    public int getSubType(){
        return Unit.NONCOLLIDEABLE;
    }

    @Override
    public void update(ArrayList<Unit> units, Camera c) {
        selected = false;
        Vector2f travelVector = new Vector2f(target.x - location.x, target.y - location.y).normalise().scale(speed);
        /*float xvel = (MyMath.xDistance(location, targets.get(0)) /
                MyMath.distance(location, targets.get(0))),
                yvel = (MyMath.yDistance(location, targets.get(0)) /
                MyMath.distance(location, targets.get(0)));*/
        // add vel to pos
        location.x += travelVector.x;
        location.y += travelVector.y;
        if(MyMath.distance(location, target) < speed){
            for(Unit u : units){
                if(MyMath.distance(location, u.location) < splashRange){
                    if(u.getType() == Unit.TANK || u.getType() == Unit.BUILDING){
                        u.hp -= 20 * Math.random();
                    }
                    else{
                        u.hp -= 2 * Math.random();
                    }
                }
            }
            c.addVFX(new SplashVFX(location.x, location.y, splashRange));
            Sounds.EXPLOSION.playAt(1, 0.7f, location.x, location.y, 0);
            exploded = true;
        }
    }

    @Override
    protected Image getSpriteImage() {
        return Images.GRENADE.getScaledCopy((int)radius, (int)radius);
    }
    
    @Override
    public void update() {
    }

    @Override
    public boolean mustRemove() {
        return exploded;
    }
    
    @Override
    public boolean isFinished() {
        return exploded;
    }
}
