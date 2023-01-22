package game;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Egyedi felugró ablak osztály, amit mentésnél és betöltésnél használ a program.
 */
public class OptionPane {

	/**
	 * JFrame-je a felugró ablaknak.
	 */
	private JFrame frame;
	/**
	 * Neve a kért/menteni kívánt fájlnak.
	 */
	private String name;
	
	/**
	 * Default konstruktora a felugró ablaknak, ami megjeleníti az ablakot és kér egy fájlnevet a felhasználótól.
	 */
	public OptionPane() {
		
	    frame = new JFrame();
	    name = JOptionPane.showInputDialog(frame,"Enter File Name");
	}
	
	/**
	 * Visszaadja a felhasználó által megadott fájl nevét.
	 * @return A fájl neve.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Feldob egy hibaablakot, egy "Fájl nem található!" felirattal.
	 */
	public void error() {
		JOptionPane.showMessageDialog(frame, "No file found!");
	}
}
