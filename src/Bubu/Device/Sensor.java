package Bubu.Device;

import Bubu.Constants.Constants;
import Bubu.Entity.Point;
import Bubu.Environment.Map;
import Bubu.Util.Coordinate;

import java.awt.*;

/**
 * Created by jiaching on 2016/3/25.
 */
public class Sensor {
    private Map map;
    private int distance;
    private double fixedAngle;
    private double angle;
    private Point centerPosition;
    private Point nextPosition;
    private Point edgePoint;

    public Sensor(Map _map,double _angle,Point _position) {
        map = _map;
        fixedAngle = _angle;
        centerPosition = _position;
        edgePoint = new Point(0,0);
    }

    public int detect(double steeringAngle) {

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


        return 0;
    }

    public void updateNextPosition(Point _nextPosition) {
        nextPosition = _nextPosition;
    }

    public void updateCenterPosition(Point _centerPosition) {
        centerPosition = _centerPosition;
    }

    public void draw(Graphics g,Point anchor) {
        Point translatePosition = Coordinate.translate(anchor, nextPosition, false, true);
        Point translateEdgePosition = Coordinate.translate(anchor,edgePoint,false,true);
        g.drawLine(
                (int) translatePosition.getX(),
                (int) translatePosition.getY(),
                (int) translateEdgePosition.getX(),
                (int) translateEdgePosition.getY());
    }
}
