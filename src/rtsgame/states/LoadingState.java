/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import rtsgame.components.ingame.units.Building;
import rtsgame.components.ingame.units.Tank;
import rtsgame.components.ingame.units.Unit;
import rtsgame.resources.Images;
import rtsgame.resources.Sounds;

/**
 *
 * @author GeoYS_2
 */
public class LoadingState extends BaseState{

    private int loadTimer; // purely for aesthetic purposes
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        super.init(gc, sbg);
        Images.LoadImages();
        Unit.LoadSprite();
        Building.LoadBuildingImage();
        Tank.LoadImages();
        Sounds.LoadMusic();
        Sounds.LoadSounds();
        loadTimer = 50;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        super.update(gc, sbg, i);
        
        loadTimer --;
        
        if(loadTimer == 0)
            sbg.enterState(MenuState.ID, new FadeOutTransition(), new FadeInTransition());
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.drawString("LOADING", gc.getWidth() / 2, gc.getHeight() / 2);
        super.render(gc, sbg, grphcs);
    }
    
}
