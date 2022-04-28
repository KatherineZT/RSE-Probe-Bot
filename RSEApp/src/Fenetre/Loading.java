package Fenetre;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * Classe Loading
 * G�re les d�lais demand�s par l'utilisateur
 * @author Charles-�ric Langlois, Arthur Van Bettsbrugge et Katherine Zamudio-Turcotte
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
	 * Cr�e un message annon�ant le d�lai et 
	 * appelle la m�thode qui cr�e le d�lai
	 */
	public void attendreLong() {
		lblLoading = new JLabel("En attente de la r�ouverture de\nla connection bluetooth...");
		setLocationRelativeTo(null);
		delai(10000);
	}
	
	/**
	 * Appelle la m�thode qui cr�e le d�lai
	 */
	public void attendreCourt() {
		delai(1000);
	}
	
	/**
	 * Cr�e un de d�lai dans le Thread dans lequel �volue l'application
	 * @param nbMillisec Temps du d�lai en millisecondes
	 */
	public void delai(int nbMillisec) {
		try {
            Thread.sleep(nbMillisec);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
	}
}
