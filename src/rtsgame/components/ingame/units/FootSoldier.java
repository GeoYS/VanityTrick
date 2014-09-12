/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.units;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.resources.Images;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class FootSoldier extends Unit{
    public FootSoldier(Vector2f p, BaseGameState bgs){
        super(p, bgs);
        super.name = "Soldier";
    }

    @Override
    protected Image getSpriteSheet() {
        return Images.FOOTSOLDIERSPRITESHEET;
    }

    @Override
    protected Image getAttackingImage() {
        return Images.FOOTSOLDIERSHOOTING;
    }
    
}
