package ODSYredux;

import java.applet.Applet;

public class ODSYRunner extends Applet{

    int gameChoice = 0;
    
    public void init(){
        
    }
    
    public void paint(Graphics g){
        
    }
    
    public void update(Graphics g){ //main system tick function
        boolean exitTime = false;
        while(true){
            
            if(exitTime){
                exit();
            }
            
            try{
                Thread.sleep(10);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }
    
    private void exit(){
        System.exit(0);
    }
    
    
    private void decrementGame(){
        
    }
    
    private void incrementGame(){
        
    }
    
    

}