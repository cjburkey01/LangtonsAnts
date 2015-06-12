package com.cjburkey.langton.ants.thread;

import com.cjburkey.langton.ants.var.Main;

public class TickLoop implements Runnable {
	
	public void run() {
		
		Main.running = true;
		
		while(Main.running) {
			
			Main.aut.tick(false);
			
			try {
				Thread.sleep(1000 / Main.fps);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.exit(0);
		
	}
	
}