package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class RecombinationMultiPoint<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {


    private int[] child1, child2, segment1, segment2;

    private int cut1;
    private int cut2;

    public RecombinationMultiPoint(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        child1 = new int[ind1.getNumGenes()];
        child2 = new int[ind2.getNumGenes()];
        cut1 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        } while (cut1 == cut2);
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        for (int i = 0; i < cut1; i++) {
            child1[i] = ind2.getGene(i);
            child2[i] = ind1.getGene(i);
        }

        for (int i = cut2; i < child1.length; i++) {
            child1[i] = ind2.getGene(i);
            child2[i] = ind1.getGene(i);
        }

        createChild(child1,ind1);
        createChild(child2,ind2);

        System.arraycopy(child1, 0, ind1.getGenome(), 0, child1.length);
        System.arraycopy(child2, 0, ind2.getGenome(), 0, child2.length);
    }

    private void createChild(int[] child, I ind) {
        //procurar no pai os elementos que já estão no filho e passá-los a negativo
        for (int i = 0; i < child.length; i++) {//percorre o child

            for (int j = 0; j < child.length; j++) {//percorre o ind

                if (child[i] == ind.getGene(j)) {
                    ind.setGene(j, -ind.getGene(j));
                    break;
                }
            }
        }

        //copiar para o filho os genes que estão a positivo
        for (int i = 0; i < child.length; i++) {
            if (ind.getGene(i) > 0) {
                for (int k = 0; k < child.length; k++) {
                    if (child[k] == 0) {
                        child[k] = ind.getGene(i);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String toString(){
        return "MPX";
    }    
}