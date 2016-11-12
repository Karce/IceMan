package com.Ice;

public class Entity {
	public boolean onGround;
	public boolean canMoveLeft;
	public boolean canMoveRight;
	
	public int x;			//X coord
	public int y;			//Y coord
	public int width;		
	public int height;
	
	public int hp; //Current Health
	
	public double xa, ya;	//The location that could be moved to if Trymove returns true
	
	public Level lvl;

	public void init(Level lvl) {
		this.lvl = lvl;
		onGround = true;
	}
	
	//xa is the changed x value to determine where to go.
	public void tryMove (double xa, double ya) {
		onGround = false;
		canMoveLeft = true;
		canMoveRight = true;
		
		if (lvl.isFree(this, x + xa, y)) {
			x += xa;
		} else {
			//if going left
			if (xa < 0) {
				canMoveLeft = false;
				double xx = x / 20;
				xa = -(xx - (int)xx) * 20;
			} else {
				canMoveRight = false;
				double xx = (x + width) / 20;
				xa = 20 - (xx - (int)xx) * 20;
			}
			if (lvl.isFree(this, x + xa, y)) {
				x += xa;
			}
		}
		if (lvl.isFree(this, x, y + ya)) {
			y += ya;
		} else {
			if (ya < 0) onGround = true;
			if (ya > 0) {
				double yy = y / 20;
				ya = -(yy - (int)yy) * 20;
			} else {
				double yy = (y + height) / 20;
				ya = (yy - (int)yy) * 20;
			}
			if (lvl.isFree(this, x, y + ya)) {
				y += ya;
			}
		}
	}
	
	//used just for icespikes, hit detection
	public boolean hit(double xa, double ya) {
		
		if (lvl.isFree(this, x + xa, y)) {
			x += xa;
		} else {
			return true;
		}
		if (lvl.isFree(this, x, y + ya)) {
			y += ya;
		} else {
			return true;
		}
		return false;
	}
}
