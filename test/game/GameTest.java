package game;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Test;

/**
 * A GameTest osztály a program JUnitban történő teszteléséhez.
 */
public class GameTest {

	/**
	 * A Generate osztály default konstruktorának tesztelése.
	 * Megnézi, hogy a várt alap pálya mérettel egyenlő-e.
	 */
	@Test
	public void testCreateDefaultGame() {
		Generate gen = new Generate();
		int result = gen.getMod();
		Assert.assertEquals(20, result);
	}
	
	/**
	 * A Generate osztály specifikus konstruktorának tesztelése.
	 * Megnézi, hogy a várt pálya mérettel egyenlő-e.
	 */
	@Test
	public void testCreateSpecificGame() {
		int m = 5;
		ArrayList<Integer> b = new ArrayList<Integer>();
		ArrayList<Integer> s = new ArrayList<Integer>();
		Generate gen = new Generate(m, b, s);
		int result = gen.getMod();
		Assert.assertEquals(5, result);
	}
	
	/**
	 * A Generate osztály countNeighbours() függvényének tesztelése.
	 * Megnézi, hogy a létrehozott egy szomszédot látja-e.
	 */
	@Test
	public void testCountNeighbours() {
		int m = 5;
		ArrayList<Integer> b = new ArrayList<Integer>();
		ArrayList<Integer> s = new ArrayList<Integer>();
		Generate gen = new Generate(m, b, s);
		boolean[][] f = gen.getField();
		f[2][1] = true;
		int result = gen.countNeighbours(2, 2);
		Assert.assertEquals(1, result);
	}
	
	/**
	 * A Generate osztály nextField() függvényének tesztelése.
	 * Megnézi, hogy a várt következő generációt kapja-e.
	 */
	@Test
	public void testNextField() {
		Generate gen = new Generate();
		gen.getField()[1][2] = true;
		gen.getField()[2][2] = true;
		gen.getField()[3][2] = true;
		gen.nextField();
		boolean r1 = gen.getField()[1][2];
		boolean r2 = gen.getField()[3][2];
		boolean r3 = gen.getField()[2][1];
		boolean r4 = gen.getField()[2][2];
		boolean r5 = gen.getField()[2][3];
		Assert.assertEquals(false, r1);
		Assert.assertEquals(false, r2);
		Assert.assertEquals(true, r3);
		Assert.assertEquals(true, r4);
		Assert.assertEquals(true, r5);
	}
	
	/**
	 * A Generate osztály setBlank() függvényének tesztelése.
	 * Megnézi, hogy az eredetileg élő cella halott lett-e.
	 */
	@Test
	public void testSetBlank() {
		Generate gen = new Generate();
		gen.getField()[1][2] = true;
		gen.setBlank(gen.getField());
		boolean result = gen.getField()[1][2];
		Assert.assertEquals(false, result);
	}
	
	/**
	 * A SimWindow osztályon belüli StartButton osztály tesztelése.
	 * Megnézi, hogy a gomb nyomás hatására elindult-e az időzítő.
	 */
	@Test
	public void testStartButton() {
		HomeWindow h = new HomeWindow();
		int time = 500;
		Generate gen = new Generate();
		SimWindow sim = new SimWindow(gen, time, h);
		JButton button = new JButton();
		button.addActionListener(sim.new StartButton());
		sim.add(button);
		button.doClick();
		boolean result = sim.getFieldGraphics().getTimer().isRunning();
		Assert.assertEquals(true, result);
	}
	
	/**
	 * A HomeWindow osztályon belüli SimulateButton osztály tesztelése.
	 * Megnézi, hogy a gomb nyomás hatására eltűnt-e a kezdőablak.
	 */
	@Test
	public void testSimulateButton() {
		HomeWindow h = new HomeWindow();
		JButton button = new JButton();
		button.addActionListener(h.new SimulateButton());
		h.add(button);
		button.doClick();
		boolean result = h.isVisible();
		Assert.assertEquals(false, result);
	}
	
	/**
	 * A HomeWindow osztályon belüli createCheckBox() függvény tesztelése.
	 * Megnézi, hogy létre lett-e hozva a panelen belül a 9 JCheckBox komponens.
	 */
	@Test
	public void testCreateCheckBox() {
		HomeWindow h = new HomeWindow();
		JPanel p = new JPanel();
		ArrayList<Integer> l = new ArrayList<Integer>();
		h.createCheckBox(p, l);
		int result = p.getComponentCount();
		Assert.assertEquals(9, result);
	}
	
	/**
	 * A SimWindow osztályon belüli SettingsButton osztály tesztelése.
	 * Megnézi, hogy a gomb nyomás hatására megjelenik-e a kezdőablak és eltűnik-e a szimulációs ablak.
	 */
	@Test
	public void testSettingsButton() {
		HomeWindow h = new HomeWindow();
		int time = 500;
		Generate gen = new Generate();
		SimWindow sim = new SimWindow(gen, time, h);
		JButton button = new JButton();
		button.addActionListener(sim.new SettingsButton());
		sim.add(button);
		button.doClick();
		boolean r1 = sim.isActive();
		boolean r2 = h.isVisible();
		Assert.assertEquals(false, r1);
		Assert.assertEquals(true, r2);
	}
	
	/**
	 * A Generate osztály setRandom() függvényének tesztelése.
	 * Megnézi, hogy lett-e élő cella a pályán.
	 */
	@Test
	public void testSetRandom() {
		Generate gen = new Generate();
		gen.setRandom(gen.getField());
		boolean result = false;
		for (int i = 0; i < gen.getMod(); i++) {
			for (int j = 0; j < gen.getMod(); j++) {
				if (gen.getField()[i][j]) {
					result = true;
				}
			}
		}
		Assert.assertEquals(true, result);
	}
	
	/**
	 * A SimWindow osztályon belüli BlankButton osztály tesztelése.
	 * Megnézi, hogy a gombnyomás hatására az eredetileg élő cella halott lett-e.
	 */
	@Test
	public void testBlankButton() {
		HomeWindow h = new HomeWindow();
		int time = 500;
		Generate gen = new Generate();
		gen.getField()[1][2] = true;
		SimWindow sim = new SimWindow(gen, time, h);
		JButton button = new JButton();
		button.addActionListener(sim.new BlankButton());
		sim.add(button);
		button.doClick();
		boolean result = gen.getField()[1][2];
		Assert.assertEquals(false, result);
	}
	
	/**
	 * A SimWindow osztályon belüli RandomButton osztály tesztelése.
	 * Megnézi, hogy a gombnyomás hatására lett-e élő cella a pályán.
	 */
	@Test
	public void testRandomButton() {
		HomeWindow h = new HomeWindow();
		int time = 500;
		Generate gen = new Generate();
		SimWindow sim = new SimWindow(gen, time, h);
		JButton button = new JButton();
		button.addActionListener(sim.new RandomButton());
		sim.add(button);
		button.doClick();
		boolean result = false;
		for (int i = 0; i < gen.getMod(); i++) {
			for (int j = 0; j < gen.getMod(); j++) {
				if (gen.getField()[i][j]) {
					result = true;
				}
			}
		}
		Assert.assertEquals(true, result);
	}
	
	/**
	 * A FieldGraphics osztály tesztelése, hogy egérnyomásra a cellán változik-e annak az állapota.
	 * Megnézi, fehér lett-e az eredetileg fekete színű cella színe.
	 */
	@Test
	public void testMousePressed() {
		HomeWindow h = new HomeWindow();
		JButton button = new JButton();
		button.addActionListener(h.new SimulateButton());
		h.add(button);
		button.doClick();
		try {
			Robot r = new Robot();
			r.mouseMove(250, 400);
			r.mousePress(InputEvent.getMaskForButton(1));
			r.mouseRelease(InputEvent.getMaskForButton(1));
			r.mouseMove(400, 400);
			r.delay(500);
			Color r1 = r.getPixelColor(250, 400);
			Assert.assertEquals(Color.WHITE, r1);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
