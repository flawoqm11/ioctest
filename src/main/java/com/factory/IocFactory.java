package com.factory;

import com.pojo.BeanDefinition;
import com.pojo.MyProperty;
import com.reader.AnnReader;
import com.reader.XmlReader;
import org.dom4j.DocumentException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class IocFactory {
    public IocFactory(String file) throws Exception {
        System.out.println("=====加载beanDefinition=====");

        XmlReader xmlReader = new XmlReader();
        AnnReader annReader = new AnnReader();
        try {
            beanDefinitionMap= new HashMap<>();

            beanDefinitionMap.putAll(xmlReader.readXml(file));
            beanDefinitionMap.putAll(annReader.read());
            System.out.println("=====加载beanDefinition完成=====");
        } catch (DocumentException | UnsupportedEncodingException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        System.out.println("=====创建单例bean=====");
        Set<String> set = beanDefinitionMap.keySet();
        for(String s : set) {
            if (!beanDefinitionMap.get(s).isLazy() && beanDefinitionMap.get(s).isSingleton()){
                getBean(s);
            }
        }
        System.out.println("=====创建单例bean完成=====");

    }
    public Map<String, BeanDefinition> beanDefinitionMap;
    public Map<String,Object> beanMap = new HashMap<String, Object>();

    public Object getBean(String beanName) throws Exception {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new Exception("不存在beanDefinition");
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition.isSingleton()) {
            if (beanMap.containsKey(beanName)) {
                return beanMap.get(beanName);
            }
        }
        return createBean(beanName);
    }

    public Object createBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        Object object = null;
        try {
            Class<?> clazz = Class.forName(beanDefinition.getClassName());
            object = clazz.newInstance();
            List<MyProperty> propertyList = beanDefinition.getPropertyList();
            for (MyProperty myProperty : propertyList) {
                setPropertyForObject(object, myProperty);
            }
            beanMap.put(beanName, object);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
    public Object setPropertyForObject(Object object, MyProperty myProperty) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String pName = myProperty.getName();
        String pValue = myProperty.getValue();
        Field field = object.getClass().getDeclaredField(pName);
        Class<?> fieldType = field.getType();
        String sMethod = "set" + pName.substring(0,1).toUpperCase() + pName.substring(1);
        Method method = object.getClass().getMethod(sMethod, fieldType);
        //判断依赖 有依赖的bean的话再次调用createBean生成依赖bean
        if (myProperty.isDepend()) {
            Object objectDe = createBean(myProperty.getDependName());
            method.invoke(object, objectDe);
            return object;
        }
        String typeName = fieldType.getSimpleName();
        if ("String".equals(typeName)) {
            method.invoke(object,pValue);
        }
        if ("Integer".equals(typeName)) {
            method.invoke(object,new Integer(pValue));
        }
        return object;
    }

    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }

    public void setBeanDefinitionMap(Map<String, BeanDefinition> beanDefinitionMap) {
        this.beanDefinitionMap = beanDefinitionMap;
    }

    public Map<String, Object> getBeanMap() {
        return beanMap;
    }

    public void setBeanMap(Map<String, Object> beanMap) {
        this.beanMap = beanMap;
    }

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }
}
