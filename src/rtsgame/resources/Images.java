/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.resources;

/**
 *
 * @author GeoYS_2
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author GeoYS_2
 */
public class Images {
    
    public static Image CURSOR, SIDEBAR, CLOUD1, CLOUD2, CLOUD3, /*GRASS,*/ DIRT, SPLASH, TREE, BARBEDWIRE, MACHINEGUN, MACHINEGUNTRIPOD,
            FOOTSOLDIERSPRITESHEET,FOOTSOLDIERSHOOTING, ENGINEERSPRITESHEET,ENGINEERSHOOTING, TENT, SNIPERSHOOTING, GRENADE,
            TITLEPAGE, TITLE, MESSAGEBOX, LEVELSELECT, MENU, DEADTANK;
    public static void LoadImages() throws SlickException{
        TITLEPAGE = new Image("res/titlepage.png");
        TITLE = new Image("res/title.png");
        MENU = new Image("res/menubutton.png");
        LEVELSELECT = new Image("res/levelselecttitle.png");
        MESSAGEBOX = new Image("res/textboxbackground.png");
        CURSOR = new Image("res/cursor.png");
        SIDEBAR = new Image("res/sidebar.png");
        DEADTANK = new Image("res/deadtank.png");
        CLOUD1 = new Image("res/cloud1T.png");
        CLOUD2 = new Image("res/cloud2T.png");
        CLOUD3 = new Image("res/cloud3T.png");
        //GRASS = new Image("grass.jpg");
        DIRT = new Image("res/dirt.png");
        SPLASH = new Image("res/splashvfx.png");
        TREE = new Image("res/tree.png");
        BARBEDWIRE = new Image("res/barbedwire.png");
        MACHINEGUN = new Image("res/machinegun.png");
        MACHINEGUNTRIPOD = new Image("res/tripod.png");
        FOOTSOLDIERSPRITESHEET = new Image("res/footsoldierwalking.png");
        FOOTSOLDIERSHOOTING = new Image("res/footsoldiershooting.png");
        ENGINEERSPRITESHEET = new Image("res/engineerwalking.png");
        ENGINEERSHOOTING = new Image("res/engineershooting.png");
        TENT = new Image("res/tent.png");
        SNIPERSHOOTING = new Image("res/snipershooting.png");
        GRENADE = new Image("res/grenade.png");
    }
}
