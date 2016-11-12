package com.Ice;

import java.util.Random;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public abstract class Screen {
	private final String[] chars = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", ".,!?:;\"'+-=/\\< "};
	protected static Random random = new Random();
	public Ice ice;
	public SpriteBatch batch = new SpriteBatch();
	public OrthographicCamera camera;
	public Music currentMusic;
	
	public void removed () {
		batch.dispose();
	}

	public final void init (Ice ice) {
		this.ice = ice;
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, Ice.GAME_WIDTH, 0, Ice.GAME_HEIGHT, -1, 1);

		batch = new SpriteBatch(100);
		batch.setProjectionMatrix(projection);
		
	}

	protected void setScreen (Screen screen) {
		ice.setScreen(screen);
	}

	public void draw (TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		if (width < 0) width = -width;
		batch.draw(region, x, y, width, region.getRegionHeight());
	}

	public void drawString (String string, int x, int y) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			for (int ys = 0; ys < chars.length; ys++) {
				int xs = chars[ys].indexOf(ch);
				if (xs >= 0) {
					draw(Data.texts[xs][ys + 9], x + i * 6, y);
				}
			}
		}
	}

	public abstract void render ();

	public void tick (Input input) {
	}
	
	public void switchMusic(Music newMusic, float volume) {
		//stop old music
		if(currentMusic != null) {
			currentMusic.stop();
		}
		
		//start new music
		currentMusic = newMusic;
		currentMusic.setLooping(true);
		currentMusic.setVolume(volume);
		currentMusic.play();
	}
	
}
