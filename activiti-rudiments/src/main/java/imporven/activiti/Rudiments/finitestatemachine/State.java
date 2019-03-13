package imporven.activiti.Rudiments.finitestatemachine;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-13 14:19
 * 有限状态机  状态
 */
public class State {

    private String name;

    public State(String name){
        this.name = name;
    }

    void onEnter(){
        System.out.println("================进入"+name+"状态=================");
    };
    void onLeave(){
        System.out.println("================离开"+name+"状态=================");
    };

}
