package com.palne;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public abstract class FlyingObject {
	
	protected int x;
	protected int y;
	
	protected int width = 20;
	protected int height = 20;
	
	protected Game game;
	
	public FlyingObject(int x, int y, Game game) {
		super();
		this.x = x;
		this.y = y;
		this.game = game;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void move(){
		
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		g.setColor(c);
	}

	public void KeyPressed(KeyEvent e){
		
	}
	
	public void keyReleased(KeyEvent e){
		
	}

	public Rectangle getRect(){
		return new Rectangle(x, y, width-5, height-5);
	}

	public void action(Graphics g){
		draw(g);
		move();
	}
	
	public void remove(){
		game.remove(this);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	

}
