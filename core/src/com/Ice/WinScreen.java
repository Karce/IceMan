package com.Ice;

public class WinScreen extends Screen{
	public WinScreen() {
		
	}

	@Override
	public void render() {
		batch.begin();
		
		batch.draw(Data.win, 0, 0);
		
		batch.end();		
	}
	
	public void tick(Input input) {
		
	}
}
