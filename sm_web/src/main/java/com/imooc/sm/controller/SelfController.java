package com.imooc.sm.controller;

import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.SelfService;
import com.imooc.sm.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller("selfController")
public class SelfController {

    @Autowired
    private SelfService selfService;

    @Autowired
    private StaffService staffService;

    //不进行判断，能到达这个页面说明之前没有登陆
    public void toLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //请求转发首先访问的是某个jsp页面，重定向首先访问的是Controller的某个方法

        //这里的url本身就在 webapp目录下，中间没有参杂其他
        request.getRequestDispatcher( "login.jsp" ).forward( request,response );
    }


    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Staff staff = null;
        String password = request.getParameter( "password" );
        String account = request.getParameter( "account" );
        staff = selfService.login( account,password );
        if(staff!=null){
            request.getSession().setAttribute( "USER",staff );
            response.sendRedirect( "main.do" );
        }else{
            response.sendRedirect( "toLogin.do" );
        }
    }

    //  logout.do
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.getSession().setAttribute( "USER",null );
        response.sendRedirect( "toLogin.do" );
    }

    //  main.do
    public void main(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher( "index.jsp" ).forward( request,response );
    }

    //  self/info.do  //二级目录，访问info.jsp需要在之前加上 ../info.jsp
    public void info(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher( "../info.jsp" ).forward( request,response );
    }

    //  self/toChangePassword.fo
    public void toChangePassword(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher( "../change_password.jsp" ).forward( request,response );
    }

    //  self/changePassword.do
    public void changePassword(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        Staff staff = (Staff)request.getSession().getAttribute( "USER" );

        String old_pas,new_pas1,new_pas2;
        old_pas = request.getParameter( "old_password" );
        new_pas1 = request.getParameter( "new_password1" );
        new_pas2 = request.getParameter( "new_password2" );
        System.out.println( old_pas + "     " + new_pas1 + "       " + new_pas2 );
        if(old_pas.equals( staff.getPassword() )){
            if(new_pas1 !="" && new_pas2!=""){
                if(new_pas1.equals( new_pas2 )){
                    staff.setPassword( new_pas1 );
                    staffService.edit( staff );
                    response.sendRedirect( "../logout.do" );//重新登陆
                }else{
                    //response.sendRedirect( "toChangePassword.do" );
                    response.getWriter().write( "<script type=\"text/javascript\">parent.location.href=\"../logout.do\"</script>" );
                }
            }else{
                response.sendRedirect( "toChangePassword.do" );
            }
        }else{
            response.sendRedirect( "toChangePassword.do" );
        }
    }
}
