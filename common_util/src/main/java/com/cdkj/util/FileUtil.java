package com.cdkj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * PackageName:com.cdkj.common.util
 * Descript:文件处理类 <br/>
 * date: 2016/3/28 <br/>
 * User: bovine
 * version 1.0
 */
public class FileUtil {
    public final static String SEPARATOR = "/";
    protected static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 单个文件上传
     *
     * @param is
     * @param fileName
     * @param filePath
     */

    public static String upFile(InputStream is, String fileName, String filePath) {
        //对名字进行处理
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File f = new File(filePath + "/" + fileName);
        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(f);
            bos = new BufferedOutputStream(fos);
            byte[] bt = new byte[4096];
            int len;
            while ((len = bis.read(bt)) > 0) {
                bos.write(bt, 0, len);
            }
        } catch (Exception e) {
            logger.error("run error", e);
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                    bos = null;
                }
                if (null != fos) {
                    fos.close();
                    fos = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
                if (null != bis) {
                    bis.close();
                    bis = null;
                }

            } catch (Exception e) {
                logger.error("run error", e);
            }
        }
        return fileName;
    }

    /**
     * 文件转换为 byte
     *
     * @param filePath 文件路径
     * @return byte数组
     */
    public static byte[] file2Byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * byte[] 上传
     *
     * @param buf      byte数组
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    public static boolean byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file;
        boolean mkdirResult = false;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                mkdirResult = dir.mkdirs();
            }
            if (mkdirResult) {
                return false;
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 产生文件对应的文件夹
     *
     * @param filePath String
     * @return String
     */

    public static boolean mkDir(String filePath) {
        boolean result = false;
        Path path = Paths.get(filePath);
        Path parendPath = path.getParent();
        File dir = new File(parendPath.toString());
        if (dir == null || !dir.exists()) {
            result = dir.mkdirs();
        }
        return result;
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        // 不存在返回 false
        if (!file.exists()) {
            return flag;
        } else {
            // 判断是否为文件
            // 为文件时调用删除文件方法
            if (file.isFile()) {
                return deleteFile(sPath);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除文件，如果该文件所在的目录为空，则删除这个目录
     *
     * @param sPath 被删除目录的文件路径
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteFileWithDir(String sPath) {
        boolean flag = false;

        if (StringUtil.isEmpty(sPath))
            return flag;

        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
            //删除目录
            try {
                if (flag) {
                    String dirPath = file.getParent();
                    File dirs = new File(dirPath);
                    if (dirs.exists() && dirs.isDirectory()) {
                        //如果目录下无文件，则删除目录
                        if (dirs.listFiles().length == 0) {
                            dirs.delete();
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("FileUtil.deleteFileWithDir error:{}", e);
            }
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        boolean flag = false;
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * URL文件转成 byte[]
     *
     * @param url URL文件
     * @return byte[]
     * @throws MalformedURLException
     */
    public static byte[] urlToByte(String url) throws MalformedURLException {
        URL ur = new URL(url);
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(ur.openStream());
            out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] content = out.toByteArray();
        return content;
    }
}
