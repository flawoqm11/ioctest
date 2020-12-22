package com.reader;

import com.pojo.BeanDefinition;
import com.pojo.MyProperty;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlReader {
    static String BEAN_LABEL = "bean";
    static String PROPERTY_LABEL = "property";
    public Map readXml(String file) throws DocumentException {
        Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("D:\\ideaworkspacedai\\ioctest\\src\\main\\resources\\bean.xml"));
        // 通过document对象获取根节点

        Element root = document.getRootElement();
        // 通过element对象的elementIterator方法获取迭代器
        Iterator it = root.elementIterator();
        // 遍历迭代器，获取根节点中的信息（书籍）
        while (it.hasNext()) {
            BeanDefinition beanDefinition = new BeanDefinition();

            System.out.println("=====开始遍历=====");
            Element node = (Element) it.next();
            String nodeName = node.getQName().getName();
            System.out.println("标签名为：" + node.getQName().getName());
            if ("bean".equals(nodeName)) {
                // 获取book的属性名以及 属性值
                List<Attribute> bookAttrs = node.attributes();
                for (Attribute attr : bookAttrs) {
                    if ("id".equals(attr.getName())) {
                        beanDefinition.setId(attr.getValue());
                        System.out.println("id为 :"  + attr.getValue());
                    }
                    if ("name".equals(attr.getName())) {
                        beanDefinition.setName(attr.getValue());
                    }
                    if ("class".equals(attr.getName())) {
                        beanDefinition.setClassName(attr.getValue());
                    }
                    if ("lazy-init".equals(attr.getName())) {
                        beanDefinition.setLazy(new Boolean(attr.getValue()));
                    }
                }
                Iterator itt = node.elementIterator();
                while (itt.hasNext()) {
                    Element nodeChild = (Element) itt.next();
                    String childNodeName = nodeChild.getQName().getName();
                    if ("property".equals(childNodeName)) {
                        MyProperty myProperty = new MyProperty();
                        List<Attribute> childAttrs = nodeChild.attributes();
                        for (Attribute attr : childAttrs) {
                            if ("name".equals(attr.getName())) {
                                myProperty.setName(attr.getValue());
                            }
                            if ("value".equals(attr.getName())) {
                                myProperty.setValue(attr.getValue());
                            }
                        }
                        Iterator iitt = nodeChild.elementIterator();
                        while (iitt.hasNext()) {
                            Element nnChild = (Element) iitt.next();
                            String ccNodeName = nnChild.getQName().getName();
                            if ("ref".equals(ccNodeName)) {
                                List<Attribute> ccAttrs = nnChild.attributes();
                                for (Attribute attr : ccAttrs) {
                                    if ("bean".equals(attr.getName())) {
                                        myProperty.setDepend(true);
                                        myProperty.setDependName(attr.getValue());
                                    }
                                }
                            }
                        }
                        beanDefinition.addProperty(myProperty);
                    }
                }
            }
            System.out.println("=====结束遍历=====");
            if (StringUtils.isEmpty(beanDefinition.getName())) {
                beanDefinition.setName(beanDefinition.getId());
            }
            beanDefinitionMap.put(beanDefinition.getName(), beanDefinition);
        }
        return beanDefinitionMap;
    }
}
