package com.shsxt.xm.api.service;

/**
 * Created by lp on 2017/12/8.
 */
public interface ISmsService {

    /**
     * 发送手机短信
     * @param phone
     * @param code
     * @param type
     */
    public  void sendPhoneSms(String phone,String code,Integer type);
}
