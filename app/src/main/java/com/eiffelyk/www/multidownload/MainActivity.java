package com.eiffelyk.www.multidownload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.eiffelyk.www.multidownload.adapter.ListAdapter;
import com.eiffelyk.www.multidownload.constant.Constant;
import com.eiffelyk.www.multidownload.entity.DownloadFile;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        ListView listView = (ListView) this.findViewById(R.id.listview);

        // item数据
        List<DownloadFile> files = new ArrayList<DownloadFile>();
        DownloadFile file1 = new DownloadFile(getFileName(Constant.URL1), Constant.URL1 ,"http://img0.bdstatic.com/img/image/shouye/xinshouye/sheying121.jpg");
        files.add(file1);
        DownloadFile file2 = new DownloadFile(getFileName(Constant.URL2), Constant.URL2,"http://img0.bdstatic.com/img/image/shouye/xinshouye/qiche121.jpg");
        files.add(file2);

        LayoutInflater inflater = LayoutInflater.from(this);
        View footerView = inflater.inflate(R.layout.list_item_footeview, null);
        listView.addFooterView(footerView);

        ListAdapter adapter = new ListAdapter(this, files);
        listView.setAdapter(adapter);

        Button btn_manage = (Button) footerView.findViewById(R.id.btn_manage);
        btn_manage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 跳转到下载管理界面
                Intent intent = new Intent(MainActivity.this, DownloadManageActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 根据地址获取文件名称
     *
     * @param path 下载地址
     * @return 下载文件的名称
     */
    private String getFileName(String path) {
        int start = path.lastIndexOf("/") + 1;
        return path.substring(start);
    }
}
