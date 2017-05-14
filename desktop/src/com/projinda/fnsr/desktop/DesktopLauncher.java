package com.projinda.fnsr.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projinda.fnsr.RandomRhythm;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Random Rhythm";
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new RandomRhythm(), config);
	}
}
