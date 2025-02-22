import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import arduino.*;



public class ArduinoConnect {
	// input : xRate, yRate, zRate, leftClick, rightClick, mouseLock, recenter
	static RunnableDemo R1 = new RunnableDemo( "Thread-1");
	static SlaveThread R2 = new SlaveThread("Thread-2");
	
	static int oldLeft = 0;
	static int oldRight = 0;
	static int oldLock = 0;
	static int lockSet = 0;
	static int oldRecenter = 0;
	static int state = 0;
	static int ringmodes = 6;//is the number of modes for the ring finger
	static String[] mode={"reg","ylock","xlock","all-lock","arrowkeys","slow"};
	static boolean arrowkeys=false;
	private static int mousespeed=3;//the higher this is the slower the mouse moves. 3 is the normal mode, 10 is slow
    
	static String comMaster="COM19";//often times the right hand
	static String comSlave="COM21";
	static Arduino nano1 = new Arduino(comMaster, 115200);
    static Arduino nano2 = new Arduino(comSlave, 115200);
    
	public static void main(String[] args) throws Exception {

		GUI.createWindow();
		
		if (GUI.dominant){
			System.out.println("Dominant hand : Right");
			System.out.println("COM Port: " + GUI.rightPort);
			comMaster = GUI.rightPort;
			comSlave = GUI.leftPort;
			nano1 = new Arduino(comMaster, 115200);
			nano2 = new Arduino(comSlave, 115200);
		}
		else{
			System.out.println("Dominant hand : Left");
			System.out.println("COM Port: " + GUI.leftPort);
			comMaster = GUI.leftPort;
			comSlave = GUI.rightPort;
			nano1 = new Arduino(comMaster, 115200);
			nano2 = new Arduino(comSlave, 115200);
		}
		if(nano1.openConnection()){
			//GUI.createWindow();
    		System.out.println("Connection established Master"+comMaster);
    		if (nano2.openConnection()){
    			System.out.println("Connection established Slave"+comSlave);
    			//starts the threads for the left hand and the right hand
	    		R1.start();
	    		R2.start();
	    		int x=0;
	    		//change loop to reflect the time that one want to run the simulation
	        	while(x < 1000000)
	        	{
	        		String data = nano1.serialRead();
	        		String lines[] = data.split("\\r?\\n");
	        		
	        		//loop that handles dominate hand input. 
	        		for (int i=0; i<Array.getLength(lines);++i)
	        		{
	        			//System.out.println(lines[i]);
	        			//System.out.println(data);
	        			mouseMove(lines[i]);
	        			x++;
	        		}
	        		
	        		String dataSecondary = nano2.serialRead();
	        		String linesSecond[] = dataSecondary.split("\\r?\\n");
	        		
	        		//Runs the non dominate hand input
	        		for (int i=0; i<Array.getLength(linesSecond);++i)
	        		{
	        			//System.out.println(linesSecond[i]);
	        			//System.out.println(data);
	        			left_hand(linesSecond[i]);
	        		}
		       	}
	        	RunnableDemo.stop.set(true);
	        	SlaveThread.stop.set(true);
	    		
	        	nano1.closeConnection();
	        	nano2.closeConnection();
    		}
    	}
		
    	else{
    		System.out.println("Connection not established");
    		return;
    	}
    	

	}
    public static void left_hand(String data) throws AWTException{
    	 if(data.length() != 10) 
         {
         	//System.out.println("Input string not the right length");
         	return;
         }
    	String xR = data.substring(0,2);
		String yR = data.substring(2,4);
		String zR = data.substring(4,6);
		 

	  SlaveThread.xRate.set(Integer.parseInt(xR));
	  SlaveThread.yRate.set(Integer.parseInt(yR));
	  SlaveThread.zRate.set(Integer.parseInt(zR));
      SlaveThread.index.set(Character.getNumericValue(data.charAt(6))); 
      SlaveThread.middle.set(Character.getNumericValue(data.charAt(7))); 
      SlaveThread.ring.set(Character.getNumericValue(data.charAt(8))); 
      return;
	}
	// takes in a string containing the three data points and operates the mouse in turn
    public static void mouseMove(String data) throws AWTException
    {
        Robot robot = new Robot();
        
        //check if input is the right length
        if(data.length() != 10) 
        {
        	return;
        }
        //getting xyz values from the first 3 characters of our data string
        //0 indicates no movement; 1,2,3 means degree of positive movement; 4,5,6 indicates negative movement
		String xR = data.substring(0,2);
		String yR = data.substring(2,4);
		String zR = data.substring(4,6);
		
		
        int xRate = Integer.parseInt(xR);
        int yRate = Integer.parseInt(yR);
        int zRate = Integer.parseInt(zR);
        
        //get 0/1 for each of the 4 buttons to indicate whether they are pushed (receive 1) or not (return 0)
        int leftClick = Character.getNumericValue(data.charAt(6));
        int rightClick = Character.getNumericValue(data.charAt(7));
        int mouseLock = Character.getNumericValue(data.charAt(8));
        int recenter = Character.getNumericValue(data.charAt(9));
        
        // Retrieve current mouse positions
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;
        
        
        // Update mouse state based on read in data
        if(leftClick == 1)	//if left button is clicked, run through checks
        {
        	if(oldLeft == 0)	//if oldLeft = 0, it means that left click wasn't active last loop
        	{
        		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); //do a left button click
        		oldLeft = 1; //set oldLeft to 1 to know that the button is currently held down
        		if(GUI.dominant){
        			GUI.rightIndexPushed();
        		} else {
        			GUI.leftIndexPushed();
        		}
        		
        	}
        }
        else if(leftClick == 0) // check if left button is not held down
        {
        	if(oldLeft == 1)	//if the left button was previously held down, do a left button release command
        	{
        		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        		oldLeft = 0;	//set oldLeft to 0 to know that the button is no longer held
        		if (GUI.dominant)
        			GUI.rightIndexReleased();
        		else
        			GUI.leftIndexReleased();
        	}
        } 
        if(rightClick == 1)	//same logic as left click
        {
        	if(oldRight == 0)
        	{
        		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        		oldRight = 1;
        		if(GUI.dominant)
        			GUI.rightMiddlePushed();
        		else 
        			GUI.leftMiddlePushed();
        	}
        }
        else if(rightClick == 0)
        {
        	if(oldRight == 1)
        	{
        		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK); 
        		oldRight = 0;
        		if (GUI.dominant)
        			GUI.rightMiddleReleased();
        		else 
        			GUI.leftMiddleReleased();
        	}
        }
        
        if(mouseLock == 1)	//same logic for the "old" state
        {
        	if(oldLock == 0)
        	{
        		//6 differnt modes 
        		//1.) move 
        		//2.) lockx 
        		//3.) locky
        		//4.) lockall
        		//5.) moveslow
        		//6.) arrow keys
        		++lockSet;
        		if(lockSet >= ringmodes)
        		{
        			lockSet = 0;	// use lockSet as a toggle for locking out the x movement
        			mousespeed=3;
        		}
        		else if (lockSet ==4){
        			arrowkeys=true;
        		}
        		else if (lockSet ==5){
        			arrowkeys=false;
        		}
        		System.out.print(mode[lockSet]);
        		oldLock = 1; //set the oldLock to 1 if the lock button is pressed and wasn't pressed previously
        		if(state == 0)
        			if (GUI.dominant)
        				GUI.rightRingPushed();
        			else
        				GUI.rightRingPushed();
        	}
        }
        else if(mouseLock == 0)
        {
        	if(oldLock == 1){
        		oldLock = 0;
        		if(state == 0)
        			if(GUI.dominant)
        				GUI.rightRingReleased();
        			else
        				GUI.leftRingReleased();
        	}
        }
        
        if(lockSet == 1)
        {
        	xRate = 0;		//only lock out x movement if the lock is set to 1
        }
        else if(lockSet == 2)
        {
        	yRate = 0;		//only lock out x movement if the lock is set to 1
        }
        else if (lockSet == 3)
        {
        	yRate = 0;
        	xRate = 0;
        }
        else if (lockSet == 5)
        {
        	mousespeed=10;
        }
        GUI.toggle_val(mode[lockSet]);
        if(recenter == 1)	//move the mouse to an arbitrarily determined point on the screen if recenter is pressed
        {
        	if(oldRecenter == 0)
        	{
        		oldRecenter = 1;
        		RunnableDemo.mouseX.set(1000);
        		RunnableDemo.mouseY.set(500);
        		if(state == 0)
        			if(GUI.dominant)
        				GUI.rightPinkyPushed();
        			else
        				GUI.leftPinkyPushed();
        		return;
        	}
        }
        else if(recenter == 0)
        {
        	if(oldRecenter == 1)
        	{
        		if(state == 0)
        			if(GUI.dominant)
        				GUI.rightPinkyReleased();
        			else
        				GUI.leftPinkyReleased();
        		oldRecenter = 0;
        	}
        }
        
        //currently nothing set for arrow keys
        if (arrowkeys)
        {
        	
        	
        	return;
        }
        
        //Interaction with the Tread for RunnableDemo. 
        //The runnable demo will always be running but the below if cases change the speed the mouse moves
        // x and y can be set to 0 for no movement. 
        
        if (xRate==0)
        {
        	RunnableDemo.xmove.set(0);
        }
        else if(xRate<=49)
		{
        	RunnableDemo.xmove.set((int) Math.ceil(xRate/mousespeed));
		}
        else if(xRate>=50)
		{
        	RunnableDemo.xmove.set((int) Math.floor((49-xRate)/mousespeed));
		}
		if(yRate==0)
		{
			RunnableDemo.ymove.set(0);
		}
		else if(yRate<=49)
		{
			RunnableDemo.ymove.set((int)Math.ceil (yRate/mousespeed));
		}
        else if(yRate>=50)
		{
			RunnableDemo.ymove.set((int) Math.floor((49-yRate)/mousespeed));
		}
        return;
    }
}
