import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


class SlaveThread implements Runnable {
   private Thread t;
   private String threadName;
   static public AtomicInteger xRate = new AtomicInteger(0);
   static public AtomicInteger yRate = new AtomicInteger(0);
   static public AtomicInteger zRate = new AtomicInteger(0);
   static public AtomicInteger index=new AtomicInteger(0);
   static public AtomicInteger middle=new AtomicInteger(0);
   static public AtomicInteger ring=new AtomicInteger(0);
   static public AtomicInteger pinky=new AtomicInteger(0);
   static public AtomicBoolean stop = new AtomicBoolean(false);
   static int xr=xRate.get();
   static int yr=yRate.get();
   static int oldcopy = 0;
   static int oldpaste = 0;
   static int oldredo = 0;
   static int oldundo = 0;
   static int oldgroup = 0;
   static int oldungroup = 0;
   static int oldduplicate = 0;
   static int oldlock = 0;
   static int oldselectAll = 0;
   static int olddelete = 0;
   static int oldfitView = 0;
   static int oldDrop = 0;
   static int oldworkplane = 0;
   static int oldruler = 0;
   static public boolean arrowkeys=false;
   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   double width = screenSize.getWidth();
   double height = screenSize.getHeight();
   //MouseInfo.getPointerInfo().getLocation().x
   SlaveThread( String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
   }
   static public synchronized void reconfigure(int x, int y)
   {
	   
   }
   public void run() {
      System.out.println("Running " +  threadName );
      try {
    	  Robot robot = new Robot();
    	  while(!stop.get()){
    		  xr=xRate.get();
    		  yr=yRate.get();
    	      int dir = direction();
    	      //System.out.println("***************"+dir);
    	      //System.out.println("***************"+SlaveThread.index.get());
    		     if ( index.get() ==  1){ 
    		    	 if(!GUI.dominant){
    	        		GUI.rightIndexPushed();
    		    	 } else {
    	        		GUI.leftIndexPushed();
    		    	 }
    	            	switch(dir){ 	
    	            	case 0: 
    	            		oldlock = 0;
        	        		oldduplicate = 0;
        	        		oldgroup = 0;
        	        		oldungroup = 0;
    	            		break;
    	            	
    	            	case 1:			// if right turn group 
    	                	if (xr> 45 && oldgroup == 0){ //works
    	                		Tinker.group(robot);
    	                		oldgroup = 1;
    	                		oldungroup = 0;
    	                		oldduplicate = 0;
    	                		oldlock = 0;
    	                		GUI.cmd_val("group");
    	                		System.out.println("group");
    	                	}
    	            		break;
    	            		
    	            	case 2:// if left turn ungroup
    	                	if (xr> 45  && oldungroup == 0){ //works
    	                		Tinker.ungroup(robot);
    	                		oldungroup = 1;
    	                		oldgroup = 0;
    	                		oldduplicate = 0;
    	                		oldlock = 0;
    	                		GUI.cmd_val("ungroup");
    	                		System.out.println("ungroup");
    	                	}  
    	            		break;
    	            		
    	            	case 3:   //if Down duplicate
    	            		if (yr> 45 && oldduplicate == 0){ //works
    	                		Tinker.duplicate(robot);
    	                		oldduplicate = 1;
    	                		oldgroup = 0;
    	                		oldungroup = 0;
    	                		oldlock = 0;
    	                		GUI.cmd_val("duplicate");
    	                		System.out.println("duplicate");
    	                	}
    	            		break;
    	            		
    	            	case 4: // if up lock
    	        			if (yr> 45 && oldlock == 0){//works
    	                		Tinker.lock(robot);
    	                		oldlock = 1;
    	                		oldduplicate = 0;
    	                		oldgroup = 0;
    	                		oldungroup = 0;
    	                		GUI.cmd_val("lock");
    	                		System.out.println("lock");
    	                	}
    	            		break;
    	            	}        		
    	            } else if(index.get() ==  0){
    	            	if (!GUI.dominant)
    	        			GUI.rightIndexReleased();
    	        		else
    	        			GUI.leftIndexReleased();
    	            	oldlock = 0;
    	        		oldduplicate = 0;
    	        		oldgroup = 0;
    	        		oldungroup = 0;
    	            }   
    	          
    	          if (middle.get() == 1){
    	        	  if(!GUI.dominant)
    	        			GUI.rightMiddlePushed();
    	        	  else 
    	        			GUI.leftMiddlePushed();
    	          	switch(dir){ 
    	          	case 0: 
    	          		oldpaste = 0;
	              		oldcopy = 0;
	              		oldundo = 0;
	              		oldredo = 0;
	              		break;
    	          	case 1:			// if right turn paste
    	              	if (xr> 45 && oldpaste == 0){//works
    	              		Tinker.paste(robot);
    	              		oldpaste = 1;
    	              		oldcopy = 0;
    	              		oldundo = 0;
    	              		oldredo = 0;
	                		GUI.cmd_val("PASTE");
    	              		System.out.println("PASTE");
    	              	}
    	          		break;
    	          	case 2:// if left turn copy
    	              	if (xr> 45  && oldcopy == 0){//works
    	              		Tinker.copy(robot);
    	              		oldcopy = 1;
    	              		oldpaste = 0;
    	              		oldundo = 0;
    	              		oldredo = 0;
	                		GUI.cmd_val("copy");
    	              		System.out.println("copy");
    	              	}  
    	          		break;
    	          		
    	          	case 3:   //if Down undo
    	          		if (yr> 45 && oldundo == 0){//works
    	              		Tinker.undo(robot);
    	              		oldundo = 1;
    	              		oldpaste = 0;
    	              		oldcopy = 0;
    	              		oldredo = 0;
	                		GUI.cmd_val("undo");
    	              		System.out.println("undo");
    	              	}
    	          		break;
    	          		
    	          	case 4: // if up redo
    	      			if (yr> 45 && oldredo == 0){ //works
    	              		Tinker.redo(robot);
    	              		oldredo = 1;
    	              		oldcopy = 0;
    	              		oldpaste = 0;
    	              		oldundo = 0;
	                		GUI.cmd_val("redo");
    	              		System.out.println("redo");
    	              	}
    	          		break;
    	          	}
    	          	
    	          	
    	          } else if(middle.get() == 0){
    	        	  	if (!GUI.dominant)
    	        			GUI.rightMiddleReleased();
    	        		else 
    	        			GUI.leftMiddleReleased();
	    	        	oldpaste = 0;
	    	          	oldcopy = 0;
	    	          	oldundo = 0;
	    	          	oldredo = 0; 
    	          }
    	          
    	          
    	          // ring
    	          if ( ring.get() ==  1){ 
    	        	if (!GUI.dominant)
          				GUI.rightRingPushed();
          			else
          				GUI.rightRingPushed();
    	          	switch(dir){ 
    	          	case 0: 
	              		oldselectAll = 0;
	              		olddelete = 0;
	              		oldfitView = 0;
	              		oldDrop = 0;
	              		break;
    	          	case 1:			// if left turn selectAll 
    	              	if (xr> 45 && oldselectAll == 0){ //works
    	              		Tinker.selectAll(robot);
    	              		oldselectAll = 1;
    	              		olddelete = 0;
    	              		oldfitView = 0;
    	              		oldDrop = 0;
	                		GUI.cmd_val("selectAll");
    	              		System.out.println("selectAll");
    	              	}
    	          		break;
    	          		
    	          	case 2:// if right turn delete
    	              	if (xr> 45 && olddelete == 0){ //works
    	              		Tinker.delete(robot);
    	              		olddelete = 1;
    	              		oldselectAll = 0;
    	              		oldfitView = 0;
    	              		oldDrop = 0;
	                		GUI.cmd_val("delete");
    	              		System.out.println("delete");
    	              	}  
    	          		break;
    	          	case 3:   //if Down fitView
    	          		if (yr> 45 && oldfitView == 0){ //works
    	              		Tinker.fitView(robot);
    	              		oldfitView = 1;
    	              		olddelete = 0;
    	              		oldselectAll = 0;
    	              		oldDrop = 0;
	                		GUI.cmd_val("fitView");
    	              		System.out.println("fitView");
    	              	}
    	          		break;
    	          		
    	          	case 4: // if up Drop
    	      			if (yr> 45 && oldDrop == 0){ //woks
    	              		Tinker.Drop(robot);
    	              		oldDrop = 1;
    	              		oldfitView = 0;
    	              		olddelete = 0;
    	              		oldselectAll = 0;
	                		GUI.cmd_val("Drop");
    	              		System.out.println("Drop");
    	              	}
    	          		break;
    	          	}
    	          	        		
    	          } else if(ring.get() ==  0){
          			if(!GUI.dominant)
          				GUI.rightRingReleased();
          			else
          				GUI.leftRingReleased();
    	          	oldDrop = 0;
    	      		oldfitView = 0;
    	      		olddelete = 0;
    	      		oldselectAll = 0;
    	          }
    	      }  
    	  }catch (AWTException e) {
    	         System.out.println("Thread " +  threadName + " interrupted.");
    	   } 
     
   }
   
   static int direction(){
   	int dirX, dirY;
   	//System.out.println(yr+", "+xr);
   	if (xr > 75) {
   		xr = xr - 49;
   		dirX = 1; // turning right
   	}
   	else if(xr>25 && xr<50){
   		dirX= 2; // Turning left
   	}
   	else 
   		dirX=0;
   	if (yr > 75){
   		yr = yr - 49;
   		dirY = 3; // Down
   	}
   	else if(yr>25 && yr<50){
   		dirY = 4; // up 
   	}
   	else
   		dirY=0;
   	
   	if (xr > yr){
   		return dirX;
   	} else{
   		return dirY;
   	}
   }
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}
