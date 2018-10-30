
package spaceinvaders;



//main class calls on the space invaders to be implemented. 
//Where the main menu function should go

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;




public class spaceinvaders extends JFrame implements Attributes {
	public static boolean flag = false; 
	public static JFrame frame;
    public static JFrame frame3;

    
	public spaceinvaders () { 
		//Create and set up the window.
        frame = new JFrame(); 
       // JLabel l1;

        JLabel panel=new JLabel(new ImageIcon("Spaceinvaders/menu.png"));
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(BOARD_W,BOARD_H);
        
        //display window
		frame.setResizable(false);
        panel.setLayout(null);
        
        //declare new button then add it to the panel with the size and dimensions of the button. 
        JButton button = new JButton("Play Game");      
  
        panel.add(button);
        
        Insets insets = panel.getInsets();
        Dimension size = button.getPreferredSize();
        button.setBounds(280 + insets.left, 340 + insets.top,
                size.width+25, size.height+20);
 
                  
        //mouse event for mouse when its on the button
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { 
            	Insets insets = panel.getInsets();
                Dimension size = button.getPreferredSize();
                button.setBounds(280 + insets.left, 340 + insets.top,
                        size.width+60, size.height+48);    
                button.setBackground(Color.BLACK);
                button.setOpaque(true); 
                button.setBorderPainted(true);
            }
            
        });
       //if the mouse leave that area
      button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Insets insets = panel.getInsets();
                Dimension size = button.getPreferredSize();
                button.setBounds (280 + insets.left, 340 + insets.top,
                        size.width+25, size.height+20);
                button.setBackground(UIManager.getColor("control"));
                button.setBorderPainted(true);              
            }
        }); 
     
        frame.add(panel);       
        frame.setVisible(true);
  	   button.addActionListener(new Action());    	   
	}
			
    public static void main(String[] args) {
        	new spaceinvaders(); 
        	       
    }
    //if triggered calls space board in a new frame and deletes the old one. 
     public static class Action implements ActionListener { 
    		public void actionPerformed (ActionEvent e) { 
    			JFrame frame2 = new JFrame();       		
    			frame2.add(new Spaceboard());
    			frame2.setTitle("Space Invaders");
    			frame2.setDefaultCloseOperation(frame2.EXIT_ON_CLOSE);
    			frame2.setSize(BOARD_W, BOARD_H);
    			frame2.setLocationRelativeTo(null);
    			frame2.setVisible(true);
    			frame2.setResizable(false);
    			
    			frame.dispose();   			
    		}
      }
}





