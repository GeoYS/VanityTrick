/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.sidebar.UnitSidebar;

/**
 *
 * @author GeoYS_2
 */
public class Button {
    private Rectangle r;
    private boolean hovering, down, disabled;
    private String message, hoverText;
    protected  Input input;
    private  Image icon;
    protected  float x, y, w, l;
    
    public Button(float nx, float ny, float width, float length, String nmessage, Input in){
        x = nx;
        y = ny;
        w = width;
        l = length;
        r = new Rectangle(x, y, width, length);
        hovering = false;
        input = in;
        message = nmessage;
        icon = null;
        hoverText = null;
        disabled = false;
    }
    
    public Button(float nx, float ny, float width, float length, String nmessage, Input in, String nHoverText){
        x = nx;
        y = ny;
        w = width;
        l = length;
        r = new Rectangle(x, y, width, length);
        hovering = false;
        input = in;
        message = nmessage;
        icon = null;
        hoverText = nHoverText;
        disabled = false;
    }
    
    public Button(Image image, float nx, float ny, Input in){
        icon = image.getScaledCopy(25, 25);
        x = nx;
        y = ny;
        w = icon.getWidth();
        l = icon.getHeight();
        hovering = false;
        input = in;
        r = new Rectangle(x, y, w, l);
        hoverText = null;
        disabled = false;
    }
    
    public Button(Image image, float nx, float ny, Input in, String nHoverText){
        icon = image.getScaledCopy(20, 20);
        x = nx;
        y = ny;
        w = icon.getWidth();
        l = icon.getHeight();
        hovering = false;
        input = in;
        r = new Rectangle(x, y, w, l);
        hoverText = nHoverText;
        disabled = false;
    }
    
    public boolean isDisabled(){
        return disabled;
    }
    
    public void disable(){
        disabled = true;
    }
    
    public Image getIcon(){
        return icon;
    }
    
    public void setPos(float nx, float ny){
        x = nx;
        y = ny;
    }
    
    public float getHeight(){
        return r.getHeight();
    }
    
    public void update(){
        if(disabled){
            return;
        }
        if(r.contains(input.getMouseX(), input.getMouseY())){
            hovering = true;
            if(clicked()){
                onClick();
            }
        }
        else{
            hovering = false;
        }
    }
    
    private boolean clicked(){
        if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
            down = true;
            return false;
        }
        if(!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && down){
            down = false;
            return true;
        }
        return false;
    }

    protected void onClick() {
        
    }
    
    public void render(Graphics g) throws SlickException{
        if(icon != null){
            
            if(hovering){
                g.setColor(Color.lightGray);
                g.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                g.setColor(Color.white);
                g.draw(new Rectangle(x, y, w, l));  
                if(hoverText != null){
                    UnitSidebar.hovertext = new HoverText(hoverText,input.getMouseX() - 20, input.getMouseY() - 20);
                }
            }
            else{
                g.setColor(Color.gray);
                g.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
            }
            g.drawImage(icon, x, y);
            return;
        }
        if(hovering){
            g.setColor(Color.white);
            g.draw(new Rectangle(r.getX(), r.getY() , r.getWidth(), r.getHeight()));
            if(hoverText != null){
                UnitSidebar.hovertext = new HoverText(hoverText, 500, 500/*input.getMouseX() - 20, input.getMouseY() - 20*/);
            }
        }
        g.drawString(message, r.getCenterX() - message.length() * 4.5f, r.getCenterY() - 5);
    }
    
    
}
