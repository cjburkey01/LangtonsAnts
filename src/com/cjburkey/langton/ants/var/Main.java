package com.cjburkey.langton.ants.var;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.cjburkey.langton.ants.Aut;
import com.cjburkey.langton.ants.obj.Ant;
import com.cjburkey.langton.ants.othercore.PlaceMode;

public class Main {
	
	public static boolean ran = false;
	public static Aut aut = new Aut();
	public static Toolkit tools = Toolkit.getDefaultToolkit();
	public static Dimension screen = tools.getScreenSize();
	public static int tileSize = 2;
	public static int cycles = 0;
	public static int mapSize = Func.round((int) (screen.height - 100) / tileSize);
	public static int[][] map = new int[mapSize][mapSize];
	public static JFrame frame = new JFrame();
	public static int fps = 20;
	public static boolean running = false;
	public static boolean playing = false;
	public static boolean drag = false;
	public static boolean grid = true;
	public static boolean circle = true;
	public static ArrayList<Ant> ants = new ArrayList<Ant>();
	public static Point mousePos = new Point(100, 100);
	public static PlaceMode placeMode = PlaceMode.NORMAL;
	public static BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	public static Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), "blackCursorForLangtonsAnts");
	public static final String home = System.getProperty("user.home");
	public static final String sep = System.getProperty("file.separator");
	public static final String folder = home + sep + "langtonsants" + sep + "saves" + sep;
	public static final File fFolder = new File(folder);
	public static final String end = ".antMapFile";
	
}