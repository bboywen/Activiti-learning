package imporven.activiti.Rudiments.repository;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-19 22:28
 */
public class ImageTest {


    ProcessEngine processEngine;
    RepositoryService repositoryService;

    @Before
    public void initProcess() {
        processEngine = ProcessEngines.getProcessEngine("spring");
        repositoryService = processEngine.getRepositoryService();
    }

    /**
     * 流程图片相关操作了解
     */
    @Test
    public void viewImage() throws IOException {

        Deployment deployment = Optional.of(repositoryService.createDeploymentQuery()
                .deploymentKey("leave")
                .latest()
                .singleResult()).orElseThrow(()->new RuntimeException("没有找到key为leave的部署对象"));

        List<String> deploymentResourceNames = repositoryService.getDeploymentResourceNames(deployment.getId());
        String pngName = deploymentResourceNames.stream()
                .filter(resourceName -> resourceName.endsWith(".png"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("没有找到相应图片名称"));

        String fileName = "C:\\" + pngName;
        InputStream resourceAsStream = repositoryService.getResourceAsStream(deployment.getId(), pngName);
        FileUtils.copyInputStreamToFile(resourceAsStream,new File(fileName));

    }

}
