package com.cjburkey.langton.ants;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class DrawPane extends JPanel {
	
	private static final long serialVersionUID = 6412601985446943509L;
	
	public DrawPane() {
		
		this.setPreferredSize(new Dimension(Var.round(Var.mapSize * Var.tileSize), Var.round(Var.mapSize * Var.tileSize)));
		
	}
	
	public void paintComponent(Graphics gg) {
		
		Graphics2D g = (Graphics2D) gg;
		
		for(int x = 0; x < Var.mapSize; x ++) {
			
			for(int y = 0; y < Var.mapSize; y ++) {
				
				g.setColor((Var.map[x][y] == 0) ? Color.WHITE : Color.BLACK);
				g.fillRect(x * Var.tileSize, y * Var.tileSize, Var.tileSize, Var.tileSize);
				
			}
			
		}
		
		for(int i = 0; i < Var.ants.size(); i ++) {
			
			Ant ant = Var.ants.get(i);
			
			g.setColor(Color.RED);
			g.fillRect((ant.x * Var.tileSize) + Var.tileSize / 3, (ant.y * Var.tileSize) + Var.tileSize / 3, Var.tileSize / 2, Var.tileSize / 2);
			
		}
		
		g.setColor(Color.BLUE);
		g.fillRect(Var.round(Var.mousePos.x), Var.round(Var.mousePos.y), Var.tileSize, Var.tileSize);
		
		if(Var.circle) {
			
			g.setStroke(new BasicStroke(2));
			g.setColor(Color.BLACK);
			g.drawOval(Var.mousePos.x - 40, Var.mousePos.y - 40, 80, 80);
			
		}

		g.setStroke(new BasicStroke(1));
		g.setColor(Color.GRAY);
		
		if(Var.grid) {
			
			for(int x = 0; x < Var.mapSize; x ++) {
				int xPos = x * Var.tileSize;
				g.drawLine(xPos, 0, xPos, this.getHeight());
			}
			
			for(int y = 0; y < Var.mapSize; y ++) {
				int yPos = y * Var.tileSize;
				g.drawLine(0, yPos, this.getWidth(), yPos);
			}
			
		}
		
	}
	
}