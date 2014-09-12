/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.units;

import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.components.ingame.Camera;
import rtsgame.resources.Images;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class Grenadier extends FootSoldier{
    public Grenadier(Vector2f p, BaseGameState bgs){
        super(p, bgs);
        super.speed = 2;
        super.name = "Grenadier";
        super.hp = super.totalHp = 20;
        super.reloadTime = 40;
    }
    
    public int getSubType(){
        return Unit.GRENADIER;
    }           
            
    
    @Override
    protected void attackEnemy(ArrayList<Unit> units, Camera c) {

        if(enemy.getEnemy() == null /*|| (enemy.enemy.getType() == Unit.TANK && this.getType() != Unit.TANK)*/){
            enemy.setEnemy(this);
            enemy.setIsChasing(true);
        }
        shotTimer = reloadTime;

        Grenade grenade = new Grenade(new Vector2f(location), new Vector2f(enemy.getLocation()), gameState);
        
        units.add(grenade);
        c.addVFX(grenade);
    }
    
    @Override
    protected Image getAttackingImage() {
        return Images.ENGINEERSHOOTING;
    }

    @Override
    protected Image getSpriteSheet() {
        return Images.ENGINEERSPRITESHEET;
    }
}
