package warehouse;

import agentSearch.Action;
import agentSearch.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WarehouseState extends State implements Cloneable {

    //TODO this class might require the definition of additional methods and/or attributes

    private int[][] matrix;
    private int lineAgent, columnAgent;
    private int lineExit;
    private int columnExit;
    private int steps;
    private int numShelves = 0;

    public WarehouseState(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] == Properties.AGENT){
                    lineExit = lineAgent = i;
                    columnExit = columnAgent = j;
                }
                if(matrix[i][j] == Properties.SHELF){
                    numShelves++;
                }
            }
        }
    }

    public WarehouseState(int[][] matrix, int lineAgent, int columnAgent, int lineExit, int columnExit, int numShelves) {

        this.matrix = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i],0,this.matrix[i],0,matrix[i].length);
        }

        this.lineAgent = lineAgent;
        this.columnAgent = columnAgent;
        this.lineExit = lineExit;
        this.columnExit = columnExit;
        this.numShelves = numShelves;
    }

    public int getLineExit() {
        return lineExit;
    }

    public int getColumnExit() {
        return columnExit;
    }

    public int getNumShelves() {
        return numShelves;
    }



    public void executeAction(Action action) {
        action.execute(this);
        // TODO
        throw new UnsupportedOperationException("Not implemented yet."); // delete after implementing
    }

    public void executeActionSimulation(Action action) {
        action.execute(this);
        // TODO

        fireUpdatedEnvironment();
        throw new UnsupportedOperationException("Not implemented yet."); // delete after implementing
    }


    public boolean canMoveUp() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean canMoveRight() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean canMoveDown() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean canMoveLeft() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void moveUp() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void moveRight() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void moveDown() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void moveLeft() {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void setCellAgent(int line, int column) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return matrix.length;
    }

    public Color getCellColor(int line, int column) {
        if (line == lineExit && column == columnExit && (line != lineAgent || column != columnAgent))
            return Properties.COLOREXIT;

        switch (matrix[line][column]) {
            case Properties.AGENT:
                return Properties.COLORAGENT;
            case Properties.SHELF:
                return Properties.COLORSHELF;
            default:
                return Properties.COLOREMPTY;
        }
    }

    public int getLineAgent() {
        return lineAgent;
    }

    public int getColumnAgent() {
        return columnAgent;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WarehouseState)) {
            return false;
        }

        WarehouseState o = (WarehouseState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public WarehouseState clone() {
        return new WarehouseState(matrix,lineAgent,columnAgent,lineExit,columnExit,numShelves);
    }

    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

}
