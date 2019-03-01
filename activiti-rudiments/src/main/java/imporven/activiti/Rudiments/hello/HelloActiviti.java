package imporven.activiti.Rudiments.hello;

import org.activiti.engine.*;
import org.activiti.form.api.FormService;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-2-20 15:50
 */
public class HelloActiviti {

    public static void main(String args[]){

        /**
         * 默认流程引擎
         */
        ProcessEngine processEngines = ProcessEngines.getDefaultProcessEngine();

        //动态流程服务
        DynamicBpmnService dynamicBpmnService = processEngines.getDynamicBpmnService();
        System.out.println("===dynamicBpmnService : " +  dynamicBpmnService + "===");

        //流程表单服务
        org.activiti.engine.FormService formService = processEngines.getFormService();
        System.out.println("===formService : " +  formService + "===");

        //流程历史服务
        HistoryService historyService = processEngines.getHistoryService();
        System.out.println("===historyService : " +  historyService + "===");

        //用户管理服务
        IdentityService identityService = processEngines.getIdentityService();
        System.out.println("===identityService : " +  identityService + "===");

        //执行命令或定时器服务
        ManagementService managementService = processEngines.getManagementService();
        System.out.println("===managementService : " +  managementService + "===");

        //流程定义期服务
        RepositoryService repositoryService = processEngines.getRepositoryService();
        System.out.println("===repositoryService : " +  repositoryService + "===");

        //运行时服务
        RuntimeService runtimeService = processEngines.getRuntimeService();
        System.out.println("===runtimeService : " +  runtimeService + "===");

        //任务管理服务
        TaskService taskService = processEngines.getTaskService();
        System.out.println("===taskService : " +  taskService + "===");
    }

}
