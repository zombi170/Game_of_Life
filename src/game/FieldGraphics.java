package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A pálya grafikus megjelenítéséért felelős osztálya.
 */
public class FieldGraphics extends JPanel implements ActionListener, MouseListener{
	
	/**
	 * A játék logikájáért felelős osztály.
	 */
	private Generate gen;
	/**
	 * Az időzítő.
	 */
	private Timer timer;
	/**
	 * Az egyes cellák mérete a pályán.
	 */
	private int inc;
	/**
	 * A maximális mérete a pályának.
	 */
	private int max = 800;
	
	/**
	 * Default konstruktora a FieldGraphics osztálynak, ami létrehoz egy alap játékot, alap beállításokkal.
	 */
	public FieldGraphics() {
		gen = new Generate();
		inc = 25;
		timer = new Timer(500, this);
	}
	
	/**
	 * Konstruktora a FieldGraphics osztálynak, adott Generate és időzítő attribútumokkal.
	 * @param g Generate osztály.
	 * @param t Két generáció megjelenítése között eltelt idő milliszekundumban.
	 */
	public FieldGraphics(Generate g, int t) {
		gen = g;
		inc = max / g.getMod();
		timer = new Timer(t, this);
	}
	
	/**
	 * Kirajzoló függvény, amely megjeleníti a pálya kinézetét.
	 * @param g Graphics osztály, amivel rajzol a függvény.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		
		g.setColor(Color.BLACK);
		g.fillRect(25, 25, max, max);
		
		g.setColor(Color.WHITE);
		for (int x = 25; x <= max; x += inc) {
			for (int y = 25; y <= max; y += inc) {
				
				if (gen.getField()[(x - 25) / inc][(y - 25) / inc]) {
					g.fillRect(x, y, inc, inc);
				}
				else {
					g.drawRect(x, y, inc, inc);
				}
			}
		}
	}
	
	/**
	 * Az időzítő miatti akció hatására legenerálja a következő generációt és azt meg is jeleníti.
	 * @param e Az akció, ami kivátotta a függvény lefutását.
	 */
	public void actionPerformed(ActionEvent e) {
		
		gen.nextField();
		repaint();
	}
	
	/**
	 * Visszaadja az időzítőt.
	 * @return Időzítő.
	 */
	public Timer getTimer() {
		return timer;
	}
	
	/**
	 * Visszaadja a játék logikájáért felelős osztályt.
	 * @return Generate osztály.
	 */
	public Generate getGen() {
		return gen;
	}
	
	/**
	 * Beállítja a játék logikájáért felelős osztályt és ezáltal a pályát is átállítja.
	 * @param g Generate osztály.
	 */
	public void setGen(Generate g) {
		gen = g;
		inc = max / g.getMod();
	}

	/**
	 * Kötelezően implementálandó függvénye a MouseListener implementációnak, amit nem használ a program, ezért üres.
	 */
	public void mouseClicked(MouseEvent e) {}

	/**
	 * Egérklikkelés hatására a pályán megváltoztatja az adott mező állapotát.
	 * @param e Egérklikkelés adatainak az osztálya.
	 */
	public void mousePressed(MouseEvent e) {
		int x = ((e.getX() - 25) / inc);
		int y = ((e.getY() - 25) / inc);
		if (x < gen.getMod() && y < gen.getMod() && x >= 0 && y >= 0) {
			gen.getField()[x][y] = !gen.getField()[x][y];
			repaint();
		}
	}

	/**
	 * Kötelezően implementálandó függvénye a MouseListener implementációnak, amit nem használ a program, ezért üres.
	 */
	public void mouseReleased(MouseEvent e) {}
	/**
	 * Kötelezően implementálandó függvénye a MouseListener implementációnak, amit nem használ a program, ezért üres.
	 */
	public void mouseEntered(MouseEvent e) {}
	/**
	 * Kötelezően implementálandó függvénye a MouseListener implementációnak, amit nem használ a program, ezért üres.
	 */
	public void mouseExited(MouseEvent e) {}
}
