package com.cjburkey.langton.ants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.UIManager;

import com.cjburkey.langton.ants.render.Error;
import com.cjburkey.langton.ants.var.Func;
import com.cjburkey.langton.ants.var.Main;
import com.cjburkey.langton.ants.var.Prog;

public class Run {
	
	public static void main(String[] args) {
		
		//try {
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		//} catch(Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception e1) {
				new Error(e1.getMessage(), "An error ocurred!");
				e1.printStackTrace();
			}
		//}

		Prog.init();
		Func.init();
		Func.setSize(-1, -1);
		
		try {
			File folder = new File("stuff/");
			File[] files = folder.listFiles();
			for(int i = 0; i < files.length; i ++) {
				File file = files[i];
				File start = new File("stuff/" + file.getName());
				File finish = new File(Main.folder + file.getName());
				if(!finish.exists())
					Files.copy(start.toPath(), finish.toPath());
			}
		} catch (IOException e) {
			new Error(e.getMessage(), "Couldn't copy file.");
			e.printStackTrace();
		}
		
	}
	
}