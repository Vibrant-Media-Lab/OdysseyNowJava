import java.awt.*;
import java.awt.event.*;
import java.land.Math;

public class ball{
    int spin = 0;
    boolean directionRight = true;
    int speed = 5;
    int x;
    int y;
    
    public ball(){
        
    }
    
    public void move(){
        if(directionRight){
            x = x + speed*(Math.cos(spin));
            y = y - speed*(Math.sin(spin));
        }
        else{
            x = x - speed*(Math.cos(spin));
            y = y + speed*(Math.sin(spin));
        }
    }
    
    public void setSpeed(int newSpeed){
        speed = newSpeed;
    }
    
    public void setDirection(boolean dir){
        directionRight = dir;
    }
    
    public void setPosition(int xIn, int yIn){
        x = xIn;
        y = yIn;
    }
    
    public void setSpin(int spinIn){
        spin = spinIn;
    }
    
    
    public void paint(Graphics2D g) {
            g.fillOval(x, y, 30, 30);
    }

}