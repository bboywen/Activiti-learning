package imporven.activiti.Rudiments.finitestatemachine;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-14 10:17
 */
public class Action {

    private String name;
    public Action(String name){
        this.name = name;
    }
    public void onBefore() {
        System.out.println("执行"+name+"行为前事件");
    }

    public void onAfter() {
        System.out.println("执行"+name+"行为后事件");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
