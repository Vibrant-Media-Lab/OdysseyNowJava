import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class ball{
    int spin1 = 0;
    int spin2 = 0;
    boolean directionRight = true;
    
    double speed = 5;
    double decelRate = 0.03;
    
    double x;
    double y;
    int size = 30;
    
    private ODSYRunner game;
    
    public ball(ODSYRunner in){
        game = in;
        x = game.xSize/2;
        y = game.ySize/2;
        //size = (game.box1).getSize();
        //size = (7/8)*size;
    }
    
    public void move(){
        speed -= decelRate;
        if(speed < 0)
            speed = 0;
        
        if(directionRight){
            x = x + (speed*(Math.cos(Math.toRadians(spin1))));
            y = y + (speed*(Math.sin(Math.toRadians(spin1))));
        }
        else{
            x = x - (speed*(Math.cos(Math.toRadians(spin2))));
            y = y - (speed*(Math.sin(Math.toRadians(spin2))));
        }
        
    }
    
    public void keyTyped(int a){
    }
    
    public void setSpeed(int newSpeed){
        speed = newSpeed;
    }
    
    public void addSpin1(){
        spin1 = spin1 + 1;
        if(spin1 > 45){
            spin1 = 45;
        }
    }
    
    public void addSpin2(){
        spin2 = spin2 + 1;
        if(spin2 > 45){
            spin2 = 45;
        }
    }
    
    public void subtractSpin1(){
        spin1 = spin1 - 1;
        if(spin1< -45){
            spin1 = -45;
        }
    }
    
    public void subtractSpin2(){
        spin2 = spin2 - 1;
        if(spin2 < -45){
            spin2 = -45;
        }
    }
    
    public void addSpeed(){
        speed++;
    }
    
    public void setDirection(boolean dir){
        directionRight = dir;
        speed = 5;
    }
    
    public boolean getDirection(){
        return directionRight;
    }
    
    public void setPosition(int xIn, int yIn){
        x = xIn;
        y = yIn;
    }
    
    public void reset1(){
        x = (game.box1).getX();
        y = (game.box1).getY();
        directionRight = true;
    }
    
    public void reset2(){
        x = (game.box2).getX();
        y = (game.box2).getY();
        directionRight = false;
    }
    
    public void setSpin(int spinA, int spinB){
        double ratioA = ((double)spinA)/((double)1024);
        double ratioB = ((double)spinB)/((double)1024);
        
        spin1 = (int)((ratioA*90) - 45);
        spin2 = (int)((ratioB*90) - 45);
    }
    
    public int getX(){
        return (int)x;
    }
    
    public int getY(){
        return (int)y;
    }
    
    public int getSize(){
        return size;
    }
    
    public void paint(Graphics2D g) {
            g.fillRect( (int)Math.floor(x), (int)Math.floor(y), size, size);
    }

}