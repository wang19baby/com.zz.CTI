package com.zz.CTI.Util;

import org.springframework.beans.BeansException;  
import org.springframework.beans.factory.NoSuchBeanDefinitionException;  
import org.springframework.context.ApplicationContext;  
import org.springframework.context.ApplicationContextAware;  
import org.springframework.stereotype.Component;  
  
/**
 * 解决:spring中普通类调用service
 * SpringContextUtil实现ApplicationContextAware接口
 * @author zhou.zhang
 *
 */
@Component  
public class SpringContextUtil implements ApplicationContextAware {  
    private static ApplicationContext applicationContext = null;  
  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException  
    {  
        Logs.info("------SpringContextUtil setApplicationContext-------");  
        SpringContextUtil.applicationContext = applicationContext;  
    }  
  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    /** 
     * 注意 bean name默认 = 类名(首字母小写) 
     * 例如: A8sClusterDao = getBean("a8sClusterDao") 
     * @param name 
     * @return 
     * @throws BeansException 
     */  
    public static Object getBean(String name) throws BeansException {  
        return applicationContext.getBean(name);  
    }  
  
    /** 
     * 根据类名获取到bean 
     * @param <T> 
     * @param clazz 
     * @return 
     * @throws BeansException 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T getBeanByName(Class<T> clazz) throws BeansException {  
        try {  
            char[] cs=clazz.getSimpleName().toCharArray();  
            cs[0] += 32;//首字母大写到小写  
            return (T) applicationContext.getBean(String.valueOf(cs));  
        } catch (Exception e) {
        	Logs.error(e.getMessage());
            return null;  
        }   
    }  
  
    public static boolean containsBean(String name) {  
        return applicationContext.containsBean(name);  
    }  
  
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {  
        return applicationContext.isSingleton(name);  
    }  
}  