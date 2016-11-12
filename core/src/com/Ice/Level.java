package com.Ice;

import java.util.ArrayList;

public class Level {

	public Player man;
	
	Platform[] plats;
	int num = 18; //number of platforms
	
	public Platform[][] grid; //The grid system
	public int xcords;			//This gonna be 37
	public int ycords;			//This gonna be 20
	public int width, height;			//The amount of pixel platforms are there 32, 18
	
	public GameScreen g;
	
	public ArrayList<Fireman> fireMen;
	public ArrayList<Icespike> iceSpikes;
	public Boss bigBoss;
	
	int levelNumber;
	
	
	public Level(GameScreen g, int levelNumber) {
		this.g = g;
		this.levelNumber = levelNumber;
		man = new Player(this, 100, 20);
		fireMen = new ArrayList<Fireman>();
		iceSpikes = new ArrayList<Icespike>();
		
		xcords = Ice.GAME_WIDTH/20;
		ycords = Ice.GAME_HEIGHT/20;
		
		grid = new Platform[xcords][ycords];
		popGrid(levelNumber);
		width = 32;
		height = 20;
		this.man.init(this);
	}
	
	public void render() {
		g.batch.disableBlending();
		for(int x = 0; x < grid.length; x++) {
			for(int y = 0; y < grid[x].length; y++) {
				if(grid[x][y] != null)
					grid[x][y].render(g); 
			}
		}
		g.batch.enableBlending();
		for(Fireman i: fireMen) {
			if(i.dead == true) {
				fireMen.remove(i);
				break;
			}
			else
				i.render(g);
		}
		man.render(g);
		if(!iceSpikes.isEmpty()) {
			for(Icespike i: iceSpikes) {
				if(i.dispose == true) {
					iceSpikes.remove(i);
					break;
				}
				else
					i.render(g);
			}
		}
		if(bigBoss != null)
			if(!bigBoss.dead)
				bigBoss.render(g);
		
		if(levelNumber == 9) {
			if(bigBoss != null) {
				if(bigBoss.dead) {
					grid[31][14] = null;
					grid[31][13] = null;
					grid[31][12] = null;
				}
			}	
		}
	}
	
	public void tick(Input input) {
		man.tick(input);
		for(Fireman i: fireMen) {
			i.tick();
		}
		for(Icespike i: iceSpikes) {
			i.tick();
		}
		if(bigBoss != null)
			if(!bigBoss.dead)
				bigBoss.tick();
	}
	
	//Populate the grid with platforms/wall, will use a pixmap to do the population
	public void popGrid(int LevelNumber) {
		for(int x = 0; x < (xcords); x++) {
			for(int y = 0; y < (ycords); y++) {
				int col = (Data.levels[LevelNumber].getPixel(x, 17 - y) & 0xffffff00) >>> 8; //The color of the pixel
		
				if(col == 0x2128FF)
					grid[x][y] = new Platform(0, (x * 20), (y * 20));
				else if(col == 0xFF93F0)
					grid[x][y] = new Platform(1, (x * 20), (y * 20));
				else if(col == 0xFF265C)
					grid[x][y] = new Platform(2, (x * 20), (y * 20));
				else if(col == 0xFFB400)
					grid[x][y] = new Platform(3, (x * 20), (y * 20));
				else if(col == 0x000000)
					grid[x][y] = new Platform(5, (x * 20), (y * 20));
				else if(col == 0xFF4800)
					grid[x][y] = new Platform(4, (x * 20), (y * 20));
				else if(col == 0xFFEA00) {
					fireMen.add(new Fireman(this, x * 20, y * 20));
				}
				else if(col == 0xFF0096) {
					bigBoss = new Boss(this, x * 20, y * 20);
				}
					
			}
		}
			
				
	}
	
	//ee is the entity trying to move, xc is the x cord of where to move, yc is the y cord to moveto
	//working
	public boolean isFree (Entity ee, double xc, double yc) {
		int x0 = (int)(xc / 20);
		int y0 = (int)(yc / 20);
		int x1 = (int)((xc + ee.width) / 20);
		int y1 = (int)((yc + ee.height) / 20);
		boolean ok = true;
		for (int x = x0; x <= x1; x++)
			for (int y = y0; y <= y1; y++) {
				if (x >= 0 && y >= 0 && x < width && y < height) {
					if (grid[x][y] != null) ok = false;
				}
			}

		return ok;
	}
	
	//the transition between screens
	//passes an int that indicates the newLevel DIFFERENCE between the old and new level in Data.levels[]
	//-1 means go back one level
	//1 means continue one level
	public void transition (int newLevel) {
		g.transition(newLevel);
	}
	public void gameOver() {
		g.gameOver();
	}
	
	//x and y coords of the new Icespike and direction
	public void playerShoot(int x, int y, boolean left) {
		iceSpikes.add(new Icespike(this, x, y, left));
	}
}
