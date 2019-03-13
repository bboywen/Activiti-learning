package imporven.activiti.Rudiments.finitestatemachine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-13 14:18
 * 有限状态机应用 加深对于有限状态机概念的理解
 * 有限状态机三个特性：
 *    * 状态总数（state）是有限的。
 * 　　* 任一时刻，只处在一种状态之中。
 * 　　* 某种条件下，会从一种状态转变（transition）到另一种状态。
 */
public class FiniteStataMachine {

    //初始状态 一个有限状态机的初始状态
    private Action initial;
    //当前状态 记录状态机运转当前状态
    private Action currentState;
    //状态变化路线集合
    private Map<Action,Transition> transferRoutes;

    /**
     * @param action 状态
     * @param transition 转移
     * @return 动作转移映射集合
     * 添加动作转移映射
     */
    public Map<Action, Transition> addTransferRoutes(Action action,Transition transition){
        getTransferRoutes().put(action,transition);
        return transferRoutes;
    }

    public Action transition(){
        currentState.leave();
        Transition transition = transferRoutes.get(currentState);
        if(transition.meetCondition()){
//            currentState = transition.getTo();
            currentState.enter();
        }
        return currentState;
    }

    public Action getInitial() {
        return initial;
    }

    public void setInitial(Action initial) {
        this.initial = initial;
    }

    public Action getCurrentState() {
        if(currentState == null){
            currentState = initial;
        }
        return currentState;
    }

    public Map<Action, Transition> getTransferRoutes() {
        if(transferRoutes.isEmpty()){
            transferRoutes = new HashMap<>();
        }
        return transferRoutes;
    }

}
