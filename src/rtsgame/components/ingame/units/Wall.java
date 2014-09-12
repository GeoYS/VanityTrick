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
public class Wall extends Gaia{
    public static final int START = 1;
    public static final int MIDDLE = 2;
    public static final int END = 3;
    
    private float rot;
    private Image sprite;

    public Wall(Vector2f p, float nrot, int position /* 1 for starting point, 2 for middle, 3 for end point*/, BaseGameState bgs){
        super(p, bgs);
        
        radius = 16;
        rot = nrot;
        switch(position){
            case START : sprite = Images.BARBEDWIRE.getSubImage(0 * 32, 0, 32, 32);
                break;
            case MIDDLE : sprite = Images.BARBEDWIRE.getSubImage(2 * 32, 0, 32, 32);
                break;
            case END : sprite = Images.BARBEDWIRE.getSubImage(3 * 32, 0, 32, 32);
                break;
        } 
        sprite.rotate(rot);
    }
    
    public int getType(){
        return Unit.WALL;
    }
    
    public boolean mustRemove(){
        return hp <= 0;
    }

    @Override
    protected Image getSpriteImage() {
        return sprite;
    }
}
