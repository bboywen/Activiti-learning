package imporven.activiti.Rudiments.hello;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.junit.Test;

public class CustomerProcessEngine {

    @Test
    public void createProcessEngine(){
        //通过直接创建ProcessEngineConfiguration实现类获取 再设置开关属性
        ProcessEngineConfiguration processEngineConfiguration = new StandaloneProcessEngineConfiguration();
        processEngineConfiguration.setDatabaseType(ProcessEngineConfigurationImpl.DATABASE_TYPE_MSSQL);
        processEngineConfiguration.setDatabaseSchemaUpdate("true");
        processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/activiti6ui?characterEncoding=UTF-8");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("rootroot");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }

}
