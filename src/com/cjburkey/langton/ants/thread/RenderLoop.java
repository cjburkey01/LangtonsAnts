package com.cjburkey.langton.ants.thread;

import com.cjburkey.langton.ants.var.Main;

public class RenderLoop implements Runnable {
	
	public void run() {
		
		Main.running = true;
		
		while(Main.running) {
			
			Main.aut.render();
			
		}
		
	}
	
}