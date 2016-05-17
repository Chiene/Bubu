package Bubu.Device.FuzzySystem;

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
                index += 1;
            }

        }

        double result = alphaWeight/weight;
        return result;
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
                result = -20;
                break;
            case 1://SZ(SM)
                result = 0;
                break;
            case 2://SN(SF)
                result = 20;
                break;
            case 3://MP(MS)
                result = -15;
                break;
            case 4://MZ(MZ)
                result = 0;
                break;
            case 5://MN(MF)
                result = 25;
                break;
            case 6://FP(FS)
                result = -10;
                break;
            case 7://FZ(FM)
                result = 0;
                break;
            case 8://FN(FF)
                result = 15;
                break;
        }
        return result;
    }

}
