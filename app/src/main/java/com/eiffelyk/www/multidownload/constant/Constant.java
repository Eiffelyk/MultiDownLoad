package com.eiffelyk.www.multidownload.constant;

/**
 *  类名称：Constant 类描述： 常量 创建人：wpy 创建时间：2014-10-13
 * 上午10:23:13
 */
public class Constant {
    /**
     * 下载的url地址
     */
    public static final String URL1 = "http://xz.uzzf.com/wzfcsc_26.zip";
    public static final String URL2 = "http://big.uzzf.com/bj400_90M.zip";

    /**
     * 本地保存地址
     */
    public static final String LOCALPATH = "/mnt/sdcard/myDownloaderDemo/";
    /**
     * 是否限定下载网络环境 true限定，false不限定
     */
    public static final boolean IS_NETWORKTYPE = true;
    /**
     * 是否允许后台下载
     */
    public static final boolean IS_BG_DOWNLOADER =false;
    /**
     * 任务数量
     */
    public static final int DOWNLOADERS = 4;
    /**
     * 单个文件下载的线程数量
     */
    public static final int THREADCOUNT = 3;
    /**
     * 下载管理广播的action
     */
    public static final String DOWNLOADMANAGEACTION = "com.wpy.multithreadeddownload.DownloadManageActivity";
}
