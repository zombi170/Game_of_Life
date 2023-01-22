package game;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 * A kezdőlap osztálya a játéknak. A teljes ablak felépítéséért és működéséért felelős.
 */
public class HomeWindow extends JFrame{
	
	/**
	 * Az ablak JComboBox komponense, amivel lehet állítani a pálya méretét.
	 */
	private JComboBox<Integer> size;
	/**
	 * Az ablak csúszkája, amellyel a játék sebességét lehet állítani.
	 */
	private JSlider quick;
	/**
	 * ArrayList típusú lista, amely tartalmazza a születés szabályait.
	 */
	private ArrayList<Integer> birthList;
	/**
	 * ArrayList típusú lista, amely tartalmazza a túlélés szabályait.
	 */
	private ArrayList<Integer> surviveList;
	
	/**
	 * A "Simulate" gomb ActionListener implementáció alapú osztálya.
	 */
	public class SimulateButton implements ActionListener{
		
		/**
		 * Felépíti a játék alapjait, illetve a szimulációs ablakot, majd átvált a szimulációs ablakra.
		 * @param e Az akció, ami kivátotta a függvény lefutását.
		 */
		public void actionPerformed(ActionEvent e) {
			Generate gen = new Generate((int) size.getSelectedItem(), birthList, surviveList);
			SimWindow sim = new SimWindow(gen, quick.getValue(), getThis());
			setVisible(false);
			sim.setVisible(true);
		}
	}
	
	/**
	 * Visszaadja a kezdőablak "this" pointerét.
	 * @return HomeWindow "this" pointere
	 */
	private HomeWindow getThis() {
		return this;
	}
	
	/**
	 * Létrehoz egy sor JCheckBox-ot, adott JPanel-en belül.
	 * @param panel A Jpanel, amin belül a JCheckBox-okat létre akarjuk hozni.
	 * @param list A lista, amihez a JCheckBox-ok tartoznak.
	 */
	public void createCheckBox(JPanel panel, ArrayList<Integer> list) {
		
		String numbers = new String("0,1,2,3,4,5,6,7,8");
		String[] str = numbers.split(",");
		
		for (int i = 0; i < 9; i++) {
			
			final Integer num = i;
			
			JCheckBox check = new JCheckBox(str[i]);
			check.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == 1) {
						list.add(num);
					} else {
						list.remove(num);
					}
				}
			});
			
			panel.add(check);
		}
	}
	
	/**
	 * Felépíti a kezdőablak összes komponensét és azok kinézetét.
	 */
	private void setHomeWindow() {
		
		JButton simulate = new JButton("Simulate");
		simulate.addActionListener(new SimulateButton());
		
		birthList = new ArrayList<Integer>();
		surviveList = new ArrayList<Integer>();
		
		JPanel b = new JPanel();
		JLabel birth = new JLabel("Birth:");
		b.add(birth);
		createCheckBox(b, birthList);
		
		JPanel s = new JPanel();
		JLabel survive = new JLabel("Survive:");
		s.add(survive);
		createCheckBox(s, surviveList);
		
		Integer[] div = {5, 8, 10, 16, 20, 25, 32, 40, 50, 80, 100, 160};
		size = new JComboBox<Integer>(div);
		
		JPanel siz = new JPanel();
		JLabel si = new JLabel("Size:");
		siz.add(si);
		siz.add(size);
		
		quick = new JSlider();
		quick.setMinimum(50);
		quick.setMaximum(1000);
		quick.setMinorTickSpacing(50);
		quick.setMajorTickSpacing(100);
		quick.setPaintTicks(true);  
		quick.setPaintLabels(true);
		
		JLabel refresh = new JLabel("Refresh Rate (ms)", SwingConstants.CENTER);
		
		setLayout(new GridLayout(6,1));
		add(simulate);
		add(b);
		add(s);
		add(siz);
		add(refresh);
		add(quick);
	}
	
	/**
	 * Default konstruktora a HomeWindow osztálynak, 
	 * ami beállítja az ablak alapvető beállításait, majd meghívja a setHomeWindow() függvényt.
	 */
	public HomeWindow() {
		
		super("Game of Life");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(500, 300));
		setResizable(false);
		setHomeWindow();
	}
	
	/**
	 * Main függvénye a programnak, ami létrehoz egy HomeWindow-t, majd azt láthatóvá teszi.
	 * @param args Argumentumok.
	 */
	public static void main(String[] args) {
		
        HomeWindow w = new HomeWindow();
        w.setVisible(true);
    }
}
