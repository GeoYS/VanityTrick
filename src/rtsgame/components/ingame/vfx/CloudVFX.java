/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.vfx;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.components.ingame.Camera;
import rtsgame.resources.Images;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class CloudVFX implements VFX{

    private Vector2f pos, vel;
    private Image cloud;
    private BaseGameState gameState;
    
    public CloudVFX(BaseGameState bgs){
        gameState = bgs;
        pos = new Vector2f((float) Math.random() * bgs.WIDTH, (float) Math.random() * bgs.LENGTH);
        vel = new Vector2f((float) Math.random(), (float) Math.random());
        if(Math.random() > 0.33){
            cloud = Images.CLOUD1.getScaledCopy(400, 300);
        }
        else if(Math.random() > 0.33){
            cloud = Images.CLOUD2.getScaledCopy(400, 300);
        }
        else{
            cloud = Images.CLOUD3.getScaledCopy(400, 300);
        }
        cloud.setAlpha(0.5f);
    }
    
    @Override
    public void update() {
        pos = pos.add(vel);
        if(pos.x < 0){
            pos.x = gameState.WIDTH - 5;
        }
        if(pos.y < 0){
            pos.y = gameState.LENGTH - 5;
        }
        if(pos.x > gameState.WIDTH){
            pos.x = 5;
        }
        if(pos.y > gameState.LENGTH){
            pos.y = 5;
        }
    }

    @Override
    public void render(Graphics g, Camera c) {
        g.drawImage(cloud, pos.x - c.pos.x, pos.y - c.pos.y);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
