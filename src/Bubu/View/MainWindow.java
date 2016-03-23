package Bubu.View;

import javax.swing.JFrame;

import Bubu.Constants.Constants;

public class MainWindow extends JFrame{
	public MainWindow() {
		super("Bubu");
		// TODO Auto-generated constructor stub
		setVisible(true);
		setSize(Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT+40);
		getBounds().getWidth();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new TrackPanel());
	}

}
