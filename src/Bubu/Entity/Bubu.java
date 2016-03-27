package Bubu.Entity;

import java.awt.Graphics;
import java.util.Vector;

import Bubu.Constants.Constants;
import Bubu.Device.FuzzySystem;
import Bubu.Device.Sensor;
import Bubu.Environment.Map;
import Bubu.Util.Coordinate;

public class Bubu {
	Point centerPosition;
	Point nextPosition;
	Map map;
    Vector<Sensor> sensors;
    FuzzySystem fuzzySystem;
	double horizontalAngle = 90;
	double steeringAngle = 0;

	public Bubu(Map _map, Point _position) {
		// TODO Auto-generated constructor stub
        centerPosition = _position;
		nextPosition = _position;
        map = _map;
        fuzzySystem = new FuzzySystem();
        sensors = new Vector<>();
        sensors.add(new Sensor(map,horizontalAngle - 45,centerPosition));
        sensors.add(new Sensor(map,horizontalAngle,centerPosition));
        sensors.add(new Sensor(map,horizontalAngle+45,centerPosition));

	}

	public void update() {
		Point next = getNextPosition(centerPosition);
		centerPosition.setX(next.getX());
		centerPosition.setY(next.getY());
		steeringAngle = fuzzySystem.getSteeringAngle();
		horizontalAngle = horizontalAngle - Math.asin((2*Math.sin(Math.toRadians(steeringAngle)) / Constants.CAR_HEIGHT ));
		for (Sensor sensor: sensors) {
            sensor.updateCenterPosition(centerPosition);
			sensor.updateNextPosition(getNextPosition(centerPosition));
			sensor.detect(steeringAngle);
        }
	}

	public void setPosition(Point _position) {
		centerPosition = _position;
	}

	public void draw(Graphics g,Point anchor) {
		Point translatePosition = Coordinate.translate(anchor, centerPosition, false, true);
		translatePosition.setX(translatePosition.getX()-Constants.CAR_RADIUS);
		translatePosition.setY(translatePosition.getY()-Constants.CAR_RADIUS);
		g.drawOval((int)translatePosition.getX(),(int)    translatePosition.getY(), Constants.CAR_WIDTH, Constants.CAR_HEIGHT);
        for(Sensor sensor:sensors) {
            sensor.draw(g,anchor);
        }
	}

	private Point getNextPosition(Point currentPosition) {
		return new Point(currentPosition.getX() + Math.cos(Math.toRadians(horizontalAngle + steeringAngle)) + Math.sin(Math.toRadians(steeringAngle)) * Math.sin(Math.toRadians(horizontalAngle)),currentPosition.getY() + Math.sin(Math.toRadians(horizontalAngle + steeringAngle)) - Math.sin(Math.toRadians(steeringAngle)) * Math.cos(Math.toRadians(horizontalAngle)));
	}



}
