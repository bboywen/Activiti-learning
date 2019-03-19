package imporven.activiti.Rudiments.core;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.junit.Test;

import java.util.Calendar;
import java.util.UUID;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-19 15:58
 * 自定义id生成器
 */
public class IDGenerator implements IdGenerator {

    @Override
    public synchronized String getNextId() {
        long time = Calendar.getInstance().getTimeInMillis();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return time + uuid;
    }

    @Test
    public void uuid(){
        System.out.println(getNextId());
        System.out.println(getNextId().length());
    }
}
