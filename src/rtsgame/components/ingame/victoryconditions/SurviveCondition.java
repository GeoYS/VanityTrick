/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.victoryconditions;

import rtsgame.components.ingame.units.Unit;

/**
 *
 * @author GeoYS_2
 */
public class SurviveCondition implements VictoryCondition{

    private int tReq, tPas;
    private Unit mS;
    
    public SurviveCondition(int t, Unit mustSurvive){
        tReq = t;
        tPas = 0;
        mS = mustSurvive;
    }
    
    public void update(){
        tPas ++;
    }
    
    @Override
    public boolean conditionReached() {
        return tPas >= tReq;
    }

    @Override
    public boolean conditionFailed() {
        return !mS.isAlive();
    }
    
}
