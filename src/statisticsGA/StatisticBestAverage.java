package statisticsGA;

import experiments.Experiment;
import experiments.ExperimentEvent;
import ga.*;
import utils.Maths;

import java.io.File;

public class StatisticBestAverage<E extends Individual, P extends Problem<E>> implements GAListener  {
    
    private final double[] values;
    private int run;

    public StatisticBestAverage(int numRuns, String experimentHeader) {
        values = new double[numRuns];
        File file = new File("statistic_average_fitness.xls");
        if(!file.exists()){
            utils.FileOperations.appendToTextFile("statistic_average_fitness.xls", experimentHeader + "\t" + "Average:" + "\t" + "StdDev:" + "\t"+ "Elapsed Time (in minutes):"+"\r\n");
        }
    }

    @Override
    public void generationEnded(GAEvent e) {    
    }

    @Override
    public void runEnded(GAEvent e) {
        GeneticAlgorithm<E, P> ga = e.getSource();
        values[run++] = ga.getBestInRun().getFitness();
    }

    @Override
    public void experimentEnded(ExperimentEvent e) {
        double average = Maths.average(values);
        double sd = Maths.standardDeviation(values, average);

        String experimentConfigurationValues = ((Experiment) e.getSource()).getExperimentValues();
        double timeMiliSec = e.getSource().getElapsedTime();
        double timeMin = Math.floor(timeMiliSec/1000/60);
        double timeSec = ((timeMiliSec/1000/60) - timeMin)*60;

        utils.FileOperations.appendToTextFile("statistic_average_fitness.xls", experimentConfigurationValues + "\t" + average + "\t" + sd + "\t" + String.format("%.0f", timeMin) + ":" + String.format("%.0f", timeSec) + "\r\n");
    }
}
