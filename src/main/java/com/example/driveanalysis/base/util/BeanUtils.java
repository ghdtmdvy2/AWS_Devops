package com.example.driveanalysis.base.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

// Bean 을 수동적으로 등록 해주는 BeanUtils
@Component
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    // ApplicationContext 를 세팅 해준다.
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtils.applicationContext = applicationContext;
    }
    // 해당 return 이 되는 객체는 Bean 으로 등록이 되게 해준다.
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
