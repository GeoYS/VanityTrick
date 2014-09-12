/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import rtsgame.resources.Images;

/**
 *
 * @author GeoYS_2
 */
public class MessageBox {
    private Message message;
    private int width, height;
    private float x, y;
    private Button confirm;
    private boolean closed;
    private Image background;
    
    public MessageBox(String s, GameContainer gc, float nx, float ny){
        x = nx;
        y = ny;
        closed = false;
        message = new Message(s);
        width = message.getMaxWidth(gc.getGraphics().getFont()) + 20;
        height = message.getNumberRows() * gc.getGraphics().getFont().getLineHeight() + 50;
        confirm = new Button(x + width / 2 - gc.getGraphics().getFont().getWidth("Cool!"),
                y + height - 40 + 10, gc.getGraphics().getFont().getWidth("Cool!"),
                gc.getGraphics().getFont().getLineHeight(),
                "Cool!",
                new Input(gc.getHeight())){

                    @Override
                    protected void onClick() {
                        closed = true;
                    }
                };
        background = Images.MESSAGEBOX.getScaledCopy(width, height);
        background.setAlpha(0.8f);
    }
    
    public void update(){
        confirm.update();
    }
    
    public boolean closed(){
        return closed;
    }
    
    public void render(Graphics g) throws SlickException{
        g.setColor(Color.white);
        g.drawImage(background, x - 3, y - 3);
        g.setColor(Color.blue);
        message.draw(g, x, y);
        confirm.render(g);
    }
}
