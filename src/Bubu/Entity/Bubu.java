package Bubu.Entity;

import java.awt.Graphics;
import java.util.Vector;

import Bubu.Constants.Constants;
import Bubu.Device.FuzzySystem.FuzzySystem;
import Bubu.Device.Sensor;
import Bubu.Environment.Map;
import Bubu.Interface.ControlSystem;
import Bubu.Util.Coordinate;

public class Bubu {
	Point centerPosition;
	Point nextPosition;
	Map map;
    Vector<Sensor> sensors;
    FuzzySystem fuzzySystem;
	double horizontalAngle = 90;
	double steeringAngle = 0;

	ControlSystem controlSystem;

	public Bubu(Map _map, Point _position ,ControlSystem _controlSystem) {
		// TODO Auto-generated constructor stub
		this.controlSystem = _controlSystem;
        centerPosition = _position;
		nextPosition = _position;
        map = _map;
        fuzzySystem = new FuzzySystem();
        sensors = new Vector<>();
        sensors.add(new Sensor(map,45,centerPosition));
        sensors.add(new Sensor(map,0,centerPosition));
        sensors.add(new Sensor(map,-45,centerPosition));

	}

	public void update() {
		Point next = getNextPosition(centerPosition);
		centerPosition.setX(next.getX());
		centerPosition.setY(next.getY());
		System.out.println(String.format("%.2f %.2f %.2f",sensors.get(2).getDistance(),sensors.get(1).getDistance(),sensors.get(0).getDistance()));
		double dis[] = {sensors.get(2).getDistance(),sensors.get(1).getDistance(),sensors.get(0).getDistance()};
		steeringAngle = controlSystem.getSteeringAngle(dis);
		System.out.println("SteeringAngle " +steeringAngle);
		System.out.println();
		horizontalAngle = Math.toDegrees(Math.toRadians(horizontalAngle) - Math.asin((2*Math.sin(Math.toRadians(steeringAngle)) / Constants.CAR_HEIGHT )));

		for (Sensor sensor: sensors) {
            sensor.updateCenterPosition(centerPosition);
			sensor.updateNextPosition(getNextPosition(centerPosition));
			sensor.detect(steeringAngle,horizontalAngle);
        }
	}

	public void setPosition(Point _position) {
		centerPosition = _position;
	}

	public void draw(Graphics g,Point anchor) {
		Point translatePosition = Coordinate.translate(anchor, centerPosition, false, true);
		translatePosition.setX(translatePosition.getX()-Constants.CAR_RADIUS);
		translatePosition.setY(translatePosition.getY()-Constants.CAR_RADIUS);
		//System.out.println("position x: " + translatePosition.getX() + " y: " + translatePosition.getY());
		g.drawOval((int)translatePosition.getX(),(int)    translatePosition.getY(), Constants.CAR_WIDTH, Constants.CAR_HEIGHT);
        for(Sensor sensor:sensors) {
            sensor.draw(g,anchor);
        }
	}

	private Point getNextPosition(Point currentPosition) {
		return new Point(currentPosition.getX() +
				Math.cos(Math.toRadians(horizontalAngle + steeringAngle)) +
				Math.sin(Math.toRadians(steeringAngle)) *
						Math.sin(Math.toRadians(horizontalAngle)),
				currentPosition.getY() +
						Math.sin(Math.toRadians(horizontalAngle + steeringAngle)) -
						Math.sin(Math.toRadians(steeringAngle)) *
								Math.cos(Math.toRadians(horizontalAngle)));
	}

}
