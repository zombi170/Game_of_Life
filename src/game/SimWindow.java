package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * A szimulációs ablak osztálya, ami a játék fő részét jeleníti meg.
 */
public class SimWindow extends JFrame{

	/**
	 * A pálya grafikus megjelenítéséért felelős osztálya.
	 */
	private FieldGraphics f;
	/**
	 * A szimuláció elindításáért/szünetelteséért felelős gomb.
	 */
	private JButton start;
	/**
	 * A kezdőablak.
	 */
	private HomeWindow home;
	
	/**
	 * A "Start" gomb ActionListener implementáció alapú osztálya.
	 */
	public class StartButton implements ActionListener{
		
		/**
		 * Elindítja a szimulációt vagy éppen hogy leállítja, attól függően, hogy épp melyik változtat a program állapotán.
		 * @param e Az akció, ami kivátotta a függvény lefutását.
		 */
		public void actionPerformed(ActionEvent e) {
			if (!f.getTimer().isRunning()) {
				f.getTimer().start();
				start.setText("Pause");
			} else {
				f.getTimer().stop();
				start.setText("Start");
			}
		}	
	}
	
	/**
	 * A "Random" gomb ActionListener implementáció alapú osztálya.
	 */
	public class RandomButton implements ActionListener{
		
		/**
		 * Generál egy random kinézetű pályát.
		 * @param e Az akció, ami kivátotta a függvény lefutását.
		 */
		public void actionPerformed(ActionEvent e) {
			f.getGen().setRandom(f.getGen().getField());
			f.repaint();
		}
	}
	
	/**
	 * A "Blank" gomb ActionListener implementáció alapú osztálya.
	 */
	public class BlankButton implements ActionListener{
		
		/**
		 * Generál egy üres pályát.
		 * @param e Az akció, ami kivátotta a függvény lefutását.
		 */
		public void actionPerformed(ActionEvent e) {
			f.getGen().setBlank(f.getGen().getField());
			f.repaint();
		}
	}

	/**
	 * A "Save" gomb ActionListener implementáció alapú osztálya.
	 */
	private class SaveButton implements ActionListener{
		
		/**
		 * Elmenti a pálya jelenlegi állapotát.
		 * @param e Az akció, ami kivátotta a függvény lefutását.
		 */
		public void actionPerformed(ActionEvent e) {
			
			if (f.getTimer().isRunning()) {
				start.doClick();
			}
			
			OptionPane save = new OptionPane();
			
			try {
				if (save.getName() != null) {
					FileOutputStream fs = new FileOutputStream(save.getName());
					ObjectOutputStream out = new ObjectOutputStream(fs);
					out.writeObject(f.getGen());
					out.close();
				}
			}
			catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
	
	/**
	 * A "Load" gomb ActionListener implementáció alapú osztálya.
	 */
	private class LoadButton implements ActionListener{
		
		/**
		 * Betölt egy korábban elmentett pályát.
		 * @param e Az akció, ami kivátotta a függvény lefutását.
		 */
		public void actionPerformed(ActionEvent e) {
			
			if (f.getTimer().isRunning()) {
				start.doClick();
			}
			
			OptionPane load = new OptionPane();
			
			try {
				if (load.getName() != null) {
					FileInputStream fs = new FileInputStream(load.getName());
					ObjectInputStream in = new ObjectInputStream(fs);
					Generate temp = (Generate) in.readObject();
					in.close();
					
					int time = f.getTimer().getDelay();
					dispose();
					SimWindow newer = new SimWindow(temp, time, home);
					newer.setVisible(true);
				}
			}
			catch (FileNotFoundException nofile) {
				nofile.printStackTrace();
				load.error();
			}
			catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
	
	/**
	 * A "Settings" gomb ActionListener implementáció alapú osztálya.
	 */
	public class SettingsButton implements ActionListener{
		
		/**
		 * Visszalép a kezdőablakra.
		 * @param e Az akció, ami kivátotta a függvény lefutását.
		 */
		public void actionPerformed(ActionEvent e) {
			dispose();
			home.setVisible(true);
		}
	}
	
	/**
	 * Konstruktora a szimulációs ablak osztálynak, ami felépíti az egész ablak komponenseit és megjelenését.
	 * @param gen A játék alap Generate osztálya.
	 * @param time Két generáció megjelenítése között eltelt idő milliszekundumban.
	 * @param h A kezdőablak.
	 */
	public SimWindow(Generate gen, int time, HomeWindow h) {
		
		super("Game of Life");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(950, 900));
		setResizable(false);
		home = h;
		
		f = new FieldGraphics(gen, time);
		f.addMouseListener(f);
		add(f, BorderLayout.CENTER);
		
		JLabel data = new JLabel("B: " + gen.getList(true) 
			+ "      S: " + gen.getList(false) 
			+ "      Size: " + gen.getMod() 
			+ "      Timer: " + time + "ms", SwingConstants.CENTER);
		add(data, BorderLayout.NORTH);
		
		start = new JButton("Start");
		start.addActionListener(new StartButton());
		
		JButton random = new JButton("Random");
		random.addActionListener(new RandomButton());
		
		JButton blank = new JButton("Blank");
		blank.addActionListener(new BlankButton());
		
		JButton save = new JButton("Save");
		save.addActionListener(new SaveButton());
		
		JButton load = new JButton("Load");
		load.addActionListener(new LoadButton());
		
		JButton set = new JButton("Settings");
		set.addActionListener(new SettingsButton());
		
		JPanel settings = new JPanel();
		settings.add(start);
		settings.add(random);
		settings.add(blank);
		settings.add(save);
		settings.add(load);
		settings.add(set);
		settings.setLayout(new GridLayout(6,1));
		add(settings, BorderLayout.EAST);
	}
	
	/**
	 * Visszaadja a FieldGraphics osztályt, ami a grafika létrehozásáért felelős, kizárólag tesztelés miatt.
	 * @return Az ablakhoz tartozó FieldGraphics osztály.
	 */
	public FieldGraphics getFieldGraphics() {
		return f;
	}
}
