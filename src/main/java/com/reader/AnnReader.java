package com.reader;

import com.pojo.BeanDefinition;
import com.pojo.MyProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnReader {

    public Map<String,BeanDefinition> read() throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
        String url = getClassPath();
        List<String> classList = getClassFromUrl(new File(url));
        for (String cla : classList) {
            cla = cla.replace(url,"");
            cla = cla.substring(0,cla.indexOf(".")).replace("\\",".");
            Class<?> clazz = Class.forName(cla);
            Component component = clazz.getDeclaredAnnotation(Component.class);
            if (component != null ) {
                BeanDefinition beanDefinition = parseAnn(cla, clazz);
                beanDefinitionMap.putAll(parseAnnBean(cla, clazz));
                beanDefinitionMap.put(clazz.getSimpleName().substring(0,1).toLowerCase() + clazz.getSimpleName().substring(1), beanDefinition);
            }
        }
        return beanDefinitionMap;
    }

    //解析bean标签生成的bean
    private Map<? extends String,? extends BeanDefinition> parseAnnBean(String cla, Class<?> clazz) {
        Map beanDefinitionMap = new HashMap();
        //TODO
        return beanDefinitionMap;
    }

    private BeanDefinition parseAnn(String className, Class<?> clazz) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setClassName(className);
        beanDefinition.setName(clazz.getSimpleName());
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Autowired) {
                    MyProperty myProperty = new MyProperty();
                    myProperty.setName(field.getName().substring(0,1).toLowerCase() + field.getName().substring(1));
                    myProperty.setDepend(true);
                    myProperty.setDependName(field.getName().substring(0,1).toLowerCase() + field.getName().substring(1));
                    beanDefinition.addProperty(myProperty);
                }
                if (annotation instanceof Value) {
                    MyProperty myProperty = new MyProperty();
                    myProperty.setName(field.getName().substring(0,1).toLowerCase() + field.getName().substring(1));
                    myProperty.setValue((((Value) annotation).value()));
                    beanDefinition.addProperty(myProperty);
                }
            }
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Bean) {

                }
            }
        }
        return beanDefinition;
    }

    private List<String> getClassFromUrl(File file) {
        List<String> classList = new ArrayList<>();
        if (file.isDirectory()) {
            for (File files : file.listFiles()) {
                classList.addAll(getClassFromUrl(files));
            }
        } else {
            if (validateClass(file)) {
                classList.add(file.getPath());
            }
        }
        return classList;
    }

    boolean validateClass(File file) {
        String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        if (fileType.equals("class")) {
            return true;
        }
        return false;
    }

    private String getClassPath() throws UnsupportedEncodingException {
        String url = URLDecoder.decode(Context.class.getResource("/").getPath(), String.valueOf(Charset.defaultCharset()));
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = url.replaceAll("/", "\\\\");
        return url;
    }

    public static void main(String[] args) throws Exception {
        AnnReader annReader = new AnnReader();
        annReader.read();
    }
}
