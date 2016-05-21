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
    private ArrayList<DNA> dnaList;
    private int dimensions;
    private double result1 = Double.MAX_VALUE;


    //設定疊代次數、族群大小、突變機率、交配機率
    public GeneticSystem(int _dimensions,int _iterationCounts, int _populationSize, double _crossProbability, double _mutProbability, BasicFunction _rbf) {
        this.crossProbability = _crossProbability;
        this.mutProbability = _mutProbability;
        this.iterationCount = _iterationCounts;
        this.populationSize = _populationSize;
        this.dimensions = _dimensions;
        rbf = _rbf;
        dnaList = new ArrayList();

    }

    public void loadTrainningData(String _trainninDatas[]) {
        this.trainninDatas = _trainninDatas;
    }

    public void run() {
        for(int i=0; i < this.populationSize;i++) {
            this.dnaList.add(generateDNA(dimensions,this.rbf.getNeuronCount()));
        }
        this.solutionDNA = copyDNA(this.dnaList.get(0));
        int t=0;
        while (t < iterationCount) {
            reproduction();
            System.out.println(calculateDiffError(this.dnaList.get(0)));
            crossOver();
            mutate();
            t++;
        }
    }

    public DNA getSolutionDNA() {return this.solutionDNA;}

    private DNA generateDNA(int dimensions, int neuronNumber) {
        double weights[] = new double[neuronNumber];
        double distances[] = new double[neuronNumber*dimensions];
        double sigma[] = new double[neuronNumber];
        double theta = Math.random();

        for(int i=0;i<neuronNumber;i++) {
            weights[i] = (Math.random()*Constants.DNA_MAX_WEIGHT *2)-Constants.DNA_MAX_WEIGHT;
            sigma[i] = (Math.random()*10);
            for(int j = 0 ; j<dimensions ;j++) {
                distances[i*dimensions+j] = (Math.random()*30);
            }
        }
        DNA dna = new DNA(weights,distances,sigma,theta);
        dna.setFitnessValue(calculateFitnessValue(dna));
        return dna;
    }

    private DNA[] generateDNAs(int dimensions,int neuronNumber) {
        DNA DNAs[] = new DNA[this.populationSize];
        for(int i=0;i<this.populationSize;i++) {
            DNAs[i] = generateDNA(dimensions,neuronNumber);
        }
        return DNAs;
    }

    private double calculateFitnessValue(DNA dna) {
        this.rbf.setParameter(dna.getTheta(),dna.getWeights(),dna.getDistances(),dna.getSigma());
        double error = 0;
        for(int j=0;j<this.trainninDatas.length;j++) {
            String datas[] = this.trainninDatas[j].split(" ");
            double input[] = {Double.valueOf(datas[0]) ,Double.valueOf(datas[1]),Double.valueOf(datas[2])};

            error+=  Math.pow(Math.abs(Double.valueOf(datas[3]) -  this.rbf.getOutput(input)),2);
        }

        return (error / 2);
    }

    private double calculateDiffError(DNA dna) {
        this.rbf.setParameter(dna.getTheta(),dna.getWeights(),dna.getDistances(),dna.getSigma());
        double error = 0;
        for(int j=0;j<this.trainninDatas.length;j++) {
            String datas[] = this.trainninDatas[j].split(" ");
            double input[] = {Double.valueOf(datas[0]) ,Double.valueOf(datas[1]),Double.valueOf(datas[2])};

            error+=  Math.abs(Double.valueOf(datas[3]) -  this.rbf.getOutput(input));
        }
        return error/this.trainninDatas.length;

    }

    private void reproduction() {

        ArrayList<DNA> poolsDNAs = new ArrayList<>();
        double totalValue = 0;
        for(int d = 0 ; d < this.dnaList.size();d++) {
            this.dnaList.get(d).setFitnessValue(calculateFitnessValue(this.dnaList.get(d)));
            if(this.dnaList.get(d).getFitnessVaule() < this.solutionDNA.getFitnessVaule()) {
                this.solutionDNA = copyDNA(this.dnaList.get(d));
            }
            totalValue += this.dnaList.get(d).getFitnessVaule();
        }
        this.dnaList.sort(DNAComparator);

        int greatDNACount =(int) ((double)this.populationSize * Constants.DNA_FIRST_PERCENT);
        int secondDNACount = (int) ((double)this.populationSize * Constants.DNA_SECOND_PERCENT);
        int newDNACount = (int) ((double)this.populationSize * Constants.DNA_NEW_PERCENT);

        DNA greatDNA = this.solutionDNA;
        while(greatDNACount > 0) {
            poolsDNAs.add(copyDNA(greatDNA));
            greatDNACount--;
        }
        int i = 1;
        while (secondDNACount > 0) {
            double result = (  totalValue / this.dnaList.get(i).getFitnessVaule());
            int reproduceCount =(int) Math.round( result / this.populationSize) ;
            if(reproduceCount >0 ) {
                for(int j=0;j<reproduceCount;j++) {
                    poolsDNAs.add(this.dnaList.get(i));
                    secondDNACount--;
                }
            } else {
                secondDNACount--;
            }
            i++;
        }

        while (poolsDNAs.size() <  this.dnaList.size()) {
            poolsDNAs.add(generateDNA(this.dimensions,this.rbf.getNeuronCount()));
        }


        this.dnaList.clear();
        this.dnaList = poolsDNAs;

        assert this.dnaList.size() == this.populationSize;
    }

    private void crossOver() {

    }

    private void mutate() {
        for(int i =0 ;i < this.populationSize ;i++) {
            double probability= Math.random();
            if(probability < this.mutProbability) {
                if(this.dnaList.get(i).getFitnessVaule() > this.solutionDNA.getFitnessVaule()) {
                    this.dnaList.get(i).mutate();
                }
            }
        }

    }
    private Comparator<DNA> DNAComparator = new Comparator<DNA>() {
        @Override
        public int compare(DNA o1, DNA o2) {
            if(o1.getFitnessVaule() > o2.getFitnessVaule()) {
                return 1;
            }
            else if(o1.getFitnessVaule() < o2.getFitnessVaule()){
                return -1;
            } else {
                return 0;
            }
        }
    };

    private DNA copyDNA(DNA copyDna) {
        DNA dna = new DNA(copyDna.getWeights(),copyDna.getDistances(),copyDna.getSigma(),copyDna.getTheta());
        dna.setFitnessValue(copyDna.getFitnessVaule());
        return dna;
    }
}
