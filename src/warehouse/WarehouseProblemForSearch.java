package warehouse;

import agentSearch.Action;
import agentSearch.Problem;

import java.util.LinkedList;
import java.util.List;

public class WarehouseProblemForSearch<S extends WarehouseState> extends Problem<S> {

    private Cell goalPosition;
    private List<Action> actions;

    public WarehouseProblemForSearch(S initialWarehouseState, Cell goalPosition) {
        super(initialWarehouseState);
        this.goalPosition = goalPosition;

        //lista de ações
        actions = new LinkedList<Action>(){{
            add(new ActionUp());
            add(new ActionLeft());
            add(new ActionDown());
            add(new ActionRight());
        }};
    }

    public Cell getGoalPosition() {
        return goalPosition;
    }

    @Override
    public List<S> executeActions(S state) {
        List<S> successors = new LinkedList<>();

        for (Action action: actions) {
            if (action.isValid(state)){
                S successor = (S) state.clone();
                action.execute(successor);
                successors.add(successor);
            }
        }
        return successors;
    }

    public boolean isGoal(S state) {
        if(goalPosition.getLine() == state.getLineExit() && goalPosition.getColumn() == state.getColumnExit()) {//porta
            return state.getLineAgent()==getGoalPosition().getLine() && state.getColumnAgent()==getGoalPosition().getColumn();
        }
        //prateleira
        return state.getLineAgent()==getGoalPosition().getLine() && state.getColumnAgent()==getGoalPosition().getColumn()+1;

    }
}
