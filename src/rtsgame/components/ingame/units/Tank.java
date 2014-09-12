/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.units;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.MyMath;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.vfx.SplashVFX;
import rtsgame.resources.Images;
import rtsgame.resources.Sounds;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class Tank extends Unit{
    
    public static Image tankMovingSpriteSheet, tankCannon;
    private float splashRange;
    
    public Tank(Vector2f p, BaseGameState bgs){
        super(p, bgs);
        super.name = "Tank";
        super.speed = 1;
        super.attackAccuracy = 0.7f;
        super.attackDamage = 2;
        super.accuracyOffset = 150; // at 150 distance, 100% accuracy
        super.reloadTime = 2000 / 30;
        super.radius = 32;
        super.hp = super.totalHp = 100;
        super.rangeOfSight = 200;
        splashRange = 100;
    }
    @Override
    protected void shotSound() {
        Sounds.TANKSHOT.playAt(location.x, location.y, 0);
    }
    
    public static void LoadImages() throws SlickException{
        tankMovingSpriteSheet = new Image("res/tankspritesheet.png");
        tankCannon = new Image("res/tankcannon.png");
    }
    
    public int getType(){
        return Unit.TANK;
    }

    @Override
    protected void attackEnemy(ArrayList<Unit> units, Camera c) {
        super.attackEnemy(units, c);
        for(Unit u : units){
            if(MyMath.distance(enemy.location, u.location) < splashRange){
                if(u.isEnemy() != isEnemy()){
                    u.setEnemy(this);
                    u.isChasing = true;
                }
                u.hp -= 2 * Math.random();
            }
        }
        c.addVFX(new SplashVFX(enemy.location.x, enemy.location.y, splashRange));
    }
    
    @Override
    protected void attackBarbedWire(ArrayList<Unit> units, Camera c){
        attackEnemy(units, c);
    }
    
    @Override
    protected boolean readyToShoot(ArrayList<Unit> units){
        if(super.readyToShoot(units) && !isMoving){
            return true;
        }
        return false;
    }
    
    @Override
    protected Image getSpriteSheet(){
        return tankMovingSpriteSheet;
    }
    
    @Override
    protected Image getSpriteImage(){
        //sprite logic update
        if(isMoving){
            walkingTimer ++;
            if(walkingTimer >= cycleTime){
                walkingTimer = 0;
            }
        }
        else{
            walkingTimer = 0;
        }
        Image sprite;
        if(isAlive()){
            sprite = sprites[(int) MyMath.div(walkingTimer, 8)].getScaledCopy((int)radius * 2, (int)radius * 2);
        }
        else{
            sprite = Images.DEADTANK.getScaledCopy((int)radius*2, (int)radius*2);;
        }
        float rotDeg = -(float)Math.toDegrees(Math.atan2(lastLoc2.x - location.x, lastLoc2.y - location.y)/*(lastTarget.x - location.x, lastTarget.y - location.y)*/);
        
        /*if(!isMoving){
            rotDeg = -(float)Math.toDegrees(Math.atan2(lastTarget.x - location.x, lastTarget.y - location.y));
        
        }*/
        sprite.rotate(rotDeg);
                
        return sprite;
    }
    @Override
    public void renderSprite(Graphics g, Camera c) {
        super.renderSprite(g, c);
        Image cannon = tankCannon.getScaledCopy((int)radius * 2, (int)radius * 2);
        if(isAlive() && enemy != null){            
            cannon.rotate(-(float)Math.toDegrees(Math.atan2(-enemy.location.x + location.x, -enemy.location.y + location.y)));
        }
        else{
            float rotDeg = -(float)Math.toDegrees(Math.atan2(lastLoc2.x - location.x, lastLoc2.y - location.y)/*(lastTarget.x - location.x, lastTarget.y - location.y)*/);
        
            /*if(!isMoving){
                rotDeg = 180 -(float)Math.toDegrees(Math.atan2(lastTarget.x - location.x, lastTarget.y - location.y));

            }*/
            cannon.rotate(rotDeg);
        }
        g.drawImage(cannon, location.x - c.pos.x - radius, location.y  - c.pos.y - radius);
    }
    
    @Override
    public Unit copy(){
        return new Tank(location, gameState);
    }
}
