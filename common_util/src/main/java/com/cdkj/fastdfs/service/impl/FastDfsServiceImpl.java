package com.cdkj.fastdfs.service.impl;

import com.cdkj.fastdfs.model.pojo.FastDfsFile;
import com.cdkj.fastdfs.FileManager;
import org.apache.commons.codec.binary.Base64;

import java.io.*;

/**
 * Created by Administrator on 2016-05-13.
 */
public class FastDfsServiceImpl {

    /**
     * @param fileUrl  文件绝对路径
     * @param fileName 文件名称
     * @param fileExt  文件后缀
     * @return 服务器文件路径
     * @throws Exception
     */
    public static String upFile(String fileUrl, String fileName, String fileExt) throws Exception {
        FileInputStream fis = new FileInputStream(fileUrl);

        byte[] file_buff = null;
        if (fis != null) {
            int len = fis.available();
            file_buff = new byte[len];
            fis.read(file_buff);
        }

        FastDfsFile file = new FastDfsFile(fileName, file_buff, fileExt);

        String fileAbsolutePath = FileManager.upload(file);
        fis.close();

        return fileAbsolutePath;
    }


    /**
     * @param in       InputStream
     * @param fileName 文件名称
     * @param fileExt  文件后缀
     * @return 服务器文件路径
     * @throws Exception
     */
    public static String upFile(InputStream in, String fileName, String fileExt) throws Exception {
        byte[] file_buff = input2byte(in);
        FastDfsFile file = new FastDfsFile(fileName, file_buff, fileExt);
        String fileAbsolutePath = FileManager.upload(file);

        return fileAbsolutePath;
    }

    /**
     * @param in       InputStream
     * @param fileName 文件名称
     * @param fileExt  文件后缀
     * @return 服务器文件路径
     * @throws Exception
     */
    public static String upFile(InputStream in, String fileName, String fileExt, String groupName) throws Exception {
        byte[] file_buff = input2byte(in);
        FastDfsFile file = new FastDfsFile(fileName, file_buff, fileExt);
        String fileAbsolutePath = FileManager.upload(file, groupName);

        return fileAbsolutePath;
    }

    /**
     * @param fileName 文件名称
     * @param fileExt  文件后缀
     * @return 服务器文件路径
     * @throws Exception
     */
    public static String upFileByByteArray(String byteArrayStr, String fileName, String fileExt) throws Exception {
        byte[] file_buff = Base64.decodeBase64(byteArrayStr);
        FastDfsFile file = new FastDfsFile(fileName, file_buff, fileExt);
        String fileAbsolutePath = FileManager.upload(file);

        return fileAbsolutePath;
    }


    /**
     * InputStream 转 byte[]
     *
     * @param buf
     * @return
     */
    public static final InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    /**
     * byte[] 转 InputStream
     *
     * @param inStream
     * @return
     * @throws IOException
     */
    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        swapStream.close();

        return in2b;
    }


    public static int deleteFile(String url) {
//        String a = "http://114.55.42.56:8080/group1/M00/0A/1C/cjcqOFc-s6eAXWIcAAEOFgxyE2Q442.jpg";
        int i = -1001;
        url = url == null ? "" : url;
        if (url.startsWith("http")) {
            //http://114.55.42.56:8080
            String temp = url.substring(0, url.lastIndexOf(":") + 5);
            //group1/M00/0A/1C/cjcqOFc-s6eAXWIcAAEOFgxyE2Q442.jpg
            String tempb = url.substring(url.lastIndexOf(":") + 6, url.length());
            //group1
            String group = tempb.substring(0, tempb.indexOf("/"));
            //M00/0A/1C/cjcqOFc-s6eAXWIcAAEOFgxyE2Q442.jpg
            String filePath = tempb.substring(tempb.indexOf("/") + 1, tempb.length());
            try {
                i = FileManager.deleteFile(group, filePath);
            } catch (Exception e) {
                e.printStackTrace();
                i = -1;
            }
        }

        return i;
    }
}
