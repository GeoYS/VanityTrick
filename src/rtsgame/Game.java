/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import rtsgame.resources.Images;
import rtsgame.states.*;

/**
 *
 * @author GeoYS_2
 */
public class Game extends StateBasedGame{

    public static float GCHEIGHT;
    
    public Game(String name) throws SlickException{
        super(name);
    }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new LoadingState());
        this.addState(new MenuState());
        this.getCurrentState().init(gc, this);
        this.addState(new LevelSelectState());
        this.addState(new HighscoreState());
        this.addState(new HelpState());
        this.addState(new CreditsState());
        this.addState(new PlayGodState());
        this.addState(new EditorState());
        this.addState(new FirstLevelState());
        this.addState(new SecondLevelState());
        this.addState(new ThirdLevelState());
        this.addState(new FourthLevelState());
        GCHEIGHT = this.getContainer().getHeight();
    }
    
}
