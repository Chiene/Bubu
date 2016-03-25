package Bubu.Device;

import Bubu.Constants.Constants;

/**
 * Created by jiaching on 2016/3/25.
 */
public class FuzzySystem {
    int min = 0;
    int maxt = 50;
    double steeringAngle = 0;
    boolean isAdd = true;
    public FuzzySystem() {

    }

    public double getSteeringAngle() {
        if(steeringAngle >= 40) {
            isAdd = false;
        }
        if(steeringAngle <= -40) {
            isAdd = true;
        }

        if(isAdd) {
            steeringAngle+=1;
        }else {
            steeringAngle-=1;
        }
        return steeringAngle;


    }
}
