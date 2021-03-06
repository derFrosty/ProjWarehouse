package statisticsGA;

import experiments.Experiment;
import experiments.ExperimentEvent;
import ga.*;

import java.io.File;

public class StatisticBestInRun<I extends Individual, P extends Problem<I>> implements GAListener {

    private I bestInExperiment;
    private int seed;

    public StatisticBestInRun(String experimentHeader) {
        File file = new File("statistic_best_per_experiment_fitness.xls");
        if(!file.exists()){
            utils.FileOperations.appendToTextFile("statistic_best_per_experiment_fitness.xls", experimentHeader + "\t" + "Fitness:" + "\t" + "Elapsed Time (in minutes):"+ "\t" + "Seed:" + "\r\n");
        }
    }

    @Override
    public void generationEnded(GAEvent e) {
    }

    @Override
    public void runEnded(GAEvent e) {
        GeneticAlgorithm<I, P> ga = e.getSource();
        if (bestInExperiment == null || ga.getBestInRun().compareTo(bestInExperiment) > 0) {
            bestInExperiment = (I) ga.getBestInRun().clone();
            seed = ga.getRun();
            //System.out.println(seed);
        }
    }

    @Override
    public void experimentEnded(ExperimentEvent e) {

        String experimentTextualRepresentation = ((Experiment) e.getSource()).getExperimentTextualRepresentation();
        String experimentHeader = ((Experiment) e.getSource()).getExperimentHeader();
        String experimentConfigurationValues = ((Experiment) e.getSource()).getExperimentValues();

        double timeMiliSec = e.getSource().getElapsedTime();
        double timeMin = Math.floor(timeMiliSec/1000/60);
        double timeSec = ((timeMiliSec/1000/60) - timeMin)*60;

        String time = String.format("%.0f", timeMin) + ":" + String.format("%.0f", timeSec);

        utils.FileOperations.appendToTextFile("statistic_best_per_experiment_fitness.xls", experimentConfigurationValues + "\t" + bestInExperiment.getFitness() + "\t" + time + "\t" + seed + "\r\n");
        utils.FileOperations.appendToTextFile("statistic_best_per_experiment.txt", "\r\n\r\n" + experimentTextualRepresentation + "\r\n" + bestInExperiment + "\r\n" + "Elapsed Time (in minutes) => " + String.format("%.0f", timeMin) + ":" + String.format("%.0f", timeSec) + "\r\n" +"Seed:" + seed);
    }
}
