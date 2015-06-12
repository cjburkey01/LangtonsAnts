package com.cjburkey.langton.ants;

import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import com.cjburkey.langton.ants.obj.Ant;
import com.cjburkey.langton.ants.var.Func;
import com.cjburkey.langton.ants.var.Main;
import com.cjburkey.langton.ants.var.Prog;

public class Aut {
	
	public void frame() {
		
		try {
			
			Prog.url = new File("img/icon.png");
			Prog.img = Main.tools.createImage(Prog.url.toURI().toURL().toExternalForm());
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			
		}
		
		Main.frame.getContentPane().removeAll();
		Prog.tools.getContentPane().removeAll();
		
		for(int i = 0; i < Main.frame.getListeners(KeyListener.class).length; i ++) {
			KeyListener listener = Main.frame.getListeners(KeyListener.class)[i];
			Main.frame.removeKeyListener(listener);
		}
		
		for(int i = 0; i < Prog.tools.getListeners(KeyListener.class).length; i ++) {
			KeyListener listener = Prog.tools.getListeners(KeyListener.class)[i];
			Prog.tools.removeKeyListener(listener);
		}
		
		Prog.menuBar.add(Prog.ams);
		
		Main.frame.setIconImage(Prog.img);
		Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main.frame.setResizable(false);
		Main.frame.add(Prog.drawPane);
		Main.frame.setJMenuBar(Prog.menuBar);
		Main.frame.pack();
		Main.frame.setLocationRelativeTo(null);
		Main.frame.setTitle("Langton's Ant by CJ Burkey");
		Main.frame.setVisible(true);
		Main.frame.setFocusable(true);
		Main.frame.requestFocus();
		
		Prog.tools.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Prog.tools.setResizable(false);
		Prog.tools.setLayout(new BoxLayout(Prog.tools.getContentPane(), BoxLayout.Y_AXIS));
		Prog.tools.add(Prog.speed);
		Prog.tools.add(Prog.tick);
		Prog.tools.add(Prog.play);
		Prog.tools.add(Prog.pause);
		Prog.tools.add(Box.createVerticalStrut(25));
		Prog.tools.add(Prog.clear);
		Prog.tools.add(Prog.save);
		Prog.tools.add(Prog.load);
		Prog.tools.add(Prog.folder);
		Prog.tools.add(Box.createVerticalStrut(25));
		Prog.tools.add(Prog.dragMode);
		Prog.tools.add(Prog.circle);
		Prog.tools.add(Prog.grid);
		Prog.tools.add(Box.createVerticalStrut(25));
		Prog.tools.add(Prog.placeMode);
		Prog.tools.add(Prog.random);
		Prog.tools.add(Prog.center);
		Prog.tools.add(Box.createVerticalStrut(25));
		Prog.tools.add(Prog.size);
		Prog.tools.pack();
		Prog.tools.setLocationRelativeTo(null);
		Prog.tools.setLocation(0, Prog.tools.getLocation().y);
		Prog.tools.setSize(Prog.tools.getWidth() + 75, Prog.tools.getHeight());
		Prog.tools.setFocusable(false);
		Prog.tools.setFocusableWindowState(false);
		Prog.tools.setTitle("Langton's Ant Tools");
		Prog.tools.setVisible(true);
		
		Func.addListeners();
		
	}
	
	public void tick(boolean force) {
		
		if(Main.playing || force) {
			
			Main.cycles ++;
			
			for(int i = 0; i < Main.ants.size(); i ++) {
				
				Ant ant = Main.ants.get(i);
				int currentTile = -1;
				
				if(ant.x < Main.mapSize && ant.x > 0 && ant.y < Main.mapSize && ant.y > 0)
					currentTile = Main.map[ant.x][ant.y];
				else
					Main.ants.remove(i);
				
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
					
					Main.map[ant.x][ant.y] = (currentTile == 1) ? 0 : 1;
					ant.move();
					
				}
				
			}
			
			if(Main.ants.size() == 0) {
				
				Main.playing = false;
				
			}
			
		}
		
	}
	
	public void render() {
		
		if(Main.playing) {
			
			Prog.pause.setEnabled(true);
			Prog.play.setEnabled(false);
			Prog.tick.setEnabled(false);
			
		} else {
			
			Prog.pause.setEnabled(false);
			
			if(Main.ants.size() > 0) {
				Prog.play.setEnabled(true);
				Prog.tick.setEnabled(true);
			} else {
				Prog.play.setEnabled(false);
				Prog.tick.setEnabled(false);
			}
			
		}
		
		int blacks = 0, whites = 0;
		
		for(int x = 0; x < Main.mapSize; x ++) {
			
			for(int y = 0; y < Main.mapSize; y ++) {
				
				if(Main.map[x][y] == 1) {
					blacks ++;
				}
				
			}
			
		}
		
		Prog.placeMode.setText("<html>PlaceMode: " + Main.placeMode + " <span color='blue'>(P)</html>");
		Prog.dragMode.setText("<html>AllowDrag: " + Main.drag + "</html>");
		Prog.grid.setText("<html>Grid: " + Main.grid + "</html>");
		Prog.circle.setText("<html>CursorCircle: " + Main.circle + "</html>");
		
		whites = (int) Math.pow(Main.mapSize, 2) - blacks;
		
		Prog.ams.setText("WhiteCount: " + whites + "\t|\tBlackCount: " + blacks + "\t|\tAntCount: " + Main.ants.size() + "\t|\tSpeed: " + Main.fps + "\t|\tCycles: " + Main.cycles);
		
		Prog.drawPane.repaint();
		
	}
	
}