package com.palne;


public class Bullet extends FlyingObject {
	
	protected int speed = 12;

	public Bullet(int x, int y, Game game) {
		super(x, y, game);
		
	}
	
	@Override
	public void move() {
		if(y<0 || y>Game.HEIGHT){
			this.remove();
		}
	}
	
	
}
