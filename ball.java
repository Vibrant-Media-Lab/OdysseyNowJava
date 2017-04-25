import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class ball{
    int spin1 = 0;
    int spin2 = 0;
    boolean directionRight = true;
    
    double speed = 5;
    double decelRate = 0.03;
    double minMaxSpeed = 4.2;
    double maxMaxSpeed = 9;
    double maxSpeed = 7;
    
    double x;
    double y;
    int size;
    
    int xMin;
    int xMax;
    int yMin;
    int yMax;
    
    boolean present = true;
    boolean visible = true;
    
    private ODSYRunner game;
    
    public ball(ODSYRunner in){
        game = in;
        x = game.xSize/2;
        y = game.ySize/2;
        xMin = (int)(0 - (x/8));
        xMax = (9/8)*game.xSize;
        yMin = (int)(0 - (y/8));
        yMax = (9/8)*game.ySize;
        
        size = (game.box1).getSize();
        size = (int)(((3)*((double)size))/5);
    }
    
    public void move(){
        speed -= decelRate;
        decelRate = ((double)0.03/7)*speed;
        
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
        
        if(x < xMin){ x = xMin; }
        if(x > xMax){ x = xMax; }
        if(y < yMin){ y = yMin; }
        if(y > yMax){ y = yMax; }
        
    }
    
    public void keyTyped(int a){}
    
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
        maxSpeed++;
        if(maxSpeed > maxMaxSpeed)
            maxSpeed = maxMaxSpeed;
    }
    
    public void subSpeed(){
        maxSpeed--;
        if(maxSpeed < minMaxSpeed)
            maxSpeed = minMaxSpeed;
    }
    
    public void setDirection(boolean dir){
        directionRight = dir;
        speed = maxSpeed;
    }
    
    public boolean getDirection(){
        return directionRight;
    }
    
    public boolean onScreen(){
        if(x > game.xSize || y > game.ySize){
            return false;
        }
        if(x < (0 - size) || y < (0 - size) ){
            return false;
        }
        
        return true;
    }
    
    public void setPosition(int xIn, int yIn){
        x = xIn;
        y = yIn;
    }
    
    public void reset1(){
        //x = (game.box1).getX();
        //y = (game.box1).getY();
        if(!getDirection())
            setDirection(true);
        
    }
    
    public void reset2(){
        //x = (game.box2).getX();
        //y = (game.box2).getY();
        //directionRight = false;
        if(getDirection())
            setDirection(false);
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
    
    public void setPresence(boolean a){
        present = a;
        visible = a;
        setDirection(false);
    }
    
    public void setVisibility(boolean a){
        visible = a;
    }
    
    public void makeBig(){
        size = (game.box1).getSize();
    }
    
    public void makeSmall(){
        size = (game.box1).getSize();
        size = (int)(((3)*((double)size))/5);
    }
    
    public void bounce(){
        if(getDirection())
            setDirection(false);
        else
            setDirection(true);
    }
    
    public void serve(){
        setDirection(false);
        x = game.xSize + 10;
        y = game.ySize/2;
    }
    
    public void paint(Graphics2D g) {
        if(visible && present)
            g.fillRect( (int)Math.floor(x), (int)Math.floor(y), size, size);
    }

}