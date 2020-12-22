package com.pojo;

public class MyProperty {
    private String name;
    private String value;
    private boolean isDepend;
    private String dependName;
    private boolean isBeanAnn;

    public boolean isBeanAnn() {
        return isBeanAnn;
    }

    public void setBeanAnn(boolean beanAnn) {
        isBeanAnn = beanAnn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isDepend() {
        return isDepend;
    }

    public void setDepend(boolean depend) {
        isDepend = depend;
    }

    public String getDependName() {
        return dependName;
    }

    public void setDependName(String dependName) {
        this.dependName = dependName;
    }
}
