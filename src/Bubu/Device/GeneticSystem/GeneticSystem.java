package Bubu.Device.GeneticSystem;

import Bubu.Constants.Constants;
import Bubu.Interface.BasicFunction;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by jiaching on 2016/5/17.
 */
public class GeneticSystem {

    private String trainninDatas[];
    private int iterationCount;
    private int populationSize;
    private double crossProbability;
    private double mutProbability;
    private BasicFunction  rbf;
    private DNA solutionDNA;
    private HashMap DNAFitValue;

    //設定疊代次數、族群大小、突變機率、交配機率
    public GeneticSystem(int _iterationCounts, int _populationSize, double _crossProbability, double _mutProbability, BasicFunction _rbf) {
        this.crossProbability = _crossProbability;
        this.mutProbability = _mutProbability;
        this.iterationCount = _iterationCounts;
        this.populationSize = _populationSize;
        DNAFitValue = new HashMap<DNA,Double>();
        rbf = _rbf;
    }

    public void loadTrainningData(String _trainninDatas[]) {
        this.trainninDatas = _trainninDatas;
    }


    public void run(int dimensions) {
        DNA dnas[] = generateDNAs(dimensions,this.rbf.getNeuronCount());

        double totalValue = 0;
        for(int i=0;i<dnas.length;i++) {
            double fitValue = getFitnessValue(dnas[i])/this.trainninDatas.length;
            totalValue += fitValue;
            this.DNAFitValue.put(dnas[i],fitValue);
        }
        reproduction(totalValue);

        int t=0;
        while (t < iterationCount) {


        }

        /*
        while (this.iterationCount > 0) {

            this.iterationCount--;
        }*/
    }

    public DNA getSolutionDNA() {
        return solutionDNA;
    }



    private DNA generateDNA(int dimensions, int neuronNumber) {
        double weights[] = new double[neuronNumber];
        double distances[] = new double[neuronNumber*dimensions];
        double sigma[] = new double[neuronNumber];
        double delta = Math.random();

        for(int i=0;i<neuronNumber;i++) {
            weights[i] = (Math.random()*80)-40;
            sigma[i] = (Math.random()*10);
            for(int j = 0 ; j<dimensions ;j++) {
                distances[i*dimensions+j] = (Math.random()*30);
            }
        }
        return new DNA(weights,distances,sigma,delta);
    }

    private DNA[] generateDNAs(int dimensions,int neuronNumber) {
        DNA DNAs[] = new DNA[this.populationSize];
        for(int i=0;i<this.populationSize;i++) {
            DNAs[i] = generateDNA(dimensions,neuronNumber);
        }
        return DNAs;
    }

    private double getFitnessValue(DNA dna) {
        this.rbf.setParameter(dna.getTheta(),dna.getWeights(),dna.getDistances(),dna.getSigma());
        double error = 0;
        for(int j=0;j<this.trainninDatas.length;j++) {
            String datas[] = this.trainninDatas[j].split(" ");
            double input[] = {Double.valueOf(datas[0]) ,Double.valueOf(datas[1]),Double.valueOf(datas[2])};
            error+= Math.abs(Double.valueOf(datas[3]) -  this.rbf.getOutput(input));
        }
        return error;
    }

    private void reproduction(double totalValue) {
        List list = sortByValues(this.DNAFitValue);
        this.DNAFitValue.clear();
        DecimalFormat df = new DecimalFormat("##");

        for(int i =0 ;i< list.size();i++) {
            Map.Entry entry =(Map.Entry) list.get(i);
            if(i < Constants.MAX_REPRODUCE_NUMBER) {
                this.DNAFitValue.put(entry.getKey(),entry.getValue());
            } else {
                int reproduceCount = (int) Double.parseDouble(
                        df.format((Double)entry.getValue() / totalValue * (this.populationSize - Constants.MAX_REPRODUCE_NUMBER)));
                for(int j=0;j<reproduceCount;j++) {
                    this.DNAFitValue.put(entry.getKey(),entry.getValue());
                }
            }
        }
        assert this.DNAFitValue.size() == this.populationSize;
    }

    public void crossOver() {

    }

    private static List sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return (-1) * ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        return list;
    }


}
