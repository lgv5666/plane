package com.palne;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class Blood extends FlyingObject {
	
	private int blood = 63;
	
	private Random r = new Random();
	private String[] dir = {"L","R"};
	private String direction = dir[0];
	private int step = 20;
	
	private static Image image = Game.load("images/blood.png");

	public Blood(int x, int y, Game game) {
		super(x, y, game);
		this.width = 43;
		this.height = 40;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	
	@Override
	public void move() {
		y+=3;
		randomMove();
		
		if(y>Game.HEIGHT){
			game.remove(this);
		}
		
		limit();
	}

	public int getBlood() {
		return blood;
	}
	
	private void randomMove(){
		if (step==0) {
			direction = dir[r.nextInt(dir.length)];
			step = 25;
		}
		if(direction.equals("L"))  x-=4;
		if(direction.equals("R"))  x+=4;
		step--;
	}
	
	private void limit(){
		if(x<0){
			x = 0;
			direction = "R";
			
		}
		if(x+image.getWidth(null)>Game.WIDTH) {
			x = Game.WIDTH - image.getWidth(null);
			direction = "L";
		}
	}

}
