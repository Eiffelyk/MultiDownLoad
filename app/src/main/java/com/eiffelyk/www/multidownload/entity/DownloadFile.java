package com.eiffelyk.www.multidownload.entity;

/**
 *
 * 类名称：File
 * 类描述： 文件的信息
 */
public class DownloadFile {

    private String name; //下载文件名
    private String path; //下载路径
    private String img_path;  //预览图路径
    private String content;  //下载描述

    public DownloadFile(String name, String path,String img_path) {
        this.name = name;
        this.path = path;
        this.img_path = img_path;
    }

    public DownloadFile() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "File [name=" + name + ", path=" + path + ", img_path="
                + img_path + ", content=" + content + "]";
    }

}
