/**
 * project name:ach
 * file name:FileServiceImpl
 * package name:com.cdkj.file.service
 * date:2017-08-31 13:49
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.file.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.RedisKeys;
import com.cdkj.manager.file.service.api.FileService;
import com.cdkj.manager.file.util.EncryptUtils;
import com.cdkj.util.HttpKit;
import com.cdkj.util.JsonUtils;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 文件管理实现层
 * date: 2017-08-31 13:49
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class FileServiceImpl extends BaseServiceImpl implements FileService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 上传文件
     *
     * @param file      文件
     * @param groupName 组名
     * @param isCut     是否进行图片压缩：0否,1是
     * @return
     */
    @Override
    public String upload(MultipartFile file, String groupName, String isCut) throws Exception {
//        String token = getToken();
        String token = "";
        String url = redisClient.get(RedisKeys.SYS_DICT_FILE_UPLOAD_URL);//上传接口地址

        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("isCut", isCut);

        //会在项目的根目录的临时文件夹下生成一个文件
        File f = multipartToFile(file);

        // 实例化http客户端
        HttpClientConnectionManager httpClientConnectionManager = new BasicHttpClientConnectionManager();
        HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(httpClientConnectionManager).build();
        // 实例化post提交方式
        HttpPost post = new HttpPost(url);

        // 实例化参数对象
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName(HTTP.UTF_8));//设置请求的编码格式
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
        // 文本参数
        if (!params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), entry.getValue());//增加文本内容
            }
            builder.addBinaryBody("file", f);//增加字节内容，第二个参数可以是File,byte[],InputStream
            post.setEntity(builder.build());
        }

        // 执行post请求并得到返回对象 [ 到这一步我们的请求就开始了 ]
        HttpResponse resp = httpClient.execute(post);

        //删除文件
        if (f.exists()) {
            if (f.delete()) {
                logger.debug("fileServiceImpl.upload:file delete failed: filePath={}", f.getAbsoluteFile());
            }
        }

        // 解析返回请求结果
        HttpEntity entity = resp.getEntity();
        InputStream is = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String temp;
        while ((temp = reader.readLine()) != null) {
            buffer.append(temp);
        }
        return buffer.toString();
    }

    @Override
    public String getToken() throws Exception {
        String url = redisClient.get(RedisKeys.SYS_DICT_FILE_LOGIN_URL);//登录接口地址
        String userName = redisClient.get(RedisKeys.SYS_DICT_FILE_USER_NAME);//接口账号
        String password = redisClient.get(RedisKeys.SYS_DICT_FILE_PWD);//接口密码
        String key = redisClient.get(RedisKeys.SYS_DICT_FILE_KEY);//秘钥

        String sign = EncryptUtils.aesEncrypt(userName + "&" + password, key);
        Map<String, String> params = new HashedMap();
        params.put("userName", userName);
        params.put("sign", sign);
        Map<String, Object> result = JsonUtils.getMaptoFromJson(HttpKit.post(url, params, ""));
        if ((Integer) result.get("resultCode") == 1) {
            return result.get("result").toString();
        } else {
            throw new CustException(result.get("errorMsg").toString());
        }
    }

    /**
     * 多文件上传
     *
     * @param files 文件数组
     * @return
     */
    @Override
    public List<String> uploadFiles(MultipartFile[] files) throws Exception {
        List<String> urls = new ArrayList<>();
        //循环获取file数组中得文件
        for (MultipartFile file : files) {
            //上传文件
            String json = upload(file, "group1", "0");
            if (StringUtil.isNotEmpty(json)) {
                Map<String, Object> map = JsonUtils.getMaptoFromJson(json);
                if (map.size() > 0) {
                    if ("1".equals(map.get("resultCode").toString())) {
                        Map<String, String> resultMap = (Map<String, String>) map.get("result");
                        urls.add(resultMap.get("Original"));
                    }
                }
            }
        }
        return urls;
    }

    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    private File multipartToFile(MultipartFile multfile) throws IOException {
        CommonsMultipartFile cf = (CommonsMultipartFile) multfile;
        //这个myfile是MultipartFile的
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        //手动创建临时文件
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                fi.getName());
        multfile.transferTo(tmpFile);
        return tmpFile;
    }
}
