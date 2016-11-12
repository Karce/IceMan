package com.Ice;

public class Player extends Entity{

	public int frame;				// Step frame's offset
	public int currentFrame;		//GameScreen's current Frame
	/*
	public boolean stoppedMovingInput = false;	// User has stopped moving input
	public boolean frameMove = true; //The input moved from last frame?
	public boolean frameMove2 = true; //^^
	*/
	
	// Character action positions:
	// Frames
	public int jumpFrame = 0;	// Frame at which IceMan jumps (Relates to y-axis)
	public int moveFrame = 0;	// Frame at which IceMan moves (Relates to x-axis)
	// Positions
	public int movePos = 0;		// Position at which IceMan begins moving
	
	public boolean reverse = false;
	boolean walk = false;
	
	public int lastHit; //frame since last hit
	boolean hit = false;
	int hitx;	//x pos of hit
	int hity;	//y pos of hit
	
	int lastShot;
	
	public Player(Level lvl, int x, int y){
		this.lvl = lvl;
		this.x = x;
		this.y = y;
		frame = 0;
		width = 19;
		height = 39;
		
		hp = 3;
		lastHit = 0;
		lastShot = 0;
	}
	
	public void render(Screen g) {
		
		int stepFrame = frame / 3 % 3;
		if (!GameScreen.paused)
			currentFrame = ((GameScreen)g).frameCounter;
		
		if (!reverse) {
			if(onGround) {
				g.batch.draw(Data.player[stepFrame][0], x, y);
			}
			else if(currentFrame - jumpFrame < 10){
				g.batch.draw(Data.player[3][0], x, y);
			}
			else if(currentFrame - jumpFrame < 30){
				g.batch.draw(Data.player[4][0], x, y);
			}
			else
				g.batch.draw(Data.player[3][0], x, y);
		}
		else {
			if(onGround) {
				g.batch.draw(Data.player[4 - stepFrame][1], x, y);
			}
			else if(currentFrame - jumpFrame < 10){
				g.batch.draw(Data.player[1][1], x, y);
			}
			else if(currentFrame - jumpFrame < 30){
				g.batch.draw(Data.player[0][1], x, y);
			}
			else
				g.batch.draw(Data.player[1][1], x, y);
		}
		
		if(hp == 0)
			lvl.gameOver();
			
		
	}
	
	//Alters xa and ya that are placeholders to then use tryMove to decide if you can move there.
	public void tick (Input input) {
		xa = 0;
		ya = 0;
		walk = false;
		/*
		frameMove2 = frameMove;
		frameMove = false;
		*/
		for(Fireman i: lvl.fireMen) {
			if(Math.abs(i.x - x) < 20 && Math.abs(i.y - y) < 39 && (int)(((currentFrame - lastHit) / 60.0) * 1000.0) > 1500) {
				//knockback and hp drain
				if(i.x > x) {
					xa -= 2;
				}
				if(i.x < x) {
					xa += 2;
				}
				if(i.y > y) {
					ya -= 2;
				}
				if(i.y < y) {
					ya += 2;
				}
				hit = true;
				hitx = i.x;
				hity = i.y;
				hp--;
				lastHit = currentFrame;
			}
		}
		
		//determines when no longer knockbacked
		if((int)(((currentFrame - lastHit) / 60.0) * 1000.0) > 500) {
			hit = false;
		}
		//actual knockback
		if(hit) {
			if(hitx > x) {
				xa -= 2;
			}
			if(hitx < x) {
				xa += 2;
			}
			if(hity > y) {
				ya -= 2;
			}
			if(hity < y) {
				ya += 2;
			}
		}

		if(input.buttons[Input.DOWN]) {
			ya--;
		}
		if(input.buttons[Input.UP]) {jump();}
		if(input.buttons[Input.LEFT]) {moveLeft(input);}
		if(input.buttons[Input.RIGHT]) {moveRight(input);}
		if(input.buttons[Input.SPACE]) {shoot();}
		
		if(walk) {
			if(currentFrame % 2 == 0) {
				frame++;
			}
		}	
		else
			frame = 0;
		
		if(!onGround) {
			if(currentFrame - jumpFrame < 10){
				ya += 5;
				//Jumping momentum for x-axis
				if(xa - movePos > 0)
					xa += 1;
				if(xa - movePos < 0)
					xa -= 1;
			}
			else if(currentFrame - jumpFrame < 20){
				ya += 2;
				//Jumping momentum for x-axis
				if(xa - movePos > 0)
					xa += 1;
				if(xa - movePos < 0)
					xa -= 1;
			}
			else if(currentFrame - jumpFrame < 30){
				//Jumping momentum for x-axis
				if(xa - movePos > 0)
					xa += 1;
				if(xa - movePos < 0)
					xa -= 1;
			}
			else {
				ya -= 2;
				//Jumping momentum for x-axis
				if(xa - movePos > 0)
					xa += 2;
				if(xa - movePos < 0)
					xa -= 2;
			}
		}
		ya--;	//Gravity
		
		tryMove(xa, ya);
		
		//TRANSITION BETWEEN LEVELS when gets to far to the side
		if (x < ((0) - 10))
			lvl.transition(-1);
		if (x > (Ice.GAME_WIDTH) - 10)
			lvl.transition(1);
		if (y < 0)
			lvl.gameOver();
	}
	
	public void moveLeft(Input input) {
		if(input.buttons[Input.LSHIFT])
			xa -= 2;
		else xa--;
		
		walk = true;
		reverse = true;
	}
	
	public void moveRight(Input input) {
		if(input.buttons[Input.LSHIFT])
			xa += 2;
		else xa++;
		
		walk = true;
		reverse = false;
	}
	
	public void jump() {
		if(onGround) {
			onGround = false;
			jumpFrame = currentFrame;
			movePos = (int)xa;
		}
	}
	
	//x, y, and direction of the player
	public void shoot() {			// height/2
		if((int)(((currentFrame - lastShot) / 60.0) * 1000.0) > 150) {
			if(!reverse) {
				lvl.playerShoot(x + 15, y + (40/2), false);
			}
			else
				lvl.playerShoot(x - 5, y + (40/2), true);
			lastShot = currentFrame;
		}
		
	}
	
}
