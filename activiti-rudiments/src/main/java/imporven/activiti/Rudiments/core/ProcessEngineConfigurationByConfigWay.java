package imporven.activiti.Rudiments.core;

import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-1 18:26
 */
public class ProcessEngineConfigurationByConfigWay implements ProcessEngineConfigurator {
    public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        System.out.println("=====================进入activiti.cfg.xml装载方式初始化前配置拓展=======================");
    }

    public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
        System.out.println("=====================进入activiti.cfg.xml装载方式初始化后配置拓展=======================");
    }

    public int getPriority() {
        return 10000;
    }
}
