package Modele;

import java.util.Observable;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 * Classe HC06
 * Cr�e le mod�le
 * @author Charles-�ric Langlois, Arthur Van Bettsbrugge et Katherine Zamudio-Turcotte
 */
public class HC06 extends Observable {
	private String hc06Url = "btspp://201703153182:1;authenticate=false;encrypt=false;master=false";
	private int temp = 0;
	private int hum = 0;
	private int lum = 0;
	private int fum = 0;
	private int co = 0;
	private int etat = 0;
	
	private StreamConnection streamConnection;
	private OutputStream os;
	private InputStream is;

	/**
	 * Constructeur de mod�le
	 */
	public HC06() {
		connecterAdresse();
	}

	/**
	 * Re�oit les valeurs lanc�es par le robot
	 * @throws Exception
	 */
	public void go() throws Exception {
		int mult = 0;
		int mod = 0;

		mult = is.read();
		mod = is.read();
		temp = mult * 256 + mod;

		mult = is.read();
		mod = is.read();
		hum = mult * 256 + mod;

		mult = is.read();
		mod = is.read();
		lum = mult * 256 + mod;

		mult = is.read();
		mod = is.read();
		fum = mult * 256 + mod;

		mult = is.read();
		mod = is.read();
		co = mult * 256 + mod;
		
		etat = is.read();
		System.out.println(etat);
	}
	
	/**
	 * Ferme la connection avec le Bluetooth
	 * @throws Exception
	 */
	public void closeStreamConnection() throws Exception {
		try {
			streamConnection.close();
		} catch (Exception ex) {
		}
	}

	/**
	 * Renvoie la valeur de temp�rature
	 * @return Valeur de temp�rature
	 */
	public int getTemp() {
		return temp;
	}

	/**
	 * Renvoie la valeur de l'humidit�
	 * @return Valeur de l'humidit�
	 */
	public int getHum() {
		return hum;
	}

	/**
	 * Renvoie la valeur de la lumi�re ambiante
	 * @return Valeur de la lumi�re ambiante
	 */
	public int getLum() {
		return lum;
	}

	/**
	 * Renvoie la valeur de PPM de gaz
	 * @return Valeur de PPM de gaz
	 */
	public int getFum() {
		return fum;
	}

	/**
	 * Renvoie la valeur de PPM de CO
	 * @return Valeur de PPM de CO
	 */
	public int getCO() {
		return co;
	}

	/**
	 * Renvoie le OutputStream
	 * @return OutputStream
	 */
	public OutputStream getOs() {
		return os;
	}

	/**
	 * Renvoie l'�tat de la lecture (lit ou non les valeurs)
	 * @return �tat de lalecture
	 */
	public int getEtat() {
		return etat;
	}

	/**
	 * Mise � jour
	 */
	private void miseAJour() {
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Connecte l'application au Bluetooth du robot
	 */
	public void connecterAdresse() {
		try {
			streamConnection = (StreamConnection) Connector.open(hc06Url);
			os = streamConnection.openOutputStream();
			is = streamConnection.openInputStream();
		} catch (Exception ex) {
		}
	}
}
