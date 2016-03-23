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
}
