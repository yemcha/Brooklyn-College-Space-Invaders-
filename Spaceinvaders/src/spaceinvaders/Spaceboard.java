package spaceinvaders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Main logic for the game play 
//this is where the classes interact and where the bulk of the game is written

public class Spaceboard extends JPanel implements Runnable, Attributes { 

	    private Dimension d;
	    private ArrayList aliens;   
	    private Player player;
	    private Missile missile;
	    private Wall wall;
	    private int alienX = 150;
	    private int alienY = 5;    
	    private int direction = -1;
	    private int deaths = 0;
	    private boolean ingame = true;	 
	    private int death = 5;
	    private String message = "Game Over";
	    private String life = "Lives left: "+ death;
	    private Thread animator;
	    Image img;
	    //calls on the gaminit as well sets dimensions for the space board
	    public Spaceboard() 
	    {
	        addKeyListener (new TAdapter());
	        setFocusable(true);
	        img = Toolkit.getDefaultToolkit().createImage("Spaceinvaders/space1.png"); 
	        d = new Dimension(BOARD_W, BOARD_H);     	          
	        gameInit();
	        setDoubleBuffered(true);
	    }
	    
	    //the bulk of the game is with in game in it. 
	    public void addNotify() {
	        super.addNotify();
	        gameInit();
	    }
	    
	    //game in it is the class with most of the logic for drawing 
	        public void gameInit() {
	        	//add the alien elements in an array list to draw from it later
	            aliens = new ArrayList();	           
	            int min = 0;
	            int max = 5;
	            //where the number of aliens is set. 
	            //first is the horizontal number of aliens, then the vertical number
	            for (int i=0; i < 4; i++) {
	            	for (int j=0; j < 6; j++) {
	            		//Spacing for the aliens now done right
	            		//using random number generator to get a random photo path as in ("alien" x to y.)
	            		//Since name of photos only differ in that number
	            		
	            		Random rand = new Random ();
	    	            int randomNum = rand.nextInt((max - min) + 1) + min;	    			    	            
	    	            ImageIcon  a= new ImageIcon ("Spaceinvaders/alien"+randomNum+".png","Alienship");	        	            		
	                    Alien alien = new Alien(alienX + 65*j, alienY + 65*i);              
	                    alien.setImage(a.getImage());
	                    aliens.add(alien);           
	                }
	            }
	            
	            //makes new instances of the classes to draw them
	            player = new Player();
	            missile = new Missile();
	            wall = new Wall();
	           
	            
	            //string for the animator and painter to draw the sprites 
	            if (animator == null || !ingame) {
	                animator = new Thread(this);
	                animator.start();
	            }
	        }

	        //where all the sprites get drawn
	        //draw back ground
	      
	        	   
	        // iterator that goes thru the aliens and keeps on drawing aliens 
	        // so long as the aliens array list is not empty
	        public void drawAliens (Graphics g)
	        {
	            Iterator it = aliens.iterator();
	            
	            while (it.hasNext()) {
	                Alien alien = (Alien) it.next();

	                if (alien.isVisible()) {
	                    g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
	                }

	                if (alien.isDestroyed()) {
	                    alien.die();
	                }
	            }
	        }
	        
	        //draw the player
	        public void drawPlayer(Graphics g) {

	            if (player.isVisible()) {
	                g.drawImage(player.getImage(), player.getX(), player.getY(), this);
	            	}
	            
	            if (player.isDestroyed()) {
	                player.die();
	                ingame = false;
	            	}
	        }
	       
	        //draw the missile or the paper plane
	        public void drawMissile(Graphics g) {
	            if (missile.isVisible())
	                g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
	        }
	        
	        //draw the desk or wall
	        public void drawWall(Graphics g) {	
	            if (wall.isVisible())
	                g.drawImage(wall.getImage(), wall.getX(), wall.getY(), this);
	            }
		
	        // draw the bomb and match it with the aliens
	        
	        public void drawBombing(Graphics g) {

	            Iterator i3 = aliens.iterator();

	            while (i3.hasNext()) {
	                Alien a = (Alien) i3.next();
	                Alien.Bomb b = a.getBomb();

	                if (!b.isDestroyed()) 
	                {
	                    g.drawImage(b.getImage(), b.getX(), b.getY(), this); 
	                }
	            }
	        }	        
	       	        
	      //where things get painted
	      //the actual method for the painting
	        public void paint(Graphics g)
	        {
	        	
	 	       // Loads the background image and stores in img object.                	            
	           super.paint(g);	           
	           g.setColor(Color.green);   
	           g.drawImage(img, 0, 0, BOARD_W, BOARD_H, this); 
	           g.setColor(Color.ORANGE);
	           if (ingame) {
	        	
	            g.drawLine(0, FLOOR, BOARD_W, FLOOR);		           
	            drawAliens(g);
	            drawPlayer(g);
	            drawMissile(g);
	            drawBombing(g);
	            drawWall(g);	            
	            //font style and size   
	            Font small = new Font("COMIC_SANS", Font.BOLD, 14);
 	            FontMetrics metr = this.getFontMetrics(small);    	           
 	            g.setColor(Color.white);
 	            g.setFont(small);
 	            //g.drawString(life, MessageW/2, MessageH/2);
                 
	          }
	          Toolkit.getDefaultToolkit().sync();
	          g.dispose();
	        }
	        
	        
	        
	        //logic for ending the game, called after a specfic amount of deaths. 
	        public void gameOver()
	        {

	            Graphics g = this.getGraphics();

	            g.setColor(Color.black);
	            g.fillRect(0, 0, BOARD_W, BOARD_H);

	            g.setColor(new Color(0, 32, 48));
	            g.fillRect(50, BOARD_W/2 - 30, BOARD_W-100, 50);
	            g.setColor(Color.white);
	            g.drawRect(50, BOARD_W/2 - 30, BOARD_W-100, 50);

	            Font small = new Font("COMIC_SANS", Font.BOLD, 14);
	            FontMetrics metr = this.getFontMetrics(small);
	           
	            g.setColor(Color.white);
	            g.setFont(small);
	            g.drawString(message, (BOARD_W - metr.stringWidth(message))/2, 
	                BOARD_W/2);
	        }
	        
	     
	        
	        public void animationCycle()  {

	            if (deaths == ALIENS_LEFT) {
	                ingame = false;
	                message = "You've saved us all!";
	            }

	            // player
	            // method for the wall to move called
	            wall.move();       
	            player.act();
	   
	            	
	            
	            // ADD WIDER DETECTION (kinda done)
	            // Missile
	            if (missile.isVisible()) {
	                Iterator it = aliens.iterator();
	                int MissileX = missile.getX() + 5 ;
	                int MissileY = missile.getY(); 

	                while (it.hasNext()) {
	                    Alien alien = (Alien) it.next();
	                    int alienX = alien.getX();
	                    int alienY = alien.getY() + 5;
	                    
	                    //if the differnce between the x of the missile and alien is in the specfied range, (same with y)
	                    //instead of bieng the exact x and y
	                    
	                    if (alien.isVisible() && missile.isVisible()) {
	                        if (MissileX - (alienX) >= 20 && 
	                            MissileX - (alienX + ALIEN_W) <= 20 &&
	                            MissileY - (alienY) >= 10 &&
	                            MissileY - (alienY+ALIEN_H) <= 10) {
	                        	//add up how many aliens died in deaths++ then kill missile and alien
	                                deaths++;
	                                missile.die();
	                                alien.die();
	                         }    
	                    }
	                }
	                
	                //track the missile and give it the speed via the y. After the it goes past y = 0 in coordinates it dies.
	                int y = missile.getY();
	                y -= 7;
	                if (y < 0)
	                    missile.die();
	                else missile.setY(y);
	            }

	            // aliens
	            
	            
	            //while theres aliens in the aliens array keep on going in the directions
	             Iterator it1 = aliens.iterator();

	             while (it1.hasNext()) {
	                 Alien a1 = (Alien) it1.next();
	                 int x = a1.getX();
	                //if it hits the border change the direction then go down
	                 if (x  >= BOARD_W - BORDER_R && direction != -1) {
	                     direction = -1;
	                     Iterator i1 = aliens.iterator();
	                     while (i1.hasNext()) {
	                         Alien a2 = (Alien) i1.next();
	                         a2.setY(a2.getY() + MOVE_DOWN);
	                     }
	                 }
	                // same but the left border
	                if (x <= BORDER_L && direction != 1) {
	                    direction = 1;

	                    Iterator i2 = aliens.iterator();
	                    while (i2.hasNext()) {
	                        Alien a = (Alien)i2.next();
	                        a.setY(a.getY() + MOVE_DOWN);
	                    }
	                }
	            }


	            Iterator it = aliens.iterator();
	            //if the alien itarator is not empty and goes past the the "floor" game over.
	            while (it.hasNext()) {
	                Alien alien = (Alien) it.next();
	                if (alien.isVisible()) {

	                    int y = alien.getY();

	                    if (y > FLOOR - ALIEN_H) {
	                        ingame = false;
	                        message = "Invasion!";	                        
	                    }
	                    alien.act(direction);          	                    	
	                }
	            }
    
	            // bombs
	            //alien bombing and how many lives it takes from the player. 
	            Iterator i3 = aliens.iterator();
	            Random generator = new Random();

	            while (i3.hasNext()) {
	                int Missile = generator.nextInt(15);
	                Alien a = (Alien) i3.next();
	                Alien.Bomb b = a.getBomb();
	                if (Missile == LIVES && a.isVisible() && b.isDestroyed()) {

	                    b.setDestroyed(false);
	                    b.setX(a.getX());
	                    b.setY(a.getY());   
	                    
	                }
	                //ADD WALL DETECTION
	                //Wall detection needs an accurate X detection
	                //add animation when player is shot
	                int bombX = b.getX();
	                int bombY = b.getY();
	                int playerX = player.getX() +5;
	                int playerY = player.getY();
	                int wallX = wall.getX() + 10 ;  
	                int wallY = wall.getY() + 30;
	                int missileX = missile.getX();
	                int missileY = missile.getY();	              
	                int lives = 0;
	                
	                //if the sprite is over lapping another sprite within the bounds of the specfied is the algorithim for collision
	                if (player.isVisible() && b.isVisible()) {
	                    if (bombX - (playerX) >= 5 && 
	                        bombX - (playerX+PLAYER_W) <= 5 &&
	                        bombY - (playerY) >= 5 &&          
	                        bombY - (playerY+PLAYER_H) <= 5 ) {	                    		                    
	                            // display how many times you were hit
	                            // need life string to read this and display it, for some reason this does not get read   	                            
	                            b.setDestroyed(true);
	                            death --;
	                            lives ++;	   
	                            
	                          //  String lifes = "Lives left = " + death;
	                          //  Graphics g = this.getGraphics();
	                          //  Font small = new Font("COMIC_SANS", Font.BOLD, 14);
	             	          //  FontMetrics metr = this.getFontMetrics(small);    	           
	             	          //  g.setColor(Color.white);
	             	          //  g.setFont(small);
	             	          //  g.drawString(lifes, MessageW/2, MessageH/2);	                           
	                          	                          
	                            if (lives == 5) {	                            	
	                            player.setDestroyed(true);
	                            b.setDestroyed(true);
	                            }	                           
	                            // GAME NOW ENDS WHEN YOU DIE ALOT NOT JUST ONCE.
	                            if (player.isDestroyed()); {
	    	                    	ingame = false;
	    	                    	message = "GAME OVER!!";
	    	                    }
	    	                    
	                        }
	                }
	          //wall detection collision, the player shot first. then the bomb collision second. 
	          //needs some way to fix the image x and y
	                if (wall.isVisible() && missile.isVisible()) {
	                	if (wallX - (missileX) >= -40 &&          
	                	    wallX - (missileX+WallW) <= 50 &&
	                        wallY - (missileY) >= 5 &&          
	                        wallY - (missileY+WallH) <= 5){
	                		missile.setDestroyed(true);
	                		missile.die();
	                	}
	                 }
	                if (wall.isVisible() && b.isVisible()) {
	                	if (wallX - (bombX) >= -40 &&          
		                	    wallX - (bombX+WallW) <= -50 &&
		                        wallY - (bombY) >= 5 &&          
		                        wallY - (bombY+WallH) <= -5){
	                		b.setDestroyed(true);
	                		b.die();		
	                  }
	                	
	               }
	                
	                //to detect bomb is gone after it's past the floor
	                if (!b.isDestroyed()) {
	                    b.setY(b.getY() + 1);   
	                    if (b.getY() >= FLOOR - BOMB_SIZE) {
	                        b.setDestroyed(true);
	                    }
	                }
	            }
	        }

	        //thread handling and interrupts. 
	        
	       public void run() {
	            long beforeTime, timeDiff, sleep;
	            beforeTime = System.currentTimeMillis();
	            while (ingame) {
	                repaint();
	                animationCycle();

	                timeDiff = System.currentTimeMillis() - beforeTime;
	                sleep = DELAY - timeDiff;

	                if (sleep < 0) 
	                    sleep = 2;
	                try {
	                    Thread.sleep(sleep);
	                } catch (InterruptedException e) {
	                    System.out.println("interrupted");
	                }
	                beforeTime = System.currentTimeMillis();
	            }
	            gameOver();
	         }

	  class TAdapter extends KeyAdapter {

      public void keyReleased(KeyEvent e) {
          player.keyReleased(e);
      }

      public void keyPressed(KeyEvent e) {

        player.keyPressed(e);
        //canon is placed right now
        //modified shooting spot to come from the right hand. 
        int x = player.getX() + 22;
        int y = player.getY() + 22;
   
        if (ingame)
        {
            int key = e.getKeyCode();
 	        if (key == KeyEvent.VK_SPACE)
 	        {   	  
 	        //if there's no visible missiles then you can launch a new one. 
              if (!missile.isVisible())
                  missile = new Missile(x, y);
          } 
        }
      }     
   }
}


