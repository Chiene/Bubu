package Bubu.Util;

import Bubu.Entity.Point;

public class Coordinate {
	public static Point translate(Point anchor,Point target,boolean isReflection_x,boolean isReflection_y) {
		Point newPoint = new Point(target.getX()+anchor.getX(), target.getY()+anchor.getY());
		if(isReflection_x) {
			newPoint.setX(newPoint.getX()- (target.getX()*2));
		} 
		
		if(isReflection_y) {
			newPoint.setY(newPoint.getY()-(target.getY()*2));
		}
		
		return newPoint;
	}
	public static double getDistance(Point startPoint,Point endPoint) {
		return Math.sqrt(Math.pow(startPoint.getX()-endPoint.getX(),2)+Math.pow(startPoint.getY()-endPoint.getY(),2));
	}

	public static boolean isVerticalLine(double xDiff) {
		return xDiff == 0;
	}

	public static boolean isHorizontalLine(double yDiff) {
		return yDiff == 0;
	}

	public static double getSlope(double xDiff,double yDiff) {
		double slope = 0;
		if (isVerticalLine(xDiff)) {
			slope = Double.MAX_VALUE;
		} else if(isHorizontalLine(yDiff)){
			slope = 0;
		}
		else {
			slope = yDiff/xDiff;
		}
		return slope;
	}

	public static double getLineConstant(double slope,Point poiint) {
		// y= mx+b , b= y-mx;
		if(slope == 0) {
			return poiint.getY();
		} else if(slope == Double.MAX_VALUE) {
			return 0;
		} else {
			return poiint.getY()-(slope*poiint.getX());
		}
	}
}
