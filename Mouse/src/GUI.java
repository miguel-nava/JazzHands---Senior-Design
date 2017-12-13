import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.GridBagLayout;
import javax.swing.JFrame;

import gnu.io.CommPortIdentifier;

import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.*;
public class GUI {
	public static boolean start = false;
	public static String rightPort = "";
	public static String leftPort = "";
	public static boolean dominant = true;

	
	static int PORTMAX = 256;
	static String[] portNames = new String[PORTMAX];	
	
	static String[] hands = { "Right" , "Left" };
	
	static int TOP = 0;
	static int BOTTOM = 6;
	static int LEFT = 0;
	static int RIGHT = 18;
	
	static JFrame frame = new JFrame("Jazz Hands");
    static GridBagConstraints c = new GridBagConstraints();
	// Title
	static JLabel title = new JLabel("Jazz Hands");
	
    // Labels
	static JLabel x_label = new JLabel("x: ");
	static JLabel y_label = new JLabel("y: ");
	static JLabel z_label = new JLabel("z: ");
	static JLabel toggle_label = new JLabel("Toggle: ");
	static JLabel cmd_label = new JLabel("Command");

	static JLabel right_label = new JLabel("Right Hand");
	static JLabel left_label = new JLabel("Left Hand");
	// Stats
	public static JLabel x_stat = new JLabel();	
	public static JLabel y_stat = new JLabel();	
	public static JLabel z_stat = new JLabel();	
	public static JLabel toggle_stat = new JLabel();	

	// RIGHT hand Fingers
    static JTextField indexR = new JTextField();
    static JTextField middleR = new JTextField();
    static JTextField ringR = new JTextField();
    static JTextField pinkyR = new JTextField();
	// LEFT hand Fingers
    static JTextField indexL = new JTextField();
    static JTextField middleL = new JTextField();
    static JTextField ringL = new JTextField();
    static JTextField pinkyL = new JTextField();
    
    // Button
    static JButton startButton = new JButton("Start");
	
    static void addLabel(Container pane, JLabel j, int x, int y){
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 10;
		c.weightx = 0.5;
		c.gridx = x;
		c.gridy = y;
		pane.add(j,c);
	}
    
    static void addFingers(Container pane, JTextField j, int x, int y){
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = c.SOUTH;
		c.ipadx = 10;
		c.weightx = 0.5;
		c.gridx = x;
		c.gridy = y;
		j.setBackground(Color.RED);
		pane.add(j,c);
	}
    
	public static void addComponentsToPane(Container pane) {
	    // sets layout type 
	    pane.setLayout(new GridBagLayout());
	    
	    // add Title
	    addLabel(pane, title, (LEFT+RIGHT)/2, TOP);
	    // adding labels along with their stats to be updated
	    addLabel(pane,x_label,LEFT,TOP+1);
	    addLabel(pane,y_label,LEFT,TOP+2);
	    c.anchor = c.EAST;
	    addLabel(pane,x_stat,LEFT+1,TOP+1);
	    addLabel(pane,y_stat,LEFT+1,TOP+2);
	    addLabel(pane,toggle_label,RIGHT,TOP+1);
	    addLabel(pane,toggle_stat,RIGHT,TOP+2);
	    addLabel(pane, cmd_label, ((LEFT+RIGHT)/2), BOTTOM+1);
	    
	    // adding RIGHT hand fingers
	    c.gridwidth = 6;
	    addLabel(pane, right_label, RIGHT-6, BOTTOM-2);
		c.gridwidth = 1;
	    c.ipady = 10;
	    addFingers(pane, indexR, RIGHT-7, BOTTOM-1);
		c.ipady = 20;
	    addFingers(pane, middleR, RIGHT-5, BOTTOM-1);
		c.ipady = 15;
	    addFingers(pane, ringR, RIGHT-3, BOTTOM-1);
		c.ipady = 5;
	    addFingers(pane, pinkyR, RIGHT-1, BOTTOM-1);
	    
	    // adding LEFT hand fingers
	    c.gridwidth = 6;
	    addLabel(pane, left_label, LEFT+3, BOTTOM-2);
	    c.gridwidth = 1;
		c.ipady = 10;
	    addFingers(pane, indexL, LEFT+8, BOTTOM-1);
		c.ipady = 20;
	    addFingers(pane, middleL, LEFT+6, BOTTOM-1);
		c.ipady = 15;
	    addFingers(pane, ringL, LEFT+4, BOTTOM-1);
		c.ipady = 5;
	    addFingers(pane, pinkyL, LEFT+2, BOTTOM-1);
	    
	    c.gridx = RIGHT;
	    c.gridy = BOTTOM+3;
	    pane.add(startButton, c);
	    startButton.addActionListener(new ActionListener(){
	    	@Override 
	    	public void actionPerformed(ActionEvent arg0){
	    		start = true;
	    	}
	    });
	    
	    JComboBox rightPorts = new JComboBox(portNames);
	    c.gridwidth = 7;
	    c.gridx = RIGHT-7;
	    c.gridy = BOTTOM;
	    pane.add(rightPorts, c);
	    rightPorts.setSelectedIndex(0);
	    rightPorts.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent arg0){
	    		JComboBox cb = (JComboBox)arg0.getSource();
	    		String port = (String)cb.getSelectedItem();
	    		//System.out.println(port);
	    		rightPort = port;
	    	}
	    });
	    

	    JComboBox leftPorts = new JComboBox(portNames);
	    c.gridx = LEFT+2;
	    c.gridy = BOTTOM;
	    c.gridwidth = 7;
	    pane.add(leftPorts, c);
	    leftPorts.setSelectedIndex(0);
	    leftPorts.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent arg0){
	    		JComboBox cb = (JComboBox)arg0.getSource();
	    		String port = (String)cb.getSelectedItem();
	    		//System.out.println(port);
	    		leftPort = port;
	    	}
	    });
	    
	    JComboBox hand = new JComboBox(hands);
	    c.gridx = LEFT;
	    c.gridy = BOTTOM+3;
	    //c.gridwidth = ;
	    pane.add(hand, c);
	    hand.setSelectedIndex(0);
	    hand.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent arg0){
	    		JComboBox cb = (JComboBox)arg0.getSource();
	    		String h = (String)cb.getSelectedItem();
	    		//System.out.println(port);
	    		if (h.equals(hands[0])){
	    			dominant = true;
	    		} else if(h.equals(hands[1])){
	    			dominant = false;
	    		} else {
	    			dominant = true;
	    		}
	    	}
	    });
	    
	}
	
	public static void createWindow(){
		//Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        for (int i = 0; i < PORTMAX; i++){
        	portNames[i] = "COM"+i;
        }
        
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        while(!start){
			System.out.println();
		}
        
	}
	
	
	public static void x_val(int x){
		x_stat.setText(""+x);
	}
	
	public static void y_val(int y){
		y_stat.setText(""+y);
	}
	
	public static void z_val(int z){
		z_stat.setText(""+z);
	}
	public static void toggle_val(String s){
		toggle_stat.setText(s);
	}
	public static void cmd_val(String s){
		cmd_label.setText(s);
	}

	
	
	public static void rightIndexPushed(){
		indexR.setBackground(Color.GREEN);
	}
	public static void rightIndexReleased(){
		indexR.setBackground(Color.RED);
	}
	public static void rightMiddlePushed(){
		middleR.setBackground(Color.GREEN);
	}
	public static void rightMiddleReleased(){
		middleR.setBackground(Color.RED);
	}
	public static void rightRingPushed(){
		ringR.setBackground(Color.GREEN);
	}
	public static void rightRingReleased(){
		ringR.setBackground(Color.RED);
	}
	public static void rightPinkyPushed(){
		pinkyR.setBackground(Color.GREEN);
	}
	public static void rightPinkyReleased(){
		pinkyR.setBackground(Color.RED);
	}
	


	public static void leftIndexPushed(){
		indexL.setBackground(Color.GREEN);
	}
	public static void leftIndexReleased(){
		indexL.setBackground(Color.RED);
	}
	public static void leftMiddlePushed(){
		middleL.setBackground(Color.GREEN);
	}
	public static void leftMiddleReleased(){
		middleL.setBackground(Color.RED);
	}
	public static void leftRingPushed(){
		ringL.setBackground(Color.GREEN);
	}
	public static void leftRingReleased(){
		ringL.setBackground(Color.RED);
	}
	public static void leftPinkyPushed(){
		pinkyL.setBackground(Color.GREEN);
	}
	public static void leftPinkyReleased(){
		pinkyL.setBackground(Color.RED);
	}
	
}
