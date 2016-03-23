package Bubu.Entity;

public class Point {
	private double x;
	private double y;
	public Point(double x,double y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		
	}
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
}
