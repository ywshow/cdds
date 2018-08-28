/**
 * project name:saas
 * file name:SysAccountController
 * package name:com.cdkj.system.controller
 * date:2018/3/19 14:11
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.system.service.api.SysAccountService;
import com.cdkj.model.system.pojo.SysAccount;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.PageDTO;
import com.cdkj.util.ResultInfo;
import com.cdkj.util.StringUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * description: 平台系统套账号管理 <br>
 * date: 2018/3/19 14:11
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Api(value = "/api/sys/account/", description = "套账号")
@Controller
@RequestMapping(value = "/api/sys/account/")
public class SysAccountController extends BaseController<SysAccount> {

    @Resource
    private SysAccountService sysAccountService;

    private String prefix = "page/system/account/";

    @RequiresPermissions("sys:account:account")
    @GetMapping("index")
    public String account(Model model){
        return prefix + "index";
    }

    /**
     * description: 根据ID查询 <br>
     *
     * @param id ID
     * @return 返回套账号信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "表ID", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("select/{id}")
    @ResponseBody
    public String selectById(@PathVariable("id") String id) {
        try {
            SysAccount sysAccount = sysAccountService.selectById(id);
            return JsonUtils.res(sysAccount);
        } catch (CustException e) {
            logger.error("SysAccountController.selectById()方法异常!error={}", e);
            return JsonUtils.resFailed(e.getMsg());
        } catch (Exception e) {
            logger.error("SysAccountController.selectById()方法系统异常!error={}", e);
            return JsonUtils.resFailed(202, ErrorCode.ERROR_20002, "01", "系统异常");
        }
    }

    @RequiresPermissions("sys:role:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        SysAccount account = sysAccountService.selectByPrimaryKey(id);
        model.addAttribute("model", account);
        return prefix + "edit";
    }

    @RequiresPermissions("sys:role:add")
    @GetMapping("/add")
    public String add() {
        return prefix + "add";
    }

    /**
     * description: 根据套账号查询 <br>
     *
     * @param sysAccount 套账号
     * @return 返回套账号信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysAccount", value = "套账号", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("select/account/{sysAccount}")
    @ResponseBody
    public String selectBySysAccount(@PathVariable("sysAccount") String sysAccount) {
        try {
            SysAccount model = sysAccountService.selectBySysAccount(sysAccount);
            return JsonUtils.res(model);
        } catch (CustException e) {
            logger.error("SysAccountController.selectBySysAccount()方法异常!error={}", e);
            return JsonUtils.resFailed(e.getMsg());
        } catch (Exception e) {
            logger.error("SysAccountController.selectBySysAccount()方法系统异常!error={}", e);
            return JsonUtils.resFailed(202, ErrorCode.ERROR_20002, "02", "系统异常");
        }
    }

    /**
     * description: 新增套账号 <br>
     *
     * @param sysAccount 套账号信息
     * @return 记录数
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysAccount", value = "套账号对象", required = true, dataType = "String", paramType = "path")
    })
    @RequiresPermissions("sys:account:insert")
    @PostMapping("insert")
    @ResponseBody
    public String insertSysAccount(SysAccount sysAccount) {
        try {
            if(StringUtil.isEmpty(sysAccount.getSysAccount())){
                throw new CustException("账套号为空");
            }
            SysAccount account = sysAccountService.selectBySysAccount(sysAccount.getSysAccount());
            if (!ObjectUtils.isEmpty(account)) {
                return JsonUtils.resFailed("账套号已存在");
            }
            int num = sysAccountService.insertSysAccount(sysAccount);
            return JsonUtils.res(num);
        } catch (CustException e) {
            logger.error("SysAccountController.insertSysAccount()方法异常!error={}", e);
            return JsonUtils.resFailed(e.getMsg());
        } catch (Exception e) {
            logger.error("SysAccountController.insertSysAccount()方法系统异常!error={}", e);
            return JsonUtils.resFailed(202, ErrorCode.ERROR_20002, "03", "系统异常");
        }
    }


    /**
     * description: 修改套账号 <br>
     *
     * @param sysAccount 套账号信息
     * @return 记录数
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysAccount", value = "套账号对象", required = true, dataType = "String", paramType = "path")
    })
    @RequiresPermissions("sys:account:update")
    @PostMapping("update")
    @ResponseBody
    public String updateSysAccount(SysAccount sysAccount) {
        try {
            int num = sysAccountService.updateSysAccount(sysAccount);
            return JsonUtils.res(num);
        } catch (CustException e) {
            logger.error("SysAccountController.insertSysAccount()方法异常!error={}", e);
            return JsonUtils.resFailed(e.getMsg());
        } catch (Exception e) {
            logger.error("SysAccountController.insertSysAccount()方法系统异常!error={}", e);
            return JsonUtils.resFailed(202, ErrorCode.ERROR_20002, "04", "系统异常");
        }
    }

    /**
     * description: 根据id删除 <br>
     *
     * @return 记录数
     */
    @RequiresPermissions("sys:account:delete")
    @PostMapping("delete")
    @ResponseBody
    public String delete(String ids) {
        try {
            if (StringUtil.isEmpty(ids)) {
                return JsonUtils.resFailed("ID为空");
            }
            return JsonUtils.res(sysAccountService.deleteLogic(ids));
        } catch (CustException e) {
            logger.error("SysAccountController.delete()方法异常!error={}", e);
            return JsonUtils.resFailed(e.getMsg());
        } catch (Exception e) {
            logger.error("SysAccountController.delete()方法系统异常!error={}", e);
            return JsonUtils.resFailed(202, ErrorCode.ERROR_20002, "05", "系统异常");
        }
    }

    /**
     * description: 获取所有帐套信息 <br>
     *
     * @return 记录数
     */
    @RequiresPermissions("sys:account:list")
    @GetMapping("list")
    @ResponseBody
    public String list(PageDTO pageDTO) {
        try {
            Instant instantStart = Instant.now();
            ResultInfo<SysAccount> pageInfo = sysAccountService.getAll(pageDTO);
            Instant instantEnd = Instant.now();
            System.out.println(">>>>>>>>>>>>createOrderNew：时间差"+ Duration.between(instantStart,instantEnd).toMillis());
            return JsonUtils.res(pageInfo);
        } catch (CustException e) {
            logger.error("SysAccountController.getAll()方法异常!error={}", e);
            return JsonUtils.resFailed(e.getMsg());
        } catch (Exception e) {
            logger.error("SysAccountController.getAll()方法系统异常!error={}", e);
            return JsonUtils.resFailed(202, ErrorCode.ERROR_20002, "05", "系统异常");
        }
    }
}