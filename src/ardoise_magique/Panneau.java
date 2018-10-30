package ardoise_magique;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class Panneau extends JPanel {
	
	private int h;
	
	public Panneau() {
		
	}
	
	public Panneau(int heightMenuBar, int heightToolBar) {
		this.h = heightMenuBar + heightToolBar;
		//this.setSize(700,500-h);
	}
		
	public void paintComponent(Graphics g){
		g.setColor(Color.white);
	    g.fillRect(0,h, this.getWidth(), this.getHeight());
	    
	}
	
	public void draw(Graphics g, Point p, String forme, String color) {
		if(color == "rouge") {
			g.setColor(Color.RED);
		}else if (color =="bleu") {
			g.setColor(Color.BLUE);
		} else if (color == "vert") {
			g.setColor(Color.GREEN);
		}
		
		
		if(forme == "rond") { 
		g.fillOval(p.getX(),p.getY(),10,10); 
		} else {
			g.fillRect(p.getX(), p.getY(), 10, 10);
		}
	       
	}
	
	
	
}
