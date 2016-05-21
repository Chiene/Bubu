package Bubu.View;

import java.awt.Graphics;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

import Bubu.Constants.Constants;
import Bubu.Device.GeneticSystem.GeneticSystem;
import Bubu.Device.RBF.RBF;
import Bubu.Device.RBF.RBFControlSystem;
import Bubu.Entity.Bubu;
import Bubu.Entity.Point;
import Bubu.Environment.Map;
import Bubu.Util.FileUtility;;

public class TrackPanel extends JPanel {
	Bubu bubu;
	Point anchor;
	Map map;

	public TrackPanel() {
		super();
        anchor = new Point(Constants.ANCHOR_OFFSET_X, Constants.WINDOW_HEIGHT/2 + Constants.ANCHOR_OFFSET_Y);

		initMap();
		// TODO Auto-generated constructor stub

		int neuroNumber = 6;

		GeneticSystem geneticSystem = new GeneticSystem(Constants.RBF_DEFAULT_DIMENSION,100,500,0.2,0.3,new RBF(neuroNumber));

		try {
			 geneticSystem.loadTrainningData(FileUtility.getLines(FileUtility.getFileName("resources")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		geneticSystem.run();
		RBF rbf = new RBF(neuroNumber);
		rbf.setParameter(
				geneticSystem.getSolutionDNA().getTheta(),
				geneticSystem.getSolutionDNA().getWeights(),
				geneticSystem.getSolutionDNA().getDistances(),
				geneticSystem.getSolutionDNA().getSigma()
		);

		bubu = new Bubu(map, new Point(0, 0),new RBFControlSystem(rbf));
		start();
	}

	public void start() {

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

		map.draw(g,anchor);
		bubu.draw(g, anchor);
	}

	public void initMap() {
		map = new Map();

		map.addWall(new Point(-60, 0), new Point(-60, 220));
		map.addWall(new Point(-60, 220), new Point(180, 220));
		map.addWall(new Point(180, 220), new Point(180, 370));

		map.addWall(new Point(60, 0), new Point(60, 100));
		map.addWall(new Point(60, 100), new Point(300, 100));
		map.addWall(new Point(300, 100), new Point(300, 370));

	}

}
