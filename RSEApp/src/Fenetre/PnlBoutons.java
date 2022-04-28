package Fenetre;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Modele.HC06;

/**
 * Classe PnlBoutons
 * Crée le JPanel sur lequel se trouve les boutons au bas de l'interface
 * @author Charles-Éric Langlois, Arthur Van Bettsbrugge et Katherine Zamudio-Turcotte
 */
public class PnlBoutons extends JPanel{
	Btn btnLaunch;
	Btn btnCall;
	Btn btnDelay;
	JLabel lblTitreLaunch;
	JLabel lblTitreCall;
	JLabel lblTitreDelay;
	
	/**
	 * Constructeur de PnlBoutons
	 * @param hc06 Modèle
	 */
	public PnlBoutons(HC06 hc06) {
		setLayout(null);
		setSize(404,190);
		btnLaunch = new Btn(Btn.Type.LAUNCH, hc06);
		btnCall = new Btn(Btn.Type.CALL, hc06);
		btnDelay = new Btn(Btn.Type.DELAY, hc06);
		lblTitreLaunch = new JLabel("PARTIR");
		lblTitreLaunch.setSize(new Dimension (70,30));
		lblTitreCall = new JLabel("REVENIR");
		lblTitreCall.setSize(new Dimension (70,30));
		lblTitreDelay = new JLabel("DÉLAI");
		lblTitreDelay.setSize(new Dimension (70,30));
		
		//Ajoute le bouton "Lancer"
		add(btnLaunch);
		btnLaunch.setLocation(50,7);

		add(lblTitreLaunch);
		lblTitreLaunch.setFont(new Font("Palatino", Font.CENTER_BASELINE, 12));
		lblTitreLaunch.setVisible(true);
		lblTitreLaunch.setLocation(64,72);
		
		//Ajoute le bouton "Revenir"
		add(btnCall);
		btnCall.setLocation(166,7);

		add(lblTitreCall);
		lblTitreCall.setFont(new Font("Palatino", Font.CENTER_BASELINE, 12));
		lblTitreCall.setVisible(true);
		lblTitreCall.setLocation(177,72);
		
		//Ajoute le bouton "Délai"
		add(btnDelay);
		btnDelay.setLocation(282,7);
		
		add(lblTitreDelay);
		lblTitreDelay.setFont(new Font("Palatino", Font.CENTER_BASELINE, 12));
		lblTitreDelay.setVisible(true);
		lblTitreDelay.setLocation(301,72);
	}

}