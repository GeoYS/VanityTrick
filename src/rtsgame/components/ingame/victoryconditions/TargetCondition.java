/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.victoryconditions;

import java.util.ArrayList;
import rtsgame.components.ingame.units.Unit;

/**
 *
 * @author GeoYS_2
 */
public class TargetCondition implements VictoryCondition{

    private ArrayList<Unit> toKill;
    
    public TargetCondition(ArrayList<Unit> tk){
        toKill = tk;
    }
    
    @Override
    public boolean conditionReached() {
        for(Unit u : toKill){
            if(u.isAlive()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean conditionFailed() {
        return false;
    }
    
}
