package com.projinda.fnsr.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projinda.fnsr.RhythmTest;

/**
 * Test on drawing, canvas and tests on game Random Rhythm
 * @author FN
 * @version 1.0
 */
public class DesktopLauncherTest {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Random Rhythm";
		config.width = 1024;
		config.height = 768;
		// force exit when tests are done
		config.forceExit = true;
		new LwjglApplication(new RhythmTest(), config);
	}
}
