package imporven.activiti.Rudiments.finitestatemachine;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-14 10:17
 */
public class Transition{

    private State from;
    private State to;
    public Transition(State from, State to){
        this.from = from;
        this.to = to;
    }

    public State getFrom() {
        return from;
    }

    public void setFrom(State from) {
        this.from = from;
    }

    public State getTo() {
        return to;
    }

    public void setTo(State to) {
        this.to = to;
    }

}
