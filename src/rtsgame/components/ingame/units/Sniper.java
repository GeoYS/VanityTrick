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
import rtsgame.components.ingame.vfx.ShotVFX;
import rtsgame.resources.Images;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class Sniper extends FootSoldier{
        
    private float attackRange;
    
    public Sniper(Vector2f p, BaseGameState bgs){
        super(p, bgs);        
        super.name = "Sniper";
        super.hp = super.totalHp = 1.5f;
        super.attackAccuracy = 0.90f;
        super.accuracyOffset = 425;
        super.attackDamage = 5;
        super.rangeOfSight = 100;
        super.reloadTime = 70;
        attackRange = 1000;
    }
    
    public int getSubType(){
        return Unit.SNIPER;
    }

    @Override
    protected void attackEnemy(ArrayList<Unit> units, Camera c) {
        super.attackEnemy(units, c);
        // shooting vfx 
        Vector2f diff = new Vector2f(location.x - enemy.location.x, location.y - enemy.location.y);
        float pushDis = radius;
        Vector2f push = diff.normalise().scale(pushDis);
        c.addVFX(new ShotVFX(location.x - push.x, location.y - push.y, enemy.location.x, enemy.location.y, MyMath.distance(location, enemy.location)));
    }
    @Override
    protected boolean canShoot(Unit enemy) {
        return super.canShoot(enemy) || (enemy.isInSight() && MyMath.distance(location, enemy.getLocation()) < attackRange);
    }

    @Override
    protected Image getAttackingImage() {
        return Images.SNIPERSHOOTING;
    }

}
