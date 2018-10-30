package ardoise_magique;

public class Point {
	
	protected String couleur = "rouge";
	protected String forme = "rond";
	protected int x = 0, y =0; //position du point
	
	public Point() {}
	
	public Point (String couleur, String forme, int x, int y) {
		this.couleur = couleur;
		this.forme = forme;
		this.x = x;
		this.y = y;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public String getForme() {
		return forme;
	}

	public void setForme(String forme) {
		this.forme = forme;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
