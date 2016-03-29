package Bubu.Device;

/**
 * Created by jiaching on 2016/3/29.
 */
public class Function {
    double mSlope;
    double bConstant;
    int minX;
    int maxX;
    public Function(double m,double b ,int domainLX,int domainRX) {
        mSlope = m;
        bConstant = b;
        minX = domainLX;
        maxX = domainRX;

    }

    public boolean isInDomain(int x) {

        return (x >= minX && x <= maxX);
    }

    public double getAlphaValue(double x) {
        if(mSlope == 0) {
            return bConstant;
        }
        return mSlope*x+bConstant;
    }
}
