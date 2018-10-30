package spaceinvaders;

import javax.swing.ImageIcon;

//canon class, keeps track of its location. 
	public class Missile extends Sprite {

	    private final int H_SPACE = 6;
	    private final int V_SPACE = 1;

	    public Missile() {
	    }

	    public Missile(int x, int y) {

	        ImageIcon ii = new ImageIcon ("Spaceinvaders/shot.png","shot");
	        setImage(ii.getImage());
	        setX(x + H_SPACE);
	        setY(y - V_SPACE);
	    }
	}

