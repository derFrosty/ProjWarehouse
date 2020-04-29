package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class MutationScramble <I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P>  {
    public MutationScramble(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int cut2;
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        } while (cut1 == cut2);

        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        //misturar os valores entre cuts
        //o número de trocas é 1/4 do número de valores entre os cuts
        for (int i = 0; i < Math.round((cut2-cut1)/4); i++) {
            int g,j;
            //gerar 1 index aleatório entre o cut 1 e o cut 2
            j = GeneticAlgorithm.random.nextInt(cut2-cut1+1)+cut1;

            do {
                g = GeneticAlgorithm.random.nextInt(cut2-cut1+1)+cut1;
            }while (j==g);

            int aux = ind.getGene(j);
            ind.setGene(j, ind.getGene(g));
            ind.setGene(g, aux);
        }
    }

    @Override
    public String toString() {
        return "Scramble";
    }
}
