package com.cjburkey.langton.ants.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.cjburkey.langton.ants.obj.Ant;
import com.cjburkey.langton.ants.var.Func;
import com.cjburkey.langton.ants.var.Main;

public class DrawPane extends JPanel {
	
	private static final long serialVersionUID = 6412601985446943509L;
	
	public DrawPane() {
		
		this.setPreferredSize(new Dimension(Func.round(Main.mapSize * Main.tileSize), Func.round(Main.mapSize * Main.tileSize)));
		System.out.println("Set size to: " + this.getPreferredSize());
		
	}
	
	public void paintComponent(Graphics gg) {
		
		Graphics2D g = (Graphics2D) gg;
		
		//Fill tiles
		for(int x = 0; x < Main.mapSize; x ++) {
			
			for(int y = 0; y < Main.mapSize; y ++) {
				
				g.setColor((Main.map[x][y] == 0) ? Color.WHITE : Color.BLACK);
				g.fillRect(x * Main.tileSize, y * Main.tileSize, Main.tileSize, Main.tileSize);
				
			}
			
		}
		
		//Draw ants
		for(int i = 0; i < Main.ants.size(); i ++) {
			
			Ant ant = Main.ants.get(i);
			
			g.setColor(Color.RED);
			g.fillRect((ant.x * Main.tileSize) + Main.tileSize / 3, (ant.y * Main.tileSize) + Main.tileSize / 3, Main.tileSize / 2, Main.tileSize / 2);
			
		}
		
		//Mouse square
		g.setColor(new Color(0, 0, 255, 255 / 2));
		g.fillRect(Func.round(Main.mousePos.x), Func.round(Main.mousePos.y), Main.tileSize, Main.tileSize);
		
		//Mouse circle
		if(Main.circle) {
			
			g.setStroke(new BasicStroke(2));
			g.setColor(Color.BLACK);
			g.drawOval(Main.mousePos.x - 40, Main.mousePos.y - 40, 80, 80);
			
		}
		
		//Draw coords
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 17));
		g.drawString("(" + Main.mousePos.x / Main.tileSize + "," + Main.mousePos.y / Main.tileSize + ")", Main.mousePos.x + 35, Main.mousePos.y + 35);
		
		//Draw grid
		g.setStroke(new BasicStroke(1));
		g.setColor(Color.GRAY);
		if(Main.grid) {
			
			for(int x = 0; x < Main.mapSize; x ++) {
				int xPos = x * Main.tileSize;
				g.drawLine(xPos, 0, xPos, this.getHeight());
			}
			
			for(int y = 0; y < Main.mapSize; y ++) {
				int yPos = y * Main.tileSize;
				g.drawLine(0, yPos, this.getWidth(), yPos);
			}
			
		}
		
	}
	
}