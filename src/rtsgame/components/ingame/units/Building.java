/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.units;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.Game;
import rtsgame.components.Button;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.Player;
import rtsgame.resources.Images;
import rtsgame.states.BaseGameState;

/**
 *
 * @author geshe9243
 */
public class Building extends Unit{
    
    private static Image building;
    
    private final int unitTime = 500;
    private boolean isMaking;
    private Button newUnit, newTank, newNestEngineer,
            newSniper, newScout, newGrenadier;
    private int uTimer;
    private Unit queue;
    
    public Building(Vector2f p, final BaseGameState bgs){
        super(p, bgs);
        super.type = Unit.BUILDING;
        radius = 64;
        name = "Base";
        isMaking = false;
        uTimer = 0;
        super.hp = super.totalHp = 200;
        newUnit = new Button(Images.FOOTSOLDIERSPRITESHEET.getSubImage(0,0,64,64).getScaledCopy(20, 20), 180, 600, new Input((int)Game.GCHEIGHT), "Soldier\nCost: 100 morale"/*location.x - 50, location.y - 55, 100, 30, "New Unit", new Input(gc.getHeight())*/){
            public void onClick(){
                if(Player.morale >= 100 && !isMaking){
                    newUnit(new FootSoldier(new Vector2f(location.x +  radius + (float)Math.random(), location.y + (float) radius + (float)Math.random()), bgs));
                    Player.morale -= 100;
                }
            }
        }; 
        newTank = new Button(Tank.tankCannon.getSubImage(0,0,32,32).getScaledCopy(20, 20), 180, 630, new Input((int)Game.GCHEIGHT), "Tank\nCost: 500 morale"/*location.x - 50, location.y - 55, 100, 30, "New Unit", new Input(gc.getHeight())*/){
            public void onClick(){
                if(Player.morale >= 500 && !isMaking){  
                    newUnit(new Tank(new Vector2f(location.x +  radius + (float)Math.random(), location.y + (float) radius + (float)Math.random()), bgs));
                    Player.morale -= 500;
                }
            }
        };
        newNestEngineer = new Button(Images.MACHINEGUN.getScaledCopy(20, 20), 180, 660, new Input((int)Game.GCHEIGHT), "Engineer\nCost: 350 morale"/*location.x - 50, location.y - 55, 100, 30, "New Unit", new Input(gc.getHeight())*/){
            public void onClick(){
                if(Player.morale >= 350 && !isMaking){               
                    newUnit(new NestEngineer(new Vector2f(location.x +  radius + (float)Math.random(), location.y + (float) radius + (float)Math.random()), bgs));
                    Player.morale -= 350;
                }
            }
        };
        newSniper = new Button(Images.SNIPERSHOOTING.getScaledCopy(20, 20), 210, 600, new Input((int)Game.GCHEIGHT), "Sniper\nCost: 100 morale"/*location.x - 50, location.y - 55, 100, 30, "New Unit", new Input(gc.getHeight())*/){
            public void onClick(){
                if(Player.morale >= 100 && !isMaking){             
                    newUnit(new Sniper(new Vector2f(location.x +  radius + (float)Math.random(), location.y + (float) radius + (float)Math.random()), bgs));
                    Player.morale -= 100;
                }
            }
        }; 
        newScout = new Button(Images.TREE.getScaledCopy(20, 20), 210, 630, new Input((int)Game.GCHEIGHT), "Scout\nCost: 500 morale"/*location.x - 50, location.y - 55, 100, 30, "New Unit", new Input(gc.getHeight())*/){
            public void onClick(){
                if(Player.morale >= 500 && !isMaking){
                    newUnit(new Scout(new Vector2f(location.x +  radius + (float)Math.random(), location.y + (float) radius + (float)Math.random()), bgs));
                    Player.morale -= 500;
                }
            }
        };
        newGrenadier = new Button(Images.GRENADE.getScaledCopy(20, 20), 210, 660, new Input((int)Game.GCHEIGHT), "Grenadier\nCost: 350 morale"/*location.x - 50, location.y - 55, 100, 30, "New Unit", new Input(gc.getHeight())*/){
            public void onClick(){
                if(Player.morale >= 350 && !isMaking){                
                    newUnit(new Grenadier(new Vector2f(location.x +  radius + (float)Math.random(), location.y + (float) radius + (float)Math.random()), bgs));
                    Player.morale -= 350;
                }
            }
        };
    }
    
    public static void LoadBuildingImage() throws SlickException{
        building = new Image("res/tent.png");
    }
    
    private void newUnit(Unit u){
        if(!isMaking && super.isSelected()){
            queue = u;
            uTimer = unitTime;
            isMaking = true;
        }
    }
    
    public Image getSidebarImage(){
        return Images.TENT;
    }
    
    @Override
    protected Image getSpriteImage() {
        return Images.TENT;
    }
    
    public void update(ArrayList<Unit> units, Camera c){
        
        if(units.size() <= 200 && isMaking){
            uTimer -= 5;
            if(uTimer < 0){
                isMaking = false;
                units.add(queue);
                queue = null;
            }
        }
        newUnit.update();
        newTank.update();
        newNestEngineer.update();
        newSniper.update();
        newGrenadier.update();
        newScout.update();
    }
    
    public ArrayList<Button> getButtons(){
        ArrayList<Button> myButtons = new ArrayList();
        myButtons.add(newUnit);
        myButtons.add(newTank);
        myButtons.add(newNestEngineer);
        myButtons.add(newSniper);
        myButtons.add(newScout);
        myButtons.add(newGrenadier);
        return myButtons;
    }
    
    public void render(Graphics g, Camera c){
        /*g.setColor(Color.orange);
        g.draw(new Circle(location.x - c.pos.x, location.y  - c.pos.y, 20));*/
        
        g.drawImage(getSpriteImage().getScaledCopy((int)radius*2, (int)radius*2), location.x - c.pos.x - radius, location.y  - c.pos.y - radius);
        if(super.isSelected()){
            g.setColor(Color.orange);
            //newUnit.render(g, c);
            g.setColor(Color.green);
            g.draw(new Circle(location.x - c.pos.x, location.y  - c.pos.y, radius));
        }
        
        if(isMaking){
            
            g.setColor(Color.green);
            g.draw(new Rectangle(location.x - c.pos.x - 20, location.y  - c.pos.y + radius, ((unitTime - uTimer)/ 12), 10));
        }
    }
}
