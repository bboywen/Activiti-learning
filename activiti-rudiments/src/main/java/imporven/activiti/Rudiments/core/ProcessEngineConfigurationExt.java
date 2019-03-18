package imporven.activiti.Rudiments.core;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.validator.ValidatorSet;
import org.activiti.validation.validator.ValidatorSetNames;

import java.util.List;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-19 1:17
 * 流程引擎配置类拓展
 */
public class ProcessEngineConfigurationExt extends SpringProcessEngineConfiguration {

    /**
     * TODO:通过配置xml进行拓展
     */
    @Override
    public void initProcessValidator() {
        processValidator = (new ProcessValidatorFactory()).createDefaultProcessValidator();
        List<ValidatorSet> validatorSets = processValidator.getValidatorSets();
        ValidatorSet validatorSet = new ValidatorSet(ValidatorSetNames.ACTIVITI_EXECUTABLE_PROCESS);
        validatorSet.addValidator(new UserTaskValidator());
        validatorSets.add(validatorSet);
    }
}
