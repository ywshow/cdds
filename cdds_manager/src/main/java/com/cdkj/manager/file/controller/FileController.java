/**
 * project name: saas
 * file name:FileController
 * package name:com.cdkj.file.controller
 * date:2018-03-21 9:31
 * author: wq
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.file.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.manager.file.util.EncryptUtils;
import com.cdkj.util.HttpKit;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * description: //TODO <br>
 * date: 2018-03-21 9:31
 *
 * @author wq
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
public class FileController extends BaseController {

    public String getToken() throws Exception {

        return "";
    }

    public static void main(String[] args) {
        String url = "http://fs.cd100.cn:8001/file-services/user/validateUser";

        String userName = "saas";
        String passwrod = "123456";
        String key = "ZYkSO0OFyEnC15v5";
        String sign = "";
        try {
            sign = EncryptUtils.aesEncrypt(userName + "&" + passwrod, key);
            Map<String, Object> params = new HashMap();
            params.put("userName", userName);
            params.put("sign", sign);
            String json = EncryptUtils.getUrlParamsByMap(params, "utf-8");
            System.out.println(HttpKit.post(url, json));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String uploadUrl = "http://fs.cd100.cn:8001/file-services/file/form_upload";
        String token = "B3892B9A956049C38F9E1AE6B6948EDF";
        String isCut = "0";

    }
}

