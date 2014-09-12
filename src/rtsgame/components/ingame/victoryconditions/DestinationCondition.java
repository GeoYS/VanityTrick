/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.victoryconditions;

import java.util.ArrayList;
import org.newdawn.slick.geom.Vector2f;
import rtsgame.MyMath;
import rtsgame.components.ingame.units.Unit;

/**
 *
 * @author GeoYS_2
 */
public class DestinationCondition implements VictoryCondition{

    private Vector2f destination;
    private ArrayList<Unit> unitsToTravel;
    
    public DestinationCondition(ArrayList<Unit> utt, Vector2f d){
        destination = d;
        unitsToTravel = utt;
    }
    
    @Override
    public boolean conditionReached() {
        for(Unit u : unitsToTravel){
            if(MyMath.distance(u.getLocation(), destination) > 100){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean conditionFailed() {
        for(Unit u : unitsToTravel){
            if(!u.isAlive()){
                return true;
            }
        }
        return false;
    }
    
}
