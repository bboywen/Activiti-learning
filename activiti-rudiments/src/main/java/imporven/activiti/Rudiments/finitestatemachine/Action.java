package imporven.activiti.Rudiments.finitestatemachine;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-13 14:37
 * 有限状态机  活动或动作
 */
public class Action {

  private String name;
  public Action(String name){
      this.name = name;
  }

    void enter(){
        System.out.println("================进入"+name+"状态=================");
    };
    void execute(){

    };
    void leave(){
        System.out.println("================离开"+name+"状态=================");
    };
}
