package com.shsxt.xm.server.dao;

import com.shsxt.xm.api.po.BusAccount;
import com.shsxt.xm.server.db.dao.BusAccountDao;
import com.shsxt.xm.server.service.TestBase;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by lp on 2017/12/11.
 */
public class TestBusAccountDao extends TestBase {
    @Resource
    private BusAccountDao busAccountDao;

    @Test
    public  void test01(){
        BusAccount busAccount= busAccountDao.queryBusAccountByUserId(60);
        System.out.println(busAccount);

    }
}
