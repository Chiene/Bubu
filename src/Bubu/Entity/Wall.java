package Bubu.Entity;

import java.awt.Graphics;

import Bubu.Util.Coordinate;


public class Wall {
	Point startPoint;
	Point endPoint;
	public Wall(Point startPoint,Point endPoint) {
		// TODO Auto-generated constructor stub
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public void draw(Graphics g,Point anchor) {
		Point translatePosition_start = Coordinate.translate(anchor, startPoint, false, true);
		Point translatePosition_end = Coordinate.translate(anchor, endPoint, false, true);
		g.drawLine((int)translatePosition_start.getX(), (int)translatePosition_start.getY(), (int)translatePosition_end.getX(),(int) translatePosition_end.getY());
	}

	public double calculateMinDistance(Point point) {
		///|point to line| / sqrt(a *a + b*b)
		double result = 0;
		int a =  (int)(startPoint.getX()- endPoint.getX());
		int b =  (int)(startPoint.getY()- endPoint.getY());
		result = Math.abs(a*point.getX()+b*point.getY()+a*startPoint.getX()+b*startPoint.getY())/Math.sqrt(a*a+b*b);
		return result;
	}
}
