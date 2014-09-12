/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.units;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.MyMath;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.vfx.ShotVFX;
import rtsgame.resources.Images;
import rtsgame.resources.Sounds;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class Nest extends Tank{
    
    private Image base, gun;
    
    public Nest(Vector2f p, BaseGameState bgs){
        super(p, bgs);
        super.radius = 26;
        super.attackAccuracy = 0.2f;
        super.attackDamage = 0.5f;
        super.speed = 0;
        super.reloadTime = 3;
        super.hp = super.totalHp = 20;
        base = Images.MACHINEGUNTRIPOD.getScaledCopy((int)radius * 2, (int)radius * 2);
        //base.rotate((float)Math.toDegrees(Math.random() * 2 * Math.PI));
        gun = Images.MACHINEGUN.getScaledCopy((int)radius * 2, (int)radius * 2);
    }
    @Override
    public void update(ArrayList<Unit> units, Camera c) {
        super.update(units, c);
        if(enemy != null && (!super.bulletTrajectoryGood(units) || MyMath.distance(location, enemy.location) > rangeOfSight)){
            enemy = null;
        
            isChasing = false;
        }
    }
    
    @Override
    protected void attackEnemy(ArrayList<Unit> units, Camera c) {
        attack(enemy);
        if(enemy.enemy == null || (enemy.enemy.getType() == Unit.TANK && this.getType() != Unit.TANK)){
            enemy.setEnemy(this);
            enemy.isChasing = true;
        }
        shotTimer = reloadTime;
        // shooting vfx 
        Vector2f diff = new Vector2f(location.x - enemy.location.x, location.y - enemy.location.y);
        float pushDis = radius;
        Vector2f push = diff.normalise().scale(pushDis);
        c.addVFX(new ShotVFX(location.x - push.x, location.y - push.y, enemy.location.x, enemy.location.y, radius * 2));
        shotSound();
    }
    @Override
    protected void shotSound() {
        Sounds.MACHINEGUN.playAt(1, 0.5f, location.x, location.y, 0);
    }
    
    @Override
    public int getType(){
        return Unit.NEST;
    }

    @Override
    public Image getSidebarImage() {
        return gun;
    }
    
    @Override
    protected Image getSpriteImage() {
        return base;
    }

    @Override
    public void renderSprite(Graphics g, Camera c) {
        Image gunR = gun.getScaledCopy((int)radius * 2, (int)radius * 2);
        
        if(isAlive() && enemy != null){            
            gunR.rotate(-(float)Math.toDegrees(Math.atan2(-enemy.location.x + location.x, -enemy.location.y + location.y)));
        }
        else if(!targets.isEmpty()){
             gunR.rotate(-(float)Math.toDegrees(Math.atan2(-targets.get(0).x + location.x, -targets.get(0).y + location.y)));
        }
        
        g.drawImage(base, location.x - c.pos.x - radius, location.y  - c.pos.y - radius);
        g.drawImage(gunR, location.x - c.pos.x - radius, location.y  - c.pos.y - radius);
    }

    @Override
    public Unit copy() {
        return new Nest(location, gameState);
    }
}
