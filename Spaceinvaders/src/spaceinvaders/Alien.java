package spaceinvaders;

import javax.swing.ImageIcon;
//aliens class and where it makes a new bomb at the alien location
// this also has the the movmemnt for the bomb

public class Alien extends Sprite {

    private Bomb bomb;
    public Alien(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);    
    }


	public void act(int direction) {
        this.x += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public class Bomb extends Sprite {

        private boolean destroyed;

        public Bomb(int x, int y) {
            setDestroyed(true);
            this.x = x;
            this.y = y;
      
            ImageIcon ii = new ImageIcon("Spaceinvaders/bomb.png","bomb");
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
            return destroyed;
        }
    }
}