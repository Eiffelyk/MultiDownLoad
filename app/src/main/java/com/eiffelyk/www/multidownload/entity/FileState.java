package com.eiffelyk.www.multidownload.entity;

/**
 *  类描述： 下载文件的状态信息
 */
public class FileState {

    private String name;// 文件名称
    private String image;//预览图片网络地址
    private String url;// 下载地址
    private int state;// 当前的下载状态 1 正在下载 0 已下载
    private int completeSize;// 下载的完成进度
    private int fileSize;// 文件的长度

    /**
     * @param name         文件名称
     * @param url          下载地址
     * @param state        当前的下载状态 1 正在下载 0 已下载
     * @param completeSize 下载的完成进度
     * @param fileSize     文件的长度
     */
    public FileState(String name, String url, int state, int completeSize,
                     int fileSize ,String image) {
        this.name = name;
        this.url = url;
        this.state = state;
        this.completeSize = completeSize;
        this.fileSize = fileSize;
        this.image =image;
    }

    public FileState() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCompleteSize() {
        return completeSize;
    }

    public void setCompleteSize(int completeSize) {
        this.completeSize = completeSize;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "FileState{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", state=" + state +
                ", completeSize=" + completeSize +
                ", fileSize=" + fileSize +
                '}';
    }
}
