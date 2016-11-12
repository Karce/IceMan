package com.Ice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;

public class GameScreen extends Screen{
	Level lvl;
	int currentLevel;
	public int frameCounter;			// Current Frame
	private int frameAtLastMPush = 0;	//Delay for Mute Button
	private int frameAtLastEPush = 0;	//Delay for Enter Button
	private boolean tutorial;			//Enabler for tutorial picture
	public static boolean paused = false;
	public static boolean leapControls = false;
	public boolean blueBG; //color of background, 1 = dark blue, 2 = dark red
	
	public GameScreen(){
		currentLevel = 0;
		lvl = new Level(this, currentLevel);
		frameCounter = 0;
		tutorial = false;
		blueBG = true;
	}
	
	//tut is tutorial on/off
	public GameScreen(boolean tut){
		currentLevel = 0;
		lvl = new Level(this, currentLevel);
		frameCounter = 0;
		tutorial = tut;
		paused = tut;
		blueBG = true;
	}
	
	public final void init2 () {
		//this is just testing easy music switching
		switchMusic(Data.loungin, 1.0f);
	}
	
	@Override
	public void render() {
		if(blueBG)
			Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		else
			Gdx.gl.glClearColor(0.3f, 0, 0, 1);
		batch.begin();
		
		lvl.render();

		if(tutorial) {
			batch.draw(Data.tut, TitleScreen.CENTER_X - (Data.tut.getWidth()/2), TitleScreen.CENTER_Y - (Data.tut.getHeight()/2));
		}
		else if(paused){
			batch.draw(Data.paused, TitleScreen.CENTER_X - (Data.paused.getWidth()/2), TitleScreen.CENTER_Y - (Data.paused.getHeight()/2));
			/* Leap controls display
			if(!leapControls) {
				batch.draw(Data.control1, (TitleScreen.CENTER_X + (Data.paused.getWidth()/2) - 10), TitleScreen.CENTER_Y - (Data.control1.getHeight()/2) - 20);
			}
			else
				batch.draw(Data.control2, (TitleScreen.CENTER_X + (Data.paused.getWidth()/2) - 10), TitleScreen.CENTER_Y - (Data.control2.getHeight()/2) - 20);
			*/
		}
		if(Gdx.app.getType() == ApplicationType.Android) {
			batch.draw(Data.arrows, 0, 0);
			batch.draw(Data.shoot, Ice.GAME_WIDTH - 60, 0);
			batch.draw(Data.smallPause, TitleScreen.CENTER_X - 20, Ice.GAME_HEIGHT - 42);
		}
		frameCounter++;	
		
		batch.end();
		
	}
	
	@Override
	public void tick(Input input) {
		if (!paused)
			lvl.tick(input);
		mute(input);
		if(tutorial && input.buttons[Input.ENTER] && frameCounter > 120) {
			tutorial = false;
			frameAtLastEPush = frameCounter;
		}
		if(!tutorial && (input.buttons[Input.ENTER] && delay(frameAtLastEPush) > 300)) {
			paused = !paused;
			frameAtLastEPush = frameCounter;
		}
		//Leap or normal controls
		/*
		if(paused) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			if(x > 419 && x < 535 && y > 189 && y < 210 && Gdx.input.isTouched()) {
				leapControls = false;
			}
			if(x > 419 && x < 541 && y > 238 && y < 255 && Gdx.input.isTouched()) {
				leapControls = true;
			}
		}
		*/
			
	}
	
	//Mutes the music by pressing m
	//Will mute every half a second the m key is pushed down(Based on frames)
	public void mute(Input input) {
		if(input.buttons[Input.MUTE]) {
			if(currentMusic != null && delay(frameAtLastMPush) > 500) {
				if(currentMusic.isPlaying())
					currentMusic.pause();
				else
					currentMusic.play();
				frameAtLastMPush = frameCounter;
			}
		}
	}
	
	//Puts the player in a new level
	public void transition(int newLevel) {
		currentLevel += newLevel;
		if(currentLevel < Data.levels.length) { //If there are more levels
			Level newlvl = new Level(this, currentLevel);
			lvl.man.lvl = newlvl;
		
			lvl.man.x -= (newLevel * Ice.GAME_WIDTH);
		
			newlvl.man = lvl.man;
			this.lvl = newlvl;
			if(currentLevel > 6)
				blueBG = false;
		}
		else {  //Win the game
			setScreen(new WinScreen());
			
		}
		
	}
	
	//returns the milliseconds sense last button push(delay)
	public int delay(int frameAtLastPush) {
		return (int)(((frameCounter - frameAtLastPush) / 60.0) * 1000.0);
	}
	
	public void gameOver() {
		setScreen(new GameOver());
	}

}
