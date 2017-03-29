import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class ball{
    int spin = 0;
    boolean directionRight = true;
    double speed = 0.5;
    double x;
    double y;
    
    private ODSYRunner game;
    
    public ball(ODSYRunner in){
        game = in;
        x = game.xSize/2;
        y = game.ySize/2;
    }
    
    public void move(){
        if(directionRight){
            x = x + (speed*(Math.cos(spin)));
            y = y + (speed*(Math.sin(spin)));
        }
        else{
            x = x - (speed*(Math.cos(spin)));
            y = y - (speed*(Math.sin(spin)));
        }
    }
    
    public void keyTyped(int a){
        if(a == 8){
            addSpin();
        }
    }
    
    public void setSpeed(int newSpeed){
        speed = newSpeed;
    }
    
    public void addSpin(){
        spin = spin + 50;
    }
    
    public void addSpeed(){
        speed++;
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
            g.fillOval( (int)Math.floor(x), (int)Math.floor(y), 30, 30);
    }

}