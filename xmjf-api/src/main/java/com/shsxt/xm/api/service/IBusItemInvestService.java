package com.shsxt.xm.api.service;

import com.shsxt.xm.api.query.BusItemInvestQuery;
import com.shsxt.xm.api.utils.PageList;

/**
 * Created by lp on 2017/12/11.
 */
public interface IBusItemInvestService {
    public PageList queryBusItemInvestsByParams(BusItemInvestQuery busItemInvestQuery);
}
