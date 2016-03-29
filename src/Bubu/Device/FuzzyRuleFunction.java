package Bubu.Device;

import Bubu.Entity.Point;
import Bubu.Util.Coordinate;

import java.util.Vector;

/**
 * Created by jiaching on 2016/3/29.
 */
public class FuzzyRuleFunction {
    int m_l_value;
    int m_r_value;

    int l_l_value;
    int l_r_value;

    int r_l_value;
    int r_r_value;

    Function s_function;
    Function m_l_function;
    Function m_r_function;
    Function f_l_function;
    Function f_r_function;

    //Vector<Function> functions;


    public FuzzyRuleFunction(int llvalue, int lrvalue, int mlvalue, int mrvalue, int rlvalue, int rrvalue) {

        //functions = new Vector<>();
        l_l_value = llvalue;
        l_r_value = lrvalue;

        m_l_value = mlvalue;
        m_r_value = mrvalue;

        r_l_value = rlvalue;
        r_r_value = rrvalue;

        s_function = new Function(Coordinate.getSlope((double) lrvalue - llvalue, -1), Coordinate.getLineConstant(Coordinate.getSlope((double) (lrvalue - llvalue), -1), new Point(lrvalue, 0)), llvalue, lrvalue);
        m_l_function = new Function(Coordinate.getSlope((double) ((mrvalue + mlvalue) / 2) - mlvalue, 1), Coordinate.getLineConstant(Coordinate.getSlope(((double) (((mrvalue + mlvalue) / 2) - mlvalue)), 1), new Point(mlvalue, 0)), mlvalue, (mrvalue + mlvalue) / 2);
        m_r_function = new Function(Coordinate.getSlope((double) (mrvalue - (mrvalue + mlvalue) / 2), -1), Coordinate.getLineConstant(Coordinate.getSlope(((double) ((mrvalue - (mrvalue + mlvalue) / 2))), -1), new Point(mrvalue, 0)), (mrvalue + mlvalue) / 2, mrvalue);
        f_l_function = new Function(Coordinate.getSlope((double) (rrvalue - rlvalue), 1), Coordinate.getLineConstant(Coordinate.getSlope(((double) (rrvalue - rlvalue)), 1), new Point(rlvalue, 0)), rlvalue, rrvalue);
        f_r_function = new Function(0, Coordinate.getLineConstant(0, new Point(rrvalue, 1)), rrvalue, Integer.MAX_VALUE);
//        functions.add(s_function);
//        functions.add(m_l_function);
//        functions.add(m_r_function);
//        functions.add(f_l_function);
//        functions.add(f_r_function);
    }
    public double getSmallAlpha(double distance) {
        Function functions[] = {s_function};
        return getAlpha(functions,distance);
    }

    public double getMediumAlpha(double distance) {
        Function functions[] = {m_l_function,m_r_function};
        return getAlpha(functions,distance);
    }

    public double getLargeAlpha(double distance) {
        Function functions[] = {f_l_function,f_r_function};
        return getAlpha(functions,distance);
    }

    private double getAlpha(Function[] functions,double distance) {
        double alpha = Double.MAX_VALUE;
        for (Function func: functions) {
            if (func.isInDomain((int) distance)) {
                double temp = func.getAlphaValue(distance);
                if (temp < alpha) {
                    alpha = temp;
                }

            }
        }
        if(alpha == Double.MAX_VALUE) {
            alpha = 0;
        }

        return alpha;
    }

}
