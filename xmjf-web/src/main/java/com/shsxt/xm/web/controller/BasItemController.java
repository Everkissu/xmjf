package com.shsxt.xm.web.controller;

import com.shsxt.xm.api.constant.P2PConstant;
import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.api.exceptions.ParamsExcetion;
import com.shsxt.xm.api.model.ResultInfo;
import com.shsxt.xm.api.po.*;
import com.shsxt.xm.api.query.BasItemQuery;
import com.shsxt.xm.api.service.*;
import com.shsxt.xm.api.utils.PageList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lp on 2017/12/9.
 */
@Controller
@RequestMapping("basItem")
public class BasItemController {
    @Resource
    private IBasItemService basItemService;

    @Resource
    private IBusAccountService busAccountService;

    @Resource
    private IBasUserSecurityService basUserSecurityService;
    @Resource
    private IBusItemLoanService busItemLoanService;
    @Resource
    private ISysPictureService sysPictureService;

    @RequestMapping("list")
    public  String toBasItemListPage(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "item/invest_list";
    }


    @RequestMapping("queryBasItemsByParams")
    @ResponseBody
    public PageList queryBasItemsByParams(BasItemQuery basItemQuery){
      return  basItemService.queryBasItemsByParams(basItemQuery);
    }

    @RequestMapping("updateBasItemStatusToOpen")
    @ResponseBody
    public ResultInfo updateBasItemStatusToOpen(Integer itemId){
        ResultInfo resultInfo=new ResultInfo();
        try {
            basItemService.updateBasItemStatusToOpen(itemId);
        }catch (ParamsExcetion e){
            e.printStackTrace();
            resultInfo.setCode(P2PConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(P2PConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(P2PConstant.OPS_FAILED_MSG);
        }
        return resultInfo;
    }


    @RequestMapping("itemDetailPage")
    public  String itemDetailPage(Integer itemId, ModelMap modelMap,HttpServletRequest request){
        BasItemDto basItemDto= basItemService.queryBasItemByItemId(itemId);
        BasUser basUser= (BasUser) request.getSession().getAttribute("userInfo");
        if(null!=basUser){
            BusAccount busAccount=busAccountService.queryBusAccountByUserId(basUser.getId());
            modelMap.addAttribute("busAccount",busAccount);
        }

        BasUserSecurity basUserSecurity=basUserSecurityService.queryBasUserSecurityByUserId(basItemDto.getItemUserId());

        BusItemLoan busItemLoan=busItemLoanService.queryBusItemLoanByItemId(itemId);

        List<SysPicture> sysPictures=sysPictureService.querySysPicturesByItemId(itemId);
        modelMap.addAttribute("loanUser",basUserSecurity);
        modelMap.addAttribute("busItemLoan",busItemLoan);
        modelMap.addAttribute("ctx",request.getContextPath());
        modelMap.addAttribute("item",basItemDto);
        modelMap.addAttribute("pics",sysPictures);
        return "item/details";
    }

}
