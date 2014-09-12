/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

/**
 *
 * @author GeoYS_2
 */
public class Message {
    private int rows, lineSize;
    private String message;
    public Message(String s){
        message = s;
        lineSize = (int)Math.floor(Math.sqrt(s.length())) * 2;
        rows = (int)Math.ceil((float)s.length() / (float)lineSize);
    }
    
    public int getNumberRows(){
        return rows;
    }
    
    public int getLineSize(){
        return lineSize;
    }
    
    public int getMaxWidth(Font f){
        int maxwidth = 0;
        int lastBreak = 0;
        for(int i = 0; i < message.length(); ++i/*int i = 0; i < rows; ++i*/){
            if(message.charAt(i) == ' '){
                if(message.substring(lastBreak, i).length() >= lineSize){
                    if(f.getWidth(message.substring(lastBreak, i)) > maxwidth){
                        maxwidth = f.getWidth(message.substring(lastBreak, i));
                    }
                    lastBreak = i + 1;
                }
            }
            /*if(message.substring(i * lineSize).length() > lineSize){
                if(f.getWidth(message.substring(i * lineSize, (i+1)*lineSize)) > maxwidth){
                    maxwidth = f.getWidth(message.substring(i * lineSize, (i+1)*lineSize));
                }
            }*/
        }
        return maxwidth;
    }
    
    public void draw(Graphics g, float x, float y){
        String out = "";
        int lastBreak = 0;
        for(int i = 0; i < message.length(); ++i){
            if(i == message.length() - 1){
                out += message.substring(lastBreak, i);
            }
            else if(message.charAt(i) == ' '){
                if(message.substring(lastBreak, i).length() >= lineSize){
                    out += message.substring(lastBreak, i) + "\n";
                    lastBreak = i + 1;
                }
            }
        }
        /*for(int i = 0; i < rows; ++i){
            if(message.substring(i * lineSize).length() < lineSize){
                out += message.substring(i * lineSize);
            }
            else{
                out += message.substring(i * lineSize, (i+1)*lineSize) + "\n";
            }
        }*/
        g.drawString(out, x, y);
    }
}
