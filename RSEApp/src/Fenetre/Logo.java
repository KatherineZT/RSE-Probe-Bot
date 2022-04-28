package Fenetre;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 * Classe Logo
 * Crée le logo de l'application
 * @author Charles-Éric Langlois, Arthur Van Bettsbrugge et Katherine Zamudio-Turcotte
 */
public class Logo extends JPanel {
	int x = (int) Math.round((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3) / 202);
	Image im = Toolkit.getDefaultToolkit().getImage("RSELogo2.png").getScaledInstance(202 * x, 95 * x, Image.SCALE_DEFAULT);
	
	/**
	 * Constructeur du logo
	 */
	public Logo() {
		setSize(202 * x, 95 * x);
	}
	
	/**
	 * Ajoute l'image du logo
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(im, 0, 0, this);
	}
	
	/**¸
	 * Renvoie la position en X du logo
	 * @return Position eb X du logo
	 */
	public int getValX() {
		return x;
	}
}
