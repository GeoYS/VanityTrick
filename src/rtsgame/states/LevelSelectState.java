/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.states;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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
public class LevelSelectState extends BaseState{

    public static final int ID = 2;
    private Button back, levelPlayGod, levelEditor, level1, level2, level3, level4;
    private Input input;
    private Image title;
    
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(final GameContainer gc, final StateBasedGame sbg) throws SlickException {
        super.init(gc, sbg);
        title = Images.LEVELSELECT;
        input = new Input(gc.getHeight());
        int x = gc.getWidth()/2 - 50, y = gc.getHeight() - 9 * gc.getHeight() / 10 + 220;
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
        levelPlayGod = new Button(gc.getWidth()/2 - 50,
                y,
                100, 
                40,
                "Play God",
                input){
                    @Override
                    protected void onClick(){   
                        try {
                            sbg.getState(PlayGodState.ID).init(gc, sbg);
                        } catch (SlickException ex) {
                            Logger.getLogger(LevelSelectState.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        sbg.enterState(PlayGodState.ID, new FadeOutTransition(), new FadeInTransition());
                    }
                };
        levelEditor = new Button(gc.getWidth()/2 - 50,
                y - 50,
                100, 
                40,
                "Level Editor - Disabled, for developer use only",
                input){
                    @Override
                    protected void onClick(){   
                        try {
                            sbg.getState(EditorState.ID).init(gc, sbg);
                        } catch (SlickException ex) {
                            Logger.getLogger(LevelSelectState.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        sbg.enterState(EditorState.ID, new FadeOutTransition(), new FadeInTransition());
                    }
                };
        levelEditor.disable();
        level1 = new Button(gc.getWidth()/2 - 50,
                y + 50,
                100, 
                40,
                "Campaign 1",
                input){
                    @Override
                    protected void onClick(){   
                        try {
                            sbg.getState(FirstLevelState.ID).init(gc, sbg);
                        } catch (SlickException ex) {
                            Logger.getLogger(LevelSelectState.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        sbg.enterState(FirstLevelState.ID, new FadeOutTransition(), new FadeInTransition());
                    }
                };
        level2 = new Button(gc.getWidth()/2 - 50,
                y + 100,
                100, 
                40,
                "Campaign 2",
                input){
                    @Override
                    protected void onClick(){   
                        try {
                            sbg.getState(SecondLevelState.ID).init(gc, sbg);
                        } catch (SlickException ex) {
                            Logger.getLogger(LevelSelectState.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        sbg.enterState(SecondLevelState.ID, new FadeOutTransition(), new FadeInTransition());
                    }
                };
        level3 = new Button(gc.getWidth()/2 - 50,
                y + 150,
                100, 
                40,
                "Campaign 3",
                input){
                    @Override
                    protected void onClick(){   
                        try {
                            sbg.getState(ThirdLevelState.ID).init(gc, sbg);
                        } catch (SlickException ex) {
                            Logger.getLogger(LevelSelectState.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        sbg.enterState(ThirdLevelState.ID, new FadeOutTransition(), new FadeInTransition());
                    }
                };
        level4 = new Button(gc.getWidth()/2 - 50,
                y + 200,
                100, 
                40,
                "Campaign 4",
                input){
                    @Override
                    protected void onClick(){   
                        try {
                            sbg.getState(FourthLevelState.ID).init(gc, sbg);
                        } catch (SlickException ex) {
                            Logger.getLogger(LevelSelectState.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        sbg.enterState(FourthLevelState.ID, new FadeOutTransition(), new FadeInTransition());
                    }
                };
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.drawImage(Images.TITLEPAGE, 0, 0);
        grphcs.drawImage(title, gc.getWidth()/2 - Images.TITLE.getWidth() / 2, gc.getHeight() - 9 * gc.getHeight() / 10 + 20);
        back.render(grphcs);
        levelPlayGod.render(grphcs);
        levelEditor.render(grphcs);
        level1.render(grphcs);
        level2.render(grphcs);
        level3.render(grphcs);
        level4.render(grphcs);
        super.render(gc, sbg, grphcs);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        super.update(gc, sbg, i);
        back.update();
        levelPlayGod.update();
        levelEditor.update();
        level1.update();
        level2.update();
        level3.update();
        level4.update();
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        Sounds.LEVELSELECTMUSIC.setVolume(0);
        Sounds.LEVELSELECTMUSIC.fade(500, 0.8f, false);
        Sounds.LEVELSELECTMUSIC.loop();
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        
        Sounds.LEVELSELECTMUSIC.fade(500, 0, true);
        super.leave(container, game);
    }
}
