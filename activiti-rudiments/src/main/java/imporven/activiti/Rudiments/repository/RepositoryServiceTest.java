package imporven.activiti.Rudiments.repository;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.function.Consumer;

public class RepositoryServiceTest {

    ProcessEngine processEngine;
    RepositoryService repositoryService;
    Consumer<Object> println;

    @Before
    public void initProcess(){
        processEngine = ProcessEngines.getProcessEngine("spring");
        repositoryService = processEngine.getRepositoryService();
        PrintStream printStream = System.out;
        println = printStream::println;
    }

    @Test
    public void createRepositoryTest(){
        println.accept(repositoryService);
    }

}
