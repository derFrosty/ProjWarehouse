package warehouse;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;

import java.util.Arrays;

public class WarehouseIndividual extends IntVectorIndividual<WarehouseProblemForGA, WarehouseIndividual> {

    public WarehouseIndividual(WarehouseProblemForGA problem, int size) {
        super(problem, size);

        boolean f;
        int j;
        //geração do genoma aleatoriamente
        for (int i = 0; i < size; i++) {
            do {
                j = GeneticAlgorithm.random.nextInt(size) + 1;
                f = false;

                for (int k = 0; k < i; k++) {
                    //se o número já estiver no genoma, a variavel passa a true e volta a gerar outro número
                    if (genome[k] == j) {
                        f = true;
                        break;
                    }
                }
            } while (f);

            genome[i] = j;
        }
        //System.out.println(Arrays.toString(genome));

    }

    public WarehouseIndividual(WarehouseIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {

        fitness = 0;
        for (Request r : problem.getRequests()) {

            Cell cell1 = problem.getCellAgent(); //celula do agente
            Cell cell2 = problem.getShelves().get(getShelfPos(genome,r.getRequest()[0]));

            //distância do Agente à primeira prateleira do pedido
            for (Pair p : problem.getPairs()) {
                if ((p.getCell1().equals(cell1) && p.getCell2().equals(cell2)) || (p.getCell1().equals(cell2) && p.getCell2().equals(cell1))) {
                    fitness += p.getValue();
                    break;
                }
            }

            for (int i = 0; i < r.getRequest().length-1; i++) {
                cell1 = problem.getShelves().get(getShelfPos(genome,r.getRequest()[i]));
                cell2= problem.getShelves().get(getShelfPos(genome,r.getRequest()[i+1]));

                //distância do entre prateleiras do pedido
                for (Pair p : problem.getPairs()) {
                    if ((p.getCell1().equals(cell1) && p.getCell2().equals(cell2)) || (p.getCell1().equals(cell2) && p.getCell2().equals(cell1))) {
                        fitness += p.getValue();
                        break;
                    }
                }
            }

            cell1 = problem.getShelves().get(getShelfPos(genome,r.getRequest()[r.getRequest().length-1]));
            cell2 = problem.getExit();

            //distância da última prateleira à porta
            for (Pair p : problem.getPairs()) {
                if ((p.getCell1().equals(cell1) && p.getCell2().equals(cell2)) || (p.getCell1().equals(cell2) && p.getCell2().equals(cell1))) {
                    fitness += p.getValue();
                    break;
                }
            }
        }
        return fitness;
    }

    public static int getShelfPos(int[] genome, int value) {
        int i = 0;

        while(value!=genome[i]){
            i++;
        }
        return i;
    }

    //Return the product Id if the shelf in cell [line, column] has some product and 0 otherwise
    public int getProductInShelf(int line, int column) {
        int i=0;
        for (Cell c : problem.getShelves()) {
            if (c.getLine() == line && c.getColumn() == column && genome[i]<= problem.getNumProducts()){
                return genome[i];
            }
            i++;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append("\npath: ");
        for (int i = 0; i < genome.length; i++) {
            sb.append(genome[i]).append(" ");
            //this method might require changes
        }

        return sb.toString();
    }

    /**
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(WarehouseIndividual i) {
        return (this.fitness == i.getFitness()) ? 0 : (this.fitness < i.getFitness()) ? 1 : -1;
    }

    @Override
    public WarehouseIndividual clone() {
        return new WarehouseIndividual(this);

    }
}
