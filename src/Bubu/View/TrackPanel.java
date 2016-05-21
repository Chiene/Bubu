package Bubu.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

import Bubu.Constants.Constants;
import Bubu.Device.GeneticSystem.GeneticParameter;
import Bubu.Device.GeneticSystem.GeneticSystem;
import Bubu.Device.RBF.RBF;
import Bubu.Device.RBF.RBFControlSystem;
import Bubu.Entity.Bubu;
import Bubu.Entity.Point;
import Bubu.Environment.Map;
import Bubu.Interface.ControlSystem;
import Bubu.Util.FileUtility;
;

public class TrackPanel extends JPanel {
	Bubu bubu;
	Point anchor;
	Map map;
	Label mutationLabel;
	Label crossOverLabel;
	Label populationLabel;
	Label iterationLabel;
	Label finalFitValueLabel;


	TextField mutationField;
	TextField crossOverField;
	TextField populationField;
	TextField iterationField;
	TextField finalFitValueField;

	Button trainButton;
	Button startButton;

	ControlSystem controlSystem;
	ArrayList<Component> components ;

	Timer timer;

	boolean isStart = false;

	public TrackPanel() {
		super();
        this.setLayout(null);
		timer = new Timer();
		anchor = new Point(Constants.ANCHOR_OFFSET_X, Constants.WINDOW_HEIGHT/2 + Constants.ANCHOR_OFFSET_Y);
		initControlComponent();
		initMap();
		this.repaint();
		//
	}


	public void start() {
		timer .cancel();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				bubu.update();
				repaint();

			}
		}, 0, 50);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		map.draw(g,anchor);
		if(isStart) {
			bubu.draw(g, anchor);
		}


	}

	public void initMap() {
		map = new Map();

		map.addWall(new Point(-60, 0), new Point(-60, 220));
		map.addWall(new Point(-60, 220), new Point(180, 220));
		map.addWall(new Point(180, 220), new Point(180, 370));

		map.addWall(new Point(60, 0), new Point(60, 100));
		map.addWall(new Point(60, 100), new Point(300, 100));
		map.addWall(new Point(300, 100), new Point(300, 370));

	}


	private void initControlComponent() {
		components = new ArrayList<>();

			this.mutationLabel = new Label("Mutation Probability");
		this.mutationLabel.setSize(Constants.COMPONENT_WIDTH,Constants.COMPONENT_LABEL_HEIGHT);
		this.components.add(mutationLabel);

		this.mutationField = new TextField(Constants.GENETIC_DEFAULT_MUTATION_PROBABILITY);
		this.mutationField.setSize(Constants.COMPONENT_WIDTH,Constants.COMPONENT_TEXTFIELD_HEIGHT);
		this.components.add(mutationField);

		this.crossOverLabel = new Label("CrossOver Probability");
		this.crossOverLabel.setSize(Constants.COMPONENT_WIDTH,Constants.COMPONENT_LABEL_HEIGHT);
		this.components.add(crossOverLabel);

		this.crossOverField = new TextField(Constants.GENETIC_DEFAULT_CROSSOVER_PROBABILITY) ;
		this.crossOverField.setSize(Constants.COMPONENT_WIDTH,Constants.COMPONENT_TEXTFIELD_HEIGHT);
		this.components.add(crossOverField);

		this.iterationLabel = new Label("Iteration Count");
		this.iterationLabel.setSize(Constants.COMPONENT_WIDTH,Constants.COMPONENT_LABEL_HEIGHT);
		this.components.add(iterationLabel);

		this.iterationField = new TextField(Constants.GENETIC_DEFAULT_ITERATION_COUNT);
		this.iterationField.setSize(Constants.COMPONENT_WIDTH,Constants.COMPONENT_TEXTFIELD_HEIGHT);
		this.components.add(iterationField);

		this.populationLabel = new Label("Population Size");
		this.populationLabel.setSize(Constants.COMPONENT_WIDTH,Constants.COMPONENT_LABEL_HEIGHT);
		this.components.add(populationLabel);

		this.populationField = new TextField(Constants.GENETIC_DEFAULT_POPULATION_COUNT);
		this.populationField.setSize(Constants.COMPONENT_WIDTH,Constants.COMPONENT_TEXTFIELD_HEIGHT);
		this.components.add(populationField);

		this.finalFitValueLabel = new Label("Final Fitness Value");
		this.finalFitValueLabel.setSize(Constants.COMPONENT_WIDTH+10,Constants.COMPONENT_LABEL_HEIGHT);
		this.components.add(finalFitValueLabel);

		this.finalFitValueField = new TextField("") ;
		this.finalFitValueField.setEditable(false);
		this.finalFitValueField.setSize(Constants.COMPONENT_WIDTH,Constants.COMPONENT_TEXTFIELD_HEIGHT);
		this.components.add(finalFitValueField);

		this.trainButton = new Button("Train");
		this.trainButton.setSize(Constants.COMPONENT_WIDTH, Constants.COMPONENT_LABEL_HEIGHT);
		this.trainButton.addActionListener(this.trainActionListener);
		this.components.add(trainButton);

		this.startButton = new Button("Start");
		this.startButton.setSize(Constants.COMPONENT_WIDTH, Constants.COMPONENT_LABEL_HEIGHT);
		this.startButton.setEnabled(false);
		this.startButton.addActionListener(this.startActionListener);
		this.components.add(startButton);

		for (int i=0;i<this.components.size();i++) {
			this.components.get(i).setLocation(Constants.INIT_COMPONENT_X,Constants.COMPONENT_LABEL_HEIGHT*i + 10*i);
			this.add(components.get(i));
		}


	}

	private void click_train_button() {
		this.startButton.setEnabled(false);
		this.trainButton.setEnabled(false);
		int neuronNumber = 7;
		timer.cancel();
		GeneticParameter geneticParameter = new GeneticParameter();
		geneticParameter.setCrossOverProbability(this.crossOverField.getText());
		geneticParameter.setFitValueThreshold("2.5");
		geneticParameter.setMutationProbability(this.mutationField.getText());
		geneticParameter.setPopulationSize(this.populationField.getText());
		geneticParameter.setIterationCount(this.iterationField.getText());

		GeneticSystem geneticSystem = new GeneticSystem(Constants.RBF_DEFAULT_DIMENSION,geneticParameter, new RBF(neuronNumber));

		try {
			geneticSystem.loadTrainingData(FileUtility.getLines(FileUtility.getFileName("resources")));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		geneticSystem.run();
		RBF rbf = new RBF(neuronNumber);
		rbf.setParameter(
				geneticSystem.getSolutionDNA().getTheta(),
				geneticSystem.getSolutionDNA().getWeights(),
				geneticSystem.getSolutionDNA().getDistances(),
				geneticSystem.getSolutionDNA().getSigma()
		);
		this.finalFitValueField.setText(String.valueOf(geneticSystem.getSolutionDNA().getFitnessVaule()));
		controlSystem = new RBFControlSystem(rbf);
		this.startButton.setEnabled(true);
		this.trainButton.setEnabled(true);

	}

	private void click_start_button() {
		this.isStart = true;
		bubu = new Bubu(map, new Point(0, 0),this.controlSystem);
		start();
	}

	private ActionListener trainActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			click_train_button();
		}
	};

	private ActionListener startActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			click_start_button();
		}
	};
}
