package spaceinvaders;


import javax.swing.ImageIcon;


//Wall logic and movmement. 
public class Wall extends Sprite implements Attributes {
	 private final int START_Y = 500; 
	 private final int START_X = 10;
	 private int width;
	
	 public Wall() {
		

        ImageIcon ii = new ImageIcon ("Spaceinvaders/Walls.png","wall");
        setImage(ii.getImage());
        setX(START_X);
        setY(START_Y);
  
	 }
	 

	//making the wall move.......fix
    // now done right, using bool flags.
	 //use bool flags to detect the border then set flag to left or right
	public void move() {
		x += dx;
		boolean left;
		boolean right;
		for(int i=1; i<2; i++){
    	if (x >= 600 ) {
			 left (true);
			 right (false);
		 	}else if (x <= 11)
		 		{
		 		right(true);
		 		left(false);
		 		}
		  } 
	}
	private void right(boolean b) {
		x += dx;
		dx = -4;	
	}
	private void left(boolean b) {
		x += dx;
		dx = 4;		
	}
}
	
