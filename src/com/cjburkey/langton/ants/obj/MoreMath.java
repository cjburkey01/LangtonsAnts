package com.cjburkey.langton.ants.obj;

import java.util.Random;

public class MoreMath {

	public static final int randomRange(int min, int max) {
		
		return new Random().nextInt((max - min) + 1) + min;
		
	}
	
}