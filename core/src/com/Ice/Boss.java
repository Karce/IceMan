package com.Ice;

public class Boss extends Entity {
	public int spawnFrame;		//time after spawn fireman
	public int currentFrame;
	public boolean dead;
	public int lastHit;
	
	public Boss(Level lvl, int x, int y) {
		this.lvl = lvl;
		this.x = x;
		this.y = y;
		width = 200;
		height = 220;
		
		spawnFrame = currentFrame;
		dead = false;
		lastHit = 0;
		
		hp = 100;
	}
	
	public void render(Screen g) {
		if (!GameScreen.paused)
			currentFrame = ((GameScreen)g).frameCounter;
		
		g.drawString("" + hp, (Ice.GAME_WIDTH / 2), (Ice.GAME_HEIGHT * 2 / 3));
		g.batch.draw(Data.boss, x, y);
		
		if(hp == 0) {
			dead = true;
		}
	}
	
	public void tick() {
		// spawn firemen
		if((int)(((currentFrame - spawnFrame) / 60.0) * 1000.0) > 1000) {
			lvl.fireMen.add(new Fireman(lvl, x + 100, y + 80));
			spawnFrame = currentFrame;
		}
		
		// take damage
		for(Icespike i: lvl.iceSpikes) {
			if((i.x - x) < 200 && (i.x - x) > 0 && Math.abs(i.y - y) < 220) {
				if((int)(((currentFrame - lastHit) / 60.0) * 1000.0) > 200) {
					hp--;
					lastHit = currentFrame;
				}
				i.alive = false;
			}
		}
	}
}
