package com.cdkj.util.sms;

import com.cdkj.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class SmsApi {
    public static final int YDTX = 1;// 云点通信

    public static final int SMS = 2;//sms 微服务


    /**
     * 发短信接口
     *
     * @param type   平台类型
     * @param params 传入参数
     * @return
     */
    public static Map<String, String> sendSms(int type, Map<String, String> params) {
        switch (type) {
            case YDTX:
                return sendByYDTX(params);
            case SMS:
                return smsMicroServer(params);
            default:
                return null;
        }
    }

    /**
     * 对接短信微服务发送短信
     *
     * @param params ("account", account)
     *               ("password", password)
     *               ("desKey", smsDeskey)
     *               ("url", url)
     *               ("mobile", tel)
     *               ("content", msg)
     * @return map
     */
    private static Map<String, String> smsMicroServer(Map<String, String> params) {
        Map<String, String> rtn = new HashedMap();

        String url = params.get("url");
        String userCode = params.get("account");
        String password = params.get("password");
        String desKey = params.get("desKey");
        String mobile = params.get("mobile");
        String message = params.get("content");

        SignKit.DES_KEY = desKey;
        // 1.获取token   mac地址、userCode、password
        String tokenJson = getToken(url, "28-D2-44-06-37-2E", userCode, password, desKey);
        Map<String, Object> rsMap = JsonUtils.toObject(tokenJson, Map.class);
        int resultCode = (int) rsMap.get("resultCode");
        String token = "";
        if (1 == resultCode) {
            token = (String) rsMap.get("result");
        }
        // 2.提交短信内容
        if (!StringUtils.isEmpty(token)) {
            String submitInfo = sms(url, mobile, message, userCode, password, token);
            Map<String, Object> map = JsonUtils.toObject(submitInfo, Map.class);
            rtn.put("resultCode", map.get("resultCode") + "");
            rtn.put("resultMsg", submitInfo);
            return rtn;
        } else {
            rtn.put("resultCode", "-1");
            rtn.put("resultMsg", "获取Token失败！");
            return rtn;
        }
    }

    /**
     * 发送短信
     *
     * @param mobile  手机号码
     * @param message 短信内容
     *                <p>
     *                userCode=cdkj password=123 token=123123XASD
     *                <p>
     *                mobile=18570629027 message=XASDA
     *                <p>
     *                sendType=1 taskType=123
     */
    private static String sms(String url, String mobile, String message, String userCode, String password, String token) {
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("userCode", userCode);
        map.put("password", password);
        map.put("mobile", mobile);
        map.put("message", message);
        map.put("sendType", "1");
        map.put("taskType", "123");
        Map<String, Object> encrypt = SignKit.encrypt(map, SignKit.ENCRYPT_TYPE_SUBMIT);
        String data = (String) encrypt.get("sign");

        Map<String, String> params = new HashMap<>();
        params.put("data", data);
        params.put("code", userCode);
        String post = HttpKit.post(url + "/sms/api/pool/submit", params, "");

        return post;
    }


    /**
     * 获取token
     *
     * @return 00-21-CC-64-C4-98 mac mac地址 userCode 用户编码 password 用户密码
     */
    private static String getToken(String url, String mac, String userCode, String password, String desKey) {
        Map<String, String> map = new HashMap<>();
        map.put("mac", mac);
        map.put("userCode", userCode);
        map.put("password", password);

        Map<String, Object> encrypt = SignKit.encrypt(map, SignKit.ENCRYPT_TYPE_LOGIN);
        String data = (String) encrypt.get("urlParams");

        Map<String, String> params = new HashMap<>();
        params.put("code", userCode);
        params.put("desSign", DESUtil.encrypt(data, desKey));

        return HttpKit.post(url + "/sms/api/user/getToken", params, "");
//        Map<String, Object> rsMap = JsonUtils.toObject(post, Map.class);
//        int resultCode = (int) rsMap.get("resultCode");
//        String result = (String) rsMap.get("result");
//        if (1 == resultCode)
//            return result;
//
//        return "";
    }

    private static Map<String, String> sendByYDTX(Map<String, String> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", params.get("userid"));
        map.put("account", params.get("account"));
        map.put("password", params.get("password"));
        map.put("action", "send");
        map.put("sendTime", DateUtil.getNow());
        map.put("extno", "");
        map.put("mobile", params.get("mobile"));
        map.put("content", params.get("content"));//内容必须带上署名，规范如下：
//		自定义签名短信使用须知：
//		1、自定义签名的情况下，一开始通道是不带任何签名的，一定要在短信模板上添加短信签名，这样运营商的短信网关才能识别出来是一条需要发送的短信，才能进行发送。
//		2、短信签名的内容，不能超过8个中文字符，9个字符开始，运营商的短信网关无法识别签名，也会导致发送失败。
//		3、短信签名的括号，一定要使用【】，而不是[ ]，运营商的短信网关只识别【】符号的短信签名。

        String param = UrlKit.getUrlParamsByMap(map);
        String xml = HttpKit.post(params.get("url"), param);

        return XmlConverUtil.xmltoMap(xml);
    }

    /**
     * Api Demo
     *
     * @param args
     */
    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        map.put("userid", "51");
//        map.put("account", "cdkj-cs");
//        map.put("password", "cdkj2302");
//        map.put("url", "http://120.76.202.242:8888/sms.aspx");
//
//        map.put("mobile", "18607310074");
//        map.put("content", "【12121】");
//
//        Map<String, String> result = sendSms(1, map);
//        System.out.println(result.toString());

        //{returnstatus=Success, successCounts=1, message=ok, remainpoint=95, taskID=56532}
    }
}
