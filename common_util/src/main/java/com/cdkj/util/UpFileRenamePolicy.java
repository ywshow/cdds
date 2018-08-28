package com.cdkj.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * JFinal2.0文件上传重命名策略
 *
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date 2015年7月10日下午11:23:25
 */
public class UpFileRenamePolicy {

    public static String rename(String path, String name, boolean hasParents) {
        // 获取webRoot目录
        // 用户设置的默认上传目录
        String saveDir = path;
        //String saveDir = "";
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        // 添加时间作为目录
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
        String sYear = yearFormat.format(new Date());
        String sDate = dateFormat.format(new Date());
        String uuid = UUID.randomUUID().toString();
        String ext = getFileExt(name);
        StringBuilder fileName = new StringBuilder("");
        if (hasParents) {

            StringBuilder dirPath = new StringBuilder("")
                    .append(saveDir == null ? "file" : saveDir)
                    .append(File.separator)
                    .append(sYear)
                    .append(File.separator)
                    .append(sDate);
            File dir = new File(dirPath.toString());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fileName.append(File.separator)
                    .append(sYear)
                    .append(File.separator)
                    .append(sDate)
                    .append(File.separator)
                    .append(uuid)
                    .append(ext);
            File dest = new File(fileName.toString());
            // 创建上层目录
            File dir1 = dest.getParentFile();
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
        } else {
            fileName.append(uuid)
                    .append(ext);
        }
        return fileName.toString();
    }

    /**
     * 获取文件后缀
     *
     * @param @param  fileName
     * @param @return 设定文件
     * @return String 返回类型
     */
    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
    }
}