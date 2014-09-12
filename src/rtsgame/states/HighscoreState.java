/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.states;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class HighscoreState extends BaseState{
    
    public static final int ID = 3;
    private Button back;
    private ArrayList<Integer> scores;
    
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
        try {
            scores = new ArrayList();
            Scanner fin = new Scanner(new File("highscores.txt"));
            while(fin.hasNextInt()){
                scores.add(fin.nextInt());
            }
            Collections.sort(scores);
            Collections.reverse(scores);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HighscoreState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.drawImage(Images.TITLEPAGE, 0, 0);
        String out = "Most Kills: \n";
        for(Integer i : scores){
            out += i + "\n";
        }
        grphcs.drawString(out, gc.getWidth()/2.5f, gc.getHeight()/ 4);
        back.render(grphcs);
        super.render(gc, sbg, grphcs);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        super.update(gc, sbg, i);
        back.update();
    }
    
}
