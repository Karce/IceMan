package com.Ice;

import com.badlogic.gdx.Gdx;

public class TitleScreen extends Screen {
	
	public static final int CENTER_X = Ice.GAME_WIDTH/2;
	public static final int CENTER_Y = Ice.GAME_HEIGHT/2;
	private String temp = "If this is your first time, Press Enter", temp1 = "Touch anywhere to begin!";
	
	int xPos = CENTER_X - (Data.title.getWidth()/2);
	int yPos = CENTER_Y - (Data.title.getHeight()/2);
	
	public TitleScreen() {
		currentMusic = Data.bossBattle;
		currentMusic.setLooping(true);
		currentMusic.setVolume(1.0f);
		currentMusic.play();

		//Data.menuSelect.loop();
	}
	
	// Characters are 6 wide and 6 high (pixels)
	
	@Override
	public void render () {
		
		batch.begin();

		batch.draw(Data.bg, 0, 0);
		batch.draw(Data.title, xPos, yPos);
		drawString(temp1, CENTER_X - ((temp1.length() * 6)/2), CENTER_Y + 72);
		drawString(temp, CENTER_X - ((temp.length() * 6)/2), yPos - 12);
		
		/*String msg = null;
		if (Gdx.app.getType() == ApplicationType.Android)
			msg = "TOUCH TO START";
		else
			msg = "PRESS X TO START";
		// drawString(msg, 160 - msg.length() * 3, 140 - 3 - (int)Math.abs(Math.sin(2.785484 * 0.1) * 10));
		*/
		batch.end();
	}
	
	@Override
	public void tick (Input input) {
		
		//Normal Start
		if(Gdx.input.isTouched())
			setScreen(new GameScreen(false));
		
		//Tutorials Start
		if(input.buttons[Input.ENTER]) {
			setScreen(new GameScreen(true));
		}
	}
	
}