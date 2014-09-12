/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame;

import org.newdawn.slick.*;
import rtsgame.resources.Sounds;

/**
 *
 * @author GeoYS_2
 */
public class RTSGame {
    
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game("Vanity Trick")){

            @Override
            public void exit() {
                Sounds.ReleaseAll();                
                super.exit();
            }
        };
        app.setShowFPS(false);
        // display modes test
        /*DisplayMode[] modes = Display.getAvailableDisplayModes();

         for (int i=0;i<modes.length;i++) {
             DisplayMode current = modes[i];
             System.out.println(current.getWidth() + "x" + current.getHeight() + "x" +
                                 current.getBitsPerPixel() + " " + current.getFrequency() + "Hz");
         }*/
        app.setDisplayMode(1280, 720, true);
        app.setTargetFrameRate(60);
        app.setMouseGrabbed(true);
        app.setMinimumLogicUpdateInterval(25);
        app.setMaximumLogicUpdateInterval(26);
        app.start();
    }
}
