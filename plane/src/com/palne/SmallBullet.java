package com.palne;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class SmallBullet extends Bullet {
	
	private static Image image = Game.load("images/bulletD.png");


	public SmallBullet(int x, int y, Game game) {
		super(x, y, game);
		width = 9;
		height = 25;
		speed = 10;
		this.y+=30;
	}
	
	@Override
	public void move() {
		super.move();
			y += speed;
	}
	
	@Override
	public void draw(Graphics g) {

		g.drawImage(image, x, y, null);
	}
	
	@Override
	public void action(Graphics g) {
		super.action(g);
		
		collideWithHeroPlane();
	}
	

	
	private void collideWithHeroPlane(){
		ArrayList<FlyingObject> fo = game.collideWith(this);
		for (FlyingObject flyingObject : fo) {
			if(flyingObject instanceof HeroPlane){
				HeroPlane p = (HeroPlane)flyingObject;
				this.remove();
				
				game.add(new Explode(x, y, game));
				int b = p.getBlood();
				p.setBlood(b-=10);
				
				
			}
		}
		
	}

}
