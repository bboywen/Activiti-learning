package imporven.activiti.Rudiments.core;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.validation.ValidationError;
import org.activiti.validation.validator.ProcessLevelValidator;
import org.activiti.validation.validator.ValidatorImpl;

import java.util.List;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-18 23:05
 */
public class UserTaskValidator extends ProcessLevelValidator {
    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class);
        userTasks.forEach(userTask -> {
            if(userTask.getAssignee() == null){
                this.addError(errors, userTask.getId()+"人工节点没有处理人", process, userTask, userTask.getId()+"人工节点没有处理人");
            }
        });
    }
}
