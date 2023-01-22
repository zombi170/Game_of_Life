package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * A játék logikájáért felelős osztály.
 */
public class Generate implements Serializable{
	
	/**
	 * A pálya mérete, ami mindig négyzet alakú.
	 */
	private int mod;
	/**
	 * A pálya, ami egy kétdimenziós boolean típusú tömb. (1 az élő, 0 a halott cella)
	 */
	private boolean field[][];
	/**
	 * ArrayList típusú lista, amely tartalmazza a születés szabályait.
	 */
	private ArrayList<Integer> birth;
	/**
	 * ArrayList típusú lista, amely tartalmazza a túlélés szabályait.
	 */
	private ArrayList<Integer> survive;
	
	/**
	 * Default konstruktora a Generate osztálynak, amely létrehoz egy alap játékot.
	 */
	public Generate() {
		
		mod = 20;
		birth = new ArrayList<Integer>();
		birth.add(3);
		survive = new ArrayList<Integer>();
		survive.add(2);
		survive.add(3);
		field = new boolean[mod][mod];
		setBlank(field);
	}
	
	/**
	 * Konstruktora a Generate osztálynak, amellyel beállíthatóak a specifikus beállítások a játékhoz.
	 * @param m A pálya mérete.
	 * @param b A születés szabályait tartalmazó lista.
	 * @param s A túlélés szabályait tartalmazó lista.
	 */
	public Generate(int m, ArrayList<Integer> b, ArrayList<Integer> s) {
		
		mod = m;
		birth = b;
		survive = s;
		field = new boolean[mod][mod];
		setBlank(field);
	}
	
	/**
	 * Megszámolja egy adott cellának a szomszédjainak a számát.
	 * @param x Az x paramétere a cellának.
	 * @param y Az y paramétere a cellának.
	 * @return Szomszédok száma.
	 */
	public int countNeighbours(int x, int y) {
		
		int sum = 0;
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (field[(x + i + mod) % mod][(y + j + mod) % mod]) {
					sum++;
				}
			}
		}
		
		if (field[x][y]) {
			sum--;
		}
		
		return sum;
	}
	
	/**
	 * Legenerálja a következő generációt a szabályok alapján.
	 */
	public void nextField() {
		
		boolean[][] next = new boolean[mod][mod];
		setBlank(next);
		
		for (int i = 0; i < mod; i++) {
			for (int j = 0; j < mod; j++) {
				
				int nb = countNeighbours(i,j);
				
				if (!field[i][j] && birth.contains(nb)) {
					next[i][j] = true;
				} else if (field[i][j] && !survive.contains(nb)) {
					next[i][j] = false;
				} else {
					next[i][j] = field[i][j];
				}
			}
		}
		
		field = next;
	}
	
	/**
	 * Üresre állítja a teljes pályát, tehát minden cella halott lesz.
	 * @param array A pálya amit beállít.
	 */
	public void setBlank(boolean[][] array) {
		
		for (int i = 0; i < mod; i++) {
			for (int j = 0; j < mod; j++) {
				array[i][j] = false;
			}
		}
	}
	
	/**
	 * Random állítja élőre vagy halottra a pálya összes celláját.
	 * @param array A pálya amit beállít.
	 */
	public void setRandom(boolean[][] array) {
		
		Random rand = new Random();
		
		for (int i = 0; i < mod; i++) {
			for (int j = 0; j < mod; j++) {
				array[i][j] = rand.nextBoolean();
			}
		}
	}
	
	/**
	 * Beállítja valamelyik szabálylistát.
	 * @param l A lista amit beállít.
	 * @param which Boolean, amivel megadjuk melyik listát szeretnénk módosítani (true = születés, false = túlélés).
	 */
	public void setList(ArrayList<Integer> l, boolean which) {
		if (which) {
			birth = l;
		} else {
			survive = l;
		}
	}
	
	/**
	 * Beállítja a pálya méretét.
	 * @param m A pálya méretének nagysága.
	 */
	public void setMod(int m) {
		mod = m;
		field = new boolean[mod][mod];
		setBlank(field);
	}
	
	/**
	 * Visszaadja a pálya kétdimenziós tömbjét.
	 * @return A pálya kétmineziós tömbje.
	 */
	public boolean[][] getField() {
		return field;
	}
	
	/**
	 * Visszaadja valamelyik szabálylistát.
	 * @param which Boolean, amivel megadjuk melyik listát szeretnénk megkapni (true = születés, false = túlélés).
	 * @return A kért szabálylista.
	 */
	public ArrayList<Integer> getList(boolean which) {
		return which ? birth : survive;
	}
	
	/**
	 * Visszaadja a pálya méretét.
	 * @return A pálya mérete.
	 */
	public int getMod() {
		return mod;
	}
}
