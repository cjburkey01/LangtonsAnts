package com.cjburkey.langton.ants;

public class Ant {
	
	public int x, y, dir;
	
	//DIRS:
	//0 = NORTH
	//1 = EAST
	//2 = SOUTH
	//3 = WEST
	
	public Ant(int x, int y) {
		
		dir = 0;
		this.x = x;
		this.y = y;
		
	}
	
	public void move() {
		
		if(dir == 0) {
			this.y ++;
		} else if(dir == 1) {
			this.x ++;
		} else if(dir == 2) {
			this.y --;
		} else if(dir == 3) {
			this.x --;
		}
		
	}
	
}