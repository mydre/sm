package com.imooc.sm.service;

import com.imooc.sm.entity.Staff;

import java.util.List;

public interface StaffService {
    void add(Staff staff);
    void edit(Staff staff);
    void remove(Integer id);
    Staff get(Integer id);
    List<Staff> getAll();
}