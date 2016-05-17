package Bubu.Device.RBF;

import Bubu.Interface.RadisBasicFunction;

/**
 * Created by jiaching on 2016/5/2.
 */
public class Neuron implements RadisBasicFunction {

    public Neuron() {
    }

    @Override
    public double getAngle(double _input[],double _distance[],double _sigma) {
        assert _input.length == _distance.length;
        double result = 0;
        for(int i =0;i<_input.length;i++) {
            result += Math.exp((-1) * Math.pow(_input[i]-_distance[i],2) / 2 * _sigma);
        }
        return result;
    }

}
