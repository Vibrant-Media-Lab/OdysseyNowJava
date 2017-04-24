import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Robot;
import java.lang.*;
import java.util.*;
import jssc.*;

@SuppressWarnings("serial")
public class ODSYRunner extends JPanel {
    
    //GAME CONFIG
    static boolean useAnalog = false;
    boolean mac = true; //set true if playing on macOS
    int numberOfGames = 15;
    
    boolean PlayerSpotRight = true;
    
    boolean ballInit = true; 
    boolean ballLarge = false; 
    
    boolean linePresent = true;
    int LinePosition = 400;
    boolean lineMoving = false;
    int LineHeight = 10; 
    
    int BallPlayerCollisionType = 0;
    boolean PlayerPlayerCollision = false; 
    boolean BallLineCollision = false;
    
    boolean delayedMove = false;
    int PlayerSpotSpeed = 0;
    int inertia = 0; 
    
    boolean Accessory = false; 
    boolean AccessoryHitExtinguish = false; 
    boolean AccessoryResetAction = false; 

    
    //Window size
    static int xSize = 800;
    static int ySize = 600;
    
    //player objects
    playerBox box1 = new playerBox(this, 1, xSize, ySize, useAnalog);
    playerBox box2 = new playerBox(this, 2, xSize, ySize, useAnalog);
    
    //midline object
    midLine mid = new midLine(this);
    
    //ball object
    ball pongBall = new ball(this);
    
    //game number
    int gameChoice = 1;
    int choiceTimer = 0;
    
    //controller variables
    String[] controllerValues = new String[8];
    int RV, RE, RH, xKnob, englishKnob, yKnob;
    
    //constructor, full of keyboard functions
    public ODSYRunner(){
        addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
                int keyA = detPlayerAction(e);
                if(keyA == -1)
                    return;
                else if(keyA < 4)
                    box1.keyReleased(keyA);
				else if(keyA < 8)
                    box2.keyReleased(keyA%4);
                else if(keyA == 13){
                    if(gameChoice == numberOfGames)
                        gameChoice = 1;
                    else
                        gameChoice++;
                    choiceTimer = 1000;
                    loadGame(gameChoice);
                }
                else if(keyA == 12){
                    if(gameChoice == 1)
                        gameChoice = 6;
                    else
                        gameChoice--;
                    choiceTimer = 1000;
                }
                else if(keyA == 14 || keyA == 15){
                    box2.makeVisible();
                }
                    
			}
            
			@Override
			public void keyPressed(KeyEvent e) {
                int keyB = detPlayerAction(e);
                if(keyB == -1)
                    return;
                else if(keyB < 4)
                    box1.keyPressed(keyB);
                else if(keyB < 8)
				    box2.keyPressed((keyB%4));
                else if(keyB == 8)
                    pongBall.addSpin1();
                else if(keyB == 9)
                    pongBall.subtractSpin1();
                else if(keyB == 10)
                    pongBall.addSpin2();
                else if(keyB == 11)
                    pongBall.subtractSpin2();
                else if(keyB == 14)
                    pongBall.reset1();
                else if(keyB == 15)
                    pongBall.reset2();
			}
		});
		setFocusable(true);
    }
    
    //calls movement methods of all player actors
	private void moveBox() {
		box1.move();
        box2.move();
	}
    
    //calls movement method for ball
    private void moveBall(){
        pongBall.move();
    }
    
    private void checkCollide(){
        box1.checkCollide();
        if(PlayerSpotRight)
            box2.checkCollide();   
    }
    
    private int detPlayerAction(KeyEvent e){
        int a = e.getKeyCode();
        if (a == KeyEvent.VK_A)
            return 0;
        if (a == KeyEvent.VK_D)
            return 1;
        if (a == KeyEvent.VK_W)
            return 2;
        if (a == KeyEvent.VK_S)
            return 3;
        if (a == KeyEvent.VK_LEFT)
            return 4;
        if (a == KeyEvent.VK_RIGHT)
            return 5;
        if (a == KeyEvent.VK_UP)
            return 6;
        if (a == KeyEvent.VK_DOWN)
            return 7;
        
        if (a == KeyEvent.VK_2)
            return 8;
        if (a == KeyEvent.VK_3)
            return 9;
        if (a == KeyEvent.VK_PERIOD)
            return 10;
        if (a == KeyEvent.VK_SLASH)
            return 11;
        
        if (a == KeyEvent.VK_OPEN_BRACKET)
            return 12;
        if (a == KeyEvent.VK_CLOSE_BRACKET)
            return 13;
        
        if (a == KeyEvent.VK_R)
            return 14;
        if (a == KeyEvent.VK_COMMA)
            return 15;
        
        if (a == KeyEvent.VK_ESCAPE)
            System.exit(0);
        
        
        return -1;
    }
    
    public void decrementTimer(){
        choiceTimer--;
    }
    
    public void pressCommand(){
        try{
            Robot rob = new Robot();
            rob.keyPress(KeyEvent.VK_META);
        }
        catch(Exception e){System.out.println("Error: couldn't press command");}
    }
    
    public void loadGame(int choice){
        cardReader reader = new cardReader();
        reader.read(choice);
    }
    
    @Override
    public void paint(Graphics g) {//
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Call paint method of all actors
        g2d.setColor(Color.BLACK);
        g2d.fillRect( 0, 0, xSize, ySize);
        
        g2d.setColor(Color.WHITE);
        box1.paint(g2d);
        if(PlayerSpotRight)
            box2.paint(g2d);
        if(linePresent)
            mid.paint(g2d);
        if(ballInit)
            pongBall.paint(g2d);
        
        if(choiceTimer > 0)
            g2d.drawString("" + gameChoice, 50, 50);
            //paint choice number
        decrementTimer();
    }
    
    public void setControllerValues(){
        controllerValues = SerialInput.getInput();
        
        RV = Integer.parseInt(controllerValues[4]);    
        RE = Integer.parseInt(controllerValues[5]);
        RH = Integer.parseInt(controllerValues[3]);
        
        xKnob = Integer.parseInt(controllerValues[1]);
        englishKnob = Integer.parseInt(controllerValues[0]);
        yKnob = Integer.parseInt(controllerValues[2]);
    }
    
    public void setPlayerDest(){
        box1.setDest(xKnob, yKnob);
        box2.setDest(RH, RV);
        pongBall.setSpin(englishKnob, RE);
    }
    
    public void checkForController(){
        String[] ports = SerialPortList.getPortNames();
        if(ports != null){
            for(int i = 0; i < ports.length; i++){
                if(ports[i].equals("COM3")){
                    useAnalog = true;
                    return;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("ODSY Redux");
        ODSYRunner game = new ODSYRunner();
        
        //get config
        
        frame.add(game);
        frame.setSize(xSize, ySize);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.checkForController();
        
        //get game1
        
        
        while (true) {
            //call 
            
            //INSERT FUNCTION HERE TO GET INPUT FROM CONTROLLER
            if(useAnalog){
                game.setControllerValues();
                game.setPlayerDest();
            }
            
            //System.out.println("Declared serial inputs");
            //check for colisions
            game.checkCollide();
            
            //repaint everything
            game.moveBox();
            game.moveBall();
            game.repaint();
            Thread.sleep(5);
            if(game.mac)
                game.pressCommand();
        }
    }
}