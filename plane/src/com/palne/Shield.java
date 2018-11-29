package com.palne;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class Shield extends FlyingObject {
	
	
	private Random r = new Random();
	private String[] dir = {"L","R"};
	private String direction = dir[0];
	private int step = 20;
	
	private  Image image = Game.load("images/shield.png");
	private static Image shield = Game.load("images/shieldB.png");
	
	private boolean alive = true;


	public Shield(int x, int y, Game game) {
		super(x, y, game);
		width = 45;
		height = 45;
		
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
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	
	@Override
	public void move() {
		y+=3;
		
		if(y>Game.HEIGHT){
			game.remove(this);
		}
	}
	
	@Override
	public void action(Graphics g) {
		super.action(g);
		randomMove();
		limit();
		
		collideWithHeroPlane();
		if (!alive) {
			collideWithFlyingObject();
		}
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
	
	private void collideWithHeroPlane(){
		ArrayList<FlyingObject> fo = game.collideWith(this);
		for (FlyingObject flyingObject : fo) {
			if(flyingObject instanceof HeroPlane){
				if(alive){
					remove();
				}
				alive = false;
				image = shield;
				HeroPlane p = (HeroPlane)flyingObject;
				width = 85;
				height =95;
				x = p.getX()-5;
				y =p.getY()-10;
			}
		}
		
	}
	
	private void collideWithFlyingObject(){
		ArrayList<FlyingObject>  fo = game.collideWith(this);
		for (int i=0;i<fo.size();i++) {
			if(!(fo.get(i) instanceof Blood) && !(fo.get(i) instanceof Shield) && !(fo.get(i) instanceof HeroPlane) &&!(fo.get(i) instanceof BigBullet)){
				fo.get(i).remove();
				
				if (fo.get(i) instanceof Airplane) {
					game.add(new Explode(fo.get(i).getX(), fo.get(i).getY(), game));
				}
				
				
			}
		}
		
	}
	
	@Override
	public void remove() {
		
		Game.schedule(new TimerTask(){

			@Override
			public void run() {
				Shield.super.remove();
			}
			
		}, 6000);
	}


}
