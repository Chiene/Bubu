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
    FuzzyRuleFunction SMFRuleFunction;
    FuzzyRuleFunction PZNRuleFunction;
    public FuzzySystem() {

        SMFRuleFunction = new FuzzyRuleFunction(0,40,20,100,80,120);
        PZNRuleFunction = new FuzzyRuleFunction(-160,-40,-120,120,40,160);
    }


    public double getSteeringAngle(double l,double m,double r) {

        double MAX = Constants.SENSOR_DETECT_DISTANCE*Constants.CAR_RADIUS;
        double MIN = Constants.CAR_RADIUS*10;

        
        // Distance 0<distance<30
        //System.out.println("l: " + l +" m: "+m+" r: " + r);
        //System.out.println(r-l);
        //System.out.println(PZNRuleFunction.getSmallAlpha(r-l)+ " " + PZNRuleFunction.getMediumAlpha(r-l) + " " +PZNRuleFunction.getLargeAlpha(r-l));


        if(m > min  && r > min){
            steeringAngle = 20;
        }else if(m>min && l>min) {
            steeringAngle =-20;
        }
        else {
            steeringAngle = 0;
        }


        return steeringAngle;


    }
}
