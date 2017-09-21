/*
*   OdysseyNow - Runner Class
*
*   Lead Developer: Patrick Healy, pat.healy@pitt.edu
*   Assistant Developer: Christian Brill, cjb122@pitt.edu
*   Producer: Zachary Horton
*/

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
    
    double scale = 1;
    static boolean useAnalog = false;
	
    static boolean mac = false; //set true if playing on macOS
	//  I took some inspiration from the jssc code to set this for us, later --JLM
	
    int numberOfGames = 15;
    
    boolean linePresent = true;
    
    boolean PlayerPlayerCollision = false; 
    int ballLineCollision = 0;
    
    boolean inertia = false;
    
    boolean Accessory = false; 
    boolean AccessoryHitExtinguish = false; //false is ball, true is p2 
    boolean AccessoryResetAction = false; 
    
    boolean serve = false;
	
	volatile static boolean programRunning = true;
	
	static Thread[] threads = new Thread[8];

    
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
	volatile static boolean[] assigned = new boolean[8];  //this is a variable related to the alternate method of analog input
    volatile int RV, RE, RH, xKnob, englishKnob, yKnob;
    
    //input thread for analog inputs
    inputThread inThread = new inputThread(this);
    
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
                        gameChoice = numberOfGames;
                    else
                        gameChoice--;
                    choiceTimer = 1000;
                    loadGame(gameChoice);
                }
                else if(keyA == 14 || keyA == 15){
                    box2.makeVisible();
                    pongBall.setVisibility(true);
                    if(serve){
                        pongBall.serve();
                    }
                }
                else if(keyA == 16){
                    pongBall.addSpeed();
                }
                else if(keyA == 17){
                    pongBall.subSpeed();
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
        if(box2.present)
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
        
        if (a == KeyEvent.VK_T)
            return 16;
        if (a == KeyEvent.VK_Y)
            return 17;
        
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
        String[] newAttributes = reader.read(choice);
        
        //get presence of player 2 box
        box2.setPresence(newAttributes[0].equals("yes"));
        
        //presence of ball
        pongBall.setPresence(newAttributes[1].equals("yes"));
        
        //size of ball
        if(newAttributes[2].equals("large")){
            pongBall.makeBig();
        }
        else{
            pongBall.makeSmall();
        }
        
        //presence of line
        linePresent = newAttributes[3].equals("yes");
        
        //player2 - ball collision
        if(newAttributes[4].equals("bounce")){
            box2.setBallCollide(0);   
        } 
        else if(newAttributes[4].equals("extinguishPlayer")){
            box2.setBallCollide(1);   
        } 
        else if(newAttributes[4].equals("extinguishBall")){
            box2.setBallCollide(2);  
        }
        
        //Player-Player Collision
        PlayerPlayerCollision = newAttributes[5].equals("yes");
        
        //ball-line collision
        if(newAttributes[6].equals("passthrough")){
            ballLineCollision = 0;
        }
        else if(newAttributes[6].equals("bounce")){
            ballLineCollision = 1;
        }
        else if (newAttributes[6].equals("extinguish")){
            ballLineCollision = 2;
        }
        
        //line position
        if(newAttributes[7].equals("center")){
            mid.move(mid.center);
        }
        else if(newAttributes[7].equals("left")){
            mid.move(mid.left);
        }
        
        //line size
        if(newAttributes[8].equals("full")){
            mid.setFull(true);
        }
        else{
            mid.setFull(false);
        }
            
        //player inertia
        inertia = newAttributes[9].equals("yes");
        
        //gets if reset behavior includes serves
        serve = newAttributes[10].equals("serve from right");
        
        //get if accessory attachement is used
        Accessory = newAttributes[11].equals("yes");
        
        //get AccessoryHitExtinguish
        AccessoryHitExtinguish = newAttributes[12].equals("p2");
    
    }
    
    @Override
    public void paint(Graphics g) {//
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Call paint method of all actors
        g2d.setColor(Color.BLACK);
        g2d.fillRect( 0, 0, (int)(scale*xSize), (int)(scale*ySize));
        
        g2d.setColor(Color.WHITE);
        box1.paint(g2d);
        box2.paint(g2d);
        mid.paint(g2d);
        pongBall.paint(g2d);
        
        if(choiceTimer > 0)
            g2d.drawString("" + gameChoice, (int)(50*scale), (int)(50*scale));
            //paint choice number
        decrementTimer();
    }

    public void setControllerValues(){
        
        //multithread here
        //controllerValues = SerialInput.getInput();
        inThread.run();
        
        
        RV = Integer.parseInt(controllerValues[4]);    
        RE = Integer.parseInt(controllerValues[5]);
        RH = Integer.parseInt(controllerValues[3]);
        
        xKnob = Integer.parseInt(controllerValues[1]);
        englishKnob = Integer.parseInt(controllerValues[0]);
        yKnob = Integer.parseInt(controllerValues[2]);
    }
    
    private class inputThread extends Thread{
        ODSYRunner parentGame;
        
        inputThread(ODSYRunner thisGame){
             super("my extending thread");
             start();
           }
           public void run(){
             try{
                parentGame.controllerValues = SerialInput.getInput(); 
             }
             catch(Exception e){}
           }
    }
	
	//Depending on how the microcontroller is designed, we may or may not be able to use this, but I'll leave it here
	public static class AnalogInputListener implements Runnable
	{
		public void run() {  
			boolean firstTime = true;
			int id = 0;
			while(true)
			{
				if(firstTime) //get assignment for which input particular thread is listening for.
				{
					for(int zz = 0; zz < 8; zz++)
					{
						if(assigned[zz] == false)
						{
							id = zz;
							assigned[zz] = true;
							break;
						}
					}
					firstTime = false;
				}
				try{
					switch(id) {
						case 0: //listen for a change in the English
								Thread.sleep(1000);
								break;
						case 1: //listen for a change in the xKnob
								Thread.sleep(1000);
								break;
						case 2: //listen for a change in the yKnob
								Thread.sleep(1000);
								break;
						case 3: //listen for a change in the RH
								Thread.sleep(1000);
								break;
						case 4: //listen for a change in the RV
								Thread.sleep(1000);
								break;
						case 5: //listen for a change in the RE
								Thread.sleep(1000);
								break;
						case 6: //???
								Thread.sleep(1000);
								break;
						case 7: //???
								Thread.sleep(1000);
								break;
					}
				}
				catch(InterruptedException e)
				{
					//do nothing
				}
			}
			
		}
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
    
    public double getScale(){
        return scale;
    }
    
    public void setScale(double a){
        scale = a;
    }

    public static void main(String[] args) throws InterruptedException {
		
		String osName = System.getProperty("os.name");
		if(osName.equals("Mac OS X") || osName.equals("Darwin")){
			mac = true;
		}
		
        JFrame frame = new JFrame("ODSY Redux");
        ODSYRunner game = new ODSYRunner();
        
        frame.add(game);
        frame.setSize( 800, 600);
        frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.checkForController();
		/*if(useAnalog)  //see the AnalogInputListener class above
		{
			for(int xyy = 0; xyy < 8; xyy++){
				(new Thread(new AnalogInputListener())).start();
			}
		}*/
        
        
        while (true) {
            
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