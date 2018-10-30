package ardoise_magique;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class Ardoise extends JFrame  {
	
	private JPanel container = new JPanel ();

	//création du menu
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fichier = new JMenu("Fichier");
	private JMenu edition = new JMenu("Edition");
	private JMenu forme = new JMenu("Forme du pointer");
	private JMenu couleur = new JMenu("Couleur du pointer");
	
	private JMenuItem ss1Fichier1 = new JMenuItem("Effacer");
	private JMenuItem ss1Fichier2 = new JMenuItem("Quitter");

	private JMenuItem ss2Edition1_1 = new JMenuItem("Rond");
	private JMenuItem ss2Edition1_2 = new JMenuItem("Carré");
	
	private JMenuItem ss2Edition2_1 = new JMenuItem("Rouge");
	private JMenuItem ss2Edition2_2 = new JMenuItem("Bleu");
	private JMenuItem ss2Edition2_3 = new JMenuItem("Vert");
	
	//Barre d'outils
	private JToolBar toolBar = new JToolBar();
	
	//Boutons de la barre d'outils
	private JButton square = new JButton(new ImageIcon(this.getClass().getResource("square.png"))),
					circle = new JButton(new ImageIcon(this.getClass().getResource("circle.png"))),
					red = new JButton(new ImageIcon(this.getClass().getResource("red.png"))),
					blue = new JButton(new ImageIcon(this.getClass().getResource("blue.png"))),
					green = new JButton(new ImageIcon(this.getClass().getResource("green.png")));
	
	private Panneau pan = new Panneau(menuBar.getHeight(), toolBar.getHeight());	
	
	//construction d'un objet Point
	protected Point point = new Point();
	
	protected Point newPoint = new Point();
	
	//liste de points
	protected List<Point> listPoints = new <Point>ArrayList();
	
	public Ardoise() {
		
		this.setTitle("Ardoise magique");
	    this.setSize(700, 500);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	    container.setLayout(new BorderLayout());
	   	    
	    this.initMenu();	    
	    this.initToolBar();
	  
	    pan.addMouseMotionListener(new motionListener());
	    pan.addMouseListener(new mouseListener());
	    
	    container.add(pan, BorderLayout.CENTER); 
	   
	    //On prévient notre JFrame que notre JPanel sera son content pane
	    this.setContentPane(container);

	    //Et enfin, la rendre visible   
	    this.setVisible(true);
	    
	    //Détecte le redimensionnement de la fenêtre
	    this.addComponentListener(new ComponentAdapter(){
	        public void componentResized(ComponentEvent e){
	            drawPoints();
	        }
	    });
	}
	
	private void initMenu() {
		
		this.forme.add(ss2Edition1_1);
	    this.forme.add(ss2Edition1_2);
	    
	    this.couleur.add(ss2Edition2_1);
	    this.couleur.add(ss2Edition2_2);
	    this.couleur.add(ss2Edition2_3);
	    
	    this.ss1Fichier1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
	    this.fichier.add(ss1Fichier1);
	    
	    this.ss1Fichier2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
	    this.fichier.add(ss1Fichier2);
	    
	    this.edition.add(forme);
	    this.edition.add(couleur);
	    
	    this.menuBar.add(fichier);
	    this.menuBar.add(edition);
	    
	    //sélection de la forme par défaut
	    ss2Edition1_1.setEnabled(false);
	    ss2Edition1_2.setEnabled(true);
	    
	    //sélection de la couleur par défaut
	    ss2Edition2_1.setEnabled(false);
	    ss2Edition2_2.setEnabled(true);
	    ss2Edition2_2.setEnabled(true);
	    
	    //Listener pour effacer l'ardoise
	    ss1Fichier1.addActionListener(new EffacerListener());
	    
	    //Listeners pour modifier la forme
	    ss2Edition1_1.addActionListener(new modifierFormeListener("rond"));
	    ss2Edition1_2.addActionListener(new modifierFormeListener("carre"));
	    
	    //Listeners pour modifier la couleur
	    ss2Edition2_1.addActionListener(new modifierCouleurListener("rouge"));
	    ss2Edition2_2.addActionListener(new modifierCouleurListener("bleu"));
	    ss2Edition2_3.addActionListener(new modifierCouleurListener("vert"));
	    
	    //Listener pour quitter l'application
	    ss1Fichier2.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent event){
	        System.exit(0);
	      }

	    });	    
	}
	
	private void initToolBar() {
		
		//Ajout de la barre de menus sur la fenêtre
	    this.setJMenuBar(menuBar);
	    	    
	    //Initialisation de la toolbar
	    this.toolBar.add(circle);
	    this.toolBar.add(square);
	    this.toolBar.addSeparator();
	    this.toolBar.add(red);
	    this.toolBar.add(blue);
	    this.toolBar.add(green);
	    
	    container.add(toolBar, BorderLayout.NORTH);
	    
	    //sélection de la forme par défaut
	    square.setEnabled(true);
		circle.setEnabled(false);
		
		//sélection de la couleur par défaut
		red.setEnabled(false);
		blue.setEnabled(true);
		green.setEnabled(true);
	    
		//Listeners pour modifier la forme
	    circle.addActionListener(new modifierFormeListener("rond"));
	    square.addActionListener(new modifierFormeListener("carre"));
	    
	    //Listeners pour modifier la couleur
	    red.addActionListener(new modifierCouleurListener("rouge"));
	    blue.addActionListener(new modifierCouleurListener("bleu"));
	    green.addActionListener(new modifierCouleurListener("vert"));		
	}
	
	public void drawPoints () {
		Graphics g=getGraphics();  
		for(Point p : listPoints) {
			pan.draw(g,p, p.getForme(), p.getCouleur());
		}
	}
	
	public class EffacerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			 Graphics g=getGraphics();  
			listPoints.clear();
			container.repaint();			
		}		
	}
	
	public class modifierFormeListener implements ActionListener {
		
		protected String forme;
		
		public modifierFormeListener (String forme) {
			this.forme = forme;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("La forme sélectionnée est "+ forme);
			
			if (forme == "rond") {
				ss2Edition1_1.setEnabled(false);
				ss2Edition1_2.setEnabled(true);
				square.setEnabled(true);
				circle.setEnabled(false);				
			} else {
				ss2Edition1_1.setEnabled(true);
				ss2Edition1_2.setEnabled(false);
				square.setEnabled(false);
				circle.setEnabled(true);
				point.setForme("carre");
			}			
		}	
	}

	public class modifierCouleurListener implements ActionListener {
		
		protected String couleur;
		
		public modifierCouleurListener(String couleur) {
			this.couleur = couleur;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			System.out.println("La couleur selectionnée est "+ couleur);
			
			if (couleur =="rouge") {
				ss2Edition2_1.setEnabled(false);
				ss2Edition2_2.setEnabled(true);
				ss2Edition2_3.setEnabled(true);
				red.setEnabled(false);
				blue.setEnabled(true);
				green.setEnabled(true);
				point.setCouleur("rouge");
			} else if (couleur == "vert") {
				ss2Edition2_1.setEnabled(true);
				ss2Edition2_2.setEnabled(true);
				ss2Edition2_3.setEnabled(false);
				red.setEnabled(true);
				blue.setEnabled(true);
				green.setEnabled(false);
				point.setCouleur("vert");
			} else if (couleur == "bleu") {
				ss2Edition2_1.setEnabled(true);
				ss2Edition2_2.setEnabled(false);
				ss2Edition2_3.setEnabled(true);
				red.setEnabled(true);
				blue.setEnabled(false);
				green.setEnabled(true);
				point.setCouleur("bleu");								
			}
		}
		
	}
	
	public class motionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			
			System.out.println("coordonnée X : "+ e.getX()+" et coordonnée Y "+ e.getY());
			//si la souris est dans la zone de l'ardoise
			 if (contains(e.getX(),e.getY()) && e.getY() > 60) {				 
				 Point newPoint = new Point();					 
				 newPoint.setX(e.getX()-5);
				 newPoint.setY(e.getY()-5);
				 newPoint.setCouleur(point.getCouleur());
				 newPoint.setForme(point.getForme());
				 listPoints.add(newPoint);		 
				 drawPoints();				 	
			 }
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("mouseMoved => X = "+ e.getX()+" Y = "+e.getY());
		}
	}
	
	public class mouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {
			drawPoints();			
		}
	}		
}
	

