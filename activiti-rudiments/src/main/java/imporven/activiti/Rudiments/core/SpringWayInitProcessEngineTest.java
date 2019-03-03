package imporven.activiti.Rudiments.core;

import org.activiti.engine.*;

public class SpringWayInitProcessEngineTest {

    public static void main(String[] args) {
        /**
         * 默认流程引擎
         */
        ProcessEngine processEngines = ProcessEngines.getProcessEngine("spring");

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

        processEngines.close();
    }

}
