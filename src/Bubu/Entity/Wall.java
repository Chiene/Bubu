package Bubu.Entity;

import java.awt.Graphics;
import Bubu.Util.Coordinate;
import com.sun.javafx.geom.Line2D;


public class Wall {
	private Point startPoint;
	private Point endPoint;
	int xDiff;
	private int yDiff;
	private double mSlope;
	private Line2D line;

	public Wall(Point startPoint,Point endPoint)  {
		// TODO Auto-generated constructor stub

		line = new Line2D();
		line.setLine((float)startPoint.getX(),(float)startPoint.getY(),(float)endPoint.getX(),(float)endPoint.getY());

		this.startPoint = startPoint;
		this.endPoint = endPoint;
		xDiff = (int)(endPoint.getX() - startPoint.getX());
		yDiff =(int) (endPoint.getY() - startPoint.getY());
		mSlope = Coordinate.getSlope(xDiff,yDiff);

		//line.ptLineDist()
	}
	
	public void draw(Graphics g,Point anchor) {
		Point translatePosition_start = Coordinate.translate(anchor, startPoint, false, true);
		Point translatePosition_end = Coordinate.translate(anchor, endPoint, false, true);
		g.drawLine((int)translatePosition_start.getX(), (int)translatePosition_start.getY(), (int)translatePosition_end.getX(),(int) translatePosition_end.getY());
	}

	public boolean isIntersect(Point point1,Point point2) {
		return line.intersectsLine((float) point1.getX(),(float)point1.getY(),(float)point2.getX(),(float)point2.getY());
	}


	public Point getIntersect(double lineSlope,double lineConstant) {
		double x=0;
		double y=0;
		assert(mSlope != lineSlope );
		// y=mx+b
		if(mSlope == Double.MAX_VALUE) {
			x = startPoint.getX();
			y = lineSlope *x + lineConstant;
		}
		else if( mSlope == 0) {
			y = startPoint.getY();
			x = (y - lineConstant) / lineSlope;
		}
		else {
			assert(false);
		}

		return new Point( x,y);
	}





}
