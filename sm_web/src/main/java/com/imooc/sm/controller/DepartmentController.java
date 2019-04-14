package com.imooc.sm.controller;

import com.imooc.sm.entity.Department;
import com.imooc.sm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//对应某个Controller中的某个方法，如department/list.do => departmentController,list方法
@Controller("departmentController")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    //该方法有两个参数，request和response。所以，这与DispatcherServlet中通过反射调用方法时传入的参数的类型是一致的
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> ls = departmentService.getAll();
        System.out.println(ls);
        request.setAttribute("LIST",ls);
        request.getRequestDispatcher("../department_list.jsp").forward(request,response);
    }


    public void toAdd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../department_add.jsp").forward(request,response);
    }

    public void add(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        Department department = new Department();
        department.setName(name);
        department.setAddress(address);
        departmentService.add(department);
        response.sendRedirect("list.do");
    }

    public void toEdit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Department department = departmentService.get(id);
        request.setAttribute("OBJ",department);
        request.getRequestDispatcher("../department_edit.jsp").forward(request,response);
    }

    public void edit(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        Department department = new Department();
        department.setId(id);
        department.setName(name);
        department.setAddress(address);
        departmentService.edit(department);
        response.sendRedirect("list.do");
    }

    public void remove(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        departmentService.remove(id);
        response.sendRedirect("list.do");
    }
}