package imporven.activiti.Rudiments.finitestatemachine.Test;

import imporven.activiti.Rudiments.finitestatemachine.Action;
import imporven.activiti.Rudiments.finitestatemachine.FiniteStataMachine;
import imporven.activiti.Rudiments.finitestatemachine.State;
import imporven.activiti.Rudiments.finitestatemachine.Transition;
import org.activiti.bpmn.model.Transaction;
import org.junit.Test;

import java.util.Map;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-13 15:40
 */
public class FiniteStateMachineTest {

    @Test
    public void test(){
        FiniteStataMachine light = new FiniteStataMachine();
        State gree = new State("gree");
        State yellow = new State("yellow");
        State red = new State("red");
//        light.setInitial(gree);
        Action warning = new Action("warning");
        Action stop = new Action("stop");
        Action ready = new Action("ready");
        Action go = new Action("go");
        Transition warningTransition = new Transition(gree,yellow);
        Transition stopTransition = new Transition(yellow,red);
        Transition readyTransition = new Transition(red,yellow);
        Transition goTtransition = new Transition(yellow,gree);
//        Map<State, Transition> transferRoutes = light.getTransferRoutes();
//        transferRoutes.put(warning,warningTransition);
//        transferRoutes.put(stop,stopTransition);
//        transferRoutes.put(ready,readyTransition);
//        transferRoutes.put(go,goTransition);
    }

}
