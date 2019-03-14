package imporven.activiti.Rudiments.finitestatemachine;

import java.util.Objects;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-14 10:16
 */
public class State{

    private String name;
    public State(String name){
       this.name = name;
    }
    public void enter() {
        System.out.println("进入"+name+"状态");
    }

    public void leave() {
        System.out.println("离开"+name+"状态");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return name.equals(state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
