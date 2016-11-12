package com.Ice;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
//import com.leapmotion.leap.Controller;
//import com.leapmotion.leap.FingerList;
//import com.leapmotion.leap.Frame;
//import com.leapmotion.leap.Hand;

public class Input implements InputProcessor {
	//Leap Motion Controller controller
	//public Controller controller = new Controller();
	
	//Input keys for the game controls
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	public static final int ESCAPE = 4;

	public static final int LSHIFT = 5;
	public static final int MUTE = 6;
	public static final int ENTER = 7;
	
	public static final int SPACE = 8;

	public boolean[] buttons = new boolean[64];
	public boolean[] oldButtons = new boolean[64];
	
	public void set (int key, boolean down) {
		int button = -1;

		if(key == Keys.DPAD_UP || key == Keys.W) button = UP;
		if(key == Keys.DPAD_LEFT || key == Keys.A) button = LEFT;
		if(key == Keys.DPAD_DOWN || key == Keys.S) button = DOWN;
		if(key == Keys.DPAD_RIGHT || key == Keys.D) button = RIGHT;
		
		if(key == Keys.SHIFT_LEFT) button = LSHIFT;
		if(key == Keys.M) button = MUTE;

		if(key == Keys.ESCAPE || key == Keys.MENU) button = ESCAPE;
		if(key == Keys.ENTER) button = ENTER;
		if(key == Keys.SPACE) button = SPACE;
		if(button >= 0) {
			buttons[button] = down;
		}
	}

	public void tick () {
		//Leap Motion Controls
		/*
		if(GameScreen.leapControls) {
			if(Gdx.app.getType() == ApplicationType.Desktop) {
				if(controller.isConnected()) {
					//most recent frame
					Frame frame = controller.frame();
					if (!frame.hands().isEmpty()) {
						Hand hand = frame.hands().get(0);
						if(hand.isValid()) {
							FingerList fingers = hand.fingers();
							if(fingers.count() > 1) {
								set(Keys.SPACE, true);
							}
							else
								set(Keys.SPACE, false);
							if(hand.palmVelocity().get(1) > 500){
								set(Keys.W, true);
							}
							else {
								set(Keys.W, false);
							}
							if(Math.toDegrees(hand.palmNormal().roll()) < -30) {
								set(Keys.D, true);
								if(Math.toDegrees(hand.palmNormal().roll()) < -60) {
									set(Keys.SHIFT_LEFT, true);
								}
								else {
									set(Keys.SHIFT_LEFT, false);
								}
							}
							else if(Math.toDegrees(hand.palmNormal().roll()) > 30) {
								set(Keys.A, true);
								if(Math.toDegrees(hand.palmNormal().roll()) > 60) {
									set(Keys.SHIFT_LEFT, true);
								}
								else {
									set(Keys.SHIFT_LEFT, false);
								}
							}
							else {
								set(Keys.A, false);
								set(Keys.D, false);
							}
						}
					}
					else
						releaseAllKeys();
				}
			}
		}*/
		
		for (int i = 0; i < buttons.length; i++) {
			oldButtons[i] = buttons[i];
		}
		
		//Android controls
		if(Gdx.app.getType() == ApplicationType.Android) {
			boolean left = false;
			boolean right = false;
			boolean up = false;
			boolean shift = false;
			boolean space = false;
			boolean enter = false;
			
			for(int i = 0; i < 2; i++) {
				//x and y are the scaled version of the coordinates for Iceman
				//this is necessary because a lot of phones have different resolutions
				int x = (int)(Gdx.input.getX(i) / (float)Gdx.graphics.getWidth() * Ice.GAME_WIDTH);
				int y = (360 - (int)(Gdx.input.getY(i) / (float)Gdx.graphics.getHeight() * Ice.GAME_HEIGHT));
				if(!Gdx.input.isTouched(i)) continue;
				if(x < 55 && y < 80) {
					set(Keys.DPAD_LEFT, true);
					left |= true;
				}
				if(x > 75 && x < 130 && y < 80) {
					set(Keys.DPAD_RIGHT, true);
					right |= true;
				}
				if(x < 130 && y > 60 && y < 80) {
					set(Keys.DPAD_UP, true);
					up |= true;
				}
				if((x < 20 || (x > 110 && x < 130)) && y < 80) {
					set(Keys.SHIFT_LEFT, true);
					shift |= true;
				}
				if(x < 640 && x > 640 - 60 && y < 60) {
					set(Keys.SPACE, true);
					space |= true;
				}
				if(x < TitleScreen.CENTER_X + 10 && x > TitleScreen.CENTER_X - 10 && y > Ice.GAME_HEIGHT - 40) {
					set(Keys.ENTER, true);
					enter |= true;
				}
				/*
				if(x > 320 - 64 && x < 320 - 32) {
					set(Keys.Z, true);
					z |= true;
				}
				if(x > 320 - 32 && x < 320) {
					set(Keys.X, true);
					s |= true;
				}*/
			}

			if(left == false) set(Keys.DPAD_LEFT, false);
			if(right == false) set(Keys.DPAD_RIGHT, false);
			if(up == false) set(Keys.DPAD_UP, false);
			if(shift == false) set(Keys.SHIFT_LEFT, false);
			if(space == false) set(Keys.SPACE, false);
			if(enter == false) set(Keys.ENTER, false);
		}
	}

	public void releaseAllKeys () {
		for (int i = 0; i < buttons.length; i++) {
			if(i == UP || i == DOWN) continue;
			buttons[i] = false;
		}
	}

	@Override
	public boolean keyDown (int keycode) {
		if(!GameScreen.leapControls)
			set(keycode, true);
		if(GameScreen.leapControls)
			if(keycode == Keys.ENTER || keycode == Keys.M)
				set(keycode, true);
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		set(keycode, false);
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		/*
		x = (int)(x / (float)Gdx.graphics.getWidth() * 640);
		if(x > 160 - 32 && x < 160) {
			set(Keys.DPAD_UP, !buttons[UP]);
			if(buttons[UP]) buttons[DOWN] = false;
		}
		if(x > 160 && x < 160 + 32) {
			set(Keys.DPAD_DOWN, !buttons[DOWN]);
			if(buttons[DOWN]) buttons[UP] = false;
		}*/
		//System.out.println("buttons: " + buttons[UP] + ", " + buttons[DOWN]);
		return false;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved (int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled (int amount) {
		return false;
	}
}