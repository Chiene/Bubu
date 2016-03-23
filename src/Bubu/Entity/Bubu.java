package Bubu.Entity;

import java.awt.Graphics;
import Bubu.Constants.Constants;
import Bubu.Util.Coordinate;

public class Bubu {
	Point centerPosition;

	double horizontalAngle = 90;
	double steeringAngle = 0;

	public Bubu(Point _position) {
		// TODO Auto-generated constructor stub
		centerPosition = _position;
	}

	public void update() {
		centerPosition.setX((centerPosition.getX() + Math.cos(Math.toRadians(horizontalAngle + steeringAngle)) + Math.sin(Math.toRadians(steeringAngle)) * Math.sin(Math.toRadians(horizontalAngle))));
		centerPosition.setY((centerPosition.getY() + Math.sin(Math.toRadians(horizontalAngle + steeringAngle)) - Math.sin(Math.toRadians(steeringAngle)) * Math.cos(Math.toRadians(horizontalAngle))));
		horizontalAngle = horizontalAngle - Math.asin((2*Math.sin(Math.toRadians(steeringAngle)) / Constants.CAR_HEIGHT ));
		
	}
	
	public void setPosition(Point _position) {
		centerPosition = _position;
	}

	public void draw(Graphics g,Point anchor) {
		Point translatePosition = Coordinate.translate(anchor, centerPosition, false, true);
		translatePosition.setX(translatePosition.getX()-Constants.CAR_RADIUS);
		translatePosition.setY(translatePosition.getY()-Constants.CAR_RADIUS);
		g.drawOval((int)translatePosition.getX(),(int)    translatePosition.getY(), Constants.CAR_WIDTH, Constants.CAR_HEIGHT);
		
	}
	


}
