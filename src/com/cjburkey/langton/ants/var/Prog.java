package com.cjburkey.langton.ants.var;

import java.awt.Image;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

public class Prog {
	
	public static File url;
	public static Image img;
	
	public static JFrame tools = new JFrame();
	public static JMenuBar menuBar = new JMenuBar();
	public static JLabel ams = new JLabel();

	public static JButton speed = new JButton("<html>Set Speed <span color='blue'>(S)</html>");
	public static JButton play = new JButton("<html>Play <span color='blue'>(Space)</html>");
	public static JButton pause = new JButton("<html>Pause <span color='blue'>(Space)</html>");
	public static JButton tick = new JButton("<html>Cycle <span color='blue'>(T)</html>");
	public static JButton placeMode = new JButton("place");
	public static JButton clear = new JButton("<html><span color='red'>Clear</span> <span color='blue'>(C)</html>");
	public static JButton save = new JButton("<html>Save</html>");
	public static JButton load = new JButton("<html>Load</html>");
	public static JButton dragMode = new JButton("drag");
	public static JButton grid = new JButton("grid");
	public static JButton circle = new JButton("circle");
	public static JButton folder = new JButton("<html>Open Folder</html>");
	public static JButton random = new JButton("<html>Place Random Ant</html>");
	public static JButton center = new JButton("<html>Place Center Ant</html>");
	
}