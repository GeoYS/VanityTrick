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
public class HelpState extends BaseState{

    public static final int ID = 4;
    private Button back;
    private Input input;
    
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
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.drawImage(Images.TITLEPAGE, 0, 0);
        
        //instructions to the game
        grphcs.setColor(Color.yellow);
        grphcs.drawString("Left click and drag to select units. Right click to set target for units to move to. \n"
                + "Pressing Control and a number groups the currently selected units. Press that number to select those in the group.\n"
                + "Press Q to stop currently selected troops from moving.\n"
                + "Press Shift and right click to add target points allong the selected units' movement paths.\n"
                + "Press Shift and select units to add them to currently selected.\n"
                + "Press Q and a unit button to deselect unit, or press U and on-screen unit to deselect unit.\n"
                + "Right click on enemy unit to set enemy as target for currently selected units.\n"
                + "Use mouse or press arrow keys or WASD to scroll map.\n"
                + "In god mode, use the F[number] shortcut keys to place various units.", 20, 200);
        
        back.render(grphcs);
        super.render(gc, sbg, grphcs);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        super.update(gc, sbg, i);
        back.update();
    }
}
