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
}
