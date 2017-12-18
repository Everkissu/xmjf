package com.shsxt.xm.web.controller;

import com.shsxt.xm.api.query.BusItemInvestQuery;
import com.shsxt.xm.api.service.IBusItemInvestService;
import com.shsxt.xm.api.utils.PageList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lp on 2017/12/11.
 */
@Controller
@RequestMapping("busItemInvest")
public class BusItemInvestController {

    @Resource
    private IBusItemInvestService busItemInvestService;


    @RequestMapping("queryBusItemInvestsByItemId")
    @ResponseBody
    public PageList queryBusItemInvestsByItemId(BusItemInvestQuery busItemInvestQuery){
        return busItemInvestService.queryBusItemInvestsByParams(busItemInvestQuery);
    }

}
