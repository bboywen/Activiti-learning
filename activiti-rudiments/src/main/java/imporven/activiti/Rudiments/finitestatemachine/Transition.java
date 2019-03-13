package imporven.activiti.Rudiments.finitestatemachine;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-13 14:20
 * 有限状态机 转移
 */
public class Transition {

    private State from;
    private State to;

    public Transition(State form, State to){
        this.from = form;
        this.to = to;
    }

    public Boolean meetCondition(){
        //TODO
        return true;
    }

    void onBefore(){
//        System.out.println("================执行"+name+"行为前操作=================");
    };
    void onAfter(){

//        System.out.println("================执行"+name+"行为后=================");
    };

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }
}
