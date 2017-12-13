import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Tinker {
	public static int ctrl = KeyEvent.VK_CONTROL;
	public static int shift = KeyEvent.VK_SHIFT;
	public static int alt = KeyEvent.VK_ALT;
	public static int a = KeyEvent.VK_A;
	public static int c  = KeyEvent.VK_C;
	public static int d = KeyEvent.VK_D;
	public static int f = KeyEvent.VK_F;
	public static int g = KeyEvent.VK_G;
	public static int l = KeyEvent.VK_L;
	public static int r = KeyEvent.VK_R;
	public static int v = KeyEvent.VK_V;
	public static int w = KeyEvent.VK_W;
	public static int z = KeyEvent.VK_Z;
	public static int backspace = KeyEvent.VK_BACK_SPACE;
	public static int UP = KeyEvent.VK_UP;
	public static int DOWN = KeyEvent.VK_DOWN;
	public static int LEFT = KeyEvent.VK_LEFT;
	public static int RIGHT = KeyEvent.VK_RIGHT;	

	public static int copy(Robot rbt){
		// ctrl + c
		press(rbt, ctrl); 	press(rbt, c);
		release(rbt, ctrl);	release(rbt, c);
		return 0; // SUCCESS
	}
	public static int paste(Robot rbt){ 
		// ctrl + v
		press(rbt, ctrl);	press(rbt, v);
		release(rbt, ctrl);	press(rbt, ctrl);
		return 0; // SUCCESS
	}
	public static int undo(Robot rbt){ 
		// ctrl + z
		press(rbt, ctrl);	press(rbt, z);
		release(rbt, ctrl);	release(rbt, z);
		return 0; // SUCCESS
	}
	public static int redo(Robot rbt){ 
		// ctrl + shift + z
		press(rbt, ctrl); 	press(rbt, shift);		press(rbt, z);
		release(rbt, ctrl);	release(rbt, shift);	release(rbt, z);
		return 0; // SUCCESS
	}
	public static int group(Robot rbt){
		// ctrl + g
		press(rbt, ctrl);	press(rbt, g);
		release(rbt, ctrl); release(rbt, g);
		return 0; // SUCCESS
	}
	public static int ungroup(Robot rbt){
		// ctrl + shift + g
		press(rbt, ctrl);	press(rbt, shift);		press(rbt, g);
		release(rbt, ctrl); release(rbt, shift);	release(rbt, g);
		return 0; // SUCCESS
	}
	public static int duplicate(Robot rbt){
		// ctrl + d
		press(rbt, ctrl);	press(rbt, d);
		release(rbt, ctrl);	release(rbt, d);
		return 0; // SUCCESS
	}
	public static int lock(Robot rbt){
		// ctrl + l
		press(rbt, ctrl);	press(rbt, l);
		release(rbt, ctrl);	release(rbt, l);
		return 0; // SUCCESS
	}
	public static int selectAll(Robot rbt){
		// ctrl + a
		press(rbt, ctrl);	press(rbt, a);
		release(rbt, ctrl);	release(rbt, a);
		return 0; // SUCCESS
	}
	public static int delete(Robot rbt){
		// backpace
		press(rbt, backspace);
		release(rbt, backspace);
		return 0; // SUCCESS
	}
	public static int workplane(Robot rbt){
		// w
		press(rbt, w);
		release(rbt, w);
		return 0; // SUCCESS
	}
	public static int ruler(Robot rbt){
		// r 
		press(rbt, r);
		release(rbt, r);
		return 0; // SUCCESS
	}
	
	public static int fitView(Robot rbt){
		// f
		press(rbt, f);
		release(rbt, f);
		return 0;
	}
	
	public static int Drop(Robot rbt){
		// d
		press(rbt, d);
		release(rbt, d);
		return 0;
	}
	
	private static int press(Robot rbt, int key){
		rbt.keyPress(key);
		return 0; // SUCCESS
	}
	private static int release(Robot rbt, int key){
		rbt.keyRelease(key);
		return 0; // SUCCESS
	}
}
