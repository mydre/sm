package com.imooc.sm.global;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String path = req.getServletPath();
        if(path.toLowerCase().indexOf( "login" ) != -1){//如果是登陆操作，则不进行过滤
            chain.doFilter( req,res );//放行
        }else{//如果是登陆操作意外的操作,则进行登陆验证
            Object obj = req.getSession().getAttribute( "USER" );
            if(obj != null){
                chain.doFilter( req,res );//已登陆，放行
            }else{//这里重定向，要采用绝对路径，不能采用相对路径，因为这里用户的请求的url是任意的
              res.sendRedirect( req.getContextPath() + "/toLogin.do" );
            }
        }
    }

    @Override
    public void destroy() {

    }
}
