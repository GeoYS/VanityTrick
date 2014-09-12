/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.states;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
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
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.Player;
import rtsgame.components.ingame.sidebar.UnitSidebar;
import rtsgame.components.ingame.units.Unit;

/**
 *
 * @author GeoYS_2
 */
public class EditorState extends BaseGameState{

    public static int ID = 73;
    private Button save;
    
    public int getID(){
        return ID;
    }
    
    @Override
    public void init(GameContainer gc, final StateBasedGame sbg) throws SlickException {
        super.init(gc, sbg);
        
        GCHEIGHT = gc.getHeight();
        WIDTH = 2000;
        LENGTH = 2000;
        units = new ArrayList();        
        view = new Camera(gc, units, this);
        view.fogOff();
        sidebar = new UnitSidebar(units, view, this);
        player = new Player(gc, view, sidebar.getMinimap(), true);
        save = new Button(800, 0, 100, 20, "Save Level", new Input(gc.getHeight())){

            @Override
            protected void onClick() {
                try {
                    PrintStream fout = new PrintStream("level5.txt");
                    fout.println(Player.morale);
                    fout.println(units.size());
                    for(Unit u : units){
                        fout.println(u.toString());
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EditorState.class.getName()).log(Level.SEVERE, null, ex);
                }
                sbg.enterState(LevelSelectState.ID, new FadeOutTransition(), new FadeInTransition());
            }
        };
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        save.update();
        view.update();
        player.update(units, i);
        sidebar.update();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        super.render(gc, sbg, grphcs);
        save.render(grphcs);
    }
    
}
