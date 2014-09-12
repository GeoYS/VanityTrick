/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.vfx;

import org.newdawn.slick.Graphics;
import rtsgame.components.ingame.Camera;

/**
 *
 * @author GeoYS_2
 */
public interface VFX {
    public void update();
    public void render(Graphics g, Camera c);
    public boolean isFinished();
}
