/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import rtsgame.components.Button;
import rtsgame.resources.Images;

/**
 *
 * @author GeoYS_2
 */
public class CreditsState extends BaseState{
    public static final int ID = 5;
    private Button back;
    private String[] names;
    private float x, y;
    
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gc, final StateBasedGame sbg) throws SlickException {
        super.init(gc, sbg);
        input = new Input(gc.getHeight());
        back = new Button(gc.getWidth()/2 - 50,
                gc.getHeight() - gc.getHeight() / 10,
                100, 
                40,
                "Back",
                input){
                    @Override
                    protected void onClick(){   
                        sbg.enterState(MenuState.ID, new FadeOutTransition(), new FadeInTransition());
                    }
                };
        
        x = gc.getWidth()/2 - 500;
        y = 190;
        
        names = new String[] {"George Shen - Project Creator",
            "Duane Byer - The one who helps me a lot with programming, answering lifes questions, and other stuff",
            "Zeke Sampson - Suggestions, gameplay ideas, and bug-spotting",
            "Chris Stokes, Jeremy, Noah, Andrew, and anyone else who saw a demo of this game - kool people",
            "Mr Kaune - for being a kool teacher!",
            "\n\nLibraries Used:",
            "\n\nSlick2D, which uses",
            "\n\nlwjgl"};
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        super.update(gc, sbg, i);
        back.update();
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.drawImage(Images.TITLEPAGE, 0, 0);
        grphcs.drawString("Credits", x, y);
        grphcs.setColor(Color.yellow);
        for(int i = 0; i < names.length; i++){
            grphcs.drawString(names[i], x, y + 20 + 15*i);
        }
        back.render(grphcs);
        super.render(gc, sbg, grphcs);
    }
    
}
