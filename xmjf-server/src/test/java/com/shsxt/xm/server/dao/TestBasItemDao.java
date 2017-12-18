package com.shsxt.xm.server.dao;

import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.server.db.dao.BasItemDao;
import com.shsxt.xm.api.query.BasItemQuery;
import com.shsxt.xm.server.service.TestBase;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lp on 2017/12/9.
 */
public class TestBasItemDao extends TestBase {
    @Resource
    private BasItemDao  basItemDao;

    @Test
    public void test01(){
        BasItemQuery basItemQuery=new BasItemQuery();
        //basItemQuery.setItemCycle(2);
        basItemQuery.setIsHistory(0);
        basItemQuery.setItemType(2);
        List<BasItemDto> basItems=basItemDao.queryForPage(basItemQuery);
        if(!CollectionUtils.isEmpty(basItems)){
            for(BasItemDto basItemDto:basItems){
                System.out.println(basItemDto);
            }
        }else{
            System.err.println("暂无记录!");
        }
    }
}
