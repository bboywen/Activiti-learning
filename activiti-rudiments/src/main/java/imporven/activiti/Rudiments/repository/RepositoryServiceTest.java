package imporven.activiti.Rudiments.repository;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Consumer;
import java.util.zip.ZipInputStream;

public class RepositoryServiceTest {

    ProcessEngine processEngine;
    RepositoryService repositoryService;

    @Before
    public void initProcess() {
        processEngine = ProcessEngines.getProcessEngine("spring");
        repositoryService = processEngine.getRepositoryService();
    }

    /**
     * 通过classpath方式来部署流程
     */
    @Test
    public void createRepositoryByClasspathTest() {
        Deployment deploy = repositoryService.createDeployment()
                .enableDuplicateFiltering()  //启用去重
                .addClasspathResource("diagrame/leave.bpmn")
                .name("请假申请")
                .key("leave")
                .tenantId("A")
                .category("HR")
                .deploy();
        System.out.println(deploy.getCategory());
        System.out.println(deploy.getDeploymentTime());
        System.out.println(deploy.getId());
        System.out.println(deploy.getKey());
        System.out.println(deploy.getName());
        System.out.println(deploy.getTenantId()); //租户id 多个系统租户继承流程引擎时分别系统

    }

    /**
     *
     */
    @Test
    public void queryTest() {
        //部署查询
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        Deployment hr = deploymentQuery.deploymentCategory("HR")
                .deploymentKey("leave")
                .deploymentTenantId("A")
                .latest()
                .singleResult();
        System.out.println(hr);

        //流程定义查询
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionTenantId("A")
                .processDefinitionKey("leave")
                .latestVersion()
                .singleResult();
        System.out.println(processDefinition);

        //模型查询
        Model model = repositoryService.createModelQuery()
                .modelTenantId("A")
                .modelCategory("HR")
                .modelKey("leave")
                .latestVersion()
                .singleResult();
        System.out.println(model);
    }

    @Test
    /**
     * 文本部署方式
     */
    public void createRepositoryByStringTest() {

        Deployment deploy = repositoryService.createDeployment()
                .addString("leave", IoUtil.readFileAsString("diagrame/leave.bpmn"))
                .name("请假申请")
                .key("leave")
                .tenantId("A")
                .category("HR")
                .deploy();
        System.out.println(deploy);

    }

    @Test
    /**
     * 字节部署方式
     */
    public void createRepositoryByBytesTest() {

        Deployment deploy = repositoryService.createDeployment()
                .addBytes("leave", IoUtil.readInputStream(RepositoryServiceTest.class.getClassLoader().getResourceAsStream("diagrame/leave.bpmn"), "leave"))
                .name("请假申请")
                .key("leave")
                .tenantId("A")
                .category("HR")
                .deploy();
        System.out.println(deploy);

    }

    @Test
    /**
     * 文件流部署方式
     */
    public void createRepositoryByInputStreamTest() throws FileNotFoundException {
        Deployment deploy = repositoryService.createDeployment()
                .addInputStream("leave", RepositoryServiceTest.class.getClassLoader().getResourceAsStream("diagrame/leave.bpmn"))
                .name("请假申请")
                .key("leave")
                .tenantId("A")
                .category("HR")
                .deploy();
        System.out.println(deploy);
    }

    @Test
    /**
     * 压缩流部署方式
     */
    public void createRepositoryByZipInputStreamTest() throws FileNotFoundException {
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(new ZipInputStream(RepositoryServiceTest.class.getClassLoader().getResourceAsStream("diagrame/leave.zip")))
                .name("请假申请")
                .key("leave")
                .tenantId("A")
                .category("HR")
                .deploy();
        System.out.println(deploy);
    }

    @Test
    /**
     * BPMNModel部署方式
     */
    public void createRepositoryByBpmnModelTest() throws FileNotFoundException {
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(new InputStreamSource(RepositoryServiceTest.class.getClassLoader().getResourceAsStream("diagrame/leave.bpmn")), true, false, "UTF-8");

        Deployment deploy = repositoryService.createDeployment()
                .addBpmnModel("leave", bpmnModel)
                .name("请假申请")
                .key("leave")
                .tenantId("A")
                .category("HR")
                .deploy();
        System.out.println(deploy);

    }

}
