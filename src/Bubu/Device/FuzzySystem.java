package Bubu.Device;

import Bubu.Constants.Constants;
import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;

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
        //System.out.println("l: " + l +" m: "+m+" r: " + r);
        //System.out.println(SMFRuleFunction.getSmallAlpha(m)+" " +SMFRuleFunction.getMediumAlpha(m) + " "+SMFRuleFunction.getLargeAlpha(m));
        //System.out.println(PZNRuleFunction.getSmallAlpha(r-l)+" " +PZNRuleFunction.getMediumAlpha(r-l) + " "+PZNRuleFunction.getLargeAlpha(r-l));
        //System.out.println();
        double alpha[][] = new double[Constants.SMF_RULE_NUMBER][Constants.PZN_RULE_NUMBER];
        int index = 0;
        double alphaWeight = 0;
        double weight = 0;
        for (int i = 0 ;i<Constants.SMF_RULE_NUMBER;i++) {
            for(int j = 0 ;j<Constants.PZN_RULE_NUMBER;j++) {
                double temp = getAlpha(index, m, r - l);
                if(temp == Double.MAX_VALUE) {
                    alpha[i][j] = 0;
                } else {
                    alpha[i][j] =  temp;
                }

                alphaWeight += alpha[i][j] * getTheta(index);
                weight += alpha[i][j];
                //System.out.print(alpha[i][j] + " ");
                index += 1;
            }
            //System.out.println();
        }
        //System.out.println();
        System.out.println(alphaWeight/weight);


        return alphaWeight/weight;


    }

    private double getAlpha(int index,double mDiff,double lrDiff) {
        double result = 0;
        switch (index){
            case 0://SP(SS)
                result = Math.min(SMFRuleFunction.getSmallAlpha(mDiff),PZNRuleFunction.getSmallAlpha(lrDiff));
                break;
            case 1://SZ(SM)
                result = Math.min(SMFRuleFunction.getSmallAlpha(mDiff),PZNRuleFunction.getMediumAlpha(lrDiff));
                break;
            case 2://SN(SF)
                result = Math.min(SMFRuleFunction.getSmallAlpha(mDiff),PZNRuleFunction.getLargeAlpha(lrDiff));
                break;
            case 3://MP(MS)
                result = Math.min(SMFRuleFunction.getMediumAlpha(mDiff),PZNRuleFunction.getSmallAlpha(lrDiff));
                break;
            case 4://MZ(MZ)
                result = Math.min(SMFRuleFunction.getMediumAlpha(mDiff),PZNRuleFunction.getMediumAlpha(lrDiff));
                break;
            case 5://MN(MF)
                result = Math.min(SMFRuleFunction.getMediumAlpha(mDiff),PZNRuleFunction.getLargeAlpha(lrDiff));
                break;
            case 6://FP(FS)
                result = Math.min(SMFRuleFunction.getLargeAlpha(mDiff),PZNRuleFunction.getSmallAlpha(lrDiff));
                break;
            case 7://FZ(FM)
                result = Math.min(SMFRuleFunction.getLargeAlpha(mDiff),PZNRuleFunction.getMediumAlpha(lrDiff));
                break;
            case 8://FN(FF)
                result = Math.min(SMFRuleFunction.getLargeAlpha(mDiff),PZNRuleFunction.getLargeAlpha(lrDiff));
                break;
        }

        return result;
    }
    private double getTheta(int index) {
        double result = 0;
        switch (index){
            case 0://SP(SS)
                result = -40;
                break;
            case 1://SZ(SM)
                result = -20;
                // break;
            case 2://SN(SF)
                result = 40;
                break;
            case 3://MP(MS)
                result = -30;
                break;
            case 4://MZ(MZ)
                result = 0;
                break;
            case 5://MN(MF)
                result = 20;
                break;
            case 6://FP(FS)
                result = -30;
                break;
            case 7://FZ(FM)
                result = 0;
                break;
            case 8://FN(FF)
                result = 10;
                break;
        }

        return result;
    }

}
