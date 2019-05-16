package tree.configure;

import org.opencv.core.Core;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

public class SpringMain implements InitializingBean, ServletContextAware {


//    public static void main(String[] args) {
//        System.out.println("11111111111111111111");
//        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:spring-mvc.xml");
//        ((ClassPathXmlApplicationContext) ac).registerShutdownHook();
//        ((ClassPathXmlApplicationContext) ac).start();
//        System.out.println("222222222222");
//
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        System.out.println("java.library.path------------" + System.getProperty("java.library.path"));
        System.out.println("Core library ------------" + Core.NATIVE_LIBRARY_NAME);
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.load("D:\\project\\server\\libs\\opencv_java320.dll");
    }
}
