package com.cjburkey.langton.ants.var;

import java.awt.Image;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.cjburkey.langton.ants.render.DrawPane;

public class Prog {
	
	public static File url;
	public static Image img;
	
	public static JFrame tools = new JFrame();
	public static JMenuBar menuBar = new JMenuBar();
	public static JMenuItem ams = new JMenuItem();
	
	public static DrawPane drawPane = new DrawPane();

	public static JButton speed;
	public static JButton play;
	public static JButton pause;
	public static JButton tick;
	public static JButton placeMode;
	public static JButton clear;
	public static JButton save;
	public static JButton load;
	public static JButton dragMode;
	public static JButton grid;
	public static JButton circle;
	public static JButton folder;
	public static JButton random;
	public static JButton center;
	public static JButton size;
	public static JButton kill;
	
	public static final void init() {
		
		Main.cycles = 0;
		
		speed = new JButton("<html>Set Speed <span color='blue'>(S)</html>");
		play = new JButton("<html>Play <span color='blue'>(Space)</html>");
		pause = new JButton("<html>Pause <span color='blue'>(Space)</html>");
		tick = new JButton("<html>Cycle <span color='blue'>(T)</html>");
		placeMode = new JButton("place");
		clear = new JButton("<html><span color='red'>Clear</span> <span color='blue'>(C)</html>");
		kill = new JButton("<html><span color='red'>Kill Ants</span> <span color='blue'>(K)</html>");
		save = new JButton("<html>Save</html>");
		load = new JButton("<html>Load</html>");
		dragMode = new JButton("drag");
		grid = new JButton("grid");
		circle = new JButton("circle");
		folder = new JButton("<html>Open Folder</html>");
		random = new JButton("<html>Place Random Ant</html>");
		center = new JButton("<html>Place Center Ant</html>");
		size = new JButton("<html>Set TileSize</html>");
		
		drawPane = new DrawPane();
		
	}
	
}