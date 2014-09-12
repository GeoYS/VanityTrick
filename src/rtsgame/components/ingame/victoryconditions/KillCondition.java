/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.victoryconditions;

import java.util.ArrayList;
import rtsgame.components.ingame.Player;
import rtsgame.components.ingame.units.Unit;

/**
 *
 * @author GeoYS_2
 */
public class KillCondition implements VictoryCondition{
    
    private int killsReq;
    
    public KillCondition(int n){
        killsReq = n;
    }
    
    @Override
    public boolean conditionReached(){
        return Player.kills >= killsReq;
    }

    @Override
    public boolean conditionFailed() {
        return false;
    }
    
}
