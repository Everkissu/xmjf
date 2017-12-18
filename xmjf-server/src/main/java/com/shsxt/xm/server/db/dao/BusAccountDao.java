package com.shsxt.xm.server.db.dao;

import com.shsxt.xm.api.po.BusAccount;
import com.shsxt.xm.server.base.BaseDao;
import com.shsxt.xm.server.providers.BusAccountProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

public interface BusAccountDao extends BaseDao<BusAccount> {

    /**
     * 查询登录用户账户金额信息
     * @param userId
     * @return
     */
   /* @Select("select id,user_id as userId, total ,usable,cash,frozen,wait,repay" +
            " from bus_account where user_id=#{userId}")*/
    @SelectProvider(type = BusAccountProvider.class,method = "getQueryBusAccountByUserIdSql")
    public  BusAccount queryBusAccountByUserId(@Param("userId")Integer userId);

}