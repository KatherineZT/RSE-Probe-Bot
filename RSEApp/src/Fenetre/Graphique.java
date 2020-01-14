package Fenetre;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Crée les graphiques de l'évolution d'une valeur dans le temps
 * @author Charles-Éric Langlois, Arthur Van Bettsbrugge et Katherine Zamudio-Turcotte
 */
public class Graphique extends JPanel {
	Color couleur;
	ArrayList<Integer> lstTemps;
	ArrayList<Integer> lstValeurs;
	int max; 
	int min;
	int gradX;
	int gradY;
	JLabel lblVal = new JLabel();

	/**
	 * Constructeur de graphiques
	 * @param couleur Couleur d'arrière-fond du graphique
	 * @param lstValeurs Liste contenant toutes les valeurs d'une variable
	 * @param lstTemps Liste contenant les délais entre la réception de chaque valeur
	 * @param max Valeur maximale que le capteur peut détecter
	 * @param min Valeur minimale que le capteur peut détecter
	 */
	public Graphique(Color couleur, ArrayList<Integer> lstValeurs, ArrayList<Integer> lstTemps, int max, int min) {
		setLayout(null);
		this.couleur = couleur;
		this.lstTemps = lstTemps;
		this.lstValeurs = lstValeurs;
		this.max = max;
		this.min = min;
		if (max - min > 100) {
			gradY = (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220) / ((max - min) / 50);
		} else {
			gradY = (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220) / (max - min);
		}
		gradX = (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 220) / lstTemps.size();

        add(lblVal);
        lblVal.setLocation(0, 0);
        lblVal.setSize(500, 20);
		
		addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                int a = lstValeurs.get((e.getX()/gradX));
                lblVal.setText("" + a);
                lblVal.setLocation(e.getX() + 15, e.getY());
            }
        });
	}

	/**
	 * Dessine le graphique
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Colore l'arrière-fond
		g.setColor(couleur);
		g.fillRect(0, 0, (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 200),
				(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 200));
		g.setColor(Color.BLACK);
		
		//Dessine l'axe vertical du graphique
		if (min > 0 && max - min > 100) { //Si le minimum est positif et la graduation en Y est grande
			g.drawLine(20, 20, 20, (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220)
					+ (min * gradY) / 50);
		} else if (min > 0 && max - min < 100) { //Si le minimum est positif et la graduation en Y est petite
			g.drawLine(20, 20, 20,
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220) + (min * gradY));
		} else { //Si le minimum est négatif (Température)
			g.drawLine(20, 20, 20, (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220));
		}
		g.drawLine(20, 20, 17, 28);
		g.drawLine(20, 20, 23, 28);
		
		//Dessine l'axe horizontal du graphique
		if (max - min > 100) { //Si la graduation en Y est grande
			g.drawLine(20,
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220)
							+ (min * gradY) / 50,
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 240),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220)
							+ (min * gradY) / 50);
			g.drawLine((int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 240),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220)
							+ (min * gradY) / 50,
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 248),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 223)
							+ (min * gradY) / 50);
			g.drawLine((int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 240),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220)
							+ (min * gradY) / 50,
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 248),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 217)
							+ (min * gradY) / 50);
		} else { //Si la graduation en Y est petite
			g.drawLine(20,
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220) + (min * gradY),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 240),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220) + (min * gradY));
			g.drawLine((int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 240),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220) + (min * gradY),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 248),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 223) + (min * gradY));
			g.drawLine((int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 240),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220) + (min * gradY),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 248),
					(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 217) + (min * gradY));
		}
		
		//Dessine les lignes entre les points du graphique
		for (int i = 1; i < lstValeurs.size(); i++) {
			if (max - min > 100) { //Si la graduation en Y en grande
				g.drawLine(i * gradX + 20,
						(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220)
								+ ((min * gradY) / 50) - ((lstValeurs.get(i) * gradY) / 50),
						(i - 1) * gradX + 20,
						(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220)
								+ ((min * gradY) / 50) - (lstValeurs.get(i - 1) * gradY) / 50);
			} else { //Si la graduation en Y est petite
				g.drawLine(i * gradX + 20,
						(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220) + (min * gradY)
								- lstValeurs.get(i) * gradY,
						(i - 1) * gradX + 20,
						(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 220) + (min * gradY)
								- lstValeurs.get(i - 1) * gradY);
			}
		}
	}
}
