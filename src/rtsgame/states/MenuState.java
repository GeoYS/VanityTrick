/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.states;

import com.sun.org.apache.xpath.internal.axes.OneStepIterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import rtsgame.components.Button;
import rtsgame.resources.Images;
import rtsgame.resources.Sounds;

/**
 *
 * @author GeoYS_2
 */
public class MenuState extends BaseState{

    public static final int ID = 1;
    private Button toGame, toHighscore, toHelp;
    private Input input;
    private boolean test = true;
    private Button toCredits;
    private Button quit;
    
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(final GameContainer gc, final StateBasedGame sbg) throws SlickException {
        super.init(gc, sbg);
        input = new Input(gc.getHeight());
        int x = gc.getWidth()/2 - 50, y = gc.getHeight() - 9 * gc.getHeight() / 10 + 200;
        toGame = new Button(x, y + 50, 100, 40, "Start Game", input){
            @Override
            protected void onClick(){
                sbg.enterState(LevelSelectState.ID, new FadeOutTransition(), new FadeInTransition()); 
            }
        };
        toHighscore = new Button(x, y + 100, 100, 40, "Highscore", input){
            @Override
            protected void onClick(){
                sbg.enterState(HighscoreState.ID, new FadeOutTransition(), new FadeInTransition()); 
            }
        };
        toHelp = new Button(x, y + 150, 100, 40, "Help", input){
            @Override
            protected void onClick(){
                sbg.enterState(HelpState.ID, new FadeOutTransition(), new FadeInTransition()); 
            }
        };
        toCredits = new Button(x, y + 200, 100, 40, "Credits", input){
            @Override
            protected void onClick(){
                sbg.enterState(CreditsState.ID, new FadeOutTransition(), new FadeInTransition()); 
            }
        };
        quit = new Button(x, y + 250, 100, 40, "Quit", input){
            @Override
            protected void onClick(){
                gc.exit();
            }
        };
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.drawImage(Images.TITLEPAGE, 0, 0);
        grphcs.setColor(Color.orange);
        grphcs.drawImage(Images.TITLE, gc.getWidth()/2 - Images.TITLE.getWidth() / 2, gc.getHeight() - 9 * gc.getHeight() / 10 + 20);
        grphcs.setColor(Color.white);
        toHelp.render(grphcs);
        toHighscore.render(grphcs);
        toGame.render(grphcs);
        toCredits.render(grphcs);
        quit.render(grphcs);
        super.render(gc, sbg, grphcs);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        super.update(gc, sbg, i);
        toHelp.update();
        toHighscore.update();
        toGame.update();
        toCredits.update();
        quit.update();
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        Sounds.MENUMUSIC.setVolume(0);
        Sounds.MENUMUSIC.fade(500, 0.8f, false);
        Sounds.MENUMUSIC.loop();
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        
        Sounds.MENUMUSIC.fade(500, 0, true);
        super.leave(container, game);
    }
}
