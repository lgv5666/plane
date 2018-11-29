package com.palne;

import java.awt.Graphics;
import java.awt.Image;


public class Explode extends FlyingObject {
	
	private int step = 0;
	
	private boolean init = false;
	
	private static Image[] imgs = {
		Game.load("images/0.gif"),
		Game.load("images/1.gif"),
		Game.load("images/2.gif"),
		Game.load("images/3.gif"),
		Game.load("images/4.gif"),
		Game.load("images/5.gif"),
		Game.load("images/6.gif"),
		Game.load("images/7.gif"),
		Game.load("images/8.gif"),
		Game.load("images/9.gif"),
		Game.load("images/10.gif")
	};

	public Explode(int x, int y, Game game) {
		super(x, y, game);
	}
	
	@Override
	public void draw(Graphics g) {

		if(!init) {
			init(g);
		}
		
		g.drawImage(imgs[step], x-=2, y-=2, null);
			step++;
		
		
		if(step==imgs.length){
			this.remove();
		}
	}
	
	//��ʼ��ͼƬ��ʾ����
	private void init(Graphics g){
		for (int i = 0; i < imgs.length; i++) {
			g.drawImage(imgs[i], -100, -100, null);
		}			
		init = true;
	}

}
