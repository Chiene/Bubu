package Bubu.IO;

import Bubu.Interface.SystemPrint;

/**
 * Created by jiaching on 2016/5/2.
 */
public class Printer implements SystemPrint {
    @Override
    public void print(String className, String msg) {
        System.out.println(String.format("[%s]: %s",className,msg));
    }

    @Override
    public void print(String className, String arrayName, double[] array) {
        System.out.println(String.format("[%s]: %s",className,arrayName));
        for(double value : array) {
            System.out.print(String.format("%.0f ",value));
        }
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println();
    }

}
