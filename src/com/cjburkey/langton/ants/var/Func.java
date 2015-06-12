package com.cjburkey.langton.ants.var;

import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.cjburkey.langton.ants.obj.Ant;
import com.cjburkey.langton.ants.obj.MoreMath;
import com.cjburkey.langton.ants.othercore.PlaceMode;
import com.cjburkey.langton.ants.render.Error;
import com.cjburkey.langton.ants.thread.RenderLoop;
import com.cjburkey.langton.ants.thread.TickLoop;

public class Func {
	
	public static final void addListeners() {
		
		Main.frame.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) { }
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Main.running = false;
				} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					Main.playing = !Main.playing;
				} else if(e.getKeyCode() == KeyEvent.VK_P) {
					togglePlaceMode();
				} else if(e.getKeyCode() == KeyEvent.VK_S) {
					setSpeed();
				} else if(e.getKeyCode() == KeyEvent.VK_C) {
					clear();
				} else if(e.getKeyCode() == KeyEvent.VK_T) {
					if(Prog.tick.isEnabled()) {
						Main.aut.tick(true);
					}
				}
			}
			public void keyReleased(KeyEvent e) { }
		});
		
		Prog.drawPane.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if(Main.drag) {
					Point pos = e.getPoint();
					if(pos != null) {
						Main.mousePos = pos;
					}
					click(e.getButton());
				}
			}
			public void mouseMoved(MouseEvent e) {
				Point pos = e.getPoint();
				if(pos != null) {
					Main.mousePos = pos;
				}
			}
		});
		
		Prog.drawPane.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if(!Main.drag) {
					Point pos = e.getPoint();
					if(pos != null) {
						Main.mousePos = pos;
					}
					click(e.getButton());
				}
			}
			public void mousePressed(MouseEvent e) { }
			public void mouseReleased(MouseEvent e) { }
			public void mouseEntered(MouseEvent e) { }
			public void mouseExited(MouseEvent e) { }
		});
		
		Prog.play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.playing = true;
			}
		});
		
		Prog.pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.playing = false;
			}
		});
		
		Prog.clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Func.clearMap();
			}
		});
		
		Prog.speed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSpeed();
			}
		});
		
		Prog.tick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.aut.tick(true);
			}
		});
		
		Prog.placeMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				togglePlaceMode();
			}
		});
		
		Prog.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveMap();
			}
		});
		
		Prog.load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadMap();
			}
		});
		
		Prog.dragMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.drag = !Main.drag;
			}
		});
		
		Prog.circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.circle = !Main.circle;
			}
		});
		
		Prog.grid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.grid = !Main.grid;
			}
		});
		
		Prog.random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = MoreMath.randomRange(0, Main.mapSize);
				int y = MoreMath.randomRange(0, Main.mapSize);
				if(!antAt(x, y))
					Main.ants.add(new Ant(x, y));
			}
		});
		
		Prog.center.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = (int) Main.mapSize / 2;
				if(!Func.antAt(pos, pos))
					Main.ants.add(new Ant(pos, pos));
			}
		});
		
		Prog.folder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Desktop d = Desktop.getDesktop();
				try {
					d.open(Main.fFolder);
				} catch (IOException e1) {
					new Error("Couldn't open folder\n\n" + e1.getMessage(), "Error");
					e1.printStackTrace();
				}
			}
		});
		
		Prog.size.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSize(-1, -1);
			}
		});
		
	}
	
	public static final void init() {
		
		Main.fFolder.mkdirs();
		calcMap();
		clearMap();
		
		if(!Main.ran) {
			new Thread(new TickLoop()).start();
			new Thread(new RenderLoop()).start();
			Main.ran = true;
		}
		
		System.out.println("init");
		
	}
	
	public static boolean antAt(int x, int y) {
		
		for(int i = 0; i < Main.ants.size(); i ++) {
			
			Ant ant = Main.ants.get(i);
			
			if(ant.x == x && ant.y == y) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
	public static final void set(int x, int y) {
		
		Main.map[x][y] = 1;
		
	}
	
	public static final void remove(int x, int y) {
		
		Main.map[x][y] = 0;
		
	}
	
	public static final void clearMap() {
		
		for(int x = 0; x < Main.mapSize; x ++) {
			
			for(int y = 0; y < Main.mapSize; y ++) {
				
				Main.map[x][y] = 0;
				
			}
			
		}
		
		Main.ants.clear();
		Main.playing = false;
		
	}
	
	public static final int round(int num) {
		
		return (int) Math.floor(num / Main.tileSize) * Main.tileSize;
		
	}
	
	public static void click(int button) {
		int x = Main.mousePos.x / Main.tileSize;
		int y = Main.mousePos.y / Main.tileSize;
		if(button == 1) {
			if(Main.placeMode == PlaceMode.NORMAL) {
				Func.set(x, y);
			} else {
				if(!Func.antAt(x, y)) {
					Main.ants.add(new Ant(x, y));
				}
			}
		} else if(button == 3) {
			if(Main.placeMode == PlaceMode.NORMAL) {
				Func.remove(x, y);
			} else {
				for(int i = 0; i < Main.ants.size(); i ++) {
					if(Main.ants.get(i).x == x && Main.ants.get(i).y == y) {
						Main.ants.remove(i);
					}
				}
			}
		}
	}
	
	public static void saveMap() {
		JTextField input = new JTextField("Enter file name here");
		int res = JOptionPane.showConfirmDialog(null, input, "Save", JOptionPane.OK_CANCEL_OPTION);
		if(res == JOptionPane.OK_OPTION) {
			String text = input.getText().trim().replaceAll(" ", "");
			
			if(text == "") {
				new Error("Enter a name!", "Error!");
				saveMap();
			}
			if(!text.endsWith(Main.end)) {
				text += Main.end;
			}
			
			File file = new File(Main.folder + text);
			
			try {
				FileWriter writer = new FileWriter(file);
				writer.write("[START]\n");
				writer.write("[FILE '" + input.getText().trim() + "']\n");
				writer.write(Main.tileSize + "\n");
				writer.write(Main.mapSize + "\n");
				for(int y = 0; y < Main.mapSize; y ++) {
					for(int x = 0; x < Main.mapSize; x ++) {
						writer.write(Main.map[x][y] + "\t");
					}
					writer.write("\n");
				}
				writer.write(Main.ants.size() + "\n");
				for(int i = 0; i < Main.ants.size(); i ++) {
					Ant ant = Main.ants.get(i);
					writer.write(ant.x + "\t" + ant.y + "\t" + ant.dir + "\n");
				}
				writer.write("[END]");
				writer.close();
			} catch(Exception e) {
				new Error(e.getMessage(), "Error");
			}
		}
	}
	
	public static void loadMap() {
		File[] files = Main.fFolder.listFiles();
		ArrayList<String> add = new ArrayList<String>();
		for(int i = 0; i < files.length; i ++) {
			File f = files[i];
			if(f.getName().endsWith(Main.end)) {
				add.add(f.getName().replaceAll(Main.end, ""));
			}
		}
		Object[] array = add.toArray();
		String[] finish = Arrays.copyOf(array, array.length, String[].class);
		JComboBox<String> comboBox = new JComboBox<String>(finish);
		
		int res = JOptionPane.showConfirmDialog(null, comboBox, "Load", JOptionPane.OK_CANCEL_OPTION);
		if(res == JOptionPane.OK_OPTION && comboBox.getSelectedItem() != null) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(new File(Main.folder + comboBox.getSelectedItem() + Main.end)));
				String start = reader.readLine();
				System.out.println(start);
				reader.readLine();
				int tileSize = Integer.parseInt(reader.readLine());
				int mapSize = Integer.parseInt(reader.readLine());
				if(tileSize != Main.tileSize) {
					Func.setSize(tileSize, mapSize);
				}
				System.out.println("Might have finished, but most likely not.");
				int[][] map = new int[Main.mapSize][Main.mapSize];
				for(int y = 0; y < mapSize; y ++) {
					String[] yPos = reader.readLine().split("\t");
					for(int i = 0; i < mapSize; i ++) {
						map[i][y] = Integer.parseInt(yPos[i]);
					}
				}
				int ants = Integer.parseInt(reader.readLine());
				ArrayList<Ant> antList = new ArrayList<Ant>();
				for(int i = 0; i < ants; i ++) {
					String[] params = reader.readLine().trim().split("\t");
					Ant ant = new Ant(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
					ant.dir = Integer.parseInt(params[2]);
					antList.add(ant);
				}
				Main.ants = antList;
				System.out.println(reader.readLine());
				Main.map = map;
				reader.close();
			} catch(Exception e) {
				new Error(e.getMessage(), "Error loading file.");
				e.printStackTrace();
			}
		}
	}
	
	public static void togglePause() {
		Main.playing = !Main.playing;
	}
	
	public static void setSize(int inputNum, int mapSize) {
		JTextField input = new JTextField(10 + "");
		if(inputNum == -1)
			JOptionPane.showMessageDialog(Main.frame, input, "Set Size (2-20)", JOptionPane.DEFAULT_OPTION);
		int num = inputNum;
		try {
			if(inputNum == -1)
				num = Integer.parseInt(input.getText().trim().replaceAll(" ", ""));
			if(num > 20)
				num = 20;
			if(num < 2)
				num = 2;
			System.out.println("TileSize to: " + num);
			Main.tileSize = num;
			if(mapSize == -1)
				calcMap();
			else
				calcMap(mapSize);
			Prog.init();
			Main.aut.frame();
		} catch(Exception e1) {
			new Error("Error 'can't parse letters to numbers'\n\n" + e1.getMessage(), "Enters numbers not letters");
			e1.printStackTrace();
		}
	}
	
	public static void calcMap(int mapSize) {
		Main.mapSize = mapSize;
		Main.map = new int[Main.mapSize][Main.mapSize];
	}
	
	public static void calcMap() {
		calcMap(round((int) (Main.screen.height - 100) / Main.tileSize));
	}
	
	public static void togglePlaceMode() {
		if(Main.placeMode == PlaceMode.NORMAL) {
			Main.placeMode = PlaceMode.ANTS;
		} else {
			Main.placeMode = PlaceMode.NORMAL;
		}
	}
	
	public static void setSpeed() {
		JTextField input = new JTextField(Main.fps + "");
		JOptionPane.showMessageDialog(Main.frame, input, "Set Speed (1-1000)", JOptionPane.DEFAULT_OPTION);
		int num = 0;
		try {
			num = Integer.parseInt(input.getText().trim().replaceAll(" ", ""));
			if(num > 1000)
				num = 1000;
			if(num < 1)
				num = 1;
			Main.fps = num;
		} catch(Exception e1) {
			new Error("Error 'can't parse letters to numbers'\n\n" + e1.getMessage(), "Enters numbers not letters");
		}
	}
	
	public static void clear() {
		Func.clearMap();
	}
	
}