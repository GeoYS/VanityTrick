/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import rtsgame.resources.Images;

/**
 *
 * @author GeoYS_2
 */
public class HoverText {
    private String message;
    private float x, y;
    private int width, height;
    private Image background;
    
    public HoverText(String s, float nx, float ny){
        x = nx;
        y = ny - 32;
        message = s;
        width = s.length() * 12 + 4;
        height = 3 * (12 + 4);
        background = Images.MESSAGEBOX.getScaledCopy(width, height);
    }
    
    public void render(Graphics g){
        g.drawImage(background, x - 1, y - 1);
        g.setColor(Color.black);
        g.drawString(message, x, y);
    }
}
