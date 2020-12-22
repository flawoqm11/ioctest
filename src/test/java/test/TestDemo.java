package test;

import com.bean.HelloWorld;
import com.pojo.BeanDefinition;
import com.pojo.MyProperty;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class TestDemo {
    @Test
    public void doSomething() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        HelloWorld h = (HelloWorld) applicationContext.getBean("helloWorld");
        System.out.println(h.getUser());
    }

    @Test()
    public void readXml() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("D:\\ideaworkspacedai\\ioctest\\src\\main\\resources\\bean.xml"));
        // 通过document对象获取根节点bookstore

        Element bookStore = document.getRootElement();
        // 通过element对象的elementIterator方法获取迭代器
        Iterator it = bookStore.elementIterator();
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
                    }
                    if ("name".equals(attr.getName())) {
                        beanDefinition.setName(attr.getValue());
                    }
                    if ("class".equals(attr.getName())) {
                        beanDefinition.setClassName(attr.getValue());
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
                        beanDefinition.addProperty(myProperty);
                    }
                }
                System.out.println("=====结束遍历某一本书=====");
            }

        }
    }

}
