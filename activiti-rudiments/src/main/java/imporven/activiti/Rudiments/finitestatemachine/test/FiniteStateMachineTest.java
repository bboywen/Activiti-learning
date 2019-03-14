package imporven.activiti.Rudiments.finitestatemachine.test;

import imporven.activiti.Rudiments.finitestatemachine.Action;
import imporven.activiti.Rudiments.finitestatemachine.FiniteStateMachine;
import imporven.activiti.Rudiments.finitestatemachine.State;
import imporven.activiti.Rudiments.finitestatemachine.Transition;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-14 10:39
 */
public class FiniteStateMachineTest {

    @Test
    public void execute() {
        //创建路灯有限状态机
        FiniteStateMachine light = new FiniteStateMachine();

        //创建状态
        State green = new State("green");
        State yellow = new State("yellow");
        State red = new State("red");

        //创建行为
        Action warning = new Action("warning");
        Action stop = new Action("stop");
        Action ready = new Action("ready");
        Action go = new Action("go");

        //创建转移
        Transition warningTransition = new Transition(green,yellow);
        Transition stopTransition = new Transition(yellow, red);
        Transition readyTransition = new Transition(red, yellow);
        Transition goTransition = new Transition(yellow, green);

        //初始化状态
        light.setCurrentState(new State("green"));

        //初始化转移
        light.addTransferRoute(warning,warningTransition);
        light.addTransferRoute(stop,stopTransition);
        light.addTransferRoute(ready,readyTransition);
        light.addTransferRoute(go,goTransition);

        light.execute(warning);
        Assert.assertEquals(light.getCurrentState().getName(),"yellow");

        light.execute(stop);
        Assert.assertEquals(light.getCurrentState().getName(),"red");

        light.execute(ready);
        Assert.assertEquals(light.getCurrentState().getName(),"yellow");

        light.execute(go);
        Assert.assertEquals(light.getCurrentState().getName(),"green");

    }

}
