package com.imooc.sm.global;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatcherServlet extends GenericServlet {

    private ApplicationContext applicationContext;

    public void init() throws ServletException {
        super.init();//当servlet容器创建并初始化的时候会创建IOC容器，而不是每次用户的请求都创建IOC容器。
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
        /*
        *      staff/add.do
        * */
        String path = httpServletRequest.getServletPath().substring(1);
        String beanName = null;
        String methodName = null;
        int index = path.indexOf("/");
        if(index == -1){//则表示是一个特殊的模块，有特定的Bean来处理。
            methodName = path.substring(0,path.indexOf(".do"));
            beanName = "selfController";//特殊的模块，用特殊的Controller来处理
        }else{
            beanName = path.substring(0,index) + "Controller";
            methodName = path.substring(index + 1,path.indexOf(".do"));
        }
        //如果每次请求都获取IOC容器，那么开销太大了，这里需要做适当的调整
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        Object obj = applicationContext.getBean(beanName);
        try {//通过反射来执行，并给方法传入参数
            Method method = obj.getClass().getMethod(methodName,HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(obj,httpServletRequest,httpServletResponse);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
