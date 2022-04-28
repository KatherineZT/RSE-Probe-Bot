package Main;

import Modele.HC06;
import Fenetre.Vue;

/**
 * Classe Main
 * @author Charles-Éric Langlois, Arthur Van Bettsbrugge et Katherine Zamudio-Turcotte
 *
 */
public class Main {

	/**
	 * Constructeur du Main
	 * @param args
	 */
	public static void main(String[] args) {
		HC06 hc06 = new HC06();
		Vue vue = new Vue(hc06);
	}

}
