package com.cjburkey.langton.ants;

import java.awt.Desktop;
import java.awt.Image;
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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Aut {
	
	File url;
	Image img;
	
	JFrame tools = new JFrame();
	
	JMenuBar menuBar = new JMenuBar();
	JLabel ams = new JLabel();

	JButton speed = new JButton("<html>Set Speed</html>");
	JButton play = new JButton("<html>Play</html>");
	JButton pause = new JButton("<html>Pause</html>");
	JButton tick = new JButton("<html>Cycle</html>");
	JButton placeMode = new JButton("place");
	JButton clear = new JButton("<html><span color='red'>Clear</span></html>");
	JButton save = new JButton("<html>Save</html>");
	JButton load = new JButton("<html>Load</html>");
	JButton dragMode = new JButton("drag");
	JButton grid = new JButton("grid");
	JButton circle = new JButton("circle");
	JButton folder = new JButton("<html>Open Folder</html>");
	
	public void frame() {
		
		try {
			
			url = new File("img/icon.png");
			img = Var.tools.createImage(url.toURI().toURL().toExternalForm());
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			
		}
		
		menuBar.add(ams);
		
		Var.frame.setIconImage(img);
		Var.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Var.frame.setResizable(false);
		Var.frame.add(Var.drawPane);
		Var.frame.setJMenuBar(menuBar);
		Var.frame.pack();
		Var.frame.setLocationRelativeTo(null);
		Var.frame.setTitle("Langton's Ant by CJ Burkey");
		Var.frame.setVisible(true);
		Var.frame.setFocusable(true);
		Var.frame.requestFocus();
		
		tools.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		tools.setResizable(false);
		tools.setLayout(new BoxLayout(tools.getContentPane(), BoxLayout.Y_AXIS));
		tools.add(speed);
		tools.add(tick);
		tools.add(play);
		tools.add(pause);
		tools.add(Box.createVerticalStrut(25));
		tools.add(clear);
		tools.add(save);
		tools.add(load);
		tools.add(folder);
		tools.add(Box.createVerticalStrut(25));
		tools.add(dragMode);
		tools.add(circle);
		tools.add(grid);
		tools.add(Box.createVerticalStrut(25));
		tools.add(placeMode);
		tools.pack();
		tools.setLocationRelativeTo(null);
		tools.setLocation(0, tools.getLocation().y);
		tools.setSize(tools.getWidth() + 75, tools.getHeight());
		tools.setFocusable(false);
		tools.setFocusableWindowState(false);
		tools.setTitle("Langton's Ant Tools");
		tools.setVisible(true);
		
		Var.frame.requestFocus();
		
		Var.frame.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) { }
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Var.running = false;
				} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					Var.playing = !Var.playing;
				} else if(e.getKeyCode() == KeyEvent.VK_P) {
					togglePlaceMode();
				} else if(e.getKeyCode() == KeyEvent.VK_S) {
					setSpeed();
				} else if(e.getKeyCode() == KeyEvent.VK_C) {
					clear();
				} else if(e.getKeyCode() == KeyEvent.VK_T) {
					if(tick.isEnabled()) {
						tick(true);
					}
				}
			}
			public void keyReleased(KeyEvent e) { }
		});
		
		Var.drawPane.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if(Var.drag) {
					Point pos = e.getPoint();
					if(pos != null) {
						Var.mousePos = pos;
					}
					click(e.getButton());
				}
			}
			public void mouseMoved(MouseEvent e) {
				Point pos = e.getPoint();
				if(pos != null) {
					Var.mousePos = pos;
				}
			}
		});
		
		Var.drawPane.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if(!Var.drag) {
					Point pos = e.getPoint();
					if(pos != null) {
						Var.mousePos = pos;
					}
					click(e.getButton());
				}
			}
			public void mousePressed(MouseEvent e) { }
			public void mouseReleased(MouseEvent e) { }
			public void mouseEntered(MouseEvent e) { }
			public void mouseExited(MouseEvent e) { }
		});
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Var.playing = true;
			}
		});
		
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Var.playing = false;
			}
		});
		
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Var.clearMap();
			}
		});
		
		speed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSpeed();
			}
		});
		
		tick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick(true);
			}
		});
		
		placeMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				togglePlaceMode();
			}
		});
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveMap();
			}
		});
		
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadMap();
			}
		});
		
		dragMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Var.drag = !Var.drag;
			}
		});
		
		circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Var.circle = !Var.circle;
			}
		});
		
		grid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Var.grid = !Var.grid;
			}
		});
		
		folder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Desktop d = Desktop.getDesktop();
				try {
					d.open(Var.fFolder);
				} catch (IOException e1) {
					new Error("Couldn't open folder\n\n" + e1.getMessage(), "Error");
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void click(int button) {
		int x = Var.mousePos.x / Var.tileSize;
		int y = Var.mousePos.y / Var.tileSize;
		if(button == 1) {
			if(Var.placeMode == PlaceMode.NORMAL) {
				Var.set(x, y);
			} else {
				boolean canPlace = true;
				
				for(int i = 0; i < Var.ants.size(); i ++) {
					Ant ant = Var.ants.get(i);
					if(ant.x == x && ant.y == y) {
						canPlace = false;
					}
				}
				if(canPlace)
					Var.ants.add(new Ant(x, y));
			}
		} else if(button == 3) {
			if(Var.placeMode == PlaceMode.NORMAL) {
				Var.remove(x, y);
			} else {
				for(int i = 0; i < Var.ants.size(); i ++) {
					Ant ant = Var.ants.get(i);
					if(ant.x == x && ant.y == y) {
						Var.ants.remove(i);
					}
				}
			}
		}
	}
	
	public void saveMap() {
		JTextField input = new JTextField("Enter file name here");
		int res = JOptionPane.showConfirmDialog(null, input, "Save", JOptionPane.OK_CANCEL_OPTION);
		if(res == JOptionPane.OK_OPTION) {
			String text = input.getText().trim().replaceAll(" ", "");
			
			if(text == "") {
				new Error("Enter a name!", "Error!");
				saveMap();
			}
			if(!text.endsWith(Var.end)) {
				text += Var.end;
			}
			
			File file = new File(Var.folder + text);
			
			try {
				FileWriter writer = new FileWriter(file);
				writer.write("[START]\n");
				writer.write("[FILE '" + input.getText().trim() + "']\n");
				writer.write(Var.tileSize + "\n");
				writer.write(Var.mapSize + "\n");
				for(int y = 0; y < Var.mapSize; y ++) {
					for(int x = 0; x < Var.mapSize; x ++) {
						writer.write(Var.map[x][y] + "\t");
					}
					writer.write("\n");
				}
				writer.write(Var.ants.size() + "\n");
				for(int i = 0; i < Var.ants.size(); i ++) {
					Ant ant = Var.ants.get(i);
					writer.write(ant.x + "\t" + ant.y + "\t" + ant.dir + "\n");
				}
				writer.write("[END]");
				writer.close();
			} catch(Exception e) {
				new Error(e.getMessage(), "Error");
			}
		}
	}
	
	public void loadMap() {
		File[] files = Var.fFolder.listFiles();
		ArrayList<String> add = new ArrayList<String>();
		for(int i = 0; i < files.length; i ++) {
			File f = files[i];
			if(f.getName().endsWith(Var.end)) {
				add.add(f.getName().replaceAll(Var.end, ""));
			}
		}
		Object[] array = add.toArray();
		String[] finish = Arrays.copyOf(array, array.length, String[].class);
		JComboBox<String> comboBox = new JComboBox<String>(finish);
		
		int res = JOptionPane.showConfirmDialog(null, comboBox, "Load", JOptionPane.OK_CANCEL_OPTION);
		if(res == JOptionPane.OK_OPTION && comboBox.getSelectedItem() != null) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(new File(Var.folder + comboBox.getSelectedItem() + Var.end)));
				String start = reader.readLine();
				System.out.println(start);
				reader.readLine();
				int tileSize = Integer.parseInt(reader.readLine());
				int mapSize = Integer.parseInt(reader.readLine());
				if(tileSize == Var.tileSize) {
					int[][] map = new int[mapSize][mapSize];
					for(int y = 0; y < mapSize; y ++) {
						String[] yPos = reader.readLine().split("\t");
						for(int i = 0; i < mapSize; i ++) {
							map[i][y] = Integer.parseInt(yPos[i]);
							System.out.print(yPos[i]);
						}
						System.out.println();
					}
					int ants = Integer.parseInt(reader.readLine());
					ArrayList<Ant> antList = new ArrayList<Ant>();
					for(int i = 0; i < ants; i ++) {
						String[] params = reader.readLine().trim().split("\t");
						Ant ant = new Ant(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
						ant.dir = Integer.parseInt(params[2]);
						antList.add(ant);
					}
					Var.ants = antList;
					System.out.println(reader.readLine());
					Var.map = map;
				} else {
					new Error("Tilesize not same with file.\n\nRestart and set tileSize to " + tileSize, "Mismatch");
				}
				reader.close();
			} catch(Exception e) {
				new Error(e.getMessage(), "Error loading file.");
				e.printStackTrace();
			}
		}
	}
	
	public void togglePause() {
		Var.playing = !Var.playing;
	}
	
	public void setSize() {
		tools.getContentPane().removeAll();
		tools.dispose();
		Var.frame.getContentPane().removeAll();
		JTextField input = new JTextField(10 + "");
		JOptionPane.showMessageDialog(Var.frame, input, "Set Size (2-20)", JOptionPane.DEFAULT_OPTION);
		int num = 0;
		try {
			num = Integer.parseInt(input.getText().trim().replaceAll(" ", ""));
			if(num > 20)
				num = 20;
			if(num < 2)
				num = 2;
			Var.tileSize = num;
			Var.init();
		} catch(Exception e1) {
			new Error("Error 'can't parse letters to numbers'\n\n" + e1.getMessage(), "Enters numbers not letters");
			e1.printStackTrace();
		}
	}
	
	public void togglePlaceMode() {
		if(Var.placeMode == PlaceMode.NORMAL) {
			Var.placeMode = PlaceMode.ANTS;
		} else {
			Var.placeMode = PlaceMode.NORMAL;
		}
	}
	
	public void setSpeed() {
		JTextField input = new JTextField(Var.fps + "");
		JOptionPane.showMessageDialog(Var.frame, input, "Set Speed (1-1000)", JOptionPane.DEFAULT_OPTION);
		int num = 0;
		try {
			num = Integer.parseInt(input.getText().trim().replaceAll(" ", ""));
			if(num > 1000)
				num = 1000;
			if(num < 1)
				num = 1;
			Var.fps = num;
		} catch(Exception e1) {
			new Error("Error 'can't parse letters to numbers'\n\n" + e1.getMessage(), "Enters numbers not letters");
		}
	}
	
	public void clear() {
		Var.clearMap();
	}
	
	public void tick(boolean force) {
		
		if(Var.playing || force) {
			
			Var.cycles ++;
			
			for(int i = 0; i < Var.ants.size(); i ++) {
				
				Ant ant = Var.ants.get(i);
				int currentTile = -1;
				
				if(ant.x < Var.mapSize && ant.x > 0 && ant.y < Var.mapSize && ant.y > 0)
					currentTile = Var.map[ant.x][ant.y];
				else
					Var.ants.remove(i);
				
				if(currentTile != -1) {
					
					if(currentTile == 0) {
						ant.dir ++;
					} else {
						ant.dir --;
					}
					
					if(ant.dir > 3) {
						ant.dir = 0;
					}
					
					if(ant.dir < 0) {
						ant.dir = 3;
					}
					
					Var.map[ant.x][ant.y] = (currentTile == 1) ? 0 : 1;
					ant.move();
					
				}
				
			}
			
			if(Var.ants.size() == 0) {
				
				Var.playing = false;
				
			}
			
		}
		
	}
	
	public void render() {
		
		if(Var.playing) {
			
			pause.setEnabled(true);
			play.setEnabled(false);
			tick.setEnabled(false);
			
		} else {
			
			pause.setEnabled(false);
			
			if(Var.ants.size() > 0) {
				play.setEnabled(true);
				tick.setEnabled(true);
			} else {
				play.setEnabled(false);
				tick.setEnabled(false);
			}
			
		}
		
		int blacks = 0, whites = 0;
		
		for(int x = 0; x < Var.mapSize; x ++) {
			
			for(int y = 0; y < Var.mapSize; y ++) {
				
				if(Var.map[x][y] == 1) {
					blacks ++;
				}
				
			}
			
		}
		
		placeMode.setText("<html>PlaceMode: " + Var.placeMode + "</html>");
		dragMode.setText("<html>AllowDrag: " + Var.drag + "</html>");
		grid.setText("<html>Grid: " + Var.grid + "</html>");
		circle.setText("<html>CursorCircle: " + Var.circle + "</html>");
		
		whites = (int) Math.pow(Var.mapSize, 2) - blacks;
		
		ams.setText("WhiteCount: " + whites + "\t|\tBlackCount: " + blacks + "\t|\tAntCount: " + Var.ants.size() + "\t|\tSpeed: " + Var.fps + "\t|\tCycles: " + Var.cycles);
		
		Var.drawPane.repaint();
		
	}
	
	static class RenderLoop implements Runnable {
		
		public void run() {
			
			Var.running = true;
			
			while(Var.running) {
				
				Var.aut.render();
				
			}
			
		}
		
	}
	
	static class Loop implements Runnable {
		
		public void run() {
			
			Var.running = true;
			
			while(Var.running) {
				
				Var.aut.tick(false);
				
				try {
					Thread.sleep(1000 / Var.fps);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.exit(0);
			
		}
		
	}
	
}