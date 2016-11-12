package com.Ice;

public class Icespike extends Entity{
	boolean left; 	//true if direction is going left
	int aniFrame;	//animation frame
	
	public int frame;				// Step frame's offset
	public int currentFrame;		//GameScreen's current Frame
	public int deathFrame; 			//start of death animation
	
	boolean going = true;
	boolean alive = true;
	boolean dispose = false;
	
	public Icespike(Level lvl, int x, int y, boolean left) {
		this.x = x;
		this.y = y;
		width = 10;
		height = 5;
		
		this.lvl = lvl;
		
		this.left = left;
		deathFrame = 0;
	}
	
	public void render(Screen g) {
		
		int aniFrame = frame / 2 % 2;
		if (!GameScreen.paused)
			currentFrame = ((GameScreen)g).frameCounter;
		
		if(alive) {
			if(!left)
				g.batch.draw(Data.plats[aniFrame][1], x, y);
			else
				g.batch.draw(Data.plats[aniFrame + 2][2], x, y);
		}
		if(!alive) {
			if(going) {
				deathFrame = currentFrame;
				going = false;
			}
			if((int)(((currentFrame - deathFrame) / 60.0) * 1000.0) < 100) {
				if(!left)
					g.batch.draw(Data.plats[2][1], x, y);
				else
					g.batch.draw(Data.plats[1][2], x, y);
			}
			else if((int)(((currentFrame - deathFrame) / 60.0) * 1000.0) < 200) {
				if(!left)
					g.batch.draw(Data.plats[3][1], x, y);
				else
					g.batch.draw(Data.plats[0][2], x, y);
			}
			else
				dispose = true;
		}
	}
	
	public void tick() {
		xa = 0;
		ya = 0;
		
		if(alive) {
			if(!left) {
				xa += 4;
			}
			else
				xa -= 4;
		}
		
		if(going) {
			if(currentFrame % 2 == 0) {
				frame++;
			}
		}	
		else
			frame = 0;
		
		if(currentFrame % 3 == 0) { //less gravity
			ya--;
		}
		
		//tryit!, if not destroyed
		if(alive) {
			if(hit(xa, ya)){
				alive = false;
			}
		}
		
	}
	
}
