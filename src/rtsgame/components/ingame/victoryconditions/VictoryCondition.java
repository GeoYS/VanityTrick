/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.victoryconditions;

/**
 *
 * @author GeoYS_2
 */
public interface VictoryCondition{
    
    public static int SURVIVE = 0;
    public static int TARGET = 1;
    public static int KILL = 2;
    public static int DESTINATION = 3;
    
    public boolean conditionReached();
    public boolean conditionFailed();
}
