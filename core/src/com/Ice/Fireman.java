package com.Ice;

public class Fireman extends Entity{
	
	public int frame;				// Step frame's offset
	public int currentFrame;		//GameScreen's current Frame
	
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
	
	public boolean dead;
	
	public Fireman(Level lvl, int x, int y) {
		this.x = x;
		this.y = y;
		this.lvl = lvl;
		
		width = 19;
		height = 39;
		frame = 0;
		dead = false;
		
		hp = 2;
	}
	
	public void render(Screen g) {
		int stepFrame = frame / 3 % 3;
		if (!GameScreen.paused)
			currentFrame = ((GameScreen)g).frameCounter;
		
		if (!reverse) {
			if(onGround) {
				g.batch.draw(Data.player[stepFrame][2], x, y);
			}
			else if(currentFrame - jumpFrame < 10){
				g.batch.draw(Data.player[3][2], x, y);
			}
			else if(currentFrame - jumpFrame < 30){
				g.batch.draw(Data.player[4][2], x, y);
			}
			else
				g.batch.draw(Data.player[3][2], x, y);
		}
		else {
			if(onGround) {
				g.batch.draw(Data.player[4 - stepFrame][3], x, y);
			}
			else if(currentFrame - jumpFrame < 10){
				g.batch.draw(Data.player[1][3], x, y);
			}
			else if(currentFrame - jumpFrame < 30){
				g.batch.draw(Data.player[0][3], x, y);
			}
			else
				g.batch.draw(Data.player[1][3], x, y);
		}
		if (hp == 0) {
			dead = true;
		}
	}
	
	
	//Alters xa and ya that are placeholders to then use tryMove to decide if you can move there.
	public void tick () {
		xa = 0;
		ya = 0;
		walk = false;

		if(lvl.man.x > x) {
			moveRight();
		}
		else if (lvl.man.x < x) {
			moveLeft();
		}
		if(lvl.man.y > y) {
			jump();
		}
		
		for(Icespike i: lvl.iceSpikes) {
			if(Math.abs(i.x - x) < 20 && Math.abs(i.y - y) < 40) {
				if((int)(((currentFrame - lastHit) / 60.0) * 1000.0) > 1000) {
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
					hitx = i.x;
					hity = i.y;
					hp--;
					lastHit = currentFrame;
					hit = true;
				}i.alive = false;
			}
		}
		
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
	}
	
	public void moveLeft() {
		xa--;
		walk = true;
		reverse = true;

	}
	
	public void moveRight() {
		xa++;
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
}
