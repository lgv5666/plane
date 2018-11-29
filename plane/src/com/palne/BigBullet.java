package com.palne;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class BigBullet extends Bullet {
	
	private static Image image = Game.load("images/bulletU.png");
	

	public BigBullet(int x, int y, Game game) {
		super(x, y, game);
		
		width = 11;
		height = 40;
		
		this.speed = 6;
		this.y -= 55;
	}

	@Override
	public void move() {
		super.move();
			y -= speed;
			
		
	}
	
	@Override
	public void draw(Graphics g) {

		g.drawImage(image, x, y, null);

	}
	
	@Override
	public void action(Graphics g) {
		super.action(g);
		collideWithAirplane();
	
	}
	

	
	private void collideWithAirplane(){
		ArrayList<FlyingObject> fo = game.collideWith(this);
		
		for (FlyingObject flyingObject : fo) {
			if(flyingObject instanceof Airplane){
				game.add(new Explode(x, y, game));
				this.remove();
				flyingObject.remove();
				
			}
		}
		
	}

}
