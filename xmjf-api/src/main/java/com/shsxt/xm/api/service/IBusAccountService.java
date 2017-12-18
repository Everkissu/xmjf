package com.shsxt.xm.api.service;

import com.shsxt.xm.api.dto.PayDto;
import com.shsxt.xm.api.po.BusAccount;

import java.math.BigDecimal;

/**
 * Created by lp on 2017/12/11.
 */
public interface IBusAccountService {

    public BusAccount queryBusAccountByUserId(Integer userId);

    public PayDto addRechargeRequestInfo(BigDecimal amount, String bussinessPassword,Integer userId);

    public void updateAccountRecharge(Integer userId,BigDecimal totalFee
,String outOrderNo,String sign,String tradeNo,String tradeStatus);
}
