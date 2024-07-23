package edu.innotech;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

@Component
public class LogSaverBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Object> beans = new HashMap<>();
    private final Logger LOGGER = Logger.getLogger(LogSaverBeanPostProcessor.class.getName());

    @Value("${spring.application.logfilepath}")
    private String logfilepath;

    @Value("${spring.application.logfilename}")
    private String logfilename;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException{
        System.out.println(".postProcessBeforeInitialization: beanName="+beanName);

        if (Arrays.stream(bean.getClass().getDeclaredMethods())
                                .anyMatch(m -> m.isAnnotationPresent(LogTransformation.class)))
            beans.put(beanName, bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beans.containsKey(beanName)){
            beans.remove(beanName);
            //return saveToLogProxy(bean);
        }
        return bean;
    }
    private Object saveToLogProxy(Object bean){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if(!method.isAnnotationPresent(LogTransformation.class)) return proxy.invokeSuper(obj, args);

            Object res = proxy.invokeSuper(obj, args);
            System.out.println(".saveToLogProxy: запускаем запись в лог для бина " + bean.getClass().getName());
            String msg = "Запись данных в БД. Время: "
                    + new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(Calendar.getInstance().getTime());
            System.out.println(".postProcessAfterInitialization: msg="+msg);
            LogSaver.saveToLog(msg);
            return res;
        });
        return enhancer.create();
    }
}
