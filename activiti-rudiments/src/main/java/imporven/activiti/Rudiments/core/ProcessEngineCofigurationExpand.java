package imporven.activiti.Rudiments.core;

import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;

public class ProcessEngineCofigurationExpand implements ProcessEngineConfigurator {

    public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        System.out.println("===========进入初始化之前操作============");
    }

    public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
        System.out.println("===========进入初始化之后操作============");
    }

    public int getPriority() {
        return 0;
    }

}
