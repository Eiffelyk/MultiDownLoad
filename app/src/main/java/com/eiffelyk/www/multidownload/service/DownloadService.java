package com.eiffelyk.www.multidownload.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.eiffelyk.www.multidownload.constant.Constant;
import com.eiffelyk.www.multidownload.db.DbTool;
import com.eiffelyk.www.multidownload.entity.DownloaderInfo;
import com.eiffelyk.www.multidownload.entity.FileState;
import com.eiffelyk.www.multidownload.util.Downloader;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类名称：DownloadService
 * 类描述： 后台下载
 */
public class DownloadService extends Service {

    // 下载器
    private Downloader downloader;

    private DbTool dao;
    /**
     * 存放各个下载器
     */
    private Map<String, Downloader> downloaders = new LinkedHashMap<String, Downloader>();

    /**
     * 存放每个下载文件的总长度
     */
    private Map<String, Integer> fileSizes = new HashMap<String, Integer>();
    /**
     * 存放每个下载文件完成的长度
     */
    private Map<String, Integer> completeSizes = new HashMap<String, Integer>();

    /**
     * 消息处理 接收Download中每个线程传输过来的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                String url = (String) msg.obj;
                int length = msg.arg1;

                int completeSize = completeSizes.get(url);
                completeSize = completeSize + length;
                completeSizes.put(url, completeSize);

                // Log.e("test>>", "消息处理器Handler当前进度：" + completeSize);

                int fileSize = fileSizes.get(url);
                if (completeSize == fileSize) {// 下载完成
                    dao.updataStateByUrl(url);
                    downloaders.get(url).delete(url); //删除数据库中的下载地址
                    downloaders.remove(url);
                    if (downloaders.isEmpty()) {// 如果全部下载完成，关闭service
                        stopSelf();
                    }
                }
                // 发送广播更新下载管理的进度
                Intent intent = new Intent();
                intent.setAction(Constant.DOWNLOADMANAGEACTION);
                intent.putExtra("completeSize", completeSize);
                intent.putExtra("url", url);
                DownloadService.this.sendBroadcast(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        dao = new DbTool(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pauseAll(downloaders);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String urlPath = intent.getStringExtra("downloadUrl");
        String name = intent.getStringExtra("name");
        String flag = intent.getStringExtra("flag");
        String image = intent.getStringExtra("image");
        if (flag.equals("startDownload")) {
            startDownload(name, urlPath, image, true);
        }
        if (flag.equals("changeState")) {
            changeState(name, urlPath, image);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 开始下载
     * @param urlPath 下载地址
     */
    private void startDownload(final String name, final String urlPath,final String image,final boolean isFirst) {
        Log.e("test>>", "文件的名称：" + name);
        Log.e("test>>", "文件的下载地址：" + urlPath);
        // 初始化一个下载器
        downloader = downloaders.get(urlPath);
        if (null == downloader) {
            downloader = new Downloader(name, urlPath, Constant.LOCALPATH,
                    Constant.THREADCOUNT, this, mHandler);
            downloaders.put(urlPath, downloader);
        }
        if (downloader.isDownloading()) {
            return;
        }

        new Thread() {
            public void run() {
                DownloaderInfo downloaderInfo = downloader.getDownloaderInfos();
                if (downloaderInfo!=null){
                    completeSizes.put(urlPath, downloaderInfo.getComplete());

                    if (fileSizes.get(urlPath) == null) {
                        fileSizes.put(urlPath, downloaderInfo.getFileSize());
                    }

                    // FileState state = dao.query(urlPath);
                    if (isFirst) {
                        Log.e("test>>", "文件：" + name + "第一次下载");
                        FileState fileState = new FileState(name, urlPath, 1, downloaderInfo.getComplete(), downloaderInfo.getFileSize(), image);
                        dao.saveFileState(fileState);
                    }
                    downloader.download();
                }
            }
        }.start();
    }

    /**
     * 更改下载状态（若文件正在下载，就暂停；若暂停，则开始下载）
     * @param url 下载地址
     */
    public void changeState(String name, String url,String image) {
        Downloader loader = downloaders.get(url);
        if (loader != null) {
            if (loader.isDownloading()) {// 正在下载
                loader.setPause();
            } else if (loader.isPause()) {// 暂停
                loader.reset();
                this.startDownload(name, url, image, false);
            }
        } else {
            startDownload(name, url,image, false);
        }
    }

    /***
     * 暂停所有下载
     * @param downLoaders 传入需要暂停的的多个下载L器的map
     */

    public void pauseAll(Map<String, Downloader> downLoaders) {
        try {
            for (String s : downLoaders.keySet()) {
                downLoaders.get(s).setPause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
