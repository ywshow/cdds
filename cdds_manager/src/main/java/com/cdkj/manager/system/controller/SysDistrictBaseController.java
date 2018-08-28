/**
 * project name:saas
 * file name:SysDistrictBaseController
 * package name:com.cdkj.system.controller
 * date:2018-03-31 9:56
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.system.service.api.SysDistrictBaseService;
import com.cdkj.model.system.pojo.SysDistrictBase;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: //账套地址主表控制器 <br>
 * date: 2018-03-31 9:56
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/sys/districtbase/open")
public class SysDistrictBaseController extends BaseController<SysDistrictBase> {
    @Resource
    private SysDistrictBaseService sysDistrictBaseService;

    /**
     * 通过账套号获得账套地址基础数据
     *
     * @param sysAccount 账套号
     * @return 账套地址数据
     */
    @GetMapping("/get/{sysAccount}")
    public String get(@PathVariable(value = "sysAccount") String sysAccount) {
        if (StringUtil.isEmpty(sysAccount)) {
            return JsonUtils.resFailed(211, ErrorCode.ERROR_20001, "01", "参数为空");
        }
        List<SysDistrictBase> sysDistrictBaseList = sysDistrictBaseService.selectBySysAccount(sysAccount);
        if (sysDistrictBaseList == null) {
            return JsonUtils.resFailed(211, ErrorCode.ERROR_20002, "02", "系统错误");
        }
        return JsonUtils.res(org.apache.commons.collections.CollectionUtils.isEmpty(sysDistrictBaseList) ? null : sysDistrictBaseList);
    }

    /**
     * 插入账套地址基础数据
     *
     * @param sysDistrictBase
     * @return 插入条数
     */
    @PostMapping("/insert")
    public String insert(@RequestBody SysDistrictBase sysDistrictBase) {
        try {
            int insertRest = sysDistrictBaseService.insertOrUpdateSysDistrictBase(sysDistrictBase);
            return JsonUtils.res(insertRest);
        } catch (CustException ce) {
            logger.error("SysDistrictBaseController.insert()方法异常!error={}", ce);
            return JsonUtils.resFailed(211, ErrorCode.ERROR_20002, "03", "系统异常");
        } catch (Exception e) {
            logger.error("SysDistrictBaseController.insert()方法系统异常!error={}", e);
            return JsonUtils.resFailed(211, ErrorCode.ERROR_20002, "04", "系统异常");
        }
    }

    /**
     * 修改账套地址基础数据
     *
     * @return 修改条数
     */
    @PostMapping("/update")
    public String update(@RequestBody SysDistrictBase sysDistrictBase) {
        try {
            if (StringUtil.isEmpty(sysDistrictBase.getId())) {
                return JsonUtils.resFailed(211, ErrorCode.ERROR_20001, "05", "参数为空");
            }
            int insertRest = sysDistrictBaseService.insertOrUpdateSysDistrictBase(sysDistrictBase);
            return JsonUtils.res(insertRest);
        } catch (CustException ce) {
            logger.error("SysDistrictBaseController.update()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDistrictBaseController.update()方法系统异常!error={}", e);
            return JsonUtils.resFailed(211, ErrorCode.ERROR_20002, "06", "系统异常");
        }
    }

    /**
     * 根据id删除账套地址基础数据
     *
     * @param id       主键
     * @param updateBy 修改人
     * @return 修改条数
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") String id, String updateBy) {
        try {
            if (StringUtil.isEmpty(id)) {
                return JsonUtils.resFailed(211, ErrorCode.ERROR_20001, "07", "参数为空");
            }
            SysDistrictBase sysDistrictBase = new SysDistrictBase();
            sysDistrictBase.setId(id);
            sysDistrictBase.setUpdateBy(updateBy);
            sysDistrictBase.setEnabled((short) 0);
            int insertRest = sysDistrictBaseService.insertOrUpdateSysDistrictBase(sysDistrictBase);
            return JsonUtils.res(insertRest);
        } catch (CustException ce) {
            logger.error("SysDistrictBaseController.delete()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDistrictBaseController.delete()方法系统异常!error={}", e);
            return JsonUtils.resFailed(211, ErrorCode.ERROR_20002, "08", "系统异常");
        }
    }

}
