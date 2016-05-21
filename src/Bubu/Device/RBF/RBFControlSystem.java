package Bubu.Device.RBF;

import Bubu.Interface.ControlSystem;

/**
 * Created by jiaching on 2016/5/20.
 */
public class RBFControlSystem implements ControlSystem {

    public RBF rbf;
    public RBFControlSystem(RBF _rbf) {
        this.rbf = _rbf;
    }
    @Override
    public double getSteeringAngle(double[] input) {
        return this.rbf.getOutput(input);
    }
}
