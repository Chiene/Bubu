package Bubu.Device;

import Bubu.Constants.Constants;
import Bubu.Entity.Point;
import Bubu.Entity.Wall;
import Bubu.Environment.Map;
import Bubu.Util.Coordinate;

import java.awt.*;
import java.awt.geom.Arc2D;

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
    private Point edgePoint;

    public Sensor(Map _map,double _angle,Point _position) {
        map = _map;
        fixedAngle = _angle;
        distance = Double.MAX_VALUE;
        centerPosition = _position;
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
        //Point vector = new Point(edgePoint.getX()-centerPosition.getX(),edgePoint.getY()-centerPosition.getY());
        double tempResultDistance = Double.MAX_VALUE;
        Point tempIntersectPoint = intersectPoint;

        for (Wall wall : map.getWalls()) {
            double xDiff = edgePoint.getX() - nextPosition.getX();
            double yDiff = edgePoint.getY() - nextPosition.getY();
            double slope = Coordinate.getSlope(xDiff, yDiff);
            if(wall.isIntersect(slope)) {
                Point intersectP = wall.getIntersect(slope, Coordinate.getLineConstant(slope, edgePoint));
                if (intersectP != null) {
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
        intersectPoint.setX(tempIntersectPoint.getX());
        intersectPoint.setY(tempIntersectPoint.getY());
        return 0;
    }

    public void updateNextPosition(Point _nextPosition) {
        nextPosition = _nextPosition;
    }

    public void updateCenterPosition(Point _centerPosition) {
        centerPosition = _centerPosition;
    }

    public void draw(Graphics g,Point anchor) {
        Point translatePosition = Coordinate.translate(anchor, edgePoint, false, true);
        Point translateEdgePosition = Coordinate.translate(anchor,intersectPoint,false,true);
        g.drawLine(
                (int) translatePosition.getX(),
                (int) translatePosition.getY(),
                (int) translateEdgePosition.getX(),
                (int) translateEdgePosition.getY());

    }

}
