package com.Ice;


import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;

public class Data {
	public static final String FILEPREFIX = "data/Level%s.png";
	public static final int NUMLEVELS = 10;
	
	public static Texture bg;
	public static Texture title;
	public static Texture tut;
	public static Texture paused;
	public static Texture smallPause;
	public static Texture gameOver;
	public static Texture win;
	public static Texture boss;
	public static Texture arrows;
	public static Texture shoot;
	
	public static TextureRegion texts[][];
	public static TextureRegion player[][];
	public static TextureRegion plats[][]; //Platforms
	
	public static Texture control1;
	public static Texture control2;
	
	public static Sound menuSelect;
	public static Sound menuScroll;
	public static Sound menuBack;
	
	public static Music bossBattle;
	public static Music mystical;
	public static Music loungin;

	public static Pixmap[] levels;

	public static void load() {
		levels = new Pixmap[NUMLEVELS];
		
		// Populate levels
		for(int i = 0; i < NUMLEVELS; i++) {
			levels[i] = new Pixmap(Gdx.files.internal(String.format(FILEPREFIX, i)));
		}
		
		
		boss = new Texture(Gdx.files.internal("data/Boss.png"));
		bg = new Texture(Gdx.files.internal("data/bg.png"));
		title = new Texture(Gdx.files.internal("data/title.png"));
		tut = new Texture(Gdx.files.internal("data/Tut Pic.png"));
		paused = new Texture(Gdx.files.internal("data/paused.png"));
		gameOver = new Texture(Gdx.files.internal("data/GameOverScreen.jpg"));
		win = new Texture(Gdx.files.internal("data/win.png"));
		
		control1 = new Texture(Gdx.files.internal("data/keyboard.png"));
		control2 = new Texture(Gdx.files.internal("data/leapControls.png"));
		
		texts = split("data/text.png", 6, 6, false);
		player = split("data/player.png", 23, 46, false);
		plats = split("data/Platforms.png", 20, 20, false);
		menuSelect = Gdx.audio.newSound(Gdx.files.internal("data/menuSelect.ogg"));
		menuScroll = Gdx.audio.newSound(Gdx.files.internal("data/menuScroll.ogg"));
		menuBack = Gdx.audio.newSound(Gdx.files.internal("data/menuBack.ogg"));
		bossBattle = Gdx.audio.newMusic(Gdx.files.internal("data/bossBattle.ogg"));
		mystical = Gdx.audio.newMusic(Gdx.files.internal("data/mystical.ogg"));
		loungin = Gdx.audio.newMusic(Gdx.files.internal("data/loungin.ogg"));
		
		if(Gdx.app.getType() == ApplicationType.Android) {
			arrows = new Texture(Gdx.files.internal("data/arrows4.png"));
			shoot = new Texture(Gdx.files.internal("data/shoot4.png"));
			smallPause = new Texture(Gdx.files.internal("data/pause.png"));
		}
	}
	
	public static void dispose() {
		bg.dispose();
		title.dispose();
	}
	
	private static TextureRegion[][] split (String name, int width, int height, boolean flipX) {
		Texture texture = new Texture(Gdx.files.internal(name));
		int xSlices = texture.getWidth() / width;
		int ySlices = texture.getHeight() / height;
		TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
		for (int x = 0; x < xSlices; x++) {
			for (int y = 0; y < ySlices; y++) {
				res[x][y] = new TextureRegion(texture, x * width, y * height, width, height);
				//res[x][y].flip(flipX, true);
			}
		}
		return res;
	}
}
