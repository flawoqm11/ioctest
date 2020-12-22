package com.pojo;


import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {
    private String id;
    private String name;
    private boolean singleton = true;
    private String className;
    private boolean lazy;
    private List<MyProperty> propertyList = new ArrayList();


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public List<MyProperty> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<MyProperty> propertyList) {
        this.propertyList = propertyList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public void addProperty(MyProperty myProperty) {
        propertyList.add(myProperty);
    }
}
