package com.imooc.sm.dao;

import com.imooc.sm.entity.Department;

import java.util.List;

public interface DepartmentDao {
    void insert(Department department);
    void update(Department department);
    void delete(Integer id);
    Department selectById(Integer id);
    List<Department> selectAll();
}