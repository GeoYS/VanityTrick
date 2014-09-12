/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.units;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.components.Button;
import rtsgame.components.ingame.Camera;
import rtsgame.resources.Images;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class NestEngineer extends FootSoldier{
    
    private final float unitTime = 500;
    
    private Button buildNest;
    private boolean isMaking;
    private Unit queue;
    private float uTimer;
    
    public NestEngineer(Vector2f p, final BaseGameState bgs){
        super(p, bgs);
        super.name = "Engineer";
        isMaking = false;
        uTimer = 0;
        buildNest = new Button(Images.MACHINEGUNTRIPOD.getScaledCopy(20, 20), 180, 600, new Input((int)bgs.GCHEIGHT)/*location.x - 50, location.y - 55, 100, 30, "New Unit", new Input(gc.getHeight())*/){
            public void onClick(){
                newUnit(new Nest(new Vector2f(location.x +  radius + (float)Math.random(), location.y + (float) radius + (float)Math.random()), bgs));
            }
        };
    }
    
    private void newUnit(Unit u){
        if(!isMaking && super.isSelected()){
            queue = u;
            uTimer = unitTime;
            isMaking = true;
        }
    }
    
    public void update(ArrayList<Unit> units, Camera c){
        super.update(units, c);
        if(units.size() <= 200 && isMaking){
            uTimer -= 10;
            if(uTimer < 0){
                isMaking = false;
                queue.location = new Vector2f(location);
                units.add(queue);
                units.remove(this);
                return;
                //queue = null;
            }
        }
        buildNest.update();
    }

    @Override
    public ArrayList<Button> getButtons() {
        ArrayList<Button> myButtons = new ArrayList();
        myButtons.add(buildNest);
        return myButtons;
    }
    
    @Override
    protected Image getAttackingImage() {
        return Images.ENGINEERSHOOTING;
    }

    @Override
    protected Image getSpriteSheet() {
        return Images.ENGINEERSPRITESHEET;
    }
    
    @Override
    public int getSubType(){
        return Unit.ENGINEER;
    }
    
    @Override
    public void renderSprite(Graphics g, Camera c) {
        super.renderSprite(g, c);
        /*Image gunR = Images.MACHINEGUN.getScaledCopy((int)radius*2, (int)radius * 2);
        
        if(isAlive() && enemy != null){            
            gunR.rotate(-(float)Math.toDegrees(Math.atan2(-enemy.location.x + location.x, -enemy.location.y + location.y)));
        }
        g.drawImage(gunR, location.x - c.pos.x - radius, location.y  - c.pos.y - radius);*/
        if(isMaking){
            
            g.setColor(Color.blue);
            g.draw(new Rectangle(location.x - c.pos.x - radius, location.y  - c.pos.y + radius + 12, ((unitTime - uTimer)/ unitTime) * radius * 2, 2));
        }
    }
}
