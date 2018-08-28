/**
 * project name:saas
 * file name:SysDictBaseDetailController
 * package name:com.cdkj.system.controller
 * date:2018-03-30 16:36
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.manager.system.service.api.SysDictBaseDetailService;
import com.cdkj.manager.system.service.api.SysDictBaseService;
import com.cdkj.model.system.pojo.SysDictBase;
import com.cdkj.model.system.pojo.SysDictBaseDetail;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.PageDTO;
import com.cdkj.util.ResultInfo;
import com.cdkj.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: //数据字典详细信息控制器 <br>
 * date: 2018-03-30 16:36
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/sys/basedetail/open/")
public class SysDictBaseDetailController extends BaseController<SysDictBaseDetail> {
    @Resource
    private SysDictBaseDetailService sysDictBaseDetailService;

    @Resource
    private SysDictBaseService sysDictBaseService;

    private String prefix = "system/dict/base/";

    @RequiresPermissions("sys:role:add")
    @GetMapping("/add/{baseId}")
    public String add(@PathVariable(value = "baseId") String baseId, Model model) {
        SysDictBase sysDictBase = sysDictBaseService.selectByPrimaryKey(baseId);
        model.addAttribute("sysDictBase", sysDictBase);
        return prefix + "add";
    }

    /**
     * 通过组代码获得数据字典详细信息
     *
     * @param groupCode 组代码
     * @return 数据字典详细信息
     */
    @GetMapping("get/{groupCode}")
    @ResponseBody
    public String get(@PathVariable(value = "groupCode") String groupCode) {
        if (StringUtil.isEmpty(groupCode)) {
            return JsonUtils.resFailed(209, 20001, "01", "参数为空");
        }
        List<SysDictBaseDetail> sysDictBaseDetailList = sysDictBaseDetailService.selectByGroupCode(groupCode);
        if (sysDictBaseDetailList == null) {
            return JsonUtils.resFailed(209, 20002, "02", "系统错误");
        }
        return JsonUtils.res(CollectionUtils.isEmpty(sysDictBaseDetailList) ? null : sysDictBaseDetailList);
    }

    /**
     * 通过组代码新增数据字典详细信息
     *
     * @param sysDictBaseDetail  组代码
     * @return 新增条数
     */
    @PostMapping("insert/{groupCode}")
    @ResponseBody
    public String insert(SysDictBaseDetail sysDictBaseDetail) {
        try {
            int insertRest = sysDictBaseDetailService.insertOrUpdateSysDictBaseDetail(sysDictBaseDetail);
            return JsonUtils.res(insertRest);
        } catch (CustException ce) {
            logger.error("SysDictBaseDetailController.insert()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictBaseDetailController.insert()方法系统异常!error={}", e);
            return JsonUtils.resFailed(209, 20002, "04", "系统异常");
        }
    }

    /**
     * 通过主键修改数据字典详细信息
     *
     * @param id         主键
     * @param groupCode  组代码
     * @param groupName  组名称
     * @param paramKey   参数键
     * @param paramValue 参数值
     * @return 修改条数
     */
    @PostMapping("update/{id}")
    @ResponseBody
    public String update(@PathVariable(value = "id") String id, String groupCode, String groupName, String paramKey, String paramValue) {
        try {
            if (!StringUtil.areNotEmpty(groupCode, groupName, paramKey, paramValue)) {
                return JsonUtils.resFailed(209, 20001, "05", "参数为空");
            }
            SysDictBaseDetail sysDictBaseDetail = new SysDictBaseDetail();
            sysDictBaseDetail.setId(id);
            sysDictBaseDetail.setGroupCode(groupCode);
            sysDictBaseDetail.setGroupName(groupName);
            sysDictBaseDetail.setParamKey(paramKey);
            sysDictBaseDetail.setParamValue(paramValue);
            int insertRest = sysDictBaseDetailService.insertOrUpdateSysDictBaseDetail(sysDictBaseDetail);
            return JsonUtils.resCorU(insertRest);
        } catch (CustException ce) {
            logger.error("SysDictBaseDetailController.update()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictBaseDetailController.update()方法系统异常!error={}", e);
            return JsonUtils.resFailed(209, 20002, "06", "系统异常");
        }
    }

    /**
     * 根据ID逻辑删除
     *
     * @param ids
     * @return 删除条数
     */
    @PostMapping("delete")
    @ResponseBody
    public String deleteById(String ids) {
        try {
            int insertRest = sysDictBaseDetailService.deleteLogic(ids);
            return JsonUtils.resCorU(insertRest);
        } catch (CustException ce) {
            logger.error("SysDictBaseDetailController.delete()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictBaseDetailController.delete()方法系统异常!error={}", e);
            return JsonUtils.resFailed(209, 20002, "07", "系统异常");
        }
    }

    /**
     * 根据主键删除数据字典详细信息
     * description: 通过基础字典，查询详情 <br>
     *
     * @param pageDTO sysDictBase主键
     * @return List<SysDictBaseDetail>
     */
    @GetMapping("selectBySysDictBaseId")
    @ResponseBody
    public String selectBySysDictBaseId(PageDTO pageDTO) {
        try {
            ResultInfo<SysDictBaseDetail> resultInfo = sysDictBaseDetailService.selectBySysDictBaseId(pageDTO);
            return JsonUtils.res(resultInfo);
        } catch (CustException ce) {
            logger.error("SysDictBaseDetailController.selectBySysDictBaseId()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictBaseDetailController.selectBySysDictBaseId()方法系统异常!error={}", e);
            return JsonUtils.resFailed(209, 20002, "08", "系统异常");
        }
    }

    /**
     * description: 更改值 <br>
     *
     * @param id         主键
     * @param paramValue 值
     * @return int
     */
    @PostMapping("updateParamValueById/{id}")
    @ResponseBody
    public String updateParamValueById(@PathVariable(value = "id") String id, String paramValue) {
        try {
            Integer num = sysDictBaseDetailService.updateParamValueById(id, paramValue);
            return JsonUtils.res(num);
        } catch (CustException ce) {
            logger.error("SysDictBaseDetailController.updateParamValueById()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictBaseDetailController.updateParamValueById()方法系统异常!error={}", e);
            return JsonUtils.resFailed(209, 20002, "09", "系统异常");
        }
    }


}
