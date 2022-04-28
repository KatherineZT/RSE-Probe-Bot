package Fenetre;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Classe Cadran
 * Cr�e les cinq cadrans principaux de l'interface
 * @author Charles-�ric Langlois, Arthur Van Bettsbrugge et Katherine Zamudio-Turcotte
 */
public class Cadran extends JPanel {
	int compteur = 0;
	String valeur;
	int angle;
	Color couleur;
	int minReel = 0;
	int maxReel = 0;
	int moy = 0;
	ArrayList<Integer> lstValeurs;
	ArrayList<Integer> lstTemps;
	int minCap;
	int maxCap;
	int xTitre;
	int valJaune;
	int valRouge;
	JLabel lblTitreVal = new JLabel();
	JLabel lblVal = new JLabel();
	JLabel lblDetails = new JLabel("D�tails");
	JLabel lblTitreMax = new JLabel("Max.");
	JLabel lblMax = new JLabel();
	JLabel lblTitreMin = new JLabel("Min.");
	JLabel lblMin = new JLabel();
	JLabel lblTitreMoy = new JLabel("Moy.");
	JLabel lblMoy = new JLabel();

	/**�
	 * Constructeur de cadrans
	 * @param titre Titre du cadran
	 * @param valeur Valeur (en direct) de la variable
	 * @param XTitre Longueur du titre du cadran en X
	 * @param minCap Valeur minimale que le capteur peut d�tecter
	 * @param maxCap Valeur maximale que le capteur peut d�tecter
	 * @param valJaune Valeur � partir de laquelle le cadran devient de couleur jaune
	 * @param valRouge Valeur � partir de laquelle le cadran devient de couleur rouge
	 * @param lstValeurs Liste contenant toutes les valeurs d'une variable
	 * @param lstTemps Liste contenant les d�lais entre la r�ception de chaque valeur
	 */
	public Cadran(String titre, int valeur, int XTitre, int minCap, int maxCap, int valJaune, int valRouge,
			ArrayList<Integer> lstValeurs, ArrayList<Integer> lstTemps) {
		setLayout(null);
		setSize((int) Math.round((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 166) / 3), (int) Math.round((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 168) / 2));
		this.lstValeurs = lstValeurs;
		this.lstTemps = lstTemps;
		this.minCap = minCap;
		this.maxCap = maxCap;
		this.xTitre = XTitre;
		this.valJaune = valJaune;
		this.valRouge = valRouge;

		calcValeur(valeur, titre);
		calcMaxMinMoy(lstValeurs, valeur);
		calcAngle(valeur, minCap, maxCap);
		addLbls(XTitre, lstValeurs, lstTemps, titre);
	}

	/**
	 * Ajoute les 4 cadrans : le cadran g�n�ral, maximal, minimal et moyen
	 * @param xTitre Longueur du titre du cadran en X
	 * @param lstValeurs Liste contenant toutes les valeurs d'une variable
	 * @param lstTemps Liste contenant les d�lais entre la r�ception de chaque valeur
	 * @param titre Titre du cadran
	 */
	private void addLbls(int xTitre, ArrayList<Integer> lstValeurs, ArrayList<Integer> lstTemps, String titre) {
		//Ajoute le titre du cadran
		add(lblTitreVal);
		lblTitreVal.setSize(xTitre, 16);
		lblTitreVal.setLocation((int) Math.round((this.getHeight() - xTitre) / 2), (int) Math.round(this.getHeight() / 3 + 20));

		//Ajoute la valeur en direct du cadran
		add(lblVal);
		lblVal.setSize(this.valeur.length() * 40, 60);
		lblVal.setFont(new Font("Palatino", Font.CENTER_BASELINE, 70));
		lblVal.setLocation((int) Math.round((this.getHeight() - this.valeur.length() * 40) / 2), (int) Math.round(lblTitreVal.getLocation().getY() + 25));

		//Ajoute l'option "D�tails"
		add(lblDetails);
		lblDetails.setSize(39, 16);
		lblDetails.setLocation((int) Math.round((this.getHeight() - 39) / 2), Math.round(this.getHeight() - 100));
		lblDetails.addMouseListener(new MouseAdapter() {
			@Override
			//Ajoute l'�v�nement (cr�ation du graphique)
			public void mouseClicked(MouseEvent e) {
				if (compteur == 0) {
					compteur++;
					JFrame vue = new JFrame();
					vue.setSize((int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 194),
							(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 171));
					vue.setResizable(false);
					vue.add(new Graphique(couleur, lstValeurs, lstTemps, maxCap, minCap));
					vue.setVisible(true);
					vue.setTitle(titre);
					vue.setLocationRelativeTo(null);
					vue.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							compteur--;
						}
					});
				}
			}
		});

		//Ajoute le titre "Max." du premier cadran secondaire
		add(lblTitreMax);
		lblTitreMax.setSize(27, 16);
		lblTitreMax.setLocation(((int) Math.round(this.getHeight() + 14 + (this.getWidth() - this.getHeight() - 30 - lblTitreMax.getWidth()) / 2)), 55);
		add(lblMax);
		lblMax.setSize(lblMax.getText().length() * 17, 60);
		lblMax.setFont(new Font("Palatino", Font.CENTER_BASELINE, 30));
		lblMax.setLocation(this.getHeight() + 14 + ((86 - lblMax.getText().length() * 16) / 2), 65);

		//Ajoute le titre "Min." du deuxi�me cadran secondaire
		add(lblTitreMin);
		lblTitreMin.setSize(23, 16);
		lblTitreMin.setLocation(((int) Math.round(this.getHeight() + 14 + (this.getWidth() - this.getHeight() - 30 - lblTitreMax.getWidth()) / 2)), (int) Math.round((this.getHeight() - 42) / 3 + 38));
		add(lblMin);
		lblMin.setSize(lblMin.getText().length() * 17, 60);
		lblMin.setFont(new Font("Palatino", Font.CENTER_BASELINE, 30));
		lblMin.setLocation(this.getHeight() + 14 + ((86 - lblMin.getText().length() * 16) / 2), (int) Math.round((this.getHeight() - 42) / 3 + 48));

		//Ajoute le titre "Moy." du troisi�me cadran secondaire
		add(lblTitreMoy);
		lblTitreMoy.setSize(26, 16);
		lblTitreMoy.setLocation(((int) Math.round(this.getHeight() + 14 + (this.getWidth() - this.getHeight() - 30 - lblTitreMax.getWidth()) / 2)), (int) Math.round(2 * ((this.getHeight() - 42) / 3) + 28));
		add(lblMoy);
		lblMoy.setSize(lblMoy.getText().length() * 17, 60);
		lblMoy.setFont(new Font("Palatino", Font.CENTER_BASELINE, 30));
		lblMoy.setLocation(this.getHeight() + 14 + ((86 - lblMoy.getText().length() * 16) / 2), (int) Math.round((2 * (this.getHeight() - 42) / 3) + 38));
	}

	/**
	 * Calcule l'angle de l'arc � dessiner sur le cadran
	 * @param valeur Valeur (en direct) de la variable
	 * @param minCap Valeur minimale que le capteur peut d�tecter
	 * @param maxCap Valeur maximale que le capteur peut d�tecter
	 */
	private void calcAngle(int valeur, int minCap, int maxCap) {
		angle = -(228 * (valeur - minCap)) / (maxCap - minCap);
		if (angle > 0) {
			angle = 0;
		}

		//Ajuste la couleur du cadran
		if (valRouge == 4000) { //Pour le cadran de la lumi�re ambiante
			if (valeur >= valRouge) {
				this.couleur = Color.YELLOW;
			} else if (valeur <= valJaune) {
				this.couleur = Color.YELLOW;
			} else {
				this.couleur = Color.GREEN;
			}
		} else { //Pour les autres cadrans
			if (valeur >= valRouge) {
				this.couleur = Color.RED;
			} else if (valeur <= valJaune) {
				this.couleur = Color.GREEN;
			} else {
				this.couleur = Color.YELLOW;
			}
		}
	}

	/**
	 * Calcule le maximum, le minimum et la moyenne de la variable
	 * @param lstValeurs Liste contenant toutes les valeurs d'une variable
	 * @param valeur Valeur (en direct) de la variable
	 */
	private void calcMaxMinMoy(ArrayList<Integer> lstValeurs, int valeur) {
		moy = 0;
		for (int a = 0; a < lstValeurs.size(); a++) {
			
			//Calcul du maximum
			if (lstValeurs.get(a) > maxReel || maxReel == 0) {
				maxReel = lstValeurs.get(a);
			}
			
			//Calcul du minimum
			if (lstValeurs.get(a) < minReel || minReel == 0) {
				minReel = lstValeurs.get(a);
			}
			
			//Calcul interm�diaire de la moyenne
			moy += lstValeurs.get(a);
		}

		//Calcul final de la moyenne
		if (lstValeurs.size() != 0) {
			moy = moy / lstValeurs.size();
		} else {
			moy = 0;
		}
		
		//Ajuste les trois valeurs des cadrans secondaires
		lblMax.setText(Integer.toString(maxReel));
		lblMin.setText(Integer.toString(minReel));
		lblMoy.setText(Integer.toString(moy));

	}

	/**
	 * Transforme la valeur en String et
	 * Nomme le cadran
	 * @param valeur Valeur (en direct) de la variable
	 * @param titre Titre du cadran
	 */
	private void calcValeur(int valeur, String titre) {
		this.valeur = Integer.toString(valeur);
		lblVal.setText(this.valeur);
		lblTitreVal.setText(titre);
	}

	/**
	 * Dessine le cadran
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.fillOval(0, 0, this.getHeight(), this.getHeight()); //Cadran principal (cercle ext�rieur)
		
		//Cadrans secondaires (cercles ext�rieurs)
		g.fillOval(this.getHeight() + 14, 40, (this.getHeight() - 60) / 4, (this.getHeight() - 60) / 4);
		g.fillOval(this.getHeight() + 14, (int) Math.round((this.getHeight() - 60) / 4 + 60), (this.getHeight() - 60) / 4, (this.getHeight() - 60) / 4);
		g.fillOval(this.getHeight() + 14, (int) Math.round(2 * ((this.getHeight() - 42) / 4) + 80), (this.getHeight() - 60) / 4, (this.getHeight() - 60) / 4);
		
		//Lignes dans le cadran principal
		g.setColor(new Color(240, 240, 240));
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, 0, this.getHeight() / 2);
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, 0, 0);
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, this.getHeight() / 2, 0);
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, this.getHeight(), 0);
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, this.getHeight(), this.getHeight() / 2);
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, 0, (int) Math.round(this.getHeight() / 2 * Math.tan(22.5)));
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, this.getHeight(), (int) Math.round(this.getHeight() / 2 * Math.tan(22.5)));
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, (int) Math.round(this.getHeight() / 2 * Math.tan(22.5)), 0);
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, (int) Math.round(this.getHeight() - (this.getHeight() / 2 * Math.tan(22.5))), 0);
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, 0, (int) Math.round(this.getHeight() - (this.getHeight() / 2 * Math.tan(22.5))));
		g.drawLine(this.getHeight() / 2, this.getHeight() / 2, this.getHeight(), (int) Math.round(this.getHeight() - (this.getHeight() / 2 * Math.tan(22.5))));
		
		//Cadrans secondaires (cercles int�rieurs)
		g.fillOval(this.getHeight() + 19, 45, (this.getHeight() - 60) / 4 - 10, (this.getHeight() - 60) / 4 - 10);
		g.fillOval(this.getHeight() + 19, (int) Math.round((this.getHeight() - 60) / 4 + 65), (this.getHeight() - 60) / 4 - 10, (this.getHeight() - 60) / 4 - 10);
		g.fillOval(this.getHeight() + 19, (int) Math.round(2 * ((this.getHeight() - 60) / 4) + 93), (this.getHeight() - 60) / 4 - 10, (this.getHeight() - 60) / 4 - 10);

		//Arc de valeur
		g.setColor(couleur);
		g.fillArc(0, 0, this.getHeight(), this.getHeight(), 204, this.angle);
		
		//Cadran principal (cercle int�rieur)
		g.setColor(new Color(240, 240, 240));
		g.fillOval(50, 50, this.getHeight() - 100, this.getHeight() - 100);
	}

	/**
	 * Modifie la valeur 
	 * @param val Valeur (en direct) de la variable
	 */
	public void setValeur(int val) {
		calcValeur(val, lblTitreVal.getText());
		calcMaxMinMoy(lstValeurs, val);
		calcAngle(val, minCap, maxCap);
		addLbls(xTitre, lstValeurs, lstTemps, lblTitreVal.getText());
	}
}
