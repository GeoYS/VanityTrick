/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.states;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import rtsgame.MyMath;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.Player;
import rtsgame.components.ingame.sidebar.UnitSidebar;
import rtsgame.components.ingame.units.Unit;
import rtsgame.components.ingame.victoryconditions.VictoryCondition;
import rtsgame.resources.Images;
import rtsgame.resources.Sounds;

/**
 *
 * @author GeoYS_2
 */
public class BaseGameState extends BaseState{
    
    public float WIDTH, LENGTH, GCHEIGHT;
    protected Player player;
    protected UnitSidebar sidebar;
    protected ArrayList<Unit> units;
    protected Camera view;
    protected StateBasedGame nsbg;
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        super.init(gc, sbg);
        nsbg = sbg;
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        super.update(gc, sbg, i);
        view.update();
        sidebar.update();
        player.update(units, i);
        if(getVictoryCondition() != null){
            if(getVictoryCondition().conditionReached()){
                nsbg.enterState(LevelSelectState.ID, new FadeOutTransition(), new FadeInTransition());
            }
            if(getVictoryCondition().conditionFailed()){
                nsbg.enterState(LevelSelectState.ID, new FadeOutTransition(), new FadeInTransition());
            }
        }
        
        for(int u = units.size() - 1; u >= 0; u --){
            units.get(u).update(units, view);
            if(units.get(u).mustRemove()){
                units.remove(u);
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        view.render(grphcs); 
        player.render(grphcs);
        sidebar.render(grphcs);
        super.render(gc, sbg, grphcs);
    }

    private boolean aUnitSelected(){
        if(units != null && !units.isEmpty()){
            for(Unit u : units){
                if(u.isSelected() && !u.isEnemy()){
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void drawCursor(Graphics g) {
        
        if(units != null && !units.isEmpty()){
            for(Unit u : units){
                if(MyMath.distance(u.getLocation(), new Vector2f(input.getMouseX(), input.getMouseY())) < u.getRadius() && u.isEnemy()){
                    g.drawImage(Images.GRENADE.getScaledCopy(20, 20), input.getMouseX() - 7, input.getMouseY() - 3);
                    return;
                }
            }
            super.drawCursor(g);
        }
        else{
            super.drawCursor(g);
        }
    }
    
    public void toMenu(){
        nsbg.enterState(LevelSelectState.ID, new FadeOutTransition(), new FadeInTransition());
    }

    public VictoryCondition getVictoryCondition(){
        return null;
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        Sounds.TAPS.play(1, 0.5f);
    }
}
