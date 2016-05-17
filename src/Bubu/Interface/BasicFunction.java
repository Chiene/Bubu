package Bubu.Interface;

/**
 * Created by jiaching on 2016/5/2.
 */
public interface BasicFunction {
    public double getOutput(double _input[]);
    public void setParameter(double _theta,double _weights[],double _distance[],double _sigma[]);
    public int getNeuronCount();
}
