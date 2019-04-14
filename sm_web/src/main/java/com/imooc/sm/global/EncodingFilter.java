package com.imooc.sm.global;

import javax.servlet.*;
import java.io.IOException;

/**
 * 对用户的请求和相应进行编码
 */
public class EncodingFilter implements Filter {

    private String encoding = "UTF-8";//直接复制为UTF-8初始值，放置用户没有为ENCODING赋值

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(filterConfig.getInitParameter("ENCODING") != null)
            this.encoding = filterConfig.getInitParameter("ENCODING");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        //因为是编码过滤器，不做拦截，所以直接放行
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
