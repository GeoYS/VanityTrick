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
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import rtsgame.components.MessageBox;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.Player;
import rtsgame.components.ingame.sidebar.UnitSidebar;
import rtsgame.components.ingame.units.FootSoldier;
import rtsgame.components.ingame.units.Grenadier;
import rtsgame.components.ingame.units.Sniper;
import rtsgame.components.ingame.units.Tank;
import rtsgame.components.ingame.units.Unit;
import rtsgame.components.ingame.victoryconditions.SurviveCondition;
import rtsgame.components.ingame.victoryconditions.VictoryCondition;
import rtsgame.resources.Facts;

/**
 *
 * @author GeoYS_2
 */
public class FourthLevelState extends BaseGameState{
    
    public static int ID = 8332;
    private MessageBox introduction;
    private SurviveCondition vc;
    
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
        introduction = new MessageBox("Fact: " + Facts.getRandomFact() + " Campaign 4. "
                + "Objective: One word: Survive. Keep the base alive for "
                + "3 minutes to complete the mission. "
                + "Good Luck! ", gc, gc.getWidth() / 4, gc.getHeight() / 4);
        for(Unit u : units){
            if(u.getType() == Unit.BUILDING){
                vc = new SurviveCondition(180000 / 30, u);
                break;
            }
    
        }
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(!introduction.closed()){
            introduction.update();
            return;
        }
        vc.update();
        if(Math.random() <= 0.01 && units.size() < 270) {
            FootSoldier soldier = new FootSoldier(new Vector2f(800, 1500 + (float)Math.random()), this);
            Grenadier grenadier = new Grenadier(new Vector2f(1700, 1500 + (float)Math.random()), this);
            Sniper sniper = new Sniper(new Vector2f(1800, 1500 + (float)Math.random()), this);
            if(Math.random() < 0.1){
                Tank tank = new Tank(new Vector2f(2000, 1500 + (float)Math.random()), this);
                tank.makeEnemy();
                tank.addTarget(new Vector2f(1000, 1000));
                tank.addTarget(new Vector2f(100, 100));
                units.add(tank);
            }
            soldier.makeEnemy();
            grenadier.makeEnemy();
            sniper.makeEnemy();
            soldier.addTarget(new Vector2f(1000, 1000));
            soldier.addTarget(new Vector2f(100, 100));
            sniper.addTarget(new Vector2f(1000, 1000));
            sniper.addTarget(new Vector2f(100, 100));
            grenadier.addTarget(new Vector2f(1000, 1000));
            grenadier.addTarget(new Vector2f(100, 100));
            units.add(soldier);
            units.add(grenadier);
            units.add(sniper);
            
        }
        super.update(gc, sbg, i);
    }
    private void loadLevel() throws FileNotFoundException{
        Scanner fin = new Scanner(new File("level4.txt"));
        Player.morale = fin.nextInt();
        fin.nextLine();
        int u = fin.nextInt(); 
        fin.nextLine();
        for(int i = 0; i < u; ++i){
            String unit = fin.nextLine();
            units.add(Unit.stringToUnit(unit, this));
        }
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
