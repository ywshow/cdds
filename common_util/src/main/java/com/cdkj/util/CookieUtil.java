package com.cdkj.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class CookieUtil {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    private static CookieUtil cookieUtils = new CookieUtil();

    private CookieUtil() {
    }

    public CookieUtil me() {
        return cookieUtils;
    }

    /**
     * 添加cookie对象
     *
     * @param request
     * @param response
     * @param name
     * @param value
     * @param expiry
     */
    public static void addCookieObj(HttpServletRequest request, HttpServletResponse response, String name, Object value,
                                    String path, int expiry) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            String cookieValue = baos.toString("ISO-8859-1");
            String encodedCookieValue = java.net.URLEncoder.encode(cookieValue, "UTF-8");
            addCookie(request, response, name, encodedCookieValue, path, expiry);
        } catch (Exception e) {
            logger.error("保 存对象到cookie出错： " + e.getMessage());
        }
    }

    /**
     * 获取cookie对象
     *
     * @param request
     * @param response
     * @param name
     * @return
     */
    public static Object getCookieObj(HttpServletRequest request, HttpServletResponse response, String name) {
        String cookieValue = getCookie(request, name);
        Object result = null;
        if (cookieValue != null && cookieValue.trim() != "") {
            try {
                String decoderCookieValue = java.net.URLDecoder.decode(cookieValue, "UTF-8");
                ByteArrayInputStream bais = new ByteArrayInputStream(decoderCookieValue.getBytes("ISO-8859-1"));
                ObjectInputStream ios = new ObjectInputStream(bais);
                result = ios.readObject();
                return result;
            } catch (Exception e) {
                logger.error(" 从 cookie中解析对象出错： " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * 添加cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value
     * @param expiry   有效时间
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
                                 String path, int expiry) {
        try {
            value = java.net.URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("run error", e);
        }
        Cookie[] cookies = request.getCookies();
        boolean existuser = false;
        // cookies不为空，则修改
        if (cookies != null) {
            if (cookies.length > 0) {
                StringBuilder cname = null;
                for (int i = 0; i < cookies.length; i++) {
                    cname = new StringBuilder(cookies[i].getName());
                    // 查找用户名
                    if (cname.toString().equalsIgnoreCase(name)) {
                        existuser = true;
                        cookies[i].setValue(value);
                        cookies[i].setMaxAge(expiry);
                        if (path != null && !path.equalsIgnoreCase("") && path.startsWith("/")) {
                            // path 不为空；path必须以/开头
                            cookies[i].setPath(path);
                        }
                        response.addCookie(cookies[i]);
                        break;
                    }
                }
            }
        }

        if (!existuser) {
            // 记录cookie
            Cookie cookie = null;
            cookie = new Cookie(name, value);
            cookie.setSecure(false);
            // 60 * 60 * 24 * 7);
            cookie.setMaxAge(expiry);
            if (path != null && !path.equalsIgnoreCase("") && path.startsWith("/")) {
                // path 不为空；path必须以/开头
                cookie.setPath(path);
            }
            response.addCookie(cookie);
        }
    }

    /**
     * 获取cookie
     *
     * @param request
     * @param name
     */
    public static String getCookie(HttpServletRequest request, String name) {
        String result = null;
        Cookie[] cookies = request.getCookies();
        // cookies不为空，则修改
        if (cookies != null) {
            if (cookies.length > 0) {
                StringBuilder cname = null;
                for (int i = 0; i < cookies.length; i++) {
                    cname = new StringBuilder(cookies[i].getName());
                    // 查找用户名
                    if (cname.toString().equalsIgnoreCase(name)) {
                        result = cookies[i].getValue();
                        break;
                    }
                }
            }
        }
        if (result != null) {
            try {
                result = java.net.URLDecoder.decode(result, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("run error", e);
            }
        }
        return result;
    }

    /**
     * 删除cookie
     *
     * @param request
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        // cookies不为空，则修改
        if (cookies != null) {
            if (cookies.length > 0) {
                StringBuilder cname = null;
                for (int i = 0; i < cookies.length; i++) {
                    cname = new StringBuilder(cookies[i].getName());
                    // 查找用户名
                    if (cname.toString().equalsIgnoreCase(name)) {
                        cookies[i].setValue(null);
                        cookies[i].setMaxAge(-1);
                        response.addCookie(cookies[i]);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 获得所有cookie
     *
     * @param request
     * @return
     */
    public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        // 从request范围中得到cookie数组 然后遍历放入map集合中
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
        return cookieMap;
    }

    /**
     * 获取cookie的值
     *
     * @param request
     * @param name    cookie的名称
     * @return
     */
    public static String getCookieValueByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        // 判断cookie集合中是否有我们像要的cookie对象 如果有返回它的值
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * 获得cookieByName
     *
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        // 判断cookie集合中是否有我们像要的cookie对象 如果有返回它的值
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    public static void showAllCookies(HttpServletRequest request) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Cookie:KEY=" + cookie.getName() + "\tVALUE:" + cookie.getValue());
            }
        }
    }

}
