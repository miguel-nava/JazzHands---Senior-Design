import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


class RunnableDemo implements Runnable {
   private Thread t;
   private String threadName;
   //xmove and ymove are the rate at which to move. 
   static public AtomicInteger xmove=new AtomicInteger(0);
   static public AtomicInteger ymove=new AtomicInteger(0);
   //mouseX and mouseY are locations of mouse
   static public AtomicInteger mouseX=new AtomicInteger(0);
   static public AtomicInteger mouseY=new AtomicInteger(0);
   static public boolean arrowkeys=false;
   static public AtomicBoolean stop=new AtomicBoolean(false);
   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   double width = screenSize.getWidth();
   double height = screenSize.getHeight();
   //MouseInfo.getPointerInfo().getLocation().x
   RunnableDemo( String name) {
      threadName = name;
     // System.out.println("Creating " +  threadName );
   }
   static public synchronized void reconfigure(int x, int y)
   {
	   
   }
   public void run() {
     // System.out.println("Running " +  threadName );
      try {
    	  Robot robot = new Robot();
    	  for (int i=0; i<50000;++i){
	    	  int x=new Integer(RunnableDemo.xmove.get());
	    	  int y=new Integer(RunnableDemo.ymove.get());
	    	  //System.out.println("move="+x+","+y);
	    	 // System.out.println("move="+ArduinoConnect.xmove+","+ArduinoConnect.ymove);
	    	  if (stop.get())
	    		  	return;
	    	  if (arrowkeys){
	    		  if (x>0){
	    			  
	    		  }
	    		  else if (x<0){
	    		  }
	    		  if (y>0){
	    		  }
	    		  else if (y<0){
	    		  }
    		  }
	    	  else{
		    	  while (x!=0|| y!=0){	
		    		 // System.out.println("move="+ArduinoConnect.xmove+","+ArduinoConnect.ymove);
		    		  if (x>0){
		    			  --x;
		    			  if (width<mouseX.get()){
			    			  mouseX.set((int) width);
		    			  }
		    			  else
		    				  mouseX.getAndIncrement();
		    		  }
		    		  else if (x<0){
		    			  ++x;
		    			  if (0>mouseX.get()){
			    			  mouseX.set(0);
		    			  }
		    			  else
		    				  mouseX.getAndDecrement();
		    			  //System.out.println("x less then 0");
		    		  }
		    		  if (y>0){
		    			  --y;
		    			  if (height<mouseY.get()){
		    				  mouseY.set((int)height);
		    			  }
		    			  else
		    				  mouseY.getAndDecrement();
		    		  }
		    		  else if (y<0){
		    			  ++y;
		    			  if (0>mouseY.get()){
		    				  mouseY.set(0);
		    			  }
		    			  else
		    				  mouseY.getAndIncrement();
		    			 // System.out.println("y less then 0");
		    		  }
		    		  	int currX  = mouseX.get();
		    		  	int currY = mouseY.get();
		    		  	GUI.x_val(currX);
		    		  	GUI.y_val(currY);
						robot.mouseMove(currX, currY);
		    	  }
	    	  }
	    	 // System.out.println("move="+ArduinoConnect.xmove+","+ArduinoConnect.ymove);
	    	  //System.out.println("this is the move("+mouseX+","+mouseY);
	         Thread.sleep(5);
	         /*if (Thread.currentThread().isInterrupted()) {
	        	  // cleanup and stop execution
	        	  // for example a break in a loop
	        	 return;
	        	}*/
    	  }
      } catch (InterruptedException | AWTException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
      }
   }
   
   public void start () {
      //System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}
