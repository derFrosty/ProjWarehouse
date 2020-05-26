package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class RecombinationCycle<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    int[] child1, child2, ind1_aux, ind2_aux;
    int genome_length;

    public RecombinationCycle(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {

        genome_length = ind1.getNumGenes();


        child1 = new int[genome_length];
        child2 = new int[genome_length];


        ind1_aux = new int[genome_length];
        ind2_aux = new int[genome_length];

        System.arraycopy(ind1.getGenome(),0,ind1_aux,0,genome_length);
        System.arraycopy(ind2.getGenome(),0,ind2_aux,0,genome_length);

        cycleCompute(ind1, ind2, child1, child2);

        System.arraycopy(child1,0,ind1.getGenome(),0,genome_length);
        System.arraycopy(child2,0,ind2.getGenome(),0,genome_length);
    }

    private void cycleCompute(I ind1, I ind2, int[] child1, int[] child2) {

        boolean cycle = true;

        int count = 0;
        int position = 0;

        while (count <= genome_length) {

            //para encontrar a posição apropriada para escrever para o filho
            for (int i = 0; i < genome_length; i++) {
                if (child1[i] == 0) {
                    position = i;
                    break;
                }
            }

            //verificar se cycle é par ou impar (true = par)
            if (cycle) {
                while (true) {
                    //escrever para o filho nessa posição
                    child1[position] = ind1.getGene(position);
                    count++;

                    //passar o valor dessa posição a -1 no ind_aux
                    ind1_aux[position] = -1;

                    //obter a nova posição
                    position = ind2.getIndexof(ind1.getGene(position));

                    //verificar se nesta "nova" posição, o elemento já era -1, e requere um novo cyclo
                    if (ind1_aux[position] == -1) {
                        cycle = false;
                        break;
                    }
                }
            } else {
                while (true) {
                    child1[position] = ind2.getGene(position);
                    count++;

                    ind2_aux[position] = -1;

                    position = ind1.getIndexof(ind2.getGene(position));

                    if (ind2_aux[position] == -1) {
                        cycle = true;
                        break;
                    }
                }
            }
        }

        //preencher o 2º filho
        for (int i = 0; i < genome_length; i++) {
            if(child1[i] == ind1.getGene(i)){
                child2[i] = ind2.getGene(i);
            }else{
                child2[i] = ind1.getGene(i);
            }
        }

    }

    @Override
    public String toString() {
        return "Cycle";
    }
}
