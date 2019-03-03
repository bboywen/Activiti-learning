package imporven.activiti.Rudiments.core;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-2-28 11:22
 * 通过ServiceLoader装在方式加载流程引擎配置
 */
public class ProcessEngineConfigurationByServiceLoadWay implements ProcessEngineConfigurator {

    public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        System.out.println("=====================进入ServiceLoader装载方式配置前初始化=======================");
    }

    public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
        System.out.println("=====================进入ServiceLoader装载方式配置后初始化=======================");
    }

    public int getPriority() {
        return 10000;
    }
}
