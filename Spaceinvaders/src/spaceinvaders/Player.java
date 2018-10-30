package spaceinvaders;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

//Class for player and how the player moves when the keys are pressed
	public class Player extends Sprite implements Attributes{

	    private final int START_Y = 580; 
	    private final int START_X = 350;
	    private int width;
	   
	    public Player() {
	    	//player finally shows themselves
	        ImageIcon ii = new ImageIcon ("Spaceinvaders/spaceship.png","player");
	        
	        width = ii.getImage().getWidth(null); 
	        
	        setImage(ii.getImage());
	        setX(START_X);
	        setY(START_Y);
	    }

	    
	    //act on the board moves it left and right
	    public void act() {
	        x += dx;
	        if (x <= 1) 
	            x = 1;
	        if (x >= BOARD_W - 1*width) 
	            x = BOARD_W - 1*width;
	   
	    }
	    //actually moving using the the left or right keys via a rate of |3| x
	    public void keyPressed(KeyEvent e) {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT)
	        {
	            dx = -3;
	        }

	        if (key == KeyEvent.VK_RIGHT)
	        {
	            dx = 3;
	        }
	    }

	    public void keyReleased(KeyEvent e) {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT)
	        {
	            dx = 0;
	        }

	        if (key == KeyEvent.VK_RIGHT)
	        {
	            dx = 0;
	        }
	    }
	}


	

