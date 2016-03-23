package Bubu.Entity;

import java.awt.Graphics;

import Bubu.Constants.Constants;

public class Bubu {
	double x = 50;
	double y = 50;
	double horizontalAngle = 90;
	double steeringAngle = 0;

	public Bubu() {
		// TODO Auto-generated constructor stub
	}

	public void update() {
		x = x + Math.cos(horizontalAngle + steeringAngle) + Math.sin(horizontalAngle) * Math.sin(steeringAngle);
		y = y + Math.sin(horizontalAngle + steeringAngle) - Math.sin(horizontalAngle) * Math.cos(steeringAngle);
	}

	public void draw(Graphics g) {
		g.drawOval((int) x, (int) y, 20,20);
	}
	
	
}
