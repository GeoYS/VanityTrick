/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.sidebar;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import rtsgame.MyMath;
import rtsgame.components.Button;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.Player;
import rtsgame.components.ingame.units.Unit;

/**
 *
 * @author GeoYS_2
 */
public class UnitSidebarSelected {
    private ArrayList<Unit> units;
    private ArrayList<Button> unitButtons;
    private float posShift;
    
    public UnitSidebarSelected(ArrayList<Unit> u){
        units = u;
        unitButtons = new ArrayList();
        posShift = 340;
    }
    
    public void update() throws SlickException{
        
        for(int b = 0; b < unitButtons.size(); b ++){
            unitButtons.get(b).update();
        }
        unitButtons.clear();
        int perRow = 16;
        for(int u = 0; u < units.size(); u++){
            final Unit tu = units.get(u);
            if(tu.isSelected()){
                unitButtons.add(new Button(tu.getSidebarImage(),
                        (unitButtons.size() - (perRow * MyMath.div(unitButtons.size(), perRow))) * 40 + posShift,
                        595  + (30 * MyMath.div(unitButtons.size(), perRow)),
                        Player.input){
                    public void onClick(){
                        if(input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL)){
                            tu.deselect();
                        }
                        else{
                            singleSelect(tu);
                        }
                    }
                    public void render(Graphics g) throws SlickException{
                        super.render(g);
                        g.setColor(Color.green);
                        g.fillRect(x, y + getIcon().getHeight() + 1, getIcon().getWidth() * tu.getHp() / tu.getTotalHp(), 2);
                    }
                });
                /*unitButtons.get(unitButtons.size() - 1).getIcon().getGraphics().setColor(Color.green);
                unitButtons.get(unitButtons.size() - 1).getIcon().getGraphics().drawRect(0,unitButtons.get(unitButtons.size() - 1).getIcon().getHeight() - 2,
                        unitButtons.get(unitButtons.size() - 1).getIcon().getWidth() * tu.hp / tu.totalHp, 2);*/
            }
        }
        for(int b = 0; b < unitButtons.size(); b ++){
            unitButtons.get(b).update();
        }
    }
    
    public void singleSelect(Unit u){
        for(Unit unit : units){
            if(unit != u){
                unit.deselect();
            }
            else{
                unit.select();
            }
        }
    }
    
    public void render(Graphics g) throws SlickException{
        for(int b = 0; b < unitButtons.size(); b ++){
            unitButtons.get(b).render(g);
        }
    }
}
