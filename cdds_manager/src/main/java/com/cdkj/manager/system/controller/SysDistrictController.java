/**
 * project name:saas
 * file name:SysDistrictController
 * package name:com.cdkj.system.controller
 * date:2018-03-30 17:34
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.system.service.api.SysDistrictService;
import com.cdkj.model.system.pojo.SysDistrict;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: //账套地址控制器 <br>
 * date: 2018-03-30 17:34
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/sys/district/open")
public class SysDistrictController extends BaseController<SysDistrict> {
    @Resource
    private SysDistrictService sysDistrictService;

    /**
     * 通过组代码获得账套地址数据
     *
     * @param sysAccount 账套号
     * @return 账套地址数据
     */
    @GetMapping("/get/{sysAccount}")
    public String get(@PathVariable(value = "sysAccount") String sysAccount) {
        if (StringUtil.isEmpty(sysAccount)) {
            return JsonUtils.resFailed(210, ErrorCode.ERROR_20001, "01", "参数为空");
        }
        List<SysDistrict> sysDistrictList = sysDistrictService.selectBySysAccount(sysAccount);
        if (sysDistrictList == null) {
            return JsonUtils.resFailed(210, ErrorCode.ERROR_20002, "02", "系统错误");
        }
        return JsonUtils.res(CollectionUtils.isEmpty(sysDistrictList) ? null : sysDistrictList);
    }

    /**
     * 插入账套地址数据
     *
     * @param ReqSysDistrict
     * @return 插入条数
     */
    @PostMapping("/insert")
    public String insert(@RequestBody SysDistrict ReqSysDistrict) {
        try {
            if (StringUtil.isEmpty(ReqSysDistrict.getSysAccount())) {
                return JsonUtils.resFailed(210, ErrorCode.ERROR_20001, "03", "参数为空");
            }
            int insertRest = sysDistrictService.insertOrUpdateSysDistrict(ReqSysDistrict);
            return JsonUtils.resCorU(insertRest);
        } catch (CustException ce) {
            logger.error("SysDistrictController.insert()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDistrictController.insert()方法系统异常!error={}", e);
            return JsonUtils.resFailed(210, ErrorCode.ERROR_20002, "04", "系统异常");
        }
    }

    /**
     * 修改账套地址数据
     *
     * @return 修改条数
     */
    @PostMapping("/update")
    public String update(@RequestBody SysDistrict ReqSysDistrict) {
        try {
            if (StringUtil.isEmpty(ReqSysDistrict.getId())) {
                return JsonUtils.resFailed(210, ErrorCode.ERROR_20001, "05", "参数为空");
            }
            int insertRest = sysDistrictService.insertOrUpdateSysDistrict(ReqSysDistrict);
            return JsonUtils.resCorU(insertRest);
        } catch (CustException ce) {
            logger.error("SysDistrictController.update()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDistrictController.update()方法系统异常!error={}", e);
            return JsonUtils.resFailed(210, ErrorCode.ERROR_20002, "06", "系统异常");
        }
    }

    /**
     * 删除账套地址数据
     *
     * @param id       主键
     * @param updateBy 修改
     * @return 修改条数
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") String id, String updateBy) {
        try {
            if (StringUtil.isEmpty(id)) {
                return JsonUtils.resFailed(210, ErrorCode.ERROR_20001, "07", "参数为空");
            }
            SysDistrict sysDistrict = new SysDistrict();
            sysDistrict.setId(id);
            sysDistrict.setUpdateBy(updateBy);
            sysDistrict.setEnabled((short) 0);
            int insertRest = sysDistrictService.insertOrUpdateSysDistrict(sysDistrict);
            return JsonUtils.resCorU(insertRest);
        } catch (CustException ce) {
            logger.error("SysDistrictController.delete()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDistrictController.delete()方法系统异常!error={}", e);
            return JsonUtils.resFailed(210, ErrorCode.ERROR_20002, "08", "系统异常");
        }
    }

}
