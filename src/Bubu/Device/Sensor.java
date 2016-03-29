package Bubu.Device;

import Bubu.Constants.Constants;
import Bubu.Entity.Point;
import Bubu.Entity.Wall;
import Bubu.Environment.Map;
import Bubu.Util.Coordinate;

import java.awt.*;

/**
 * Created by jiaching on 2016/3/25.
 */
public class Sensor {
    private Map map;
    private double distance;
    private double fixedAngle;
    private double angle;
    private Point centerPosition;
    private Point intersectPoint;
    private Point nextPosition;
    private Point detectPoint;
    private Point edgePoint;

    public Sensor(Map _map,double _angle,Point _position) {
        map = _map;
        fixedAngle = _angle;
        angle = _angle;
        distance = Double.MAX_VALUE;
        centerPosition = _position;
        detectPoint = new Point(0,0);
        edgePoint = new Point(0,0);
        intersectPoint = new Point(Double.MAX_VALUE,Double.MAX_VALUE);

    }

    public int detect(double steeringAngle) {
        intersectPoint.setX(Double.MAX_VALUE);
        intersectPoint.setY(Double.MAX_VALUE);
        if(steeringAngle > 0) {
            angle = fixedAngle - Math.abs(steeringAngle);
        }
        else if (steeringAngle < 0) {

            angle = fixedAngle + Math.abs(steeringAngle);
        }
        else {
            angle = fixedAngle;
        }

        edgePoint.setX(nextPosition.getX()+Constants.CAR_RADIUS*Math.cos(Math.toRadians(angle)));
        edgePoint.setY(nextPosition.getY()+Constants.CAR_RADIUS*Math.sin(Math.toRadians(angle)));
        detectPoint.setX(nextPosition.getX()+Constants.CAR_RADIUS*Constants.SENSOR_DETECT_DISTANCE*Math.cos(Math.toRadians(angle)));
        detectPoint.setY(nextPosition.getY()+Constants.CAR_RADIUS*Constants.SENSOR_DETECT_DISTANCE*Math.sin(Math.toRadians(angle)));



//
        double tempResultDistance = Double.MAX_VALUE;
        Point tempIntersectPoint = new Point( intersectPoint.getX(), intersectPoint.getY());

        for (Wall wall : map.getWalls()) {
            double xDiff = detectPoint.getX() - nextPosition.getX();
            double yDiff = detectPoint.getY() - nextPosition.getY();
            double slope = Coordinate.getSlope(xDiff, yDiff);
            if(wall.isIntersect(edgePoint,detectPoint)) {
                Point intersectP = wall.getIntersect(slope, Coordinate.getLineConstant(slope, edgePoint));
                if (intersectP != null && !(edgePoint.getX() > intersectP.getX() && edgePoint.getY() > intersectP.getY() )) {
                    distance = Coordinate.getDistance(intersectP, edgePoint);
                    if (distance < tempResultDistance) {
                        tempResultDistance = distance;
                        tempIntersectPoint.setX(intersectP.getX());
                        tempIntersectPoint.setY(intersectP.getY());
                    }
                } else {
                    assert (false);
                }
            }
        }

        distance = tempResultDistance;
        intersectPoint.setX(tempIntersectPoint.getX());
        intersectPoint.setY(tempIntersectPoint.getY());

        if( tempResultDistance == Double.MAX_VALUE || intersectPoint.getX() == detectPoint.getX()) {
            distance = Coordinate.getDistance(edgePoint,detectPoint);
            intersectPoint.setX(detectPoint.getX());
            intersectPoint.setY(detectPoint.getY());
        }

        return 0;
    }

    public void updateNextPosition(Point _nextPosition) {
        nextPosition = _nextPosition;
    }

    public double getDistance() {
        return distance;
    }

    public void updateCenterPosition(Point _centerPosition) {
        centerPosition = _centerPosition;
    }

    public void draw(Graphics g,Point anchor) {
        Point translatePosition = Coordinate.translate(anchor, nextPosition, false, true);
        Point translateEdgePosition = Coordinate.translate(anchor, edgePoint,false,true);
        g.drawLine(
                (int) translatePosition.getX(),
                (int) translatePosition.getY(),
                (int) translateEdgePosition.getX(),
                (int) translateEdgePosition.getY());

        Point translatePosition1 = Coordinate.translate(anchor, edgePoint, false, true);
        Point translateEdgePosition1 = Coordinate.translate(anchor, intersectPoint,false,true);
        g.drawLine(
                (int) translatePosition1.getX(),
                (int) translatePosition1.getY(),
                (int) translateEdgePosition1.getX(),
                (int) translateEdgePosition1.getY());


    }

}
