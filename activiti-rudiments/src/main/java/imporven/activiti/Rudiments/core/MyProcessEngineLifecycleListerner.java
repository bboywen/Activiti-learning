package imporven.activiti.Rudiments.core;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-1 10:49
 */
public class MyProcessEngineLifecycleListerner implements ProcessEngineLifecycleListener {

    public void onProcessEngineBuilt(ProcessEngine processEngine) {
        System.out.println("===============进入流程引擎构建监听===============");
        System.out.println(processEngine);
    }

    public void onProcessEngineClosed(ProcessEngine processEngine) {
        System.out.println("===============进入流程引擎关闭监听===============");
        System.out.println(processEngine);
    }
}
