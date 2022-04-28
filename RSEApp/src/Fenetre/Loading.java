package Fenetre;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * Classe Loading
 * Gère les délais demandés par l'utilisateur
 * @author Charles-Éric Langlois, Arthur Van Bettsbrugge et Katherine Zamudio-Turcotte
 */
public class Loading extends JFrame {
	
	JLabel lblLoading;
	
	Loading() {
        setSize(180, 100);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        
        lblLoading = new JLabel("Loading...");
        lblLoading.setFont(new Font("Palatino", Font.CENTER_BASELINE, 15));
        lblLoading.setLocation(56, 13);
        lblLoading.setSize(300, 30);
        add(lblLoading);
        lblLoading.setVisible(true);
        
        setVisible(true);
    }
	
	/**
	 * Crée un message annonçant le délai et 
	 * appelle la méthode qui crée le délai
	 */
	public void attendreLong() {
		lblLoading = new JLabel("En attente de la réouverture de\nla connection bluetooth...");
		setLocationRelativeTo(null);
		delai(10000);
	}
	
	/**
	 * Appelle la méthode qui crée le délai
	 */
	public void attendreCourt() {
		delai(1000);
	}
	
	/**
	 * Crée un de délai dans le Thread dans lequel évolue l'application
	 * @param nbMillisec Temps du délai en millisecondes
	 */
	public void delai(int nbMillisec) {
		try {
            Thread.sleep(nbMillisec);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
	}
}
