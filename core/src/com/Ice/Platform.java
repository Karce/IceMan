package com.Ice;

public class Platform {
	public int type;		//The type of platform
	public int x;			//X coord
	public int y;			//Y coord
	public int width;		
	public int height;
	
	public Platform(int type, int x, int y){
		this.type = type;
		this.x = x;
		this.y = y;
		width = 20;
		height = 20;
	}
	
	public void render(Screen g) {
		g.batch.draw(Data.plats[type][0], x, y);
	}
}
