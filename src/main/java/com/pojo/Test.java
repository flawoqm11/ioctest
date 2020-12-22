package com.pojo;

import com.bean.HelloWorld;
import com.factory.IocFactory;
import com.scanbean.Aut;
import com.scanbean.ScBean;

public class Test {
    public static void main(String[] args) throws Exception {
        IocFactory iocFactory = new IocFactory("bean.xml");
        try {
            HelloWorld helloWorld = (HelloWorld)iocFactory.getBean("helloWorld");
            Aut aut  = (Aut)iocFactory.getBean("aut");
            ScBean scBean  = (ScBean)iocFactory.getBean("scBean");
            System.out.println(helloWorld.getUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
