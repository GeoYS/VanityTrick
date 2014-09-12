/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.components.HoverText;
import rtsgame.components.ingame.units.Unit;
import rtsgame.components.ingame.vfx.CloudVFX;
import rtsgame.components.ingame.vfx.VFX;
import rtsgame.resources.Images;
import rtsgame.states.BaseGameState;

/**
 *
 * @author geshe9243
 */
public class Camera {
    public Vector2f pos;
    public float width, length, gw, gh, mouseScroll, scrollSpeed;
    private Input input;
    private ArrayList<Unit> units;
    private ArrayList<VFX> vfx;
    private Image fog, dirt;
    private BaseGameState gameState;
    private boolean fogOn;
    
    public Camera(GameContainer gc, ArrayList<Unit> u, BaseGameState bgs) throws SlickException{
        pos = new Vector2f(0, 0);
        width = 1280;
        length = 720;
        input = new Input(gc.getHeight());
        units = u;
        gw = gc.getWidth();
        gh = gc.getHeight();
        mouseScroll = 30;
        scrollSpeed = 30;
        vfx = new ArrayList();
        vfx.add(new CloudVFX(bgs));
        vfx.add(new CloudVFX(bgs));
        vfx.add(new CloudVFX(bgs));
        vfx.add(new CloudVFX(bgs));
        fog = new Image((int)width, (int)length);
        dirt = Images.DIRT.getScaledCopy((int)bgs.WIDTH, (int)bgs.LENGTH);
        gameState = bgs;
        fogOn = true;
    }
    
    // checks if unit in view
    public boolean contains(float x, float y){
        if((pos.x < x && x < pos.x + width) && 
                (pos.y < y && y < pos.y + length)){
            return true;
        }
        return false;
    }
    
    public boolean drawROS(Unit u){
        if(new Rectangle(pos.x - u.getRangeOfSight(), pos.y - u.getRangeOfSight(), width + 2*u.getRangeOfSight(), length + 2*u.getRangeOfSight()).contains(u.getLocation().x, u.getLocation().y)){
            return true;
        }
        return false;
    }
    
    public void update(){
        // scroll map
        if(input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S) || input.getMouseY() > gh - mouseScroll){
            pos.y += scrollSpeed;
        }
        if(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W) || input.getMouseY() < mouseScroll){
            pos.y -= scrollSpeed;
        }
        if(input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D) || input.getMouseX() > gw - mouseScroll){
            pos.x += scrollSpeed;
        }
        if(input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A) || input.getMouseX() <  mouseScroll){
            pos.x -= scrollSpeed;
        }
        // stop at edge
        if(pos.x + width > gameState.WIDTH){
            pos.x = gameState.WIDTH - width;
        }
        if(pos.x < 0){
            pos.x = 0;
        }
        if(pos.y + length > gameState.LENGTH){
            pos.y = gameState.LENGTH - length;
        }
        if(pos.y < 0){
            pos.y = 0;
        }
        VFXUpdate();
    }
    
    public void addVFX(VFX v){
        vfx.add(v);
    }
    
    private void VFXUpdate(){
        for(int v = vfx.size() - 1; v >= 0; v--){
            vfx.get(v).update();
            if(vfx.get(v).isFinished()){
                vfx.remove(v);
            }
        }
    }
    
    public BaseGameState getGame(){
        return gameState;
    }
    
    public void fogOff(){
        fogOn = false;
    }
    
    public void render(Graphics g) throws SlickException{
        g.drawImage(dirt.getSubImage((int)pos.x, (int)pos.y, (int)width, (int)length), 0, 0);
        // shout out to Duane Byer for helping with the rough fog of war
        g.setBackground(new Color(0, 30, 10));
        if(fogOn){
            fog.getGraphics().setColor(Color.black);
            fog.getGraphics().fillRect(0, 0, width, length);
            g.setColor(Color.lightGray);
            for(Unit u : units){
                if(u.getType() != Unit.GAIA && 
                        u.getType() != Unit.WALL &&
                        u.getType() != Unit.NONCOLLIDEABLE &&
                        (this.contains(u.getLocation().x, u.getLocation().y)  ||
                        drawROS(u)
                        ) &&
                        u.isAlive() &&
                        !u.isEnemy()
                        ){
                    //g.draw(new Circle(u.location.x - pos.x, u.location.y - pos.y, u.rangeOfSight));
                    fog.getGraphics().setColor(Color.transparent);
                    fog.getGraphics().setDrawMode(Graphics.MODE_ALPHA_MAP);
                    fog.getGraphics().fillOval(u.getLocation().x - pos.x - u.getRangeOfSight(), u.getLocation().y - pos.y  - u.getRangeOfSight(), u.getRangeOfSight() * 2, u.getRangeOfSight() * 2);
                }
            }
        }
        for(Unit u : units){
            if(this.contains(u.getLocation().x, u.getLocation().y)){
                u.render(g, this);                
            }
        }
                
        fog.setAlpha(0.9f);
        g.drawImage(fog, 0, 0);
        
        for(VFX v : vfx){
            v.render(g, this);
        }
        
    }
}
