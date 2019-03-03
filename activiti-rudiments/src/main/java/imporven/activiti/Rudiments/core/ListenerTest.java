package imporven.activiti.Rudiments.core;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Before;
import org.junit.Test;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-1 12:34
 */
public class ListenerTest {

    ProcessEngine processEngine;

    @Before
    public void createProcessEngine(){
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    @Test
    public void listernerTest(){
        processEngine.close();
    }

}
