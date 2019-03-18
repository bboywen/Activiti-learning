package imporven.activiti.Rudiments.repository;

import imporven.activiti.Rudiments.core.UserTaskValidator;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.ValidationError;
import org.activiti.validation.validator.ValidatorSet;
import org.activiti.validation.validator.ValidatorSetNames;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-18 23:21
 */
public class BpmnModelValidate {

    ProcessEngine processEngine;
    RepositoryService repositoryService;

    @Before
    public void initProcess() {
        processEngine = ProcessEngines.getProcessEngine("spring");
        repositoryService = processEngine.getRepositoryService();
    }

    /**
     * 流程定义校验
     * 测试发现问题：直接校验时发现一直会提示Invalid source for sequenceflow，而deploy流程确能成功.
     * 原因：1）直接校验bpmnModel时候SequenceFlowValidator会直接通过Process的flowElementMap来获取 需要给map赋值
     *       2）deploy能成功是因为其在部署过程中给flowElementMap赋值了.
     * */
    @Test
    public void validateBpmnModel(){

        BpmnModel bpmnModel = createBpmnModel();

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] bytes = bpmnXMLConverter.convertToXML(bpmnModel, "UTF-8");
        System.out.println(new String(bytes));

        ProcessValidator processValidator = (new ProcessValidatorFactory()).createDefaultProcessValidator();
        List<ValidatorSet> validatorSets = processValidator.getValidatorSets();
        ValidatorSet validatorSet = new ValidatorSet(ValidatorSetNames.ACTIVITI_EXECUTABLE_PROCESS);
        validatorSet.addValidator(new UserTaskValidator());
        validatorSets.add(validatorSet);
        List<ValidationError> validationErrors = processValidator.validate(bpmnModel);
        System.out.println(validationErrors.size());
        validationErrors.stream()
                .filter(validationError -> !validationError.isWarning())
                .map(ValidationError::getDefaultDescription)
                .forEach(System.out::println);

        Deployment deploy = repositoryService.createDeployment()
                .key("test")
                .name("测试流程")
                .addBpmnModel("test.bpmn", bpmnModel)
                .deploy();
        System.out.println(deploy);

    }

    /**
     * 手动构建BPMNModel
     */
    public BpmnModel createBpmnModel(){

        BpmnModel bpmnModel = new BpmnModel();

        Process process = new Process();
        Map<String, FlowElement> flowElementMap = process.getFlowElementMap();
        process.setName("测试流程");
        process.setId("test");

        StartEvent startEvent = new StartEvent();
        startEvent.setId("StartEvent_1");
        startEvent.setName("开始节点");
        process.getFlowElements().add(startEvent);
//        flowElementMap.put("StartEvent_1",startEvent);

        UserTask userTask1 = new UserTask();
        userTask1.setId("UserTask_1");
        userTask1.setName("录入申请单");
        process.getFlowElements().add(userTask1);
//        flowElementMap.put("UserTask_1",userTask1);


        UserTask userTask2 = new UserTask();
        userTask2.setId("UserTask_2");
        userTask2.setName("领导审批");
        process.getFlowElements().add(userTask2);
//        flowElementMap.put("UserTask_2",userTask2);


        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent_1");
        endEvent.setName("结束节点");
        process.getFlowElements().add(endEvent);
//        flowElementMap.put("endEvent_1",endEvent);


        SequenceFlow sequenceFlow1 = new SequenceFlow();
        sequenceFlow1.setId("squenceFlow1");
        sequenceFlow1.setSourceRef("StartEvent_1");
        sequenceFlow1.setTargetRef("UserTask_1");
        process.getFlowElements().add(sequenceFlow1);
//        flowElementMap.put("squenceFlow1",sequenceFlow1);


        SequenceFlow sequenceFlow2 = new SequenceFlow();
        sequenceFlow2.setId("sequenceFlow2");
        sequenceFlow2.setSourceRef("UserTask_1");
        sequenceFlow2.setTargetRef("UserTask_2");
        process.getFlowElements().add(sequenceFlow2);
//        flowElementMap.put("sequenceFlow2",sequenceFlow2);


        SequenceFlow sequenceFlow3 = new SequenceFlow();
        sequenceFlow3.setId("squenceFlow3");
        sequenceFlow3.setSourceRef("UserTask_2");
        sequenceFlow3.setTargetRef("endEvent_1");
        process.getFlowElements().add(sequenceFlow3);
//        flowElementMap.put("sequenceFlow3",sequenceFlow3);


        bpmnModel.getProcesses().add(process);

        return bpmnModel;
    }
}
