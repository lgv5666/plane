package com.palne;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class Game extends Frame{
	
	public static final int WIDTH = 350;
	public static final int HEIGHT = 550;
	
	private Image offScreenImage = null;
	private static Image background = null;
	
	private Thread thread = new Thread(new PaintThread());
	
	private ArrayList<FlyingObject> flies = new ArrayList<FlyingObject>();
	
	private HeroPlane p = new HeroPlane(350/2-40, HEIGHT-80,this);
	
	private static final Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Timer timer = new Timer();
	
	static{
		background = load("images/background.png");
	}

	public static void main(String[] args) {
		new Game().launch();
	}
	
	public static Image load(String path){
		return tk.getImage(Game.class.getClassLoader().getResource(path));
	}
	
	public static void schedule(TimerTask t, long delay, long period){
		timer.schedule(t, delay, period);
	}
	public static void schedule(TimerTask t, long delay){
		timer.schedule(t, delay);
	}
	
	public static void purge(){
		timer.purge();
	}
	
	public void launch(){
		this.setTitle("�ɻ���ս");
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(490, 120);
		this.setResizable(false);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
		this.addKeyListener(new KeyMonitor());
		
		flies.add(p);
		flies.add(new Airplane(new Random().nextInt(Game.WIDTH-30), -30, this));
		flies.add(new Airplane(new Random().nextInt(Game.WIDTH-30), -30, this));
		flies.add(new Airplane(new Random().nextInt(Game.WIDTH-30), -30, this));


		thread.start();
		
		timer.schedule(new Add(), 30000, 50000);
	}
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		
//		g.setColor(Color.GRAY);
//		g.fillRect(0, 0, WIDTH, HEIGHT);//������
		
		g.drawImage(background, 0, 0, null);
		
		for(int i=0;i<flies.size();i++){
			FlyingObject fly = flies.get(i);
			fly.action(g);//�������Ķ���
		}
		
		g.setColor(Color.WHITE);
		g.drawString("������ : "+flies.size(), 10, 40);//����ʾ��Ϣ
		g.setColor(c);
		
	}
	
	public ArrayList<FlyingObject> getList(){
		return flies;
	}
	
	public void remove(FlyingObject fo){
		flies.remove(fo);
	}
	
	public void add(FlyingObject fo){
		flies.add(fo);
	}
	
	public ArrayList<FlyingObject> collideWith(FlyingObject f){
		ArrayList<FlyingObject> flyingObj = new ArrayList<FlyingObject>();
		
		for(int i=0;i<flies.size();i++){
			FlyingObject fly = flies.get(i);
			if (fly.getRect().intersects(f.getRect()) && fly != f ) {
				flyingObj.add(fly);
			}
		}
		
		return flyingObj;
	}
	
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(WIDTH, HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	
	private class PaintThread implements Runnable{
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				Game.this.repaint();
			}
		}
	}

	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			
			for(int i=0;i<flies.size();i++){
				FlyingObject fly = flies.get(i);
				fly.KeyPressed(e);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			for(int i=0;i<flies.size();i++){
				FlyingObject fly = flies.get(i);
				fly.keyReleased(e);
			}
		}
		
	}
	
	private class Add extends TimerTask{

		@Override
		public void run() {
			addBlood();
		}
		
		private void addBlood(){
			flies.add(new Blood(new Random().nextInt(Game.WIDTH-30), -30, Game.this));
	}
		
	}
}
