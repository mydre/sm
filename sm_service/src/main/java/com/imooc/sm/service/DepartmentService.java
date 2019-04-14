package com.imooc.sm.service;

import com.imooc.sm.entity.Department;

import java.util.List;

public interface DepartmentService {
    void add(Department department);
    void edit(Department department);
    void remove(Integer id);
    Department get(Integer id);
    List<Department> getAll();
}
