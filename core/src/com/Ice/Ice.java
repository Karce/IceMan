package com.Ice;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Ice implements ApplicationListener {
	private Screen screen;
	
	public static int GAME_WIDTH = 640;
	public static int GAME_HEIGHT = 360;
	
	private final Input input = new Input();
	
	@Override
	public void create() {
		Data.load();
		Gdx.input.setInputProcessor(input);
		screen = new TitleScreen();
		
		setScreen(screen);
		//texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		//texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		//sprite = new Sprite(region);
		//sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		//sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		//sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
	}

	@Override
	public void dispose() {
		Data.dispose();
	}

	@Override
	public void render() {
		screen.tick(input);
		input.tick();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		screen.render();
	}
	public void setScreen(Screen newScreen) {
		if (screen != null) screen.removed();
		newScreen.switchMusic(screen.currentMusic, 1.0f);
		screen = newScreen;
		if (screen != null) {
			if (screen instanceof GameScreen)
				((GameScreen)screen).init2();
			screen.init(this);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
