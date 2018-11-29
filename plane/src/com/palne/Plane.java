package com.palne;

import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.util.TimerTask;

public class Plane extends FlyingObject {
	
	private TimerTask task;

	public Plane(int x, int y, Game game) {
		super(x, y, game);
	}
	
	@Override
	public void action(Graphics g) {
		super.action(g);
		limitMove();
	}
	
	public void bulletLoad(Class c, long shootPeriod){
		task = new ShootBullet(c);
		Game.schedule(task, 1000,shootPeriod);
		
	}
	
	public void limitMove(){
		if(x<0) x = 0;
		if(x+width>Game.WIDTH) x = Game.WIDTH - width;
	}
	
	@Override
	public void remove() {
		super.remove();
		if (task != null) {
			task.cancel();
		}
	}
	
	private class ShootBullet extends TimerTask{
		
		private Class c;
		private Class[] classes = new Class[]{int.class,int.class,Game.class};
		
		public ShootBullet(Class c) {
			this.c= c;
			
		}

		@Override
		public void run() {
			initBullet();
		}
		
		private void initBullet(){
			try {
				Bullet b = (Bullet)c.getConstructor(classes).newInstance(x + (width) / 2, y+height/2, game);
				game.add(b);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			
		}
		
	}

}
