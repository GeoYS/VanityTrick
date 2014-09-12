/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.resources;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author GeoYS_2
 */
public class Sounds {
    public static Sound MACHINEGUN, SOLDIERSHOT, TANKSHOT, EXPLOSION, TAPS;
    public static Music MENUMUSIC, LEVELSELECTMUSIC;
    
    public static void LoadSounds() throws SlickException{
        MACHINEGUN = new Sound("machinegunsound.ogg"); 
        SOLDIERSHOT = new Sound("machinegunsound.ogg");
        TANKSHOT = new Sound("tankfire.ogg");
        EXPLOSION = new Sound("explosion.ogg");
        TAPS = new Sound("taps.ogg");
    }
    
    public static void LoadMusic() throws SlickException{
        MENUMUSIC = new Music("gamemusic.ogg", true);
        LEVELSELECTMUSIC = new Music("gamemusic2.ogg", true);
    }
    
    public static void ReleaseAll(){
        MENUMUSIC.release();
        LEVELSELECTMUSIC.release();
        MACHINEGUN.release();
        SOLDIERSHOT.release();
        TANKSHOT.release();
        EXPLOSION.release();
        TAPS.release();
    }
}
