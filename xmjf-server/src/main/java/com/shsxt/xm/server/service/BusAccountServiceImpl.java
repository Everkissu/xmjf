package com.shsxt.xm.server.service;

import com.shsxt.xm.api.constant.P2PConstant;
import com.shsxt.xm.api.constant.YunTongFuConstant;
import com.shsxt.xm.api.dto.PayDto;
import com.shsxt.xm.api.po.BasUser;
import com.shsxt.xm.api.po.BasUserSecurity;
import com.shsxt.xm.api.po.BusAccount;
import com.shsxt.xm.api.po.BusAccountRecharge;
import com.shsxt.xm.api.service.IBasUserSecurityService;
import com.shsxt.xm.api.service.IBasUserService;
import com.shsxt.xm.api.service.IBusAccountService;
import com.shsxt.xm.api.utils.AssertUtil;
import com.shsxt.xm.server.db.dao.BusAccountDao;
import com.shsxt.xm.server.db.dao.BusAccountRechargeDao;
import com.shsxt.xm.server.utils.MD5;
import com.shsxt.xm.server.utils.Md5Util;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lp on 2017/12/11.
 */
@Service
public class BusAccountServiceImpl implements IBusAccountService {
    @Resource
    private BusAccountDao busAccountDao;
    @Resource
    private IBasUserService basUserService;

   @Resource
   private BusAccountRechargeDao busAccountRechargeDao;

    @Resource
    private IBasUserSecurityService basUserSecurityService;
    @Override
    public BusAccount queryBusAccountByUserId(Integer userId) {
        return busAccountDao.queryBusAccountByUserId(userId);
    }

    @Override
    public PayDto addRechargeRequestInfo(BigDecimal amount, String bussinessPassword, Integer userId) {
        checkParams(amount,bussinessPassword,userId);
        /**
         * 构建支付请求参数信息
         */
        BusAccountRecharge busAccountRecharge=new BusAccountRecharge();
        busAccountRecharge.setAddtime(new Date());
        busAccountRecharge.setFeeAmount(BigDecimal.ZERO);
        String orderNo= com.shsxt.xm.server.utils.StringUtils.getOrderNo();
        busAccountRecharge.setOrderNo(orderNo);
        busAccountRecharge.setFeeRate(BigDecimal.ZERO);
        busAccountRecharge.setRechargeAmount(amount);
        busAccountRecharge.setRemark("PC端用户充值");
        busAccountRecharge.setResource("PC端用户充值");
        busAccountRecharge.setStatus(2);
        busAccountRecharge.setType(3);
        busAccountRecharge.setUserId(userId);
        AssertUtil.isTrue(busAccountRechargeDao.insert(busAccountRecharge)<1, P2PConstant.OPS_FAILED_MSG);
        PayDto payDto=new PayDto();
        payDto.setBody("PC端用户充值操作");
        payDto.setOrderNo(orderNo);
        payDto.setSubject("PC端用户充值操作");
        payDto.setTotalFee(amount);
        String md5Sign=buildMd5Sign(payDto);
        payDto.setSign(md5Sign);
        return payDto;
    }

    @Override
    public void updateAccountRecharge(Integer userId, BigDecimal totalFee,
                                      String outOrderNo, String sign, String tradeNo,
                                      String tradeStatus) {
        AssertUtil.isTrue(null==userId||null==basUserService.queryBasUserById(userId),
                "用户未登录！");
        AssertUtil.isTrue(null==totalFee||StringUtils.isBlank(outOrderNo)||StringUtils.isBlank(sign)
                ||StringUtils.isBlank(tradeNo)||StringUtils.isBlank(tradeStatus),"回调参数异常");
        Md5Util md5Util = new Md5Util();

        String tempStr=md5Util.encode(outOrderNo+totalFee+toString()+tradeStatus+
                YunTongFuConstant.PARTNER+YunTongFuConstant.KEY,null);
        AssertUtil.isTrue(!tempStr.equals(sign),"订单信息异常，请联系客服！");
        AssertUtil.isTrue(!tradeStatus.equals(YunTongFuConstant.TRADE_STATUS_SUCCESS),"订单支付失败！");

        BusAccountRecharge busAccountRecharge = busAccountRechargeDao.queryBusAccountRechargeByOrderNo(outOrderNo);
        AssertUtil.isTrue(null==busAccountRecharge,"订单记录不存在，请联系管理员！");
        AssertUtil.isTrue(busAccountRecharge.getStatus().equals(1),"该订单已支付！");
        AssertUtil.isTrue(busAccountRecharge.getStatus().equals(0),"订单异常，请联系客服！");

    }

    private String buildMd5Sign(PayDto payDto) {
        StringBuffer arg = new StringBuffer();
        if(!StringUtils.isBlank(payDto.getBody())){
            arg.append("body="+payDto.getBody()+"&");
        }
        arg.append("notify_url="+payDto.getNotifyUrl()+"&");
        arg.append("out_order_no="+payDto.getOrderNo()+"&");
        arg.append("partner="+payDto.getPartner()+"&");
        arg.append("return_url="+payDto.getReturnUrl()+"&");
        arg.append("subject="+payDto.getSubject()+"&");
        arg.append("total_fee="+payDto.getTotalFee().toString()+"&");
        arg.append("user_seller="+payDto.getUserSeller());
        String tempSign= StringEscapeUtils.unescapeJava(arg.toString());
        Md5Util md5Util=new Md5Util();
        return md5Util.encode(tempSign+payDto.getKey(),"");
    }

    /**
     * 基本参数校验
     * @param amount
     * @param bussinessPassword
     * @param userId
     */
    private void checkParams(BigDecimal amount, String bussinessPassword, Integer userId) {
        AssertUtil.isTrue(amount.compareTo(BigDecimal.ZERO)<=0,"充值金额非法!");
        BasUserSecurity basUserSecurity=basUserSecurityService.queryBasUserSecurityByUserId(userId);
        AssertUtil.isTrue(null==basUserSecurity,"用户未登录!");
        AssertUtil.isTrue(StringUtils.isBlank(bussinessPassword),"交易密码不能为空!");
        bussinessPassword= MD5.toMD5(bussinessPassword);
        AssertUtil.isTrue(!bussinessPassword.equals(basUserSecurity.getPaymentPassword()),"交易密码错误!");
    }
}
