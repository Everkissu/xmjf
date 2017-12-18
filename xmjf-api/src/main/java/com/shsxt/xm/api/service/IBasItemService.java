package com.shsxt.xm.api.service;

import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.api.query.BasItemQuery;
import com.shsxt.xm.api.utils.PageList;

/**
 * Created by lp on 2017/12/9.
 */
public interface IBasItemService {
    public PageList   queryBasItemsByParams(BasItemQuery basItemQuery);

    public void updateBasItemStatusToOpen(Integer itemId);

    public BasItemDto queryBasItemByItemId(Integer itemId);
}
