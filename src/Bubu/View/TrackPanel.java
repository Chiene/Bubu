package Bubu.View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.Iterator;
import javax.swing.JPanel;

import Bubu.Entity.Bubu;
import Bubu.Entity.Point;
import Bubu.Entity.Wall;;

public class TrackPanel extends JPanel {
	Bubu bubu;
	Vector<Wall> walls; 
	Graphics2D graphics2d;

	public TrackPanel() {
		super();
		walls = new Vector<>();
		initMap();
		// TODO Auto-generated constructor stub
		bubu = new Bubu();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				bubu.update();
				repaint();
			}
		}, 0, 50);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		bubu.draw(g);
		for(Wall wall : walls) {
			wall.draw(g);
		}
	}
	
	public void initMap() {
		walls.add(new Wall(new Point(-6, 0),new Point(-6, 22)));
		walls.add(new Wall(new Point(-6, 22),new Point(18,22)));
		walls.add(new Wall(new Point(18, 22),new Point(18,37)));
		
		walls.add(new Wall(new Point(6, 0),new Point(6, 10)));
		walls.add(new Wall(new Point(6, 10),new Point(30,10)));
		walls.add(new Wall(new Point(30, 10),new Point(30,37)));
		
		
	}

}
