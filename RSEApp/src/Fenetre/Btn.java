package Fenetre;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Modele.HC06;

/**
 * Classe Btn
 * Crée les boutons présents au bas de l'interface
 * @author Charles-Éric Langlois, Arthur Van Bettsbrugge et Katherine Zamudio-Turcotte
 */
public class Btn extends JPanel {

	public enum Type {
		LAUNCH, CALL, DELAY
	}

	Type type;
	Image imgBtn, imgDisabled;
	final int size = 70;
	boolean enabled = true;

	/**
	 * Constructeur de boutons
	 * @param type Type de boutons
	 * @param hc06 Modèle
	 */
	public Btn(Type type, HC06 hc06) {
		setLayout(null);
		this.type = type;
		imgDisabled = Toolkit.getDefaultToolkit().getImage("Button-grey.png").getScaledInstance(size, size,
				Image.SCALE_DEFAULT);
		
		switch (type) { //Switch des images
			case LAUNCH: { //Pour le bouton "Lancer"
				imgBtn = Toolkit.getDefaultToolkit().getImage("Button-green.png").getScaledInstance(size, size,
					Image.SCALE_DEFAULT);
				break;
			}
			case CALL: { //Pour le bouton "Revenir"
				imgBtn = Toolkit.getDefaultToolkit().getImage("Button-red.png").getScaledInstance(size, size,
					Image.SCALE_DEFAULT);
				break;
			}
			case DELAY: { //Pour le bouton "Délai"
				imgBtn = Toolkit.getDefaultToolkit().getImage("Button-blue.png").getScaledInstance(size, size,
					Image.SCALE_DEFAULT);
				break;
			}
		}
		setSize(size, size);

		addMouseListener(new MouseAdapter() {
			/**
			 * Crée l'événement qui a lieu lorsque le bouton est cliqué
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (enabled) { //Si le bouton n'a pas été cliqué
					enabled = false;
					revalidate();
					repaint();

					
					switch (type) { //Switch des événements
						case LAUNCH: { //Pour le bouton "Lancer"
							try {
								hc06.getOs().write("1".getBytes());
							} catch (IOException e1) {
							e1.printStackTrace();
							}
							break;
						}
						case CALL: { //Pour le bouton "Revenir"
							try {
								hc06.getOs().write("0".getBytes());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							break;
						}
						case DELAY: { //Pour le bouton "Délai"
							if (JOptionPane.showConfirmDialog(null, "Confirmez lorsque vous avez rebranché le module bluetooth.",
									"Activation du délai pour la communication bluetooth",
									JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION) {
								Loading fenetreLoading = new Loading();
								fenetreLoading.attendreLong();
								fenetreLoading.dispatchEvent(new WindowEvent(fenetreLoading, WindowEvent.WINDOW_CLOSING));
							}
							enabled = true;
							revalidate();
							repaint();
							break;
						}
					}

				} else { //Si le bouton a été cliqué
					switch (type) { //Switch des événements
						case LAUNCH: { //Pour le bouton "Lancer"
							JOptionPane.showMessageDialog(null, "Le robot est déjà en marche.", "",
								JOptionPane.WARNING_MESSAGE);
							break;
						}
						case CALL: { //Pour le bouton "Revenir"
							JOptionPane.showMessageDialog(null, "Le robot est déjà en train de revenir.", "",
								JOptionPane.WARNING_MESSAGE);
							break;
						}
						case DELAY: { //Pour le bouton "Délai"
							JOptionPane.showMessageDialog(null, "Le délai est déjà commencé.", "",
								JOptionPane.WARNING_MESSAGE);
							break;
						}
					}
				}
			}
		});
	}

	/**
	 * Ajoute l'image du bouton
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if (enabled) {
			g.drawImage(imgBtn, 0, 0, this);
		} else {
			g.drawImage(imgDisabled, 0, 0, this);
		}
	}

}
