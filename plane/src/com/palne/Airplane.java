package com.palne;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

public class Airplane extends Plane {
	
	
	private static Image image = Game.load("images/plane.png");

	private Random r = new Random();
	private String[] dir = {"L","R","S"};
	private String direction = dir[0];
	private int step = 20;
	
	

	public Airplane(int x, int y, Game game) {
		super(x, y, game);
		width = 65;
		height = 45;
		this.bulletLoad(SmallBullet.class, 1500);
	}
	
	@Override
	public void draw(Graphics g) {
		
		g.drawImage(image, x, y, null);

	}

	@Override
	public void move() {
			y+=1;
			
	}
	
	@Override
	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}

	@Override
	public void action(Graphics g) {
		super.action(g);
		randomMove();

		if(y>Game.HEIGHT){
			remove();
		}
	}
	
	
	private void randomMove(){
		if (step==0) {
			direction = dir[r.nextInt(dir.length)];
			step = 30;
		}
		if(direction.equals("L"))  x-=2;
		if(direction.equals("R"))  x+=2;
		step--;
	}
	
	@Override
	public void remove() {
		super.remove();
		game.add(new Airplane(new Random().nextInt(Game.WIDTH-30), -50, game));
		
		if(new Random().nextInt(20)>=19){
			game.add(new Shield(x,y,game));
		}
	}

}
