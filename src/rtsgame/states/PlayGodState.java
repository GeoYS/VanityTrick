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
import rtsgame.components.MessageBox;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.sidebar.UnitSidebar;
import rtsgame.components.ingame.units.*;
import rtsgame.components.ingame.Player;
import rtsgame.resources.Facts;
import rtsgame.resources.Images;

/**
 *
 * @author GeoYS_2
 */
public class PlayGodState extends BaseGameState{

    public static int ID = 6;
    private MessageBox introduction;
    
    @Override
    public int getID(){
        return ID;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        super.init(gc, sbg);
        GCHEIGHT = gc.getHeight();
        WIDTH = 2000;
        LENGTH = 2000;
        units = new ArrayList();
        for(int i : new int[10]){
            units.add(new Grenadier(new Vector2f(100, 100 + (float) Math.random()), this));
        }
        
        for(int i : new int[10]){
            units.add(new Sniper(new Vector2f(WIDTH, 100 + (float) Math.random()), this));
        }
        
        for(int i : new int[10]){
            units.add(new Tank(new Vector2f(WIDTH, LENGTH - 100 + (float) Math.random()), this));
        }
        for(int i : new int[10]){
            units.add(new FootSoldier(new Vector2f(WIDTH, LENGTH - 100 + (float) Math.random()), this));
        }
        for(Unit u : units){
            u.makeEnemy();
        }
        for(int i = 0; i < 10; i ++){
            units.add(new Gaia(new Vector2f(600  + (float) Math.random(), i * 200), this));
        }
        for(int i = 0; i < 10; i ++){
            units.add(new Gaia(new Vector2f(800 + (float) Math.random(), i * 200), this));
        }
        for(int i = 0; i < 10; i ++){
            units.add(new Gaia(new Vector2f(1000  + (float) Math.random(), i * 200), this));
        }
        for(int i = 0; i < 10; i ++){
            units.add(new Gaia(new Vector2f(1200 + (float) Math.random(), i * 200), this));
        }
        units.add(new Building(new Vector2f(300, 500), this));
        view = new Camera(gc, units, this);
        sidebar = new UnitSidebar(units, view, this);
        player = new Player(gc, view, sidebar.getMinimap(), true);
        Player.morale = 9999999;
        introduction = new MessageBox("Fact: " + Facts.getRandomFact() + " Welcome to the game! "
                + "This level is essentially a sandbox for testing unit strengths and weaknesses."
                + "Use the F[number] shortcuts to place different units."
                + "Have fun! ", gc, gc.getWidth() / 4, gc.getHeight() / 4);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(!introduction.closed()){
            introduction.update();
            return;
        }
        super.update(gc, sbg, i);
        if(Math.random() <= 0.01 && units.size() < 200) {
            FootSoldier soldier = new FootSoldier(new Vector2f(800, 1500 + (float)Math.random()), this);
            Grenadier grenadier = new Grenadier(new Vector2f(1700, 1500 + (float)Math.random()), this);
            Sniper sniper = new Sniper(new Vector2f(1800, 1500 + (float)Math.random()), this);
            if(Math.random() < 0.25){
                Tank tank = new Tank(new Vector2f(2000, 1500 + (float)Math.random()), this);
                tank.makeEnemy();
                units.add(tank);
            }
            soldier.makeEnemy();
            grenadier.makeEnemy();
            sniper.makeEnemy();
            units.add(soldier);
            units.add(grenadier);
            units.add(sniper);
            
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
        super.render(gc, sbg, grphcs);
        if(!introduction.closed()){
            introduction.render(grphcs);
            this.drawCursor(grphcs);
        }
    }
}
