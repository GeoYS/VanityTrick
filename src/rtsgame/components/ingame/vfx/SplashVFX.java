/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.vfx;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import rtsgame.MyMath;
import rtsgame.components.ingame.Camera;
import rtsgame.resources.Images;

/**
 *
 * @author GeoYS_2
 */
public class SplashVFX implements VFX{
    private static float duration = 8;
    private Image splashvfx;
    
    private float x, y, range;
    private int timer;
    public SplashVFX(float nx, float ny, float nrange){
        x = nx; 
        y = ny; 
        range = nrange;
        timer = 0;
        splashvfx = Images.SPLASH;
    }

    @Override
    public void update() {
        timer ++;
    }

    @Override
    public void render(Graphics g, Camera c) {
        
        g.drawImage(splashvfx.getSubImage((int)MyMath.div(timer, 2) * 32, 0, 32, 32).getScaledCopy((int)range, (int)range), x - c.pos.x - range/2, y - c.pos.y - range/2);
    }

    @Override
    public boolean isFinished() {
        return timer >= 8;
    }
}
