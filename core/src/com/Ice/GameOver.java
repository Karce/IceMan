package com.Ice;

import com.badlogic.gdx.Gdx;

public class GameOver extends Screen{
	
	public GameOver() {
		
	}
	@Override
	public void render() {
		batch.begin();
		
		batch.draw(Data.gameOver, 0, 0);
		
		batch.end();
	}
	
	public void tick(Input input) {
		if(Gdx.input.isTouched())
			setScreen(new GameScreen(false));
	}

}
