package com.cjburkey.langton.ants;

import com.cjburkey.langton.ants.var.Func;
import com.cjburkey.langton.ants.var.Main;

public class Run {
	
	public static void main(String[] args) {
		
		Func.init();
		Func.setSize();
		Main.aut.frame();
		
	}
	
}