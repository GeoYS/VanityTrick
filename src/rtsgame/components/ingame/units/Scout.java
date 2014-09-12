/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.units;

import java.util.ArrayList;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.MyMath;
import rtsgame.states.BaseGameState;

/**
 *
 * @author geshe9243
 */
public class Scout extends FootSoldier{
    
    private float attackRange;
    
    public Scout(Vector2f p, BaseGameState bgs){
        super(p, bgs);
        super.name = "Scout";
        super.rangeOfSight = 750;
        super.speed = 6;
        super.hp = super.totalHp = 4;
        super.attackDamage = 0.5f;
        attackRange = 200;
    }
    
    public int getSubType(){
        return Unit.SCOUT;
    }

    @Override
    protected boolean readyToShoot(ArrayList<Unit> units) {
        return super.readyToShoot(units) && MyMath.distance(getLocation(), enemy.getLocation()) < attackRange;
    }

}
