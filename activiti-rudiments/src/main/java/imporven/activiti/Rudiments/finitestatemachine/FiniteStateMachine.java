package imporven.activiti.Rudiments.finitestatemachine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-14 10:16
 */
public class FiniteStateMachine {

    private State currentState;
    private Map<Action,Transition> transferRoutes = new HashMap<>();

    public State execute(Action action){
        action.onBefore();
        currentState.leave();
        Transition transition = transferRoutes.get(action);
        currentState = transition.getTo();
        currentState.enter();
        action.onAfter();
        return currentState;
    }

    public Map<Action,Transition> addTransferRoute(Action action,Transition transition){
        transferRoutes.put(action,transition);
        return transferRoutes;
    }

    public Map<Action, Transition> getTransferRoutes() {
        return transferRoutes;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
