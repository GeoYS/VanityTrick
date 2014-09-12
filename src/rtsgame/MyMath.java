package rtsgame;


import org.newdawn.slick.geom.Vector2f;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author GeoYS_2
 */
public class MyMath {
    public static float distance (Vector2f p1, Vector2f p2){
        return (float) Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }
    
    public static float xDistance (Vector2f p1, Vector2f p2){
        return p1.x - p2.x;
    }
    
    public static float yDistance (Vector2f p1, Vector2f p2){
        return p1.y - p2.y;
    }
    
    public static float div(float n, float d){
        return (n - (n % d)) / d;
    }
}
