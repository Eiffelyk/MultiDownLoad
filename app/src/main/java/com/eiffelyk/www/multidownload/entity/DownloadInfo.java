package com.eiffelyk.www.multidownload.entity;

/**
 *  类名称：DownloadInfo 类描述： 下载的信息 创建人：wpy
 * 创建时间：2014-10-10 下午3:57:21
 */
public class DownloadInfo {

    private int threadId; //线程id
    private int startPos; //开始位置
    private int endPos;     //结束位置
    private int compeleteSize; //完成大小
    private String url;  //下载路径

    /**
     * 构造函数
     *
     * @param threadId      下载器id/线程id
     * @param startPos             开始下载的节点
     * @param endPos             结束下载的节点
     * @param compeleteSize 完成的进度
     * @param url           下载器网络标识/下载地址
     */
    public DownloadInfo(int threadId, int startPos, int endPos,
                        int compeleteSize, String url) {
        this.threadId = threadId;
        this.startPos = startPos;
        this.endPos = endPos;
        this.compeleteSize = compeleteSize;
        this.url = url;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    public int getCompeleteSize() {
        return compeleteSize;
    }

    public void setCompeleteSize(int compeleteSize) {
        this.compeleteSize = compeleteSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
