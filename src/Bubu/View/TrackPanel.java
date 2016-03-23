package Bubu.View;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.JPanel;

import Bubu.Constants.Constants;
import Bubu.Entity.Bubu;
import Bubu.Entity.Point;
import Bubu.Entity.Wall;;

public class TrackPanel extends JPanel {
	Bubu bubu;
	Point anchor;
	Vector<Wall> walls;

	public TrackPanel() {
		super();

		walls = new Vector<>();
		initMap();
		// TODO Auto-generated constructor stub
		bubu = new Bubu(new Point(0, 0));
		anchor = new Point(Constants.WINDOW_WIDTH / 3, Constants.WINDOW_HEIGHT);
		
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

		bubu.draw(g, anchor);
		for (Wall wall : walls) {
			wall.draw(g, anchor);
		}
	}

	public void initMap() {
		walls.add(new Wall(new Point(-60, 0), new Point(-60, 220)));
		walls.add(new Wall(new Point(-60, 220), new Point(180, 220)));
		walls.add(new Wall(new Point(180, 220), new Point(180, 370)));

		walls.add(new Wall(new Point(60, 0), new Point(60, 100)));
		walls.add(new Wall(new Point(60, 100), new Point(300, 100)));
		walls.add(new Wall(new Point(300, 100), new Point(300, 370)));

	}

}
