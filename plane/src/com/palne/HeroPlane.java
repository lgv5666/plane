package com.palne;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;



public class HeroPlane extends Plane {
	
	private int speed = 4;
	
	private int bloodBar = 63;
	
	private Direction d = Direction.STOP;
	private boolean bL=false, bU=false, bR=false, bD = false;
	
	private static Image image = Game.load("images/hero.png");
	

	public HeroPlane(int x, int y, Game game) {
		super(x, y, game);
		width = 60;
		height = 70;
		this.bulletLoad(BigBullet.class,500);
	}
	
	@Override
	public void action(Graphics g) {
		super.action(g);
		collideWithBlood();
		collideWithAirplane();
		
		bdeath();
	}
	
	private void collideWithBlood(){
		ArrayList<FlyingObject> fo = game.collideWith(this);
		for (FlyingObject flyingObject : fo) {
			if(flyingObject instanceof Blood){
				Blood b = (Blood)flyingObject;
				b.remove();
				bloodBar = b.getBlood();
			}
		}
		
	}
	
	private void collideWithAirplane(){
		
		ArrayList<FlyingObject> fo = game.collideWith(this);
		
		for (FlyingObject flyingObject : fo) {
			if(flyingObject instanceof Airplane){

				flyingObject.remove();
				game.add(new Explode(x, y, game));
				
				bloodBar-=45;
				
			}
		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		drawBloodBar(g);
		
		g.drawImage(image, x, y, null);
		
	}
	
	private void drawBloodBar(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawRect(x, y+image.getHeight(null)+3, 63, 10);
		g.setColor(Color.GREEN);
		if(bloodBar<43) g.setColor(Color.ORANGE);
		if(bloodBar<22) g.setColor(Color.RED);
		g.fillRect(x+1, y+image.getHeight(null)+5, bloodBar, 8);
		g.setColor(c);
	}

	@Override
	public void move() {
		switch(d){
			case U: y-=speed; break;
			case D: y+=speed; break;
			case L: x-=speed; break;
			case R: x+=speed; break;
			case LU: x-=speed;y-=speed; break;
			case LD: x-=speed;y+=speed; break;
			case RU: x+=speed;y-=speed; break;
			case RD: x+=speed;y+=speed; break;
			case STOP: ; break;
		}
		
	}

	private void locateDirection() {
		if(bL && !bU && !bR && !bD) d = Direction.L;
		else if(bL && bU && !bR && !bD) d = Direction.LU;
		else if(!bL && bU && !bR && !bD) d = Direction.U;
		else if(!bL && bU && bR && !bD) d = Direction.RU;
		else if(!bL && !bU && bR && !bD) d = Direction.R;
		else if(!bL && !bU && bR && bD) d = Direction.RD;
		else if(!bL && !bU && !bR && bD) d = Direction.D;
		else if(bL && !bU && !bR && bD) d = Direction.LD;
		else if(!bL && !bU && !bR && !bD) d = Direction.STOP;
	}
	
	@Override
	public void KeyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_W: bU = true; break;
			case KeyEvent.VK_S: bD = true; break;
			case KeyEvent.VK_D: bR = true; break;
			case KeyEvent.VK_A: bL = true; break;
		}
		locateDirection();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_W: bU = false; break;
			case KeyEvent.VK_S: bD = false; break;
			case KeyEvent.VK_D: bR = false; break;
			case KeyEvent.VK_A: bL = false; break;
		}
		locateDirection();
	}

	@Override
	public void limitMove() {
		super.limitMove();
		if(y+height>Game.HEIGHT) y = Game.HEIGHT - height;
		if(y<20) y = 20;
	}
	
	@Override
	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}

	public void setBlood(int blood) {
		this.bloodBar = blood;
	}

	public int getBlood() {
		return bloodBar;
	}
	
	private void bdeath(){
		if(bloodBar<0){
			this.remove();
		}
	}

	
}
