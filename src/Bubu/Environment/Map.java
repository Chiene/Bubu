package Bubu.Environment;

import Bubu.Entity.Point;
import Bubu.Entity.Wall;

import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by jiaching on 2016/3/25.
 */
public class Map {
    private Vector<Wall> walls;
    public Map() {
        walls = new Vector<>();
    }

    public void addWall(Point startPoint,Point endPoint) {
        walls.add(new Wall(startPoint,endPoint));
    }

    public Vector<Wall> getWalls() {
        return walls;
    }

    public void draw(Graphics g, Point anchor) {
        for(Wall wall: walls) {
            wall.draw(g, anchor);
        }
    }
}
