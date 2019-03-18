package imporven.activiti.Rudiments.repository;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.impl.util.io.BytesStreamSource;
import org.activiti.engine.repository.Deployment;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-12 11:12
 */
public class BpmnModelTest {

    ProcessEngine processEngine;
    RepositoryService repositoryService;

    @Before
    public void initProcess() {
        processEngine = ProcessEngines.getProcessEngine("spring");
        repositoryService = processEngine.getRepositoryService();
    }

    /**
     * 模型与xml的相互转换
     */
    @Test
    public void convertTest(){
        //获取最新部署对象
        Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentTenantId("A")
                .deploymentKey("leave")
                .deploymentCategory("HR")
                .latest()
                .singleResult();
        System.out.println(deployment.getId());

        //通过最新部署对象获取部署资源
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration();
        ResourceEntity resourceEntity = processEngineConfiguration.getCommandExecutor()
                .execute(new Command<ResourceEntity>() {
                    @Override
                    public ResourceEntity execute(CommandContext commandContext) {
                        return commandContext.getResourceEntityManager()
                                .findResourcesByDeploymentId(deployment.getId())
                                .stream().findFirst().orElse(null);
                    }
                });

        //部署资源转换成BPMNModel
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(new BytesStreamSource(resourceEntity.getBytes()), true, false, "UTF-8");
        Process process = bpmnModel.getProcesses().get(0);

        //获取BPMNModel的所有节点名称
        Collection<FlowElement> flowElements = process.getFlowElements();
        flowElements.stream()
                .map(FlowElement::getName)
                .forEach(System.out::println);

        //获取任务节点信息
        List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class, false);
        userTasks.forEach(userTask -> {
            System.out.println(userTask.getId());
            System.out.println(userTask.getName());
            System.out.println(userTask.getAssignee());
        });

        //获取流程图信息
        System.out.println("开始打印流程图节点信息");
        bpmnModel.getLocationMap().forEach((key,graphicInfo)->{
            printGraphicInfo(key, graphicInfo);
        });

        System.out.println("开始打印流程图标签信息");
        bpmnModel.getLabelLocationMap().forEach((key,graphicInfo)->{
            printGraphicInfo(key, graphicInfo);
        });

        System.out.println("开始打印流程图连线信息");
        bpmnModel.getFlowLocationMap().forEach((key,graphicInfos)->{
            graphicInfos.forEach(graphicInfo ->{
                printGraphicInfo(key, graphicInfo);
            });
        });

        //输出BPMNModel转换xml信息
        byte[] bytes = bpmnXMLConverter.convertToXML(bpmnModel, "UTF-8");
        System.out.println(new String(bytes));
    }

    private void printGraphicInfo(String key, GraphicInfo graphicInfo) {
        System.out.println(key + ":" + graphicInfo);
        System.out.println("X:" + graphicInfo.getX());
        System.out.println("Y:" + graphicInfo.getY());
        System.out.println("height:" + graphicInfo.getHeight());
        System.out.println("width:" + graphicInfo.getWidth());
        System.out.println("xmlColumnNumber:" + graphicInfo.getXmlColumnNumber());
        System.out.println("xmlRowNumber:" + graphicInfo.getXmlRowNumber());
        System.out.println("element:" + graphicInfo.getElement());
        System.out.println("expanded:" + graphicInfo.getExpanded());
    }

    /**
     * 手动构建BPMNModel
     */
    @Test
    public void createBpmnModel(){

        BpmnModel bpmnModel = new BpmnModel();

        Process process = new Process();
        process.setName("测试流程");
        process.setId("test");

        StartEvent startEvent = new StartEvent();
        startEvent.setId("StartEvent_1");
        startEvent.setName("开始节点");
        process.getFlowElements().add(startEvent);

        SequenceFlow sequenceFlow1 = new SequenceFlow();
        sequenceFlow1.setSourceRef("StartEvent_1");
        sequenceFlow1.setTargetRef("UserTask_1");
        process.getFlowElements().add(sequenceFlow1);

        UserTask userTask1 = new UserTask();
        userTask1.setId("UserTask_1");
        userTask1.setName("录入申请单");
        process.getFlowElements().add(userTask1);

        SequenceFlow sequenceFlow2 = new SequenceFlow();
        sequenceFlow2.setSourceRef("UserTask_1");
        sequenceFlow2.setTargetRef("UserTask_2");
        process.getFlowElements().add(sequenceFlow2);

        UserTask userTask2 = new UserTask();
        userTask2.setId("UserTask_2");
        userTask2.setName("领导审批");
        process.getFlowElements().add(userTask2);

        SequenceFlow sequenceFlow3 = new SequenceFlow();
        sequenceFlow3.setSourceRef("UserTask_2");
        sequenceFlow3.setTargetRef("endEvent_1");
        process.getFlowElements().add(sequenceFlow3);

        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent_1");
        endEvent.setName("结束节点");
        process.getFlowElements().add(endEvent);

        bpmnModel.getProcesses().add(process);

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] bytes = bpmnXMLConverter.convertToXML(bpmnModel, "UTF-8");
        System.out.println(new String(bytes));

        Deployment deploy = repositoryService.createDeployment()
                .addBytes("test.bpmn", bytes)
                .key("test")
                .name("BPMNModel部署测试")
                .deploy();
        System.out.println(deploy);

    }
}
