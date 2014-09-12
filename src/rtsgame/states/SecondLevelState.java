/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.states;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import rtsgame.components.MessageBox;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.Player;
import rtsgame.components.ingame.sidebar.UnitSidebar;
import rtsgame.components.ingame.units.Unit;
import rtsgame.components.ingame.victoryconditions.KillCondition;
import rtsgame.components.ingame.victoryconditions.VictoryCondition;
import rtsgame.resources.Facts;

/**
 *
 * @author GeoYS_2
 */
public class SecondLevelState extends BaseGameState{
    
    public static int ID = 832;
    private MessageBox introduction;
    private KillCondition vc;
    
    public int getID(){
        return ID;
    }

    @Override
    public VictoryCondition getVictoryCondition() {
        return vc;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        super.init(gc, sbg);
        GCHEIGHT = gc.getHeight();
        WIDTH = 2000;
        LENGTH = 2000;
        units = new ArrayList();        
        view = new Camera(gc, units, this);
        sidebar = new UnitSidebar(units, view, this);
        player = new Player(gc, view, sidebar.getMinimap(), false);
        try {
            loadLevel();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FirstLevelState.class.getName()).log(Level.SEVERE, null, ex);
        }
        introduction = new MessageBox("Fact: " + Facts.getRandomFact() + " Campaign 2. "
                + "Objective: Oh no! Two enemies are close to the base! Select "
                + "your units and attack the enemies by right-clicking on them."
                + "Good Luck! ", gc, gc.getWidth() / 4, gc.getHeight() / 4);
        vc = new KillCondition(2);
    }
    
    private void loadLevel() throws FileNotFoundException{
        Scanner fin = new Scanner(new File("level2.txt"));
        Player.morale = fin.nextInt();
        fin.nextLine();
        int u = fin.nextInt(); 
        fin.nextLine();
        for(int i = 0; i < u; ++i){
            String unit = fin.nextLine();
            units.add(Unit.stringToUnit(unit, this));
        }
    }
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(!introduction.closed()){
            introduction.update();
            return;
        }
        super.update(gc, sbg, i);
    }
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        super.render(gc, sbg, grphcs);
        if(!introduction.closed()){
            introduction.render(grphcs);
            this.drawCursor(grphcs);
        }
    }
}
