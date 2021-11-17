package Fenetre;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import Modele.HC06;

/**
 * Classe Vue
 * Cr�e la fen�tre dans laquelle se trouve roule l'application
 * @author Charles-�ric Langlois, Arthur Van Bettsbrugge et Katherine Zamudio-Turcotte
 */
public class Vue extends JFrame implements Observer {
	HC06 hc06;
	JMenuBar mnuBar = new JMenuBar();
	JMenu mnuFichier = new JMenu("Fichier");
	JMenu mnuInfo = new JMenu("Informations");
	JMenu mnuAide = new JMenu("Aide");
	JMenuItem mnuiRelancer = new JMenuItem("Relancer");
	JMenuItem mnuiArreter = new JMenuItem("Arr�ter");
	JMenuItem mnuiQuitter = new JMenuItem("Quitter");
	JMenuItem mnuiAPropos = new JMenuItem("� propos...");
	JMenuItem mnuiUtilisation = new JMenuItem("Utilisation");
	JMenuItem mnuiEtat = new JMenuItem("�tat du robot");

	PnlBoutons pnlbtn;

	ArrayList<Integer> lstHum = new ArrayList<>();
	ArrayList<Integer> lstGaz = new ArrayList<>();
	ArrayList<Integer> lstTemp = new ArrayList<>();
	ArrayList<Integer> lstCO = new ArrayList<>();
	ArrayList<Integer> lstLumAmb = new ArrayList<>();
	ArrayList<Integer> lstTemps = new ArrayList<>();

	Cadran cadHum;
	Cadran cadTemp;
	Cadran cadCO;
	Cadran cadGaz;
	Cadran cadLumAmb;
	Logo logo;

	/**
	 * Constructeur de fen�tre
	 * @param hc06
	 */
	public Vue(HC06 hc06) {
		setLayout(null);

		this.hc06 = hc06;
		hc06.addObserver(this);

		creerInterface(hc06);
		creerEvenements();
		thd.start();
	}

	/**
	 * Regroupe toutes les �v�nements qui peuvent �tre d�clench�s par l'utilisateur
	 */
	private void creerEvenements() {
		//Pour relancer l'application
		mnuiRelancer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				try {
					hc06.closeStreamConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}

				//Vide les listes
				lstTemp.clear();
				lstHum.clear();
				lstGaz.clear();
				lstCO.clear();
				lstLumAmb.clear();
				lstTemps.clear();

				//Enl�ve les cadrans
				remove(cadHum);
				remove(cadTemp);
				remove(cadCO);
				remove(cadGaz);
				remove(cadLumAmb);

				//Recr�e les cadran
				cadHum = new Cadran("Humidit� (%)", 0, 70, 0, 83, 60, 75, lstHum, lstTemps);
				cadTemp = new Cadran("Temp�rature (�C)", 0, 97, -10, 85, 30, 40, lstTemp, lstTemps);
				cadCO = new Cadran("Monoxyde de carbone (PPM)", 0, 162, 20, 2000, 125, 400, lstCO, lstTemps);
				cadGaz = new Cadran("Gaz combustibles (PPM)", 0, 138, 20, 2000, 300, 1400, lstGaz, lstTemps);
				cadLumAmb = new Cadran("Lumi�re ambiante (Lux)", 0, 135, 1, 6000, 100, 4000, lstLumAmb, lstTemps);

				//Ajoute les cadrans
				add(cadHum);
				add(cadLumAmb);
				add(cadTemp);
				add(cadTemp);
				add(cadCO);
				add(cadGaz);

				cadHum.setLocation(20, 20);
				cadLumAmb.setLocation(
						(int) Math.round(
								Toolkit.getDefaultToolkit().getScreenSize().getWidth() - (cadLumAmb.getWidth() + 20)),
						20);
				cadTemp.setLocation(
						(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - cadTemp.getWidth())
								/ 2,
						20);
				cadCO.setLocation(20, (int) Math
						.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - (cadCO.getWidth() + 5)));
				cadGaz.setLocation(
						(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - (cadGaz.getWidth() + 20)),
						(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - (cadGaz.getWidth() + 5)));

				JFrame vue = new JFrame();
				JPanel main = new JPanel();

				main.setSize(300, 150);
				JLabel lblLoading = new JLabel("Loading...");
				main.add(lblLoading);
				vue.add(main);
				vue.setVisible(true);

				lblLoading.setPreferredSize(new Dimension(120, 30));
				lblLoading.setLocation(30, 10);
				lblLoading.setVisible(true);

				vue.setLocationRelativeTo(null);
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				vue.dispatchEvent(new WindowEvent(vue, WindowEvent.WINDOW_CLOSING));
				hc06.connecterAdresse();
			}
		});

		//Pour quitter l'application
		mnuiQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "�tes-vous s�r de vouloir fermer l'application?", "QUITTER",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});

		//Pour l'�tat du robot
		mnuiEtat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				boolean enMarche = !pnlbtn.btnLaunch.enabled;
				boolean enTrainDeRevenir = !pnlbtn.btnCall.enabled;
				String etatMouvement, etatAppel;
				if (enMarche) {
					etatMouvement = "Oui";
				} else {
					etatMouvement = "Non";
				}
				if (enTrainDeRevenir) {
					etatAppel = "Oui";
				} else {
					etatAppel = "Non";
				}
				JOptionPane.showMessageDialog(null, "�TAT DU ROBOT :\n" + "En mouvement : " + etatMouvement + "\n"
						+ "Appel� vers sa position initiale : " + etatAppel);
			}
		});

		//Pour l'utilisation de l'appeler
		mnuiUtilisation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"Sur le panneau de principal :\n" + "PARTIR -> Faire partir le robot en collecte de donn�es\n"
								+ "REVENIR -> Faire revenir le robot\n"
								+ "D�LAI -> Appuyer avant de d�brancher et rebrancher le module\n"
								+ "bluetooth (si la connection se perd)\n\n" + "Dans la barre de menus :\n"
								+ "RELANCER -> Recommencer la collecte des donn�es\n"
								+ "QUITTER -> Fermer l'application\n" + "� PROPOS -> Indications sur l'application");
			}
		});
		
		//Pour le menu "� propos"
		mnuiAPropos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"Projet int�grateur :\n" + "Application pour robot sondeur d'environnement\n"
								+ "Mod�le : DFRobotShop Rover V2 modifi�\n"
								+ "Connection : HC-06 Bluetooth Serial Module\n\n"
								+ "Par Charles-�ric Langlois, Arthur Van Betsbrugge\n"
								+ "et Katherine Zamudio-Turcotte\n" + "Groupe 03, Hiver 2018");
			}
		});
	}

	//Thread principal
	Thread thd = new Thread() {
		@Override
		public void run() {
			while (true) {
				try {
					hc06.go();
				} catch (Exception ex) {
				}
				addValeurs(); 
				setValeurs();
				checkEtatRobot();
				revalidate();
				repaint();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
				}
			}
		}
	};

	/**
	 * Ajoute les �l�ments qui forment l'interface de l'application
	 * @param hc06 Mod�le
	 */
	private void creerInterface(HC06 hc06) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize((int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth()) + 5,
				(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight()) + 5);
		setTitle("R.S.E. : Robot Sondeur d'Environnement");
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		//Cr�e les cadrans
		cadHum = new Cadran("Humidit� (%)", 0, 70, 0, 83, 60, 75, lstHum, lstTemps);
		cadTemp = new Cadran("Temp�rature (�C)", 0, 97, -10, 85, 30, 40, lstTemp, lstTemps);
		cadCO = new Cadran("Monoxyde de carbone"
				+ " (PPM)", 0, 162, 20, 2000, 125, 400, lstCO, lstTemps);
		cadGaz = new Cadran("Gaz combustibles (PPM)", 0, 138, 20, 2000, 300, 1400, lstGaz, lstTemps);
		cadLumAmb = new Cadran("Lumi�re ambiante (Lux)", 0, 135, 1, 6000, 100, 4000, lstLumAmb, lstTemps);
		logo = new Logo();
		pnlbtn = new PnlBoutons(hc06);

		//Ajoute les cadrans
		add(cadHum);
		add(cadLumAmb);
		add(cadTemp);
		add(cadCO);
		add(cadGaz);
		add(pnlbtn);

		//Ajoute le menu barre
		add(mnuBar);
		mnuBar.add(mnuFichier);
		mnuBar.add(mnuInfo);
		mnuBar.add(mnuAide);
		this.setJMenuBar(mnuBar);

		add(logo); //Ajoute le logo

		//Positionne les cadrans
		cadHum.setLocation(20, 20);
		cadLumAmb.setLocation(
				(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - (cadLumAmb.getWidth() + 20)),
				20);
		cadTemp.setLocation(
				(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - cadTemp.getWidth()) / 2, 20);
		cadCO.setLocation(20,
				(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - (cadCO.getWidth() + 5)));
		cadGaz.setLocation(
				(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - (cadGaz.getWidth() + 20)),
				(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - (cadGaz.getWidth() + 5)));
		pnlbtn.setLocation((int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 404) / 2,
				(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 243));
		logo.setLocation(
				(int) Math.round((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 202 * logo.getValX()) / 2),
				(int) Math.round(cadCO.getLocation().getY() + 40));

		//Ajoute les menus
		mnuFichier.add(mnuiRelancer);
		mnuFichier.addSeparator();
		mnuFichier.add(mnuiQuitter);

		mnuInfo.add(mnuiEtat);

		mnuAide.add(mnuiUtilisation);
		mnuAide.add(mnuiAPropos);

		mnuAide.add(mnuiAPropos);
	}

	/**
	 * Ajoute les valeurs obtenues par les capteurs � leur liste respective
	 */
	public void addValeurs() {
		lstTemp.add(hc06.getTemp());
		lstHum.add(hc06.getHum());
		lstGaz.add(hc06.getFum());
		lstCO.add(hc06.getCO());
		lstLumAmb.add(hc06.getLum());
		lstTemps.add(1);
	}

	/**
	 * Modifie les valeurs pr�sentes sur le cadran
	 */
	public void setValeurs() {
		cadTemp.setValeur(hc06.getTemp());
		cadHum.setValeur(hc06.getHum());
		cadGaz.setValeur(hc06.getFum());
		cadCO.setValeur(hc06.getCO());
		cadLumAmb.setValeur(hc06.getLum());
	}

	/**
	 * Observe l'�tat du robot
	 */
	public void checkEtatRobot() {
		try {
			boolean deRetour = false;
			
			if (hc06.getEtat() == 1) {
				deRetour = true;
			} else {
				deRetour = false;
			}
			if (deRetour) {
				pnlbtn.btnLaunch.enabled = true;
				pnlbtn.btnLaunch.revalidate();
				pnlbtn.btnLaunch.repaint();
				pnlbtn.btnCall.enabled = true;
				pnlbtn.btnCall.revalidate();
				pnlbtn.btnCall.repaint();
			}
		} catch (NullPointerException e) {

		}
	}

	/**
	 * Mise � jour
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
	}
}