package com.Ice.desktop;

import com.Ice.Ice;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "IceMan";
		cfg.width = 1280;//640;
		cfg.height = 720;//360;
		cfg.addIcon("data/iceicon.png", com.badlogic.gdx.Files.FileType.Internal);
		
		new LwjglApplication(new Ice(), cfg);
	}
}
