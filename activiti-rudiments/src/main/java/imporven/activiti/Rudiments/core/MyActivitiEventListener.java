package imporven.activiti.Rudiments.core;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-1 10:55
 */
public class MyActivitiEventListener implements ActivitiEventListener {

    public void onEvent(ActivitiEvent activitiEvent) {
        System.out.println("==================进入Activiti事件监听器===================");
        System.out.println("====================发布的事件为 : " + activitiEvent);
    }

    public boolean isFailOnException() {
        System.out.println("================进入Activiti事件监听器异常方法===============");
        return false;
    }
}
