package com.cjburkey.langton.ants;

import javax.swing.JOptionPane;

public class Error {
	
	public Error(String msg, String title) {
		
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
		
	}
	
}