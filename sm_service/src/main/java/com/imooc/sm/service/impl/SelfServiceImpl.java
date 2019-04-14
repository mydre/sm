package com.imooc.sm.service.impl;

import com.imooc.sm.dao.SelfDao;
import com.imooc.sm.dao.StaffDao;
import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.SelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("selfService")
public class SelfServiceImpl implements SelfService {

    @Autowired
    private SelfDao selfDao;

    @Autowired
    private StaffDao staffDao;


    public Staff login(String account, String password) {
        Staff staff=null;
        staff = selfDao.selectByAccount( account );
        if(staff == null){
            return null;
        }else{
            if(staff.getPassword().equals( password )){//如果密码验证通过
                return staff;
            }
            return null;//如果密码验证没有通过，自然返回空
        }
    }

    public void changePassword(Integer id, String password) {
        Staff staff = staffDao.selectById( id );
        staff.setPassword( password );
        staffDao.update( staff );
    }
}
