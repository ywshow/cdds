package com.cdkj.schedule.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.model.system.pojo.SysUser;
import com.cdkj.schedule.system.service.api.SysUserService;
import com.cdkj.schedule.util.ShiroUtils;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.ShiroMd5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * description: 登录控制器 <br>
 * date: 2018/2/9 下午1:36
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    private SysUserService sysUserService;


    @GetMapping({"/", ""})
    String welcome(Model model) {
        return "redirect:/login";
    }

    @GetMapping({"/index"})
    String index(Model model) {
        model.addAttribute("name", ShiroUtils.getUser().getNickName());
        return "index";
    }

    @GetMapping("/login")
    String login(Model model) {
        return "login";
    }

    /**
     * 用户登录操作
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @ResponseBody
    @PostMapping(value = "/login")
    public String ajaxLogin(String username, String password) {
        try {
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                throw new CustException(213, ErrorCode.ERROR_20001, "01", "参数异常");
            }
            //根据用户名查询会员信息
            SysUser sysUser = sysUserService.selectByUsername(username);
            if (ObjectUtils.isEmpty(sysUser)) {
                return JsonUtils.resFailed(215, ErrorCode.ERROR_20002, "02", "用户名不存在，请确认用户名！");
            }
            password = ShiroMd5Utils.encrypt(username, password, sysUser.getSalt());
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            try {
                //30分钟：1800000
                subject.getSession().setTimeout(1800000L);
                subject.login(token);
                subject.getSession().setAttribute("currentUser", sysUser);
                Map<String, Object> map = new HashMap<>(5);
                map.put("user", ShiroUtils.getUser());
                map.put("token", subject.getSession().getId());
                /*if(subject.isPermitted("sys:schedule:list")){
                    return JsonUtils.res(map);
                }else{
                    throw new CustException("无权限访问");
                }*/
                return JsonUtils.res(map);
            } catch (AuthenticationException e) {
                return JsonUtils.resFailed(215, ErrorCode.ERROR_20002, "02", "用户名或者密码错误！");
            }
        } catch (CustException ce) {
            logger.error("LoginController.ajaxLogin()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("LoginController.ajaxLogin()方法系统异常!error={}", e);
            return JsonUtils.resFailed(215, ErrorCode.ERROR_20002, "02", "系统异常");
        }
    }

    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

    @GetMapping("/main")
    String main(Model model) {
        return "main";
    }

    @GetMapping("/403")
    String error403() {
        return "403";
    }

}
