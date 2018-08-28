package com.cdkj.fastdfs.model.pojo;

import java.io.Serializable;

/**
 * PackageName:com.cdkj.common.plugin.fastdfs<br/>
 * Descript:FastDFS 文件实体类 <br/>
 * Date: 2018-05-12 <br/>
 * version 1.0
 */
public class FastDfsFile implements Serializable {

    private static final long serialVersionUID = 5562766419381704112L;
    private String name;

    /**
     * 文件内容
     */
    private byte[] content;

    /**
     * 文件后缀
     */
    private String ext;

    private String height = "";

    private String width = "";

    private String author = "";

    public FastDfsFile(String name, byte[] conetnt, String ext, String height,
                       String width, String author) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.height = height;
        this.width = width;
        this.author = author;
    }

    public FastDfsFile(String name, byte[] content, String ext) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
