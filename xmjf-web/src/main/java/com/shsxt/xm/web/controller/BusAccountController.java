package com.shsxt.xm.web.controller;

import com.shsxt.xm.api.constant.P2PConstant;
import com.shsxt.xm.api.dto.PayDto;
import com.shsxt.xm.api.po.BasUser;
import com.shsxt.xm.api.service.IBusAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Created by lp on 2017/12/12.
 */
@Controller
@RequestMapping("account")
public class BusAccountController {

    @Resource
    private IBusAccountService busAccountService;


    /**
     * 转发到充值页面
     * @return
     */
    @RequestMapping("rechargePage")
    public String toAccountRechargePage(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "user/recharge";
    }


    /**
     * 发起支付请求转发页面
     * @param amount
     * @param picCode
     * @param bussinessPassword
     * @return
     */
    @RequestMapping("doAccountRechargeToRechargePage")
    public  String doAccountRechargeToRechargePage(BigDecimal amount, String picCode, String bussinessPassword, HttpServletRequest request, Model model){
        model.addAttribute("ctx",request.getContextPath());
        String sessionPicCode= (String) request.getSession().getAttribute(P2PConstant.PICTURE_VERIFY_CODE);
        if(StringUtils.isBlank(sessionPicCode)){
            System.out.println("验证码已失效!");
            return "user/pay";
        }
        if(!picCode.equals(sessionPicCode)){
            System.out.println("验证码不匹配!");
            return "user/pay";
        }
        BasUser basUser= (BasUser) request.getSession().getAttribute("userInfo");
        PayDto payDto= busAccountService.addRechargeRequestInfo(amount,bussinessPassword,basUser.getId());
        model.addAttribute("pay",payDto);
        return "user/pay";
    }



    @RequestMapping("callback")
    public  String callback(@RequestParam("total_fee") BigDecimal totalFee
            ,@RequestParam("out_order_no")String outOrderNo,String sign,
             @RequestParam("trade_no")String tradeNo,
             @RequestParam("trade_status")String tradeStatus,
             HttpSession session){
        /**
         * 回调后续处理。。。
         */
        return "";

    }
}
