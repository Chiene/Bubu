package Bubu.Device.GeneticSystem;

/**
 * Created by jiaching on 2016/5/17.
 */
public class DNA {
    private double weights[];
    private double sigma[];
    private double distances[];
    private double theta;

    public DNA(double _weights[],double _distances[],double _sigma[],double _theta) {
        this.weights = _weights;
        this.distances = _distances;
        this.sigma = _sigma;
        this.theta = _theta;
    }

    public void setWeights(double _weights[]) {
        this.weights = _weights;
    }

    public void setdistances(double _distances[]) {
        this.distances = _distances;
    }

    public void setsigma(double _sigma[]) {
        this.sigma = _sigma;
    }

    public void setTheta(double _theta) {
        this.theta = _theta;
    }

    public double[] getWeights() {
        return weights;
    }

    public double[] getDistances() {
        return distances;
    }

    public double[] getSigma() {
        return sigma;
    }

    public double getTheta() {
        return theta;
    }

}
