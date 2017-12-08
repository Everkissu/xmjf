package com.shsxt.xm.api.service;

import com.shsxt.xm.api.po.BasUser;

/**
 * Created by lp on 2017/12/7.
 *  用户模块接口方法定义
 */
public interface IBasUserService {
    public BasUser queryBasUserById(Integer id);
    public  BasUser queryBasUserByPhone(String phone);

    /**
     * 保存用户记录
     * @param phone
     * @param password
     */
    public  void saveBasUser(String phone,String password);
}
