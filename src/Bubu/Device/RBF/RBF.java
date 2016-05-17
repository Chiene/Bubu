package Bubu.Device.RBF;

import Bubu.IO.Printer;
import Bubu.Interface.BasicFunction;

import java.util.Arrays;

/**
 * Created by jiaching on 2016/5/2.
 */
public class RBF implements BasicFunction{

    private int neuronCount = 0; // J number of neuron
    private double weights[]; // w
    private double distance[]; // m
    private double sigma[];
    private double theta;
    Neuron neuron;
    Printer printer;


    public RBF(int _neuronNumber) {
        assert _neuronNumber > 0;
        this.neuronCount = _neuronNumber;

        printer = new Printer();
        neuron = new Neuron();

    }


    @Override
    public void setParameter(double _theta,double _weights[],double _distance[],double _sigma[]) {

        assert this.neuronCount == _weights.length;

        this.weights = _weights;
        this.theta = _theta;
        this.sigma = _sigma;
        this.distance = _distance;

        //printer.print("RBF","weight",this.weights);

    }

    @Override
    public int getNeuronCount() {
        return this.neuronCount;
    }

    @Override
    public double getOutput(double _inputDistance[]) {
        assert _inputDistance.length == this.distance.length / this.neuronCount;
        assert this.neuronCount == sigma.length;
        int dimensions = this.distance.length / this.neuronCount;
        double value = this.theta;
        for (int i = 0;i < this.neuronCount; i++) {
            value += this.weights[i] * neuron.getAngle(_inputDistance,Arrays.copyOfRange(this.distance,i,i+dimensions),this.sigma[i]);
        }
        return value;
    }
}