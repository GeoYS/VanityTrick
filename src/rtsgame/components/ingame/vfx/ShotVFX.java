/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.vfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f; 
import rtsgame.components.ingame.Camera;

/**
 *
 * @author GeoYS_2
 */
public class ShotVFX implements VFX{
    private static float duration = 4;
    
    private Vector2f v;
    private float x, y;
    private int timer;
    public ShotVFX(float nx, float ny, float ex, float ey, float d){
        x = nx; 
        y = ny; 
        v = new Vector2f(ex - x, ey - y).normalise().scale(d);
        timer = 0;
    }

    @Override
    public void update() {
        timer ++;
    }

    @Override
    public void render(Graphics g, Camera c) {
        g.setColor(Color.white);
        g.draw(new Line(x - c.pos.x, y - c.pos.y, x + v.x * (timer/duration) - c.pos.x, y + v.y * (timer/duration) - c.pos.y));
    }

    @Override
    public boolean isFinished() {
        return timer >= duration;
    }
    
}
