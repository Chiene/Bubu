package Bubu.Entity;

import java.awt.Graphics;


public class Wall {
	Point startPoint;
	Point endPoint;
	public Wall(Point startPoint,Point endPoint) {
		// TODO Auto-generated constructor stub
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public void draw(Graphics g) {
		g.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
	}
}
